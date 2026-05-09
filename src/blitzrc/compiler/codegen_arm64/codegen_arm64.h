#ifndef CODEGEN_ARM64_H
#define CODEGEN_ARM64_H

#include "../codegen.h"

class Codegen_arm64 : public Codegen{
public:
	Codegen_arm64( ostream &out,bool debug );

	virtual void enter( const string &l,int frameSize );
	virtual void code( TNode *code );
	virtual void leave( TNode *cleanup,int pop_sz );
	virtual void label( const string &l );
	virtual void i_data( int i,const string &l );
	virtual void s_data( const string &s,const string &l );
	virtual void p_data( const string &p,const string &l );
	virtual void align_data( int n );
	virtual void flush();

private:
	enum Section{
		SEC_NONE,
		SEC_TEXT,
		SEC_DATA
	};

	Section section;
	bool inCode;
	int frameAlloc;
	int frameLocalAlloc;
	int tmpCount;
	string leaveLabel;
	int seqScratchIntOffset;
	int seqScratchFloatOffset;
	int retScratchIntOffset;
	int retScratchFloatOffset;
	int activeCallArgArea;
	bool emittingStoreDestAddr;

	void switchSection( Section s );
	void emitLine( const string &line );
	void emitLabelIfNeeded( const string &l );
	string nextTmpLabel( const string &prefix );

	bool isFloatNode( TNode *t ) const;
	string toWReg( const string &xReg ) const;
	string mangleSymbol( const string &raw ) const;
	void emitLoadImm( const string &reg,int value );
	void emitLoadSymbolAddress( const string &reg,const string &symbol );
	void emitAdjustRegImm( const string &reg,int imm );
	void emitAddrOf( TNode *t,const string &dst );
	void emitIntExpr( TNode *t,const string &dst );
	void emitFloatExpr( TNode *t,const string &dst );
	void emitCall( TNode *t,bool floatRet );
	void emitStore( TNode *src,TNode *dest );
	void emitStmt( TNode *t );
	void emitStoreIntToScratch( const string &srcReg );
	void emitLoadIntFromScratch( const string &dstReg );
	void emitStoreFloatToScratch( const string &srcReg );
	void emitLoadFloatFromScratch( const string &dstReg );
	void emitSafeLoadInt( const string &dstReg,const string &addrReg );
	void emitSafeLoadFloat( const string &dstReg,const string &addrReg );
	void emitSafeStoreInt( const string &srcReg,const string &addrReg );
	void emitSafeStoreFloat( const string &srcReg,const string &addrReg );

	string escapeString( const string &s ) const;
};

#endif
