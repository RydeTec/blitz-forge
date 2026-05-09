#include "../linker/linker.h"
#include "../config/config.h"

#include <cstdint>
#include <cstdlib>
#include <cstdio>
#include <fstream>
#include <cstring>
#include <iostream>
#include <map>
#include <string>
#include <vector>

using namespace std;

namespace {

string shellQuote( const string &value ){
	string out="'";
	for( int i=0;i<(int)value.size();++i ){
		const char c=value[i];
		if( c=='\'' ) out+="'\\''";
		else out+=c;
	}
	out+="'";
	return out;
}

bool writePlaceholderSource( const string &path ){
	ofstream out( path.c_str() );
	if( !out.good() ) return false;
	out<<"#include <stdio.h>\n";
	out<<"#include <stdlib.h>\n";
	out<<"#include <string.h>\n";
	out<<"#include <unistd.h>\n";
	out<<"#include <string>\n";
	out<<"#include <vector>\n";
	out<<"#include <sys/stat.h>\n";
	out<<"\n";
	out<<"static bool fileExists(const std::string &path){\n";
	out<<"  struct stat st;\n";
	out<<"  return stat(path.c_str(), &st) == 0;\n";
	out<<"}\n";
	out<<"\n";
	out<<"static std::string shellQuote(const std::string &value){\n";
	out<<"  std::string out = \"'\";\n";
	out<<"  for(size_t i=0;i<value.size();++i){\n";
	out<<"    if(value[i]=='\\''){ out += \"'\\\\''\"; }\n";
	out<<"    else out += value[i];\n";
	out<<"  }\n";
	out<<"  out += \"'\";\n";
	out<<"  return out;\n";
	out<<"}\n";
	out<<"\n";
	out<<"static int runViaWine(const char *wineCmd, const std::string &target, int argc, char **argv){\n";
	out<<"  std::string cmd = std::string(wineCmd) + \" \" + shellQuote(target);\n";
	out<<"  for(int i=1;i<argc;++i){\n";
	out<<"    cmd += \" \";\n";
	out<<"    cmd += shellQuote(argv[i]);\n";
	out<<"  }\n";
	out<<"  return system(cmd.c_str());\n";
	out<<"}\n";
	out<<"\n";
	out<<"int main(int argc, char **argv){\n";
	out<<"  std::string self = (argc > 0 && argv[0]) ? argv[0] : \"\";\n";
	out<<"  std::string winExe = self + \".exe\";\n";
	out<<"\n";
	out<<"  if(fileExists(winExe)){\n";
	out<<"    if(system(\"command -v wine64 >/dev/null 2>&1\") == 0){\n";
	out<<"      return runViaWine(\"wine64\", winExe, argc, argv);\n";
	out<<"    }\n";
	out<<"    if(system(\"command -v wine >/dev/null 2>&1\") == 0){\n";
	out<<"      return runViaWine(\"wine\", winExe, argc, argv);\n";
	out<<"    }\n";
	out<<"    fprintf(stderr, \"Found companion Windows executable but wine is not installed: %s\\n\", winExe.c_str());\n";
	out<<"    return 2;\n";
	out<<"  }\n";
	out<<"\n";
	out<<"  fprintf(stderr, \"BlitzForge macOS arm64 executable stub: native runtime execution is not implemented yet.\\n\");\n";
	out<<"  fprintf(stderr, \"No companion Windows executable was found at: %s\\n\", winExe.c_str());\n";
	out<<"  fprintf(stderr, \"Install companion .exe files (for example via RCCE scripts/bootstrap_macos_runtime_fallback.sh) and rerun.\\n\");\n";
	out<<"  return 1;\n";
	out<<"}\n";
	return out.good();
}

} // namespace

class StubModule : public Module{
public:
	StubModule():linked(false){}

	void *link( Module *libs ) override{
		if( linked ) return linkedData.empty() ? 0 : &linkedData[0];
		linkedData=data;
		linked=true;

		for( map<int,string>::iterator it=rel_relocs.begin();it!=rel_relocs.end();++it ){
			int dest=0;
			if( !findSym( it->second,libs,&dest ) ) return 0;
			int *p=(int*)(&linkedData[it->first]);
			*p+=(dest-(int)(intptr_t)p);
		}

		for( map<int,string>::iterator it=abs_relocs.begin();it!=abs_relocs.end();++it ){
			int dest=0;
			if( !findSym( it->second,libs,&dest ) ) return 0;
			int *p=(int*)(&linkedData[it->first]);
			*p+=dest;
		}

		return linkedData.empty() ? 0 : &linkedData[0];
	}

	bool createExe( const char *exe_file,const char *dll_file,const char *ico_file ) override{
		if( !exe_file || !*exe_file ) return false;
		(void)dll_file;
		(void)ico_file;
		const string exePath=exe_file;
		const string sourcePath=exePath+".bf_stub.cpp";
		if( !writePlaceholderSource( sourcePath ) ){
			cerr<<"Unable to write macOS executable stub source: "<<sourcePath<<endl;
			return false;
		}
		const string cmd="clang++ -O2 -arch arm64 -x c++ "+shellQuote( sourcePath )+" -o "+shellQuote( exePath );
		const int rc=system( cmd.c_str() );
		remove( sourcePath.c_str() );
		if( rc!=0 ){
			cerr<<"Failed to build macOS executable stub with clang"<<endl;
			return false;
		}
		return true;
	}

	int getPC() override{
		return (int)data.size();
	}

	void emit( int byte ) override{
		data.push_back( (unsigned char)byte );
	}

	void emitw( int word ) override{
		emit( word&0xff );
		emit( (word>>8)&0xff );
	}

	void emitd( int dword ) override{
		emit( dword&0xff );
		emit( (dword>>8)&0xff );
		emit( (dword>>16)&0xff );
		emit( (dword>>24)&0xff );
	}

	void emitx( void *mem,int sz ) override{
		unsigned char *p=(unsigned char*)mem;
		data.insert( data.end(),p,p+sz );
	}

	bool addSymbol( const char *sym,int pc ) override{
		string t(sym);
		if( symbols.find(t)!=symbols.end() ) return false;
		symbols[t]=pc;
		return true;
	}

	bool addReloc( const char *dest_sym,int pc,bool pcrel ) override{
		map<int,string> &rel=pcrel ? rel_relocs : abs_relocs;
		if( rel.find(pc)!=rel.end() ) return false;
		rel[pc]=string(dest_sym);
		return true;
	}

	bool findSymbol( const char *sym,int *pc ) override{
		map<string,int>::iterator it=symbols.find( string(sym) );
		if( it==symbols.end() ) return false;
		*pc=it->second + (int)(intptr_t)(linkedData.empty() ? 0 : &linkedData[0]);
		return true;
	}

private:
	bool findSym( const string &t,Module *libs,int *n ){
		if( findSymbol( t.c_str(),n ) ) return true;
		if( libs && libs->findSymbol( t.c_str(),n ) ) return true;
		cerr<<"Linker symbol not found: "<<t<<endl;
		return false;
	}

	vector<unsigned char> data;
	vector<unsigned char> linkedData;
	bool linked;
	map<string,int> symbols;
	map<int,string> rel_relocs,abs_relocs;
};

int Linker::version(){
	return VERSION;
}

bool Linker::canCreateExe(){
	return true;
}

Module *Linker::createModule(){
	return new StubModule();
}

void Linker::deleteModule( Module *mod ){
	delete mod;
}

extern "C" BF_DLLEXPORT Linker *_cdecl linkerGetLinker(){
	static Linker linker;
	return &linker;
}
