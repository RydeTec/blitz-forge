
#include "std.h"
#include "bbsys.h"
#include "userlibs.h"

#include <windows.h>

static vector<HMODULE> _mods;

struct Str{
	char *p;
	int size;
};

static Str _strs[256];
static int _nextStr;

static void libNotFound(){
	RTEX( "User lib not found" );
}

static void procNotFound(){
	RTEX( "User lib function not found" );
}

static string getAppDir(){
	char buff[MAX_PATH];
	if( GetModuleFileName( 0,buff,MAX_PATH ) ){
		string t=buff;
		int n=t.find_last_of( '\\' );
		if( n!=string::npos ) t=t.substr( 0,n );
		return t;
	}
	return "";
}

static std::string getCurrentWorkingDirectory() {
	char buffer[MAX_PATH];
	DWORD dwRet = GetCurrentDirectory(MAX_PATH, buffer);
	if (dwRet > 0) {
		return std::string(buffer);
	}
	// Handle error or return empty string if the directory cannot be obtained
	return std::string();
}

void _bbLoadLibs( char *p ){

	string cwd = getCurrentWorkingDirectory();
	string home = getAppDir();

	while( *p ){
		HMODULE mod=LoadLibrary( p );
		if (!mod) {
			mod = LoadLibrary((cwd + "/bin/" + string(p)).c_str());
		}
		if (!mod) {
			mod = LoadLibrary((cwd + "/../" + string(p)).c_str());
		}
		if (!mod) {
			mod = LoadLibrary((cwd + "/../bin/" + string(p)).c_str());
		}
		if (!mod) {
			mod = LoadLibrary((cwd + "/../../bin/" + string(p)).c_str());
		}
		if( !mod && home.size() ){
			mod=LoadLibrary( (home+"/../userlibs/"+p).c_str() );
		}
		p+=strlen(p)+1;
		if( mod ){
			_mods.push_back( mod );
			while( *p ){
				void *proc=GetProcAddress( mod,p );
				p+=strlen(p)+1;
				void *ptr=*(void**)p;
				p+=4;
				*(void**)ptr=proc ? proc : procNotFound;
			}
		}else{
			while( *p ){
				p+=strlen(p)+1;
				void *ptr=*(void**)p;
				p+=4;
				*(void**)ptr=libNotFound;
			}
		}
		++p;
	}
}

const char*  _bbStrToCStr( BBStr *str ){

	Str &t=_strs[_nextStr++ & 255];

	int size=str->size();

	if( !t.p || t.size<size ){
		delete[] t.p;
		t.p=new char[size+1];
		t.size=size;
	}

	memcpy( t.p,str->data(),size );
	t.p[size]=0;
	delete str;
	return t.p;
}

BBStr*		 _bbCStrToStr( const char *str ){
	return new BBStr( str );
}

bool userlibs_create(){
	return true;
}

void userlibs_destroy(){
	for( ;_mods.size();_mods.pop_back() ) FreeLibrary( _mods.back() );
}

void userlibs_link( void(*rtSym)(const char*,void*) ){
	rtSym( "_bbLoadLibs",_bbLoadLibs );
	rtSym( "_bbStrToCStr",_bbStrToCStr );
	rtSym( "_bbCStrToStr",_bbCStrToStr );
}


