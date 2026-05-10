#include "../std.h"
#include "codegen_arm64.h"
#include "../node.h"

#include <sstream>

namespace {

// AArch64 always uses 8-byte slots for both integer and pointer arguments.
// The shared Node abstraction does not yet expose a target slot-size accessor
// on this branch, so we anchor the value locally in the arm64 backend until
// that becomes available.
static constexpr int kArm64SlotSize = 8;

static string itoa_sgn( int n ){
	if( n==0 ) return "";
	if( n>0 ) return "+"+itoa(n);
	return itoa(n);
}

static bool isFloatCompareOp( int op ){
	return op==IR_FSETEQ || op==IR_FSETNE || op==IR_FSETLT || op==IR_FSETGT || op==IR_FSETLE || op==IR_FSETGE;
}

} // namespace

Codegen_arm64::Codegen_arm64( ostream &out,bool debug ):
	Codegen( out,debug ),section(SEC_NONE),inCode(false),frameAlloc(0),frameLocalAlloc(0),tmpCount(0),seqScratchIntOffset(0),seqScratchFloatOffset(0),retScratchIntOffset(0),retScratchFloatOffset(0),activeCallArgArea(0),emittingStoreDestAddr(false){
}

void Codegen_arm64::switchSection( Section s ){
	if( section==s ) return;
	section=s;
	switch( s ){
	case SEC_TEXT:
		emitLine( "\t.text" );
		break;
	case SEC_DATA:
		emitLine( "\t.data" );
		break;
	default:
		break;
	}
}

void Codegen_arm64::emitLine( const string &line ){
	out<<line<<'\n';
}

void Codegen_arm64::emitLabelIfNeeded( const string &l ){
	if( !l.size() ) return;
	emitLine( mangleSymbol( l )+":" );
}

string Codegen_arm64::nextTmpLabel( const string &prefix ){
	return prefix+"_"+itoa(++tmpCount);
}

bool Codegen_arm64::isFloatNode( TNode *t ) const{
	if( !t ) return false;
	switch( t->op ){
	case IR_SEQ:
		return isFloatNode( t->l );
	case IR_MOVE:
		return isFloatNode( t->l );
	case IR_FCALL:
	case IR_FCAST:
	case IR_FNEG:
	case IR_FADD:
	case IR_FSUB:
	case IR_FMUL:
	case IR_FDIV:
		return true;
	default:
		return false;
	}
}

string Codegen_arm64::toWReg( const string &xReg ) const{
	if( xReg.size()>1 && xReg[0]=='x' ) return "w"+xReg.substr(1);
	if( xReg.size()>1 && xReg[0]=='w' ) return xReg;
	if( xReg.size()>1 && xReg[0]=='s' ) return xReg;
	return xReg;
}

string Codegen_arm64::mangleSymbol( const string &raw ) const{
	if( !raw.size() ) return raw;
	string out;
	if( isdigit( (unsigned char)raw[0] ) ) out.push_back( '_' );
	for( int i=0;i<(int)raw.size();++i ){
		unsigned char c=(unsigned char)raw[i];
		if( isalnum(c) || c=='_' || c=='.' || c=='$' ){
			out.push_back( (char)c );
			continue;
		}
		char buff[8];
		snprintf( buff,sizeof(buff),"_x%02X",(unsigned)c );
		out+=buff;
	}
	return out;
}

void Codegen_arm64::emitLoadImm( const string &reg,int value ){
	unsigned long long v=(unsigned long long)(long long)value;
	if( v==0 ){
		emitLine( "\tmov\t"+reg+", xzr" );
		return;
	}

	bool emitted=false;
	for( int shift=0;shift<=48;shift+=16 ){
		const unsigned part=(unsigned)((v>>shift)&0xffffull);
		if( !part ) continue;
		if( !emitted ){
			emitLine( "\tmovz\t"+reg+", #"+itoa((int)part)+", lsl #"+itoa(shift) );
			emitted=true;
		}else{
			emitLine( "\tmovk\t"+reg+", #"+itoa((int)part)+", lsl #"+itoa(shift) );
		}
	}
	if( !emitted ) emitLine( "\tmov\t"+reg+", xzr" );
}

