
#pragma warning( disable:4786 )

#include "bbruntime_dll.h"
#include "../debugger/debugger.h"

#ifdef PRODEMO
#include "../shareprot/shareprot.h"
#endif

using namespace std;

#include <map>
#include <eh.h>
#include <float.h>
#include <dbghelp.h>	// SYMBOL_INFO for the lazy dbghelp symbolizer (no link dep)

#include "../bbruntime/bbruntime.h"

class DummyDebugger : public Debugger{
public:
	virtual void debugRun(){}
	virtual void debugStop(){}// bbruntime_panic(0); }
	virtual void debugStmt( int srcpos,const char *file ){}
	virtual void debugEnter( void *frame,void *env,const char *func ){}
	virtual void debugLeave(){}
	virtual void debugLog( const char *msg ){
		if (test) cout << msg << endl;
	}
	virtual void debugMsg( const char *e,bool serious ){
		if (test) {
			if (serious) cout << "Error: ";
			cout << e << endl;
			if (serious) exit(1);
		}
		if( serious ) MessageBox( 0,e,"Error!",MB_OK|MB_TOPMOST|MB_SETFOREGROUND );
	}
	virtual void debugSys( void *msg ){}
};

static HINSTANCE hinst;
static map<const char*,void*> syms;
map<const char*,void*>::iterator sym_it;
static gxRuntime *gx_runtime;

static void rtSym( const char *sym,void *pc ){
	syms[sym]=pc;
}

#ifdef PRODEMO
static void killer(){
	ExitProcess( -1 );
}
#endif

// Best-effort symbol lookup through dbghelp (system DLL, loaded lazily so we
// add no link dependency). With PDBs alongside the binaries this turns a
// module-relative crash offset into a function name + displacement.
typedef BOOL (WINAPI *SymInitialize_t)( HANDLE,PCSTR,BOOL );
typedef BOOL (WINAPI *SymFromAddr_t)( HANDLE,DWORD64,PDWORD64,PSYMBOL_INFO );
static string symbolName( void *addr ){
	static SymFromAddr_t pSymFromAddr=0;
	static bool tried=false;
	if( !tried ){
		tried=true;
		if( HMODULE dh=LoadLibraryA( "dbghelp.dll" ) ){
			SymInitialize_t pInit=(SymInitialize_t)GetProcAddress( dh,"SymInitialize" );
			SymFromAddr_t pFrom=(SymFromAddr_t)GetProcAddress( dh,"SymFromAddr" );
			if( pInit && pFrom && pInit( GetCurrentProcess(),0,TRUE ) ) pSymFromAddr=pFrom;
		}
	}
	if( !pSymFromAddr ) return "";
	char symbuf[sizeof(SYMBOL_INFO)+256];
	SYMBOL_INFO *sym=(SYMBOL_INFO*)symbuf;
	sym->SizeOfStruct=sizeof(SYMBOL_INFO);
	sym->MaxNameLen=255;
	DWORD64 disp=0;
	if( pSymFromAddr( GetCurrentProcess(),(DWORD64)(uintptr_t)addr,&disp,sym ) ){
		char out[320];
		sprintf( out," (%s+0x%X)",sym->Name,(unsigned int)disp );
		return out;
	}
	return "";
}

// Describe a code/data address as "module+0xOFFSET" when it falls inside a
// loaded module, else as a raw pointer. Module-relative offsets stay stable
// across ASLR runs, so an intermittent fault becomes attributable to a
// specific function via the linker map even when it only fires 1-in-20 runs.
static string describeAddress( void *addr ){
	char buf[160];
	MEMORY_BASIC_INFORMATION mbi;
	if( addr && VirtualQuery( addr,&mbi,sizeof(mbi) ) && mbi.AllocationBase ){
		char path[MAX_PATH];
		DWORD n=GetModuleFileName( (HMODULE)mbi.AllocationBase,path,MAX_PATH );
		if( n ){
			const char *base=path;
			for( const char *p=path;*p;++p ) if( *p=='\\'||*p=='/' ) base=p+1;
			sprintf( buf,"%s+0x%X",base,(unsigned int)((char*)addr-(char*)mbi.AllocationBase) );
			return string( buf )+symbolName( addr );
		}
	}
	sprintf( buf,"0x%p",addr );
	return buf;
}

