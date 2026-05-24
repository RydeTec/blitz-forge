
#include "std.h"
#include "bbbank.h"
#include "bbstream.h"

struct bbBank{
	char *data;
	int size,capacity;

	bbBank( int sz ):size(sz){
		capacity=(size+15)&~15;
		data=d_new char[capacity];
		memset( data,0,size );
	}
	virtual ~bbBank(){
		delete[] data;
	}
	void resize( int n ){
		if( n>size ){
			if( n>capacity ){
				capacity=capacity*3/2;
				if( n>capacity ) capacity=n;
				capacity=(capacity+15)&~15;
				char *p=d_new char[capacity];
				memcpy( p,data,size );
				delete[] data;
				data=p;
			}else memset( data+size,0,n-size );
		}
		size=n;
	}
};

static set<bbBank*> bank_set;

static inline bool debugBank( bbBank *b,const char* a ){
	if( debug ){
		if( !bank_set.count( b ) ) {
			RTEX( "bbBank does not exist" );
			return false;
		}
	} else {
		if( !bank_set.count( b ) ) {
			errorLog.push_back( std::string(a)+std::string(": bbBank does not exist"));
			return false;
		}
	}
	return true;
}

static inline bool debugBank( bbBank *b,int offset,const char* a ){
	// Negative offsets used to skip the bound check entirely (the size
	// comparison is signed; a negative `offset` is < `b->size` so passes).
	// Combined with `bbCopyBank`'s `src_p+count-1` arithmetic that meant
	// `count <= 0` could feed `memmove` a wrapped huge size_t and copy
	// gigabytes -- arbitrary read/write from inside a Blitz program.
	// Reject negative offsets up front.
	if( debug ){
		if (!debugBank( b,"" )) {
			return false;
		}
		if( offset<0 || offset>=b->size ) {
			RTEX( "Offset out of range" );
			return false;
		}
	} else {
		if (!debugBank( b,a )) {
			return false;
		}
		if( offset<0 || offset>=b->size ) {
			errorLog.push_back( std::string(a)+std::string(": Offset out of range"));
			return false;
		}
	}
	return true;
}

bbBank *bbCreateBank( int size ){
	bbBank *b=d_new bbBank( size );
	bank_set.insert( b );
	return b;
}

void bbFreeBank( bbBank *b ){
	if( bank_set.erase( b ) ) delete b;
}

int bbBankSize( bbBank *b ){
	if (!debugBank( b,"BankSize" )) return 0;
	return b->size;
}

void  bbResizeBank( bbBank *b,int size ){
	if (!debugBank( b,"ResizeBank" )) return;
	b->resize( size );
}

void  bbCopyBank( bbBank *src,int src_p,bbBank *dest,int dest_p,int count ){
	// Round 4 audit: this used to validate `src_p+count-1` and
	// `dest_p+count-1` directly. A negative `count` produced a negative
	// composite that bypassed the bound check (since
	// `src_p+count-1 < b->size` trivially holds for huge negatives), and
	// `memmove(..., count)` casts count to size_t -- so negative count
	// becomes a huge positive copy size and reads/writes far past either
	// bank, an arbitrary read/write primitive from inside a Blitz
	// program. Reject up front.
	if( count <= 0 ) return;
	if (!debugBank( src,src_p,"CopyBank (src)" )) return;
	if (!debugBank( src,src_p+count-1,"CopyBank (src)" )) return;
	if (!debugBank( dest,dest_p,"CopyBank (dest)")) return;
	if (!debugBank( dest,dest_p+count-1,"CopyBank (dest)")) return;
	memmove( dest->data+dest_p,src->data+src_p,count );
}

int  bbPeekByte( bbBank *b,int offset ){
	if (!debugBank( b,offset,"PeekByte")) return 0;
	return *(unsigned char*)(b->data+offset);
}

// Peek/Poke use memcpy rather than direct misaligned dereference. A bank
// stores raw bytes at arbitrary offsets, so reading `*(int*)(data+offset)`
// is unaligned for almost every offset that isn't a multiple of 4. x86
// allows unaligned loads silently; ARM64 (and stricter platforms) can
// trap. memcpy compiles to the same single load/store on platforms that
// support unaligned access and to a safe byte-by-byte read where they
// don't.

