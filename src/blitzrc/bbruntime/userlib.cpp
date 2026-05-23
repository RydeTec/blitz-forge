
#include "std.h"
#include "bbsys.h"

#include <windows.h>

static vector<HMODULE> _mods;

static void procNotFound(){
	RTEX( "User lib function not found" );
}

void _bbLoadLibs( char *p ){
	while( *p ){
		HMODULE mod=LoadLibrary( p );
		if( !mod ){
			// Skip this DLL and its proc table. Previously this `continue`
			// jumped to the loop test without advancing `p`, hanging the
			// runtime on startup whenever any user library DLL was missing.
			p+=strlen(p)+1;
			while( *p ){
				p+=strlen(p)+1;
				p+=4;
			}
			continue;
		}
		_mods.push_back(mod);
		p+=strlen(p)+1;
		while( *p ){
			void *proc=GetProcAddress( mod,p );
			p+=strlen(p)+1;
			void *ptr=*(void**)p;
			p+=4;
			if( !proc ) proc=procNotFound;
			*(void**)ptr=proc;
		}
	}
}

void _bbUnloadLibs(){
	for( ;_mods.size();_mods.pop_back() ) FreeLibrary( _mods.back() );
}