static void _cdecl seTranslator( unsigned int u,EXCEPTION_POINTERS* pExp ){

	string panicStr = "Unknown runtime exception";
	switch( u ){
	case EXCEPTION_INT_DIVIDE_BY_ZERO:
		panicStr = "Integer divide by zero";
		break;
	case EXCEPTION_ACCESS_VIOLATION:
		panicStr = "Memory access violation";
		break;
	case EXCEPTION_ILLEGAL_INSTRUCTION:
		panicStr = "Illegal instruction";
		break;
	case EXCEPTION_STACK_OVERFLOW:
		panicStr = "Stack overflow!";
		break;
	}

	// Append fault diagnostics: exception code, faulting instruction
	// (module-relative), and for access violations the access kind and
	// target address. Without this every intermittent native crash is an
	// unattributable one-liner; with it the offset maps to a function via
	// the linker map. Stack-overflow handling stays minimal on purpose --
	// describeAddress allocates stack, and the guard page is already blown.
	if( pExp && pExp->ExceptionRecord && u!=EXCEPTION_STACK_OVERFLOW ){
		EXCEPTION_RECORD *er=pExp->ExceptionRecord;
		char info[96];
		sprintf( info," [code 0x%08X at ",(unsigned int)er->ExceptionCode );
		panicStr+=info;
		panicStr+=describeAddress( er->ExceptionAddress );
		if( u==EXCEPTION_ACCESS_VIOLATION && er->NumberParameters>=2 ){
			const char *kind=er->ExceptionInformation[0]==0 ? "reading" :
				(er->ExceptionInformation[0]==1 ? "writing" : "executing");
			sprintf( info,", %s 0x%08X",kind,(unsigned int)er->ExceptionInformation[1] );
			panicStr+=info;
		}
		panicStr+="]";

		// Code bytes at the faulting instruction: lets an intermittent fault
		// in dynamically-emitted Blitz code (not part of any module, so no
		// symbols) be disassembled after the fact.
		if( pExp->ContextRecord ){
			CONTEXT *cx=pExp->ContextRecord;
			unsigned char *ip=(unsigned char*)cx->Eip;
			MEMORY_BASIC_INFORMATION mbi;
			if( ip && VirtualQuery( ip,&mbi,sizeof(mbi) ) && mbi.State==MEM_COMMIT ){
				panicStr+="\ncode:";
				for( int k=0;k<16;++k ){
					sprintf( info," %02X",ip[k] );
					panicStr+=info;
				}
			}
			sprintf( info,"\nregs: eax=%08X ebx=%08X ecx=%08X edx=%08X esi=%08X edi=%08X esp=%08X ebp=%08X",
				(unsigned)cx->Eax,(unsigned)cx->Ebx,(unsigned)cx->Ecx,(unsigned)cx->Edx,
				(unsigned)cx->Esi,(unsigned)cx->Edi,(unsigned)cx->Esp,(unsigned)cx->Ebp );
			panicStr+=info;
			// When the fault is inside an anonymous (non-module) executable
			// allocation -- dynamically emitted Blitz code -- dump the image
			// from its allocation base so the instruction stream leading to
			// the fault can be disassembled offline.
			if( ip && VirtualQuery( ip,&mbi,sizeof(mbi) ) && mbi.State==MEM_COMMIT && mbi.AllocationBase ){
				char path[MAX_PATH];
				if( !GetModuleFileName( (HMODULE)mbi.AllocationBase,path,MAX_PATH ) ){
					unsigned char *base=(unsigned char*)mbi.AllocationBase;
					unsigned int len=0x280;
					sprintf( info,"\nimage @0x%p (fault offset 0x%X):",base,(unsigned int)(ip-base) );
					panicStr+=info;
					for( unsigned int k=0;k<len;++k ){
						if( (k&15)==0 ){ sprintf( info,"\n%04X:",k ); panicStr+=info; }
						sprintf( info," %02X",base[k] );
						panicStr+=info;
					}
				}
			}
			// EBP-chain walk. Generated Blitz code keeps classic EBP frames,
			// so this reaches the generated main and the runtime-DLL callers
			// (which describeAddress symbolizes as module+offset).
			panicStr+="\nstack:";
			unsigned int ebp=cx->Ebp;
			for( int f=0;f<12 && ebp;++f ){
				if( !VirtualQuery( (void*)ebp,&mbi,sizeof(mbi) ) || mbi.State!=MEM_COMMIT
					|| (mbi.Protect&(PAGE_GUARD|PAGE_NOACCESS)) ) break;
				if( (char*)ebp+8 > (char*)mbi.BaseAddress+mbi.RegionSize ) break;
				unsigned int ret=((unsigned int*)ebp)[1];
				unsigned int prev=((unsigned int*)ebp)[0];
				if( !ret ) break;
				panicStr+=" <- ";
				panicStr+=describeAddress( (void*)ret );
				if( prev<=ebp ) break;
				ebp=prev;
			}
		}
	}

	bbruntime_panic( panicStr.c_str() );
}

