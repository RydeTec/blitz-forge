#include "../bbruntime_dll/bbruntime_dll.h"
#include "../debugger/debugger.h"

#include <set>
#include <string>
#include <vector>

using namespace std;

namespace {

vector<string> g_symbols;
size_t g_symbolIndex=0;

#include "generated_runtime_symbols.inc"

void addSymbol( const string &sym,set<string> &seen ){
	string cleaned=sym;
	while( cleaned.size() && (cleaned[cleaned.size()-1]=='\\' || cleaned[cleaned.size()-1]==' ' || cleaned[cleaned.size()-1]=='\t' || cleaned[cleaned.size()-1]=='\r') ){
		cleaned=cleaned.substr( 0,cleaned.size()-1 );
	}
	if( !cleaned.size() ) return;
	if( seen.count( cleaned ) ) return;
	seen.insert( cleaned );
	g_symbols.push_back( cleaned );
}

void addFallbackSymbols( set<string> &seen ){
	addSymbol( "Print$string",seen );
	addSymbol( "$Input$prompt",seen );
	addSymbol( "AppTitle$title$close_prompt=\"\"",seen );
	addSymbol( "%ExecFile$command$params=\"\"$start_in=\"\"",seen );
}

void loadRuntimeSymbols(){
	if( !g_symbols.empty() ) return;

	set<string> seen;
	g_symbols.reserve( kGeneratedRuntimeSymbolCount+8 );
	for( size_t i=0;i<kGeneratedRuntimeSymbolCount;++i ){
		addSymbol( kGeneratedRuntimeSymbols[i],seen );
	}

	// Preserve the bridge helpers and compatibility aliases expected by existing
	// compiler-side parsing even before the native runtime execution path lands.
	addSymbol( "_bbLoadLibs",seen );
	addSymbol( "_bbStrToCStr",seen );
	addSymbol( "_bbCStrToStr",seen );
	addFallbackSymbols( seen );
}

} // namespace

int Runtime::version(){
	return VERSION;
}

const char *Runtime::nextSym(){
	loadRuntimeSymbols();
	if( g_symbolIndex>=g_symbols.size() ){
		g_symbolIndex=0;
		return 0;
	}
	return g_symbols[g_symbolIndex++].c_str();
}

int Runtime::symValue( const char *sym ){
	loadRuntimeSymbols();
	for( int i=0;i<(int)g_symbols.size();++i ){
		if( g_symbols[i]==sym ){
			// Transitional stub address space for compiler-side symbol bookkeeping.
			return (i+1)*16;
		}
	}
	return -1;
}

void Runtime::startup( HINSTANCE hinst ){
	(void)hinst;
}

void Runtime::shutdown(){
	g_symbolIndex=0;
}

void Runtime::asyncStop(){
}

void Runtime::asyncRun(){
}

void Runtime::asyncEnd(){
}

void Runtime::checkmem( std::streambuf *buf ){
	(void)buf;
}

void Runtime::execute( void (*pc)(),const char *args,Debugger *dbg,bool test ){
	(void)pc;
	(void)args;
	(void)test;
	if( dbg ) dbg->debugMsg( "Runtime execution is not yet supported for native macOS arm64 builds.",true );
	testFailed=true;
}

extern "C" BF_DLLEXPORT Runtime *_cdecl runtimeGetRuntime(){
	static Runtime runtime;
	return &runtime;
}
