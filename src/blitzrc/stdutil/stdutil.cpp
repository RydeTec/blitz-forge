
#include "stdutil.h"
#include "platform.h"

#include <set>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifdef _WIN32
#include <windows.h>
#endif

using namespace std;

namespace {

// Portable replacement for the MSVC `_itoa_s` helper. Always writes a
// null-terminated decimal representation of `value` (base 10 only) and never
// overruns `bufSize`.
void portable_itoa10( int value, char *buf, size_t bufSize ){
	snprintf( buf, bufSize, "%d", value );
}

// Portable equivalent of `_ecvt_s` — produces a string of `digits` digits
// representing `value`, with `*dec` set to the decimal-point position relative
// to the start of the digit string and `*sign` set when `value` is negative.
// The output string is always null-terminated.
//
// Implemented using `snprintf` with `%e` so it works on every host without
// depending on the deprecated POSIX `ecvt` routine.
void portable_ecvt( char *buf, size_t bufSize, double value, int digits, int *dec, int *sign ){
	if( bufSize==0 ) return;
	buf[0]=0;
	*dec=0;
	*sign=0;
	if( digits<1 ) digits=1;

	char tmp[64];
	snprintf( tmp,sizeof(tmp),"%.*e",digits-1,value );

	size_t i=0;
	if( tmp[i]=='-' ){ *sign=1; ++i; }
	else if( tmp[i]=='+' ){ ++i; }

	size_t o=0;
	bool sawDot=false;
	while( tmp[i] && tmp[i]!='e' && tmp[i]!='E' && o+1<bufSize ){
		if( tmp[i]=='.' ){ sawDot=true; ++i; continue; }
		buf[o++]=tmp[i++];
	}
	buf[o]=0;
	(void)sawDot;

	if( tmp[i]=='e' || tmp[i]=='E' ){
		int exp=atoi( &tmp[i+1] );
		*dec=exp+1;
	}
}

// Portable equivalent of `_gcvt_s` using `%g` formatting.
void portable_gcvt( char *buf, size_t bufSize, double value, int digits ){
	if( bufSize==0 ) return;
	if( digits<1 ) digits=1;
	snprintf( buf,bufSize,"%.*g",digits,value );
}

} // namespace

#ifdef MEMDEBUG

struct Mem{
	Mem *next,*prev;
	const char *file;
	int line,size,tag;
};

static bool track;

static Mem head,tail;
static Mem x_head,x_tail;

static void remove( Mem *m ){
	m->next->prev=m->prev;
	m->prev->next=m->next;
}

static void insert( Mem *m,Mem *next ){
	m->next=next;
	m->prev=next->prev;
	next->prev->next=m;
	next->prev=m;
}

static void init(){
	if( head.next ) return;
	head.next=head.prev=&tail;head.tag='HEAD';
	tail.next=tail.prev=&head;tail.tag='TAIL';
	x_head.next=x_head.prev=&x_tail;x_head.tag='HEAD';
	x_tail.next=x_tail.prev=&x_head;x_tail.tag='TAIL';
}

static void check( Mem *m ){
	if( m->tag!='DNEW' ){
		MessageBox( GetDesktopWindow(),"mem_check: pre_tag!='DNEW'","Memory error",MB_OK|MB_ICONWARNING );
		if( m->tag=='NDWE' ){
			string t="Probable double delete";
			t+="- d_new file: "+string(m->file)+" line:"+itoa(m->line);
			MessageBox( GetDesktopWindow(),t.c_str(),"Memory error",MB_OK|MB_ICONWARNING );
		}
		ExitProcess( 0 );
	}
	int *t=(int*)( (char*)(m+1)+m->size );
	if( *t!='dnew' ){
		MessageBox( GetDesktopWindow(),"mem_check: post_tag!='dnew'","Memory error",MB_OK|MB_ICONWARNING );
		string t="Probable memory overwrite - d_new file: "+string(m->file)+" line:"+itoa(m->line);
		MessageBox( GetDesktopWindow(),t.c_str(),"Memory error",MB_OK|MB_ICONWARNING );
		ExitProcess( 0 );
	}
}

static void *op_new( size_t size,const char *file="<unknown>",int line=0 ){
	init();
	Mem *m=(Mem*)malloc( sizeof(Mem)+size+sizeof(int) );
	memset( m+1,0xcc,size );
	m->file=file;m->line=line;m->size=size;m->tag='DNEW';
	int *t=(int*)( (char*)(m+1)+size );*t='dnew';
	if( track ) insert( m,head.next );
	else insert( m,x_head.next );
	return m+1;
}

