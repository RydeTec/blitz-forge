
/* Win32 runtime dynamic link lib */

#ifndef BBRUNTIME_DLL_H
#define BBRUNTIME_DLL_H

#include "../stdutil/bf_export.h"

#ifdef _WIN32
#include <windows.h>
#else
// On non-Windows hosts the runtime/host backend supplies a portable opaque
// instance handle. Provide a matching typedef so legacy signatures continue
// to type-check without depending on the Win32 headers.
typedef void *HINSTANCE;
#endif

#include "../stdutil/stdutil.h"

class Debugger;

class Runtime{
public:
	bool testFailed = false;

	virtual int version();
	virtual const char *nextSym();
	virtual int symValue( const char *sym );
	virtual void startup( HINSTANCE hinst );
	virtual void shutdown();
	virtual void asyncStop();
	virtual void asyncRun();
	virtual void asyncEnd();
	virtual void checkmem( std::streambuf *buf );

	virtual void execute( void (*pc)(),const char *args,Debugger *dbg, bool test );
};

extern "C" BF_DLLEXPORT Runtime * BF_CDECL runtimeGetRuntime();

#endif