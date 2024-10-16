
#include "std.h"
#include <cctype>
#include "toker.h"
#include "ex.h"

int Toker::chars_toked;

bool Toker::noTrace;

static map<string,int> alphaTokes,lowerTokes;

static void makeKeywords(){
	static bool made;
	if( made ) return;

	alphaTokes["Dim"]=DIM;
	alphaTokes["Goto"]=GOTO;
	alphaTokes["Gosub"]=GOSUB;
	alphaTokes["Return"]=RETURN;
	alphaTokes["Exit"]=EXIT;
	alphaTokes["If"]=IF;
	alphaTokes["Then"]=THEN;
	alphaTokes["Else"]=ELSE;
	alphaTokes["EndIf"]=ENDIF;
	alphaTokes["End If"]=ENDIF;
	alphaTokes["ElseIf"]=ELSEIF;
	alphaTokes["Else If"]=ELSEIF;
	alphaTokes["While"]=WHILE;
	alphaTokes["Wend"]=WEND;
	alphaTokes["For"]=FOR;
	alphaTokes["To"]=TO;
	alphaTokes["Step"]=STEP;
	alphaTokes["Next"]=NEXT;
	alphaTokes["Function"]=FUNCTION;
	alphaTokes["End Function"]=ENDFUNCTION;
	alphaTokes["Type"]=TYPE;
	alphaTokes["End Type"]=ENDTYPE;
	alphaTokes["Each"]=EACH;
	alphaTokes["Method"]=METHOD;
	alphaTokes["End Method"] = ENDMETHOD;
	alphaTokes["Local"]=LOCAL;
	alphaTokes["Global"]=GLOBAL;
	alphaTokes["Field"]=FIELD;
	alphaTokes["Const"]=BBCONST;
	alphaTokes["Select"]=SELECT;
	alphaTokes["Case"]=CASE;
	alphaTokes["Default"]=DEFAULT;
	alphaTokes["End Select"]=ENDSELECT;
	alphaTokes["Repeat"]=REPEAT;
	alphaTokes["Until"]=UNTIL;
	alphaTokes["Forever"]=FOREVER;
	alphaTokes["Data"]=DATA;
	alphaTokes["Read"]=READ;
	alphaTokes["Restore"]=RESTORE;
	alphaTokes["Abs"]=ABS;
	alphaTokes["Sgn"]=SGN;
	alphaTokes["Mod"]=MOD;
	alphaTokes["Pi"]=PI;
	alphaTokes["True"]=BBTRUE;
	alphaTokes["False"]=BBFALSE;
	alphaTokes["Int"]=BBINT;
	alphaTokes["Float"]=BBFLOAT;
	alphaTokes["Str"]=BBSTR;
	alphaTokes["Include"]=INCLUDE;
	alphaTokes["Test"] = TEST;
	alphaTokes["EndTest"] = ENDTEST;
	alphaTokes["End Test"] = ENDTEST;

	alphaTokes["New"]=BBNEW;
	alphaTokes["Delete"]=BBDELETE;
	alphaTokes["First"]=FIRST;
	alphaTokes["Last"]=LAST;
	alphaTokes["Insert"]=INSERT;
	alphaTokes["Before"]=BEFORE;
	alphaTokes["After"]=AFTER;
	alphaTokes["Object"]=OBJECT;
	alphaTokes["Handle"]=BBHANDLE;
	alphaTokes["Assert"] = ASSERT;
	alphaTokes["Recast"]=RECAST;
	alphaTokes["Release"]=RELEASE;
	alphaTokes["Reference"]=REFERENCE;

	alphaTokes["And"]=AND;
	alphaTokes["Or"]=OR;
	alphaTokes["Xor"]=XOR;
	alphaTokes["Not"]=NOT;
	alphaTokes["Shl"]=SHL;
	alphaTokes["Shr"]=SHR;
	alphaTokes["Sar"]=SAR;

	alphaTokes["Null"]=NULLCONST;

	alphaTokes["Strict"]=USESTRICTTYPING;
	alphaTokes["NoTrace"] = NOTRACE;
	alphaTokes["DisableGC"] = DISABLEGC;
	alphaTokes["EnableGC"] = ENABLEGC;

	alphaTokes["Ptr"] = BBPOINTER;

	map<string,int>::const_iterator it;
	for( it=alphaTokes.begin();it!=alphaTokes.end();++it ){
		lowerTokes[tolower(it->first)]=it->second;
	}
	made=true;
}

Toker::Toker( istream &in ):in(in),curr_row(-1){
	makeKeywords();
	nextline();
}

map<string,int> &Toker::getKeywords(){
	makeKeywords();
	return alphaTokes;
}

int Toker::pos(){
	return ((curr_row)<<16)|(tokes[curr_toke].from);
}

int Toker::curr(){
	return at(curr_toke);
}

int Toker::at(int toke){
	return tokes[toke].n;
}

int Toker::findNext( int toke, int offset ) {
	for (int i=offset; i<tokes.size()-curr_toke; ++i) {
		if (lookAhead(i) == toke) {
			return i+curr_toke;
		}
	}
	return 0;
}

string Toker::text(){
	return textAt(curr_toke);
}