void Codegen_arm64::emitLoadSymbolAddress( const string &reg,const string &symbol ){
	const string mangled=mangleSymbol( symbol );
	emitLine( "\tadrp\t"+reg+", "+mangled+"@PAGE" );
	emitLine( "\tadd\t"+reg+", "+reg+", "+mangled+"@PAGEOFF" );
}

void Codegen_arm64::emitAdjustRegImm( const string &reg,int imm ){
	if( !imm ) return;
	if( imm>0 ){
		if( imm<=4095 ){
			emitLine( "\tadd\t"+reg+", "+reg+", #"+itoa(imm) );
		}else{
			emitLoadImm( "x17",imm );
			emitLine( "\tadd\t"+reg+", "+reg+", x17" );
		}
	}else{
		int n=-imm;
		if( n<=4095 ){
			emitLine( "\tsub\t"+reg+", "+reg+", #"+itoa(n) );
		}else{
			emitLoadImm( "x17",n );
			emitLine( "\tsub\t"+reg+", "+reg+", x17" );
		}
	}
}

void Codegen_arm64::emitAddrOf( TNode *t,const string &dst ){
	if( !t ){
		emitLine( "\tmov\t"+dst+", xzr" );
		return;
	}
	switch( t->op ){
	case IR_GLOBAL:
		emitLoadSymbolAddress( dst,t->sconst );
		return;
	case IR_LOCAL:
		emitLine( "\tmov\t"+dst+", x29" );
		emitAdjustRegImm( dst,t->iconst );
		return;
	case IR_ARG:
	{
		// During argument marshaling, IR_ARG destinations are offsets into
		// the outgoing stack argument area rooted at SP. In all other cases
		// IR_ARG refers to incoming parameters rooted at FP.
		if( emittingStoreDestAddr && activeCallArgArea>0 && t->iconst>=0 && t->iconst<activeCallArgArea ){
			emitLine( "\tmov\t"+dst+", sp" );
		}else{
			emitLine( "\tmov\t"+dst+", x29" );
		}
		emitAdjustRegImm( dst,t->iconst );
		return;
	}
	case IR_MEM:
		emitIntExpr( t->l,dst );
		return;
	default:
		emitIntExpr( t,dst );
		return;
	}
}