static void op_delete( void *q ){
	init();
	if( !q ) return;
	Mem *m=(Mem*)q-1;
	check( m );
	remove( m );
	m->tag='NDWE';
	*(int*)( (char*)(m+1)+m->size )='ndwe';
	free( m );
}

void trackmem( bool enable ){
	init();
	if( track==enable ) return;
	track=enable;
	Mem *m;
	while( (m=head.next)!=&tail ){
		remove( m );insert( m,x_head.next );
	}
}

void checkmem( ostream &out ){
	init();
	Mem *m,*next;
	int sum=0,usum=0,xsum=0;
	for( m=head.next;m!=&tail;m=next ){
		check( m );
		next=m->next;
		if( m->line ){
			out<<m->file<<" line:"<<m->line<<" "<<m->size<<" bytes"<<endl;
			sum+=m->size;
		}else{
			usum+=m->size;
		}
	}
	for( m=x_head.next;m!=&x_tail;m=m->next ){
		check( m );
		xsum+=m->size;
	}
	out<<"Tracked blitz mem in use:"<<sum<<endl;
	out<<"Tracked other mem in use:"<<usum<<endl;
	out<<"Untracked mem in use:"<<xsum<<endl;
	out<<"Total mem in use:"<<(sum+usum+xsum)<<endl;
}

void * _cdecl operator new( size_t size ){ return op_new( size ); }
void * _cdecl operator new[]( size_t size ){ return op_new( size ); }
void * _cdecl operator new( size_t size,const char *file,int line ){ return op_new( size,file,line ); }
void * _cdecl operator new[]( size_t size,const char *file,int line ){ return op_new( size,file,line ); }
void _cdecl operator delete( void *q ){ op_delete( q ); }
void _cdecl operator delete[]( void *q ){ op_delete( q ); }
void _cdecl operator delete( void *q,const char *file,int line ){ op_delete( q ); }
void _cdecl operator delete[]( void *q,const char *file,int line ){ op_delete( q ); }

#else

void trackmem( bool enable ){
}

void checkmem( ostream &out ){
}

#endif

int atoi( const string &s ){
	return atoi( s.c_str() );
}

double atof( const string &s ){
	return atof( s.c_str() );
}

string itoa( int n ){
	char buff[32];portable_itoa10( n,buff,sizeof(buff) );
	return string( buff );
}

static int b_finite( double n ){		// definition: exponent anything but 2047.

	int e;					// 11 bit exponent
	const int eMax = 2047;	// 0x7ff, all bits = 1	
	
	int *pn = (int *) &n;

	e = *++pn;				// Intel order!
	e = ( e >> 20 ) & eMax;

	return e != eMax;
}

static int b_isnan( double n ){		// definition: exponent 2047, nonzero fraction.

	int e;					// 11 bit exponent
	const int eMax = 2047;	// 0x7ff, all bits = 1	
	
	int *pn = (int *) &n;

	e = *++pn;				// Intel order!
	e = ( e >> 20 ) & eMax;

	if ( e != 2047 ) return 0;	// almost always return here

	int fHi, fLo;				// 52 bit fraction

	fHi = ( *pn ) & 0xfffff;	// first 20 bits
	fLo = *--pn;				// last 32 bits

	return  ( fHi | fLo ) != 0;	// returns 0,1 not just 0,nonzero
}

/////////////
//By FLOYD!//
/////////////
string ftoa( float n ){

	static const int digits=6;

	int eNeg = -4, ePos = 8;	// limits for e notation.

	char buffer[50]; // from MSDN example, 25 would probably suffice
	string t;
	int dec, sign;

	if (b_finite(n)){

		//		if ( digits < 1 ) digits = 1;	// less than one digit is nonsense
		//		if ( digits > 8 ) digits = 8;	// practical maximum for float

		//t = _ecvt(n, digits, &dec, &sign);


		char * tmp=new char[64];
		portable_ecvt(tmp, 64, n, digits, &dec, &sign);
		t = tmp;
		delete[] tmp;

		if ( dec <= eNeg + 1 || dec > ePos ){

			portable_gcvt(buffer, 50, n, digits);


			t = buffer;
			return t;
		}
		
		// Here is the tricky case. We want a nicely formatted
		// number with no e-notation or multiple trailing zeroes.
	
		if ( dec <= 0 ){

			t = "0." + string( -dec, '0' ) + t;
			dec = 1;	// new location for decimal point

		}
		else if( dec < digits ){

			t = t.substr( 0, dec ) + "." + t.substr( dec );

		}
		else{

			t = t + string( dec - digits, '0' ) + ".0";
			dec += dec - digits;

		}
	
		// Finally, trim off excess zeroes.

		int dp1 = dec + 1, p = t.length();	
		while( --p > dp1 && t[p] == '0' );
		t = string( t, 0, ++p );

		return sign ? "-" + t : t;

	}	// end of finite case

	if ( b_isnan( n ) )	return "NaN";
	if ( n > 0.0 )		return "Infinity";
	if ( n < 0.0 )		return "-Infinity";

	abort();
	return 0;
}