string Toker::textAt(int toke) {
	if (toke >= tokes.size()) return "";
	int from=tokes[toke].from,to=tokes[toke].to;
	return line.substr( from,to-from );
}

string Toker::getLine() {
	return line;
}

string Toker::getOriginalLine() {
	return originalLine;
}

string Toker::originalTextAt(int toke) {
	if (toke >= tokes.size()) return "";
	if (originalLine.size() != line.size()) return "";
	int from=tokes[toke].from,to=tokes[toke].to;
	return originalLine.substr( from,to-from );
}

int Toker::current_toke() {
	return curr_toke;
}

int Toker::lookAhead( int n ){
	return tokes[curr_toke+n].n;
}

void Toker::nextline(){
	curr_toke=0;
	tokes.clear();

	if (tokes_cache.empty()) {
		++curr_row;
		if( in.eof() ){
			originalLine.clear();
			line.resize(1);line[0]=EOF;
			tokes.push_back( Toke( EOF,0,1 ) );
			return;
		}
		getline( in,line ); line+='\n';
		originalLine = line;
	}
	
	chars_toked+=line.size();

	for( int k=0;k<(int)line.size(); ){
		int c=line[k],from=k;
		if( c=='\n' ){
			tokes.push_back( Toke( c,from,++k ) );
			continue;
		}
		if( isspace( c ) ){ ++k;continue; }
		if( c==';' ){
			for( ++k;line[k]!='\n';++k ){}
			continue;
		}
		if( c=='.' && isdigit( line[k+1] ) ){
			for( k+=2;isdigit( line[k] );++k ){}
			tokes.push_back( Toke( FLOATCONST,from,k ) );
			continue;
		}
		if( isdigit( c ) ){
			for( ++k;isdigit( line[k] );++k ){}
			if( line[k]=='.' ){
				for( ++k;isdigit( line[k] );++k ){}
				tokes.push_back( Toke( FLOATCONST,from,k ) );
				continue;
			}
			tokes.push_back( Toke( INTCONST,from,k ) );
			continue;
		}
		if( c=='%' && (line[k+1]=='0' || line[k+1]=='1') ){
			for( k+=2;line[k]=='0'||line[k]=='1';++k ){}
			tokes.push_back( Toke( BINCONST,from,k ) );
			continue;
		}
		if( c=='$' && isxdigit( line[k+1] ) ){
			for( k+=2;isxdigit( line[k] );++k ){}
			tokes.push_back( Toke( HEXCONST,from,k ) );
			continue;
		}
		if( isalpha( c ) ){
			for( ++k;isalnum( line[k] ) || line[k]=='_' || line[k]==':';++k ){
				if (line[k]==':') {
					if ((k+1)<(int)line.size() && line[k+1]==':') {
						k++;
					} else {
						break;
					}
				}
			}

			string ident=tolower(line.substr(from,k-from));

			if( line[k]==' ' && isalpha( line[k+1] ) ){
				int t=k;
				for( t+=2;isalnum( line[t] ) || line[t]=='_';++t ){}
				string s=tolower(line.substr(from,t-from));
				if( lowerTokes.find(s)!=lowerTokes.end() ){
					k=t;ident=s;
				}
			}

			map<string,int>::iterator it=lowerTokes.find( ident );

			if( it==lowerTokes.end() ){
				for( int n=from;n<k;++n ) line[n]=tolower(line[n]);
				tokes.push_back( Toke( IDENT,from,k ) );
				continue;
			}

			tokes.push_back( Toke( it->second,from,k ) );
			continue;
		}
		if( c=='\"' ){
			for( ++k;line[k]!='\"' && line[k]!='\n';++k ){}
			if( line[k]=='\"' ) ++k;
			tokes.push_back( Toke( STRINGCONST,from,k ) );
			continue;
		}
		int n=line[k+1];
		if( (c=='<'&&n=='>')||(c=='>'&&n=='<') ){
			tokes.push_back( Toke( NE,from,k+=2 ) );
			continue;
		}
		if( (c=='<'&&n=='=')||(c=='='&&n=='<') ){
			tokes.push_back( Toke( LE,from,k+=2 ) );
			continue;
		}
		if( (c=='>'&&n=='=')||(c=='='&&n=='>') ){
			tokes.push_back( Toke( GE,from,k+=2 ) );
			continue;
		}
		tokes.push_back( Toke( c,from,++k ) );
	}
	if( !tokes.size() ) {
		cout << "No tokens to parse!" << endl;
		exit(0);
	}
}

int Toker::next(){
	if (++curr_toke==tokes.size() || process_injected) {
		if (!process_injected && !tokes_cache.empty()) {
			tokes = tokes_cache.back();
			tokes_cache.pop_back();

			curr_toke = curr_toke_cache.back();
			curr_toke_cache.pop_back();
			curr_toke++;

			line = line_cache.back();
			line_cache.pop_back();
		} else {
			if (process_injected) {
				process_injected--; 
			} else nextline();
		}
	}
	return curr();
}

void Toker::inject(string code) {
	tokes_cache.push_back(tokes);
	curr_toke_cache.push_back(curr_toke);
	line_cache.push_back(line);

	line = code;
	nextline();
	curr_toke = -1;
	process_injected++;
}

void Toker::rollback() {
	curr_toke--;
}