void Codegen_arm64::emitIntExpr( TNode *t,const string &dst ){
	if( !t ){
		emitLine( "\tmov\t"+dst+", xzr" );
		return;
	}

	switch( t->op ){
	case IR_SEQ:
		emitIntExpr( t->l,dst );
		if( t->r ){
			emitStoreIntToScratch( dst );
			emitStmt( t->r );
			emitLoadIntFromScratch( dst );
		}
		return;
	case IR_MOVE:
		emitStore( t->l,t->r );
		return;
	case IR_CONST:
		emitLoadImm( dst,t->iconst );
		return;
	case IR_GLOBAL:
	case IR_LOCAL:
	case IR_ARG:
		emitAddrOf( t,dst );
		return;
	case IR_MEM:
		emitAddrOf( t->l,"x10" );
		emitSafeLoadInt( dst,"x10" );
		return;
	case IR_CAST:
		emitFloatExpr( t->l,"s0" );
		emitLine( "\tfcvtzs\t"+dst+", s0" );
		return;
	case IR_CALL:
		emitCall( t,false );
		emitLine( "\tmov\t"+dst+", x0" );
		return;
	case IR_NEG:
		emitIntExpr( t->l,dst );
		emitLine( "\tneg\t"+dst+", "+dst );
		return;
	case IR_ADD:
		emitIntExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="x11" ? "x12" : "x11";
			emitIntExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tadd\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_SUB:
		emitIntExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="x11" ? "x12" : "x11";
			emitIntExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tsub\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_MUL:
		emitIntExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="x11" ? "x12" : "x11";
			emitIntExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tmul\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_DIV:
		emitIntExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="x11" ? "x12" : "x11";
			emitIntExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tsdiv\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_AND:
		emitIntExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="x11" ? "x12" : "x11";
			emitIntExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tand\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_OR:
		emitIntExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="x11" ? "x12" : "x11";
			emitIntExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\torr\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_XOR:
		emitIntExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="x11" ? "x12" : "x11";
			emitIntExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\teor\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_SHL:
		emitIntExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="x11" ? "x12" : "x11";
			emitIntExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tlsl\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_SHR:
		emitIntExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="x11" ? "x12" : "x11";
			emitIntExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tlsr\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_SAR:
		emitIntExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="x11" ? "x12" : "x11";
			emitIntExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tasr\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_SETEQ:
	case IR_SETNE:
	case IR_SETLT:
	case IR_SETGT:
	case IR_SETLE:
		case IR_SETGE:
			{
				emitIntExpr( t->l,dst );
				emitLine( "\tsub\tsp, sp, #16" );
				emitLine( "\tstr\t"+dst+", [sp]" );
				const string rhs=dst=="x11" ? "x12" : "x11";
				emitIntExpr( t->r,rhs );
				emitLine( "\tldr\t"+dst+", [sp]" );
				emitLine( "\tadd\tsp, sp, #16" );
				emitLine( "\tcmp\t"+dst+", "+rhs );
				string cc="eq";
		switch( t->op ){
		case IR_SETNE:cc="ne";break;
		case IR_SETLT:cc="lt";break;
		case IR_SETGT:cc="gt";break;
		case IR_SETLE:cc="le";break;
		case IR_SETGE:cc="ge";break;
		default:break;
		}
		const string wdst=toWReg(dst);
		emitLine( "\tcset\t"+wdst+", "+cc );
		emitLine( "\tuxtw\t"+dst+", "+wdst );
		return;
	}
	case IR_FSETEQ:
	case IR_FSETNE:
	case IR_FSETLT:
	case IR_FSETGT:
	case IR_FSETLE:
	case IR_FSETGE:
	{
		emitFloatExpr( t->l,"s0" );
		emitLine( "\tsub\tsp, sp, #16" );
		emitLine( "\tstr\ts0, [sp]" );
		emitFloatExpr( t->r,"s1" );
		emitLine( "\tldr\ts0, [sp]" );
		emitLine( "\tadd\tsp, sp, #16" );
		emitLine( "\tfcmp\ts0, s1" );
		string cc="eq";
		switch( t->op ){
		case IR_FSETNE:cc="ne";break;
		case IR_FSETLT:cc="lt";break;
		case IR_FSETGT:cc="gt";break;
		case IR_FSETLE:cc="le";break;
		case IR_FSETGE:cc="ge";break;
		default:break;
		}
		const string wdst=toWReg(dst);
		emitLine( "\tcset\t"+wdst+", "+cc );
		emitLine( "\tuxtw\t"+dst+", "+wdst );
		return;
	}
	default:
		emitLine( "\t// arm64 backend unsupported integer op="+itoa(t->op) );
		emitLine( "\t.error\t\"arm64 backend unsupported integer IR op "+itoa(t->op)+"\"" );
		emitLine( "\tmov\t"+dst+", xzr" );
		return;
	}
}

void Codegen_arm64::emitFloatExpr( TNode *t,const string &dst ){
	if( !t ){
		emitLine( "\tfmov\t"+dst+", wzr" );
		return;
	}

	switch( t->op ){
	case IR_SEQ:
		emitFloatExpr( t->l,dst );
		if( t->r ){
			emitStoreFloatToScratch( dst );
			emitStmt( t->r );
			emitLoadFloatFromScratch( dst );
		}
		return;
	case IR_MOVE:
		if( t->r && t->r->op==IR_MEM ){
			emitFloatExpr( t->l,dst );
			emitAddrOf( t->r->l,"x10" );
			emitSafeStoreFloat( dst,"x10" );
		}else{
			emitStore( t->l,t->r );
			emitFloatExpr( t->l,dst );
		}
		return;
	case IR_FCALL:
		emitCall( t,true );
		if( dst!="s0" ) emitLine( "\tfmov\t"+dst+", s0" );
		return;
	case IR_FCAST:
		emitIntExpr( t->l,"x9" );
		emitLine( "\tscvtf\t"+dst+", x9" );
		return;
	case IR_FNEG:
		emitFloatExpr( t->l,dst );
		emitLine( "\tfneg\t"+dst+", "+dst );
		return;
	case IR_FADD:
		emitFloatExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="s1" ? "s2" : "s1";
			emitFloatExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tfadd\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_FSUB:
		emitFloatExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="s1" ? "s2" : "s1";
			emitFloatExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tfsub\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_FMUL:
		emitFloatExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="s1" ? "s2" : "s1";
			emitFloatExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tfmul\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_FDIV:
		emitFloatExpr( t->l,dst );
		{
			emitLine( "\tsub\tsp, sp, #16" );
			emitLine( "\tstr\t"+dst+", [sp]" );
			const string rhs=dst=="s1" ? "s2" : "s1";
			emitFloatExpr( t->r,rhs );
			emitLine( "\tldr\t"+dst+", [sp]" );
			emitLine( "\tadd\tsp, sp, #16" );
			emitLine( "\tfdiv\t"+dst+", "+dst+", "+rhs );
		}
		return;
	case IR_MEM:
		emitAddrOf( t->l,"x10" );
		emitSafeLoadFloat( dst,"x10" );
		return;
	default:
		emitIntExpr( t,"x9" );
		emitLine( "\tfmov\t"+dst+", w9" );
		return;
	}
}