/*
string ftoa( float n ){

	static const float min=.000001f,max=9999999.0f;

	int i=*(int*)&n;
	int e=(i>>23)&0xff;
	int f=i&0x007fffff;

	if( e==0xff && f ) return "NAN";

	string t;
	int s=(i>>31)&0x01;

	if( e==0xff ){
		t="INFINITY";
	}else if( !e && !f ){
		t="0.000000";
	}else if( n>=min && n<=max ){
		int dec,sgn;
		t=_fcvt( fabs(n),6,&dec,&sgn );
		if( dec<=0 ){
			t="0."+string( -dec,'0' )+t;
		}else if( dec<t.size() ){
			t=t.substr( 0,dec )+"."+t.substr( dec );
		}else{
			t=t+string( '0',dec-t.size() )+".000000";
		}
	}else{
		char buff[32];
		_gcvt( fabs(n),7,buff );
		t=buff;
	}
	return s ? "-"+t : t;
}
*/

string tolower( const string &s ){
	string t=s;
	for( int k=0;k<(int)t.size();++k ) t[k]=tolower(t[k]);
	return t;
}

string toupper( const string &s ){
	string t=s;
	for( int k=0;k<(int)t.size();++k ) t[k]=toupper(t[k]);
	return t;
}

string fullfilename( const string &t ){
	return bfplatform::absolutePath( t );
}

string filenamepath( const string &t ){
	const string full=bfplatform::absolutePath( t );
	const size_t slash=full.find_last_of( "/\\" );
	if( slash==string::npos ) return "";
	return full.substr( 0,slash+1 );
}

string filenamefile( const string &t ){
	const string full=bfplatform::absolutePath( t );
	const size_t slash=full.find_last_of( "/\\" );
	if( slash==string::npos ) return full;
	return full.substr( slash+1 );
}

std::string ltrim(const std::string &s) {
    size_t start = s.find_first_not_of(" \t\n\r\f\v");
    return (start == std::string::npos) ? "" : s.substr(start);
}

std::string rtrim(const std::string &s) {
    size_t end = s.find_last_not_of(" \t\n\r\f\v");
    return (end == std::string::npos) ? "" : s.substr(0, end + 1);
}

std::string trim(const std::string &s) {
    return rtrim(ltrim(s));
}

std::string replaceAll(std::string &s, char f, std::string r) {
	size_t apos = s.find(f);
	while (apos != std::string::npos) {
		s.replace(apos, 1, r);
		apos = s.find(f, apos + r.length());
	}
	return s;
}

const int MIN_SIZE=256;

qstreambuf::qstreambuf(){
	buf=d_new char[MIN_SIZE];
	setg( buf,buf,buf );
	// Standard `setp` takes (pbase, epptr); pptr starts at pbase, which is what
	// the legacy three-argument MSVC extension would have set.
	setp( buf,buf+MIN_SIZE );
}

qstreambuf::~qstreambuf(){
	delete buf;
}

int qstreambuf::size(){
	return pptr()-gptr();
}

char *qstreambuf::data(){
	return gptr();
}

qstreambuf::int_type qstreambuf::underflow(){
	if( gptr()==egptr() ){
		if( gptr()==pptr() ) return traits_type::eof();
		setg( gptr(),gptr(),pptr() );
	}

	return traits_type::to_int_type( *gptr() );
}

qstreambuf::int_type qstreambuf::overflow( qstreambuf::int_type c ){
	if( c==traits_type::eof() ) return c;

	if( pptr()==epptr() ){
		int sz=size();
		int n_sz=sz*2;if( n_sz<MIN_SIZE ) n_sz=MIN_SIZE;
		char *n_buf=d_new char[ n_sz ];
		memcpy( n_buf,gptr(),sz );
		delete buf;buf=n_buf;
		setg( buf,buf,buf+sz );
		setp( buf+sz,buf+n_sz );
	}

	*pptr()=traits_type::to_char_type( c );
	pbump( 1 );return traits_type::not_eof( c );
}