int Runtime::version(){
	return VERSION;
}

const char *Runtime::nextSym(){
	if( !syms.size() ){
		bbruntime_link( rtSym );
		sym_it=syms.begin();
	}
	if( sym_it==syms.end() ){
		syms.clear();return 0;
	}
	return (sym_it++)->first;
}

int Runtime::symValue( const char *sym ){
	map<const char*,void*>::iterator it=syms.find( sym );
	if( it!=syms.end() ) return (int)it->second;
	return -1;
}

void Runtime::startup( HINSTANCE h ){
	hinst=h;
}

void Runtime::shutdown(){
	trackmem( false );
	syms.clear();
}

void Runtime::execute( void (*pc)(),const char *args,Debugger *dbg, bool test ){

	bool debug=!!dbg;

	static DummyDebugger dummydebug;

	if( !dbg ) dbg=&dummydebug;

	dbg->test = test;

	trackmem( true );

	_se_translator_function old_trans=_set_se_translator( seTranslator );
	_control87( _RC_NEAR|_PC_24|_EM_INVALID|_EM_ZERODIVIDE|_EM_OVERFLOW|_EM_UNDERFLOW|_EM_INEXACT|_EM_DENORMAL,0xfffff );

	//strip spaces from ends of args...
	string params=args;
	while( params.size() && params[0]==' ' ) params=params.substr( 1 );
	while( params.size() && params[params.size()-1]==' ' ) params=params.substr( 0,params.size()-1 );

	if( gx_runtime=gxRuntime::openRuntime( hinst,params,dbg,test ) ){

#ifdef PRODEMO
		shareProtCheck( killer );
#endif
		bbruntime_run( gx_runtime,pc,debug,test );

		testFailed = gx_runtime->testFailed;
		gxRuntime *t=gx_runtime;
		gx_runtime=0;
		gxRuntime::closeRuntime( t );
	}

	_control87( _CW_DEFAULT,0xfffff );
	_set_se_translator( old_trans );
}

void Runtime::asyncStop(){
	if( gx_runtime ) gx_runtime->asyncStop();
}

void Runtime::asyncRun(){
	if( gx_runtime ) gx_runtime->asyncRun();
}

void Runtime::asyncEnd(){
	if( gx_runtime ) gx_runtime->asyncEnd();
}

void Runtime::checkmem( streambuf *buf ){
	ostream out( buf );
	::checkmem( out );
}

Runtime *_cdecl runtimeGetRuntime(){
	static Runtime runtime;
	return &runtime;
}

/********************** BUTT UGLY DLL->EXE HOOK! *************************/

static void *module_pc;
static map<string,int> module_syms;
static map<string,int> runtime_syms;
static Runtime *runtime;

static void fail(){
	MessageBox( 0,"Unable to run Blitz Basic module",0,0 );
	ExitProcess(-1);
}

struct Sym{
	string name;
	int value;
};

static Sym getSym( void **p ){
	Sym sym;
	char *t=(char*)*p;
	while( char c=*t++ ) sym.name+=c;
	sym.value=*(int*)t+(int)module_pc;
	*p=t+4;return sym;
}

static int findSym( const string &t ){
	map<string,int>::iterator it;

	it=module_syms.find( t );
	if( it!=module_syms.end() ) return it->second;
	it=runtime_syms.find( t );
	if( it!=runtime_syms.end() ) return it->second;

	string err="Can't find symbol: "+t;
	MessageBox( 0,err.c_str(),0,0 );
	ExitProcess(0);
	return 0;
}