void Codegen_arm64::emitCall( TNode *t,bool floatRet ){
	const int slot=kArm64SlotSize;
	int argc=t ? (t->iconst/slot) : 0;
	if( argc<0 ) argc=0;
	int argArea=0;
	if( argc>0 ){
		argArea=argc*slot;
		if( argArea<16 ) argArea=16;
		argArea=(argArea+15)&~15;
		emitAdjustRegImm( "sp",-argArea );
	}
	const int prevArgArea=activeCallArgArea;
	activeCallArgArea=argArea;
	if( t && t->r ) emitStmt( t->r );
	activeCallArgArea=prevArgArea;

	int regArgs=argc;
	if( regArgs>8 ) regArgs=8;
	for( int i=0;i<regArgs;++i ){
		emitLine( "\tldr\tx"+itoa(i)+", [sp, #"+itoa(i*slot)+"]" );
	}

	if( t && t->l && t->l->op==IR_GLOBAL ){
		emitLine( "\tbl\t"+mangleSymbol( t->l->sconst ) );
	}else if( t && t->l ){
		emitIntExpr( t->l,"x16" );
		emitLine( "\tblr\tx16" );
	}else{
		emitLine( "\t// call target missing" );
	}
	if( argArea>0 ) emitAdjustRegImm( "sp",argArea );

	if( floatRet ){
		emitLine( "\t// float return in s0" );
	}
}

void Codegen_arm64::emitStoreIntToScratch( const string &srcReg ){
	emitLine( "\tmov\tx10, x29" );
	emitAdjustRegImm( "x10",seqScratchIntOffset );
	emitLine( "\tstr\t"+srcReg+", [x10]" );
}

void Codegen_arm64::emitLoadIntFromScratch( const string &dstReg ){
	emitLine( "\tmov\tx10, x29" );
	emitAdjustRegImm( "x10",seqScratchIntOffset );
	emitLine( "\tldr\t"+dstReg+", [x10]" );
}

void Codegen_arm64::emitStoreFloatToScratch( const string &srcReg ){
	emitLine( "\tmov\tx10, x29" );
	emitAdjustRegImm( "x10",seqScratchFloatOffset );
	emitLine( "\tstr\t"+srcReg+", [x10]" );
}

void Codegen_arm64::emitLoadFloatFromScratch( const string &dstReg ){
	emitLine( "\tmov\tx10, x29" );
	emitAdjustRegImm( "x10",seqScratchFloatOffset );
	emitLine( "\tldr\t"+dstReg+", [x10]" );
}

void Codegen_arm64::emitSafeLoadInt( const string &dstReg,const string &addrReg ){
	const string loadLabel=nextTmpLabel( "arm64_ld_int" );
	const string badLabel=nextTmpLabel( "arm64_ld_int_bad" );
	const string doneLabel=nextTmpLabel( "arm64_ld_int_done" );
	emitLine( "\tcmp\t"+addrReg+", #0x10000" );
	emitLine( "\tb.hs\t"+mangleSymbol( loadLabel ) );
	emitLine( "\tb\t"+mangleSymbol( badLabel ) );
	label( loadLabel );
	emitLine( "\tlsr\tx14, "+addrReg+", #32" );
	emitLine( "\tcbz\tx14, "+mangleSymbol( badLabel ) );
	emitLine( "\tlsr\tx14, "+addrReg+", #36" );
	emitLine( "\tcbnz\tx14, "+mangleSymbol( badLabel ) );
	emitLine( "\tldr\t"+dstReg+", ["+addrReg+"]" );
	emitLine( "\tb\t"+mangleSymbol( doneLabel ) );
	label( badLabel );
	emitLine( "\tmov\t"+dstReg+", xzr" );
	label( doneLabel );
}