int  bbPeekShort( bbBank *b,int offset ){
	if (!debugBank( b,offset+1,"PeekShort")) return 0;
	unsigned short v;
	memcpy( &v,b->data+offset,sizeof v );
	return v;
}

int  bbPeekInt( bbBank *b,int offset ){
	if (!debugBank( b,offset+3,"PeekInt")) return 0;
	int v;
	memcpy( &v,b->data+offset,sizeof v );
	return v;
}

float  bbPeekFloat( bbBank *b,int offset ){
	if (!debugBank( b,offset+3,"PeekFloat")) return 0;
	float v;
	memcpy( &v,b->data+offset,sizeof v );
	return v;
}

void  bbPokeByte( bbBank *b,int offset,int value ){
	if (!debugBank( b,offset,"PokeByte")) return;
	*(char*)(b->data+offset)=value;
}

void  bbPokeShort( bbBank *b,int offset,int value ){
	if (!debugBank( b,offset+1,"PokeShort")) return;
	unsigned short v = static_cast<unsigned short>(value);
	memcpy( b->data+offset,&v,sizeof v );
}

void  bbPokeInt( bbBank *b,int offset,int value ){
	if (!debugBank( b,offset+3,"PokeInt")) return;
	memcpy( b->data+offset,&value,sizeof value );
}

void  bbPokeFloat( bbBank *b,int offset,float value ){
	if (!debugBank( b,offset+3,"PokeFloat")) return;
	memcpy( b->data+offset,&value,sizeof value );
}

int   bbReadBytes( bbBank *b,bbStream *s,int offset,int count ){
	//if( debug ){
	if (!debugBank( b,offset+count-1,"ReadBytes" )) return 0;
	if (!debugStream( s,"ReadBytes" )) return 0;
	//}
	return s->read( b->data+offset,count );
}

int   bbWriteBytes( bbBank *b,bbStream *s,int offset,int count ){
	//if( debug ){
	if (!debugBank( b,offset+count-1,"WriteBytes" )) return 0;
	if (!debugStream( s,"WriteBytes" )) return 0;
	//}
	return s->write( b->data+offset,count );
}

int  bbCallDLL( BBStr *dll,BBStr *fun,bbBank *in,bbBank *out ){
	//if( debug ){
	if( in ) { if(!debugBank( in,"CallDLL (in)" )) return 0; }
	if( out ) { if(!debugBank( out,"CallDLL (out)" )) return 0; }
	//}
	int t=gx_runtime->callDll( *dll,*fun,
		in ? in->data : 0,in ? in->size : 0,
		out ? out->data : 0,out ? out->size : 0 );
	delete dll;delete fun;
	return t;
}

bool bank_create(){
	return true;
}

bool bank_destroy(){
	while( bank_set.size() ) bbFreeBank( *bank_set.begin() );
	return true;
}

void bank_link( void(*rtSym)(const char*,void*) ){
	rtSym( "(BBBank)CreateBank%size=0",bbCreateBank );
	rtSym( "FreeBank(BBBank)bank",bbFreeBank );
	rtSym( "%BankSize(BBBank)bank",bbBankSize );
	rtSym( "ResizeBank(BBBank)bank%size",bbResizeBank );
	rtSym( "CopyBank(BBBank)src_bank%src_offset(BBBank)dest_bank%dest_offset%count",bbCopyBank );
	rtSym( "%PeekByte(BBBank)bank%offset",bbPeekByte );
	rtSym( "%PeekShort(BBBank)bank%offset",bbPeekShort );
	rtSym( "%PeekInt(BBBank)bank%offset",bbPeekInt );
	rtSym( "#PeekFloat(BBBank)bank%offset",bbPeekFloat );
	rtSym( "PokeByte(BBBank)bank%offset%value",bbPokeByte );
	rtSym( "PokeShort(BBBank)bank%offset%value",bbPokeShort );
	rtSym( "PokeInt(BBBank)bank%offset%value",bbPokeInt );
	rtSym( "PokeFloat(BBBank)bank%offset#value",bbPokeFloat );
	rtSym( "%ReadBytes(BBBank)bank(BBStream)file%offset%count",bbReadBytes );
	rtSym( "%WriteBytes(BBBank)bank(BBStream)file%offset%count",bbWriteBytes );
	rtSym( "%CallDLL$dll_name$func_name(BBBank)in_bank=Null(BBBank)out_bank=Null",bbCallDLL );
}