static void link(){

	while( const char *sc=runtime->nextSym() ){

		string t(sc);

		if( t[0]=='_' ){
			runtime_syms["_"+t]=runtime->symValue(sc);
			continue;
		}

		if( t[0]=='!' ) t=t.substr(1);

		if( t[0]=='(' ){
			for (int i=1;i<t.size();i++) {
				if (t[i]==')') {
					t=t.substr(i+1); break;
				}
			}
		}

		if( !isalnum(t[0]) ) t=t.substr(1);

		for( int k=0;k<(int)t.size();++k ){
			if( isalnum(t[k]) || t[k]=='_' ) continue;
			t=t.substr( 0,k );break;
		}

		runtime_syms["_f"+tolower(t)]=runtime->symValue(sc);
	}

	HRSRC hres=FindResource( 0,MAKEINTRESOURCE(1111),RT_RCDATA );if( !hres ) fail();
	HGLOBAL hglo=LoadResource( 0,hres );if( !hglo ) fail();
	void *p=LockResource( hglo );if( !p ) fail();

	int sz=*(int*)p;p=(int*)p+1;

	//replace malloc for service pack 2 Data Execution Prevention (DEP).
	module_pc=VirtualAlloc( 0,sz,MEM_COMMIT|MEM_RESERVE,PAGE_EXECUTE_READWRITE );

	memcpy( module_pc,p,sz );
	p=(char*)p+sz;

	int k,cnt;

	cnt=*(int*)p;p=(int*)p+1;
	for( k=0;k<cnt;++k ){
		Sym sym=getSym( &p );
		if( sym.value<(int)module_pc || sym.value>=(int)module_pc+sz ) fail();
		module_syms[sym.name]=sym.value;
	}

	cnt=*(int*)p;p=(int*)p+1;
	for( k=0;k<cnt;++k ){
		Sym sym=getSym( &p );
		int *pp=(int*)sym.value;
		int dest=findSym( sym.name );
		*pp+=dest-(int)pp;
	}

	cnt=*(int*)p;p=(int*)p+1;
	for( k=0;k<cnt;++k ){
		Sym sym=getSym( &p );
		int *pp=(int*)sym.value;
		int dest=findSym( sym.name );
		*pp+=dest;
	}

	runtime_syms.clear();
	module_syms.clear();
}

extern "C" _declspec(dllexport) int _stdcall bbWinMain();
extern "C" BOOL _stdcall _DllMainCRTStartup( HANDLE,DWORD,LPVOID );

bool WINAPI DllMain( HANDLE module,DWORD reason,void *reserved ){
	return TRUE;
}

int __stdcall bbWinMain(){

	HINSTANCE inst=GetModuleHandle( 0 );

	_DllMainCRTStartup( inst,DLL_PROCESS_ATTACH,0 );

#ifdef BETA
	int ver=VERSION & 0x7fff;
	string t="Created with Blitz3D Beta V"+itoa( ver/100 )+"."+itoa( ver%100 );
	MessageBox( GetDesktopWindow(),t.c_str(),"Blitz3D Message",MB_OK );
#endif

#ifdef SCHOOLS
	MessageBox( GetDesktopWindow(),"Created with the schools version of Blitz Basic","Blitz Basic Message",MB_OK );
#endif

	runtime=runtimeGetRuntime();
	runtime->startup( inst );

	link();

	//get cmd_line and params
	string cmd=GetCommandLine(),params;
	while( cmd.size() && cmd[0]==' ' ) cmd=cmd.substr( 1 );
	if( cmd.find( '\"' )==0 ){
		int n=cmd.find( '\"',1 );
		if( n!=string::npos ){
			params=cmd.substr( n+1 );
			cmd=cmd.substr( 1,n-1 );
		}
	}else{
		int n=cmd.find( ' ' );
		if( n!=string::npos ){
			params=cmd.substr( n+1 );
			cmd=cmd.substr( 0,n );
		}
	}

	runtime->execute( (void(*)())module_pc,params.c_str(),0,false );
	runtime->shutdown();

	_DllMainCRTStartup( inst,DLL_PROCESS_DETACH,0 );

	ExitProcess(0);
	return 0;
}