void Codegen_arm64::emitSafeLoadFloat( const string &dstReg,const string &addrReg ){
	const string loadLabel=nextTmpLabel( "arm64_ld_flt" );
	const string badLabel=nextTmpLabel( "arm64_ld_flt_bad" );
	const string doneLabel=nextTmpLabel( "arm64_ld_flt_done" );
	emitLine( "\tcmp\t"+addrReg+", #0x10000" );
	emitLine( "\tb.hs\t"+mangleSymbol( loadLabel ) );
	emitLine( "\tb\t"+mangleSymbol( badLabel ) );
	label( loadLabel );
	emitLine( "\tlsr\tx14, "+addrReg+", #32" );
	emitLine( "\tcbz\tx14, "+mangleSymbol( badLabel ) );
	emitLine( "\tlsr\tx14, "+addrReg+", #36" );
	emitLine( "\tcbnz\tx14, "+mangleSymbol( badLabel ) );
	emitLine( "\tldr\t"+dstReg+", ["+addrReg+"]" );
	emitLine( "\tb\t"+mangleSymbol( doneLabel ) );
	label( badLabel );
	emitLine( "\tfmov\t"+dstReg+", wzr" );
	label( doneLabel );
}

void Codegen_arm64::emitSafeStoreInt( const string &srcReg,const string &addrReg ){
	const string doneLabel=nextTmpLabel( "arm64_st_int_done" );
	const string checkUpperLabel=nextTmpLabel( "arm64_st_int_chk" );
	emitLine( "\tcmp\t"+addrReg+", #0x10000" );
	emitLine( "\tb.hs\t"+mangleSymbol( checkUpperLabel ) );
	emitLine( "\tb\t"+mangleSymbol( doneLabel ) );
	label( checkUpperLabel );
	emitLine( "\tlsr\tx14, "+addrReg+", #32" );
	emitLine( "\tcbz\tx14, "+mangleSymbol( doneLabel ) );
	emitLine( "\tlsr\tx14, "+addrReg+", #36" );
	emitLine( "\tcbnz\tx14, "+mangleSymbol( doneLabel ) );
	emitLine( "\tstr\t"+srcReg+", ["+addrReg+"]" );
	label( doneLabel );
}

void Codegen_arm64::emitSafeStoreFloat( const string &srcReg,const string &addrReg ){
	const string doneLabel=nextTmpLabel( "arm64_st_flt_done" );
	const string checkUpperLabel=nextTmpLabel( "arm64_st_flt_chk" );
	emitLine( "\tcmp\t"+addrReg+", #0x10000" );
	emitLine( "\tb.hs\t"+mangleSymbol( checkUpperLabel ) );
	emitLine( "\tb\t"+mangleSymbol( doneLabel ) );
	label( checkUpperLabel );
	emitLine( "\tlsr\tx14, "+addrReg+", #32" );
	emitLine( "\tcbz\tx14, "+mangleSymbol( doneLabel ) );
	emitLine( "\tlsr\tx14, "+addrReg+", #36" );
	emitLine( "\tcbnz\tx14, "+mangleSymbol( doneLabel ) );
	emitLine( "\tstr\t"+srcReg+", ["+addrReg+"]" );
	label( doneLabel );
}

void Codegen_arm64::emitStore( TNode *src,TNode *dest ){
	if( !dest || dest->op!=IR_MEM ){
		emitLine( "\t// non-memory destination in IR_MOVE" );
		return;
	}
	const bool simpleArgDest=dest->l && dest->l->op==IR_ARG;
	// Evaluate source first so SP-relative destination addresses are computed after
	// any nested-call argument shuffling.
	if( isFloatNode( src ) ){
		emitFloatExpr( src,"s0" );
		if( simpleArgDest ){
			emitLine( "\tfmov\tw26, s0" );
		}else{
			emitAdjustRegImm( "sp",-16 );
			emitLine( "\tstr\ts0, [sp]" );
		}
		const bool prevStoreAddr=emittingStoreDestAddr;
		emittingStoreDestAddr=true;
		emitAddrOf( dest->l,"x24" );
		emittingStoreDestAddr=prevStoreAddr;
		if( simpleArgDest ){
			emitLine( "\tfmov\ts0, w26" );
		}else{
			emitLine( "\tldr\ts0, [sp]" );
			emitAdjustRegImm( "sp",16 );
		}
		emitSafeStoreFloat( "s0","x24" );
	}else{
		if( simpleArgDest ){
			emitIntExpr( src,"x26" );
		}else{
			emitIntExpr( src,"x25" );
			emitAdjustRegImm( "sp",-16 );
			emitLine( "\tstr\tx25, [sp]" );
		}
		const bool prevStoreAddr=emittingStoreDestAddr;
		emittingStoreDestAddr=true;
		emitAddrOf( dest->l,"x24" );
		emittingStoreDestAddr=prevStoreAddr;
		if( simpleArgDest ){
			emitSafeStoreInt( "x26","x24" );
		}else{
			emitLine( "\tldr\tx25, [sp]" );
			emitAdjustRegImm( "sp",16 );
			emitSafeStoreInt( "x25","x24" );
		}
	}
}

void Codegen_arm64::emitStmt( TNode *t ){
	if( !t ) return;

	switch( t->op ){
	case IR_SEQ:
		emitStmt( t->l );
		emitStmt( t->r );
		return;
	case IR_MOVE:
		emitStore( t->l,t->r );
		return;
	case IR_CALL:
		emitCall( t,false );
		return;
	case IR_FCALL:
		emitCall( t,true );
		return;
	case IR_JSR:
	{
		emitLine( "\tbl\t"+mangleSymbol( t->sconst ) );
		return;
	}
	case IR_JUMP:
		emitLine( "\tb\t"+mangleSymbol( t->sconst ) );
		return;
	case IR_JUMPT:
	{
		const string skip=nextTmpLabel( ".Ljumpf_skip" );
		emitIntExpr( t->l,"x9" );
		emitLine( "\tcmp\tx9, #0" );
		emitLine( "\tb.eq\t"+skip );
		emitLine( "\tb\t"+mangleSymbol( t->sconst ) );
		emitLabelIfNeeded( skip );
		return;
	}
	case IR_JUMPF:
	{
		const string skip=nextTmpLabel( ".Ljumpt_skip" );
		emitIntExpr( t->l,"x9" );
		emitLine( "\tcmp\tx9, #0" );
		emitLine( "\tb.ne\t"+skip );
		emitLine( "\tb\t"+mangleSymbol( t->sconst ) );
		emitLabelIfNeeded( skip );
		return;
	}
	case IR_JUMPGE:
	{
		const string skip=nextTmpLabel( ".Ljumplt_skip" );
		emitIntExpr( t->l,"x9" );
		emitIntExpr( t->r,"x10" );
		emitLine( "\tcmp\tx9, x10" );
		emitLine( "\tb.lo\t"+skip );
		emitLine( "\tb\t"+mangleSymbol( t->sconst ) );
		emitLabelIfNeeded( skip );
		return;
	}
	case IR_RETURN:
		emitIntExpr( t->l,"x0" );
		emitLine( "\tb\t"+mangleSymbol( t->sconst ) );
		return;
	case IR_FRETURN:
		emitFloatExpr( t->l,"s0" );
		emitLine( "\tb\t"+mangleSymbol( t->sconst ) );
		return;
	case IR_RET:
		emitLine( "\tret" );
		return;
	default:
		if( isFloatNode( t ) || isFloatCompareOp( t->op ) ) emitFloatExpr( t,"s0" );
		else emitIntExpr( t,"x9" );
		return;
	}
}

string Codegen_arm64::escapeString( const string &s ) const{
	string out;
	for( int i=0;i<(int)s.size();++i ){
		unsigned char c=(unsigned char)s[i];
		switch( c ){
		case '\\':out+="\\\\";break;
		case '"':out+="\\\"";break;
		case '\n':out+="\\n";break;
		case '\r':out+="\\r";break;
		case '\t':out+="\\t";break;
		default:
			if( c<32 || c>126 ){
				char buff[8];
				snprintf( buff,sizeof(buff),"\\x%02X",(unsigned)c );
				out+=buff;
			}else out.push_back( (char)c );
			break;
		}
	}
	return out;
}

void Codegen_arm64::enter( const string &l,int frameSize ){
	switchSection( SEC_TEXT );
	inCode=true;
	leaveLabel.clear();
	frameLocalAlloc=((frameSize+15)/16)*16;
	frameAlloc=frameLocalAlloc+64;
	seqScratchIntOffset=-frameLocalAlloc-8;
	seqScratchFloatOffset=-frameLocalAlloc-16;
	retScratchIntOffset=-frameLocalAlloc-24;
	retScratchFloatOffset=-frameLocalAlloc-32;
	activeCallArgArea=0;
	emittingStoreDestAddr=false;

	emitLine( "\t.p2align\t2" );
	const string symbol=mangleSymbol( l );
	emitLine( "\t.globl\t"+symbol );
	emitLine( symbol+":" );
	emitLine( "\tstp\tx29, x30, [sp, #-16]!" );
	emitLine( "\tmov\tx29, sp" );
	if( frameAlloc>0 ) emitAdjustRegImm( "sp",-frameAlloc );
	emitLine( "\tmov\tx10, x29" );
	emitAdjustRegImm( "x10",-frameLocalAlloc-48 );
	emitLine( "\tstp\tx24, x25, [x10]" );
	emitLine( "\tmov\tx10, x29" );
	emitAdjustRegImm( "x10",-frameLocalAlloc-64 );
	emitLine( "\tstp\tx26, x27, [x10]" );
}

void Codegen_arm64::code( TNode *codeTree ){
	switchSection( SEC_TEXT );
	emitStmt( codeTree );
}

void Codegen_arm64::leave( TNode *cleanup,int pop_sz ){
	(void)pop_sz;
	switchSection( SEC_TEXT );
	if( cleanup ){
		emitLine( "\tmov\tx10, x29" );
		emitAdjustRegImm( "x10",retScratchIntOffset );
		emitLine( "\tstr\tx0, [x10]" );
		emitLine( "\tmov\tx10, x29" );
		emitAdjustRegImm( "x10",retScratchFloatOffset );
		emitLine( "\tstr\ts0, [x10]" );
		emitStmt( cleanup );
		emitLine( "\tmov\tx10, x29" );
		emitAdjustRegImm( "x10",retScratchIntOffset );
		emitLine( "\tldr\tx0, [x10]" );
		emitLine( "\tmov\tx10, x29" );
		emitAdjustRegImm( "x10",retScratchFloatOffset );
		emitLine( "\tldr\ts0, [x10]" );
	}
	emitLine( "\tmov\tx10, x29" );
	emitAdjustRegImm( "x10",-frameLocalAlloc-64 );
	emitLine( "\tldp\tx26, x27, [x10]" );
	emitLine( "\tmov\tx10, x29" );
	emitAdjustRegImm( "x10",-frameLocalAlloc-48 );
	emitLine( "\tldp\tx24, x25, [x10]" );
	if( frameAlloc>0 ) emitAdjustRegImm( "sp",frameAlloc );
	emitLine( "\tldp\tx29, x30, [sp], #16" );
	emitLine( "\tret" );
	inCode=false;
}

void Codegen_arm64::label( const string &l ){
	emitLabelIfNeeded( l );
}

void Codegen_arm64::i_data( int i,const string &l ){
	switchSection( SEC_DATA );
	if( l.size() && kArm64SlotSize>=8 ) emitLine( "\t.balign\t8" );
	emitLabelIfNeeded( l );
	if( kArm64SlotSize>=8 ) emitLine( "\t.quad\t"+itoa(i) );
	else emitLine( "\t.long\t"+itoa(i) );
}

void Codegen_arm64::s_data( const string &s,const string &l ){
	switchSection( SEC_DATA );
	emitLabelIfNeeded( l );
	emitLine( "\t.asciz\t\""+escapeString(s)+"\"" );
}

void Codegen_arm64::p_data( const string &p,const string &l ){
	switchSection( SEC_DATA );
	if( kArm64SlotSize>=8 ) emitLine( "\t.balign\t8" );
	emitLabelIfNeeded( l );
	if( p.size() ){
		emitLine( "\t.quad\t"+mangleSymbol( p ) );
	}else{
		emitLine( "\t.quad\t0" );
	}
}

void Codegen_arm64::align_data( int n ){
	switchSection( SEC_DATA );
	int align=n;
	if( kArm64SlotSize>=8 && align<8 ) align=8;
	emitLine( "\t.balign\t"+itoa(align) );
}

void Codegen_arm64::flush(){
	emitLine( "" );
}
