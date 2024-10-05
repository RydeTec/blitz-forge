
#include "std.h"
#include <cstdlib>
#include "parser.h"

#ifdef DEMO
static const int TEXTLIMIT=16384;
#else
static const int TEXTLIMIT=1024*1024-1;
#endif

enum{
	STMTS_PROG,STMTS_BLOCK,STMTS_LINE,STMTS_BLOCK_TEST
};

static bool isTerm( int c ){ return c==':' || c=='\n'; }

Parser::Parser( Toker &t ):toker(&t),main_toker(&t){
}

ProgNode *Parser::parse( const string &main, bool testMode ){

	incfile=main;
	test = testMode;

	consts=d_new DeclSeqNode();
	structs=d_new DeclSeqNode();
	structConsts=d_new DeclSeqNode();
	funcs=d_new DeclSeqNode();
	datas=d_new DeclSeqNode();
	StmtSeqNode *stmts=0;

	try{
		stmts=parseStmtSeq( STMTS_PROG );

		if( toker->curr()!=EOF ) exp( "end-of-file" );
	}catch( Ex ){
		delete stmts;delete datas;delete funcs;delete structs;delete structConsts;delete consts;
		throw;
	}

	return d_new ProgNode( consts,structs,structConsts,funcs,datas,stmts );
}

void Parser::ex( const string &s ){
	throw Ex( s,toker->pos(),incfile );
}

void Parser::exp( const string &s ){
	switch( toker->curr() ){
	case NEXT:ex( "'Next' without 'For'" );
	case WEND:ex( "'Wend' without 'While'" );
	case ELSE:case ELSEIF:ex( "'Else' without 'If'" );
	case ENDIF:ex( "'Endif' without 'If'" );
	case ENDFUNCTION:ex( "'End Function' without 'Function'" );
	case UNTIL:ex( "'Until' without 'Repeat'" );
	case FOREVER:ex( "'Forever' without 'Repeat'" );
	case CASE:ex( "'Case' without 'Select'" );
	case ENDSELECT:ex( "'End Select' without 'Select'" );
	}
	ex( "Expecting "+s + " Got: " + toker->text());
}

string Parser::parseIdent(){
	if( toker->curr()!=IDENT ) exp( "identifier near: " + toker->text() );
	string t=toker->text();
	if (t.find(":") != std::string::npos) ex( "Identifiers cannot include the : character");
	toker->next();
	return t;
}

void Parser::parseChar( int c ){
	if( toker->curr()!=c ) exp( string( "'" )+char(c)+string( "'" ) );
	toker->next();
}

StmtSeqNode *Parser::parseStmtSeq( int scope, vector<string> localIdents ){
	a_ptr<StmtSeqNode> stmts( d_new StmtSeqNode( incfile ) );
	stmts->localIdents = localIdents;
	parseStmtSeq( stmts,scope );
	return stmts.release();
}

void Parser::parseStmtSeq( StmtSeqNode *stmts,int scope ){
	for(;;){
		if (strictMode) stmts->setStrict();

		while( toker->curr()==':' || (scope!=STMTS_LINE && toker->curr()=='\n') ) {
			toker->next();
		}
		StmtNode *result=0;

		int pos=toker->pos();

#ifdef DEMO
		if( Toker::chars_toked>TEXTLIMIT ){
			ex( "Demo version source limit exceeded" );
		}
#endif

		// If Asserts, log the asserts before calculating
		int nextAssert = toker->findNext(ASSERT);
		if ((nextAssert > 0 || toker->curr() == ASSERT) && assertText == "") {
			nextAssert++;

			int pCount = 1;
			while (nextAssert > 0) {
				if (assertText != "") assertText += "AND ";

				int tokeCount = nextAssert;
				while (!isTerm(toker->at(tokeCount))) {
					if (toker->at(tokeCount) == ASSERT || toker->at(tokeCount) == '(') {
						if (toker->at(tokeCount) == '(') ++pCount;
						tokeCount++;
						continue;
					}
					if (toker->at(tokeCount) == ')') {
						if (--pCount < 1) break;
					}
					assertText += toker->textAt(tokeCount) + " ";
					tokeCount++;
				}
				int nextTerm = toker->findNext(':', tokeCount);
				nextAssert = toker->findNext(ASSERT, tokeCount);
				if (nextTerm > 0 && nextTerm < nextAssert) break;
			}

			if (assertText != "") {
				assertText = replaceAll(assertText, '"', "'");
				assertText = replaceAll(assertText, '(', "");
				assertText = replaceAll(assertText, ')', "");
				assertText = trim(assertText);
				
				string fullText = "DebugLog(\"Assert: " + assertText + "\"):";

				toker->rollback();
				toker->inject(fullText);
				toker->next();
			}
		} else {
			assertText = "";
		}

		// Disable garbage collection if not in strict mode and strict is not set
		if (scope == STMTS_PROG && stmts->size() == 0 && toker->curr() != USESTRICTTYPING && !strictMode && collectingGarbage) {
			cout << "Included file is not Strict, disabling GC globally" << endl;
			toker->rollback();
			toker->inject("DisableGC");
			toker->next();
		}

		switch( toker->curr() ){
		case INCLUDE:
			{
				if( scope!=STMTS_PROG ) ex( "'Include' can only appear in main program" );
				if( toker->next()!=STRINGCONST ) exp( "include filename" );
				string inc=toker->text();toker->next();
				inc=inc.substr( 1,inc.size()-2 );

				//WIN32 KLUDGE//
				char buff[MAX_PATH],*p;
				if( GetFullPathName( inc.c_str(),MAX_PATH,buff,&p ) ) inc=buff;
				inc=tolower(inc);

				if( included.find( inc )!=included.end() ) break;

				ifstream i_stream( inc.c_str() );
				if( !i_stream.good() ) ex( "Unable to open include file" );

				Toker i_toker( i_stream );

				string t_inc=incfile;incfile=inc;
				Toker *t_toker=toker;toker=&i_toker;

				included.insert( incfile );

				bool wasStrict = strictMode;
				strictMode = false;

				a_ptr<StmtSeqNode> ss( parseStmtSeq( scope ) );
				if( toker->curr()!=EOF ) exp( "end-of-file" );

				result=d_new IncludeNode( incfile,ss.release() );

				toker=t_toker;
				incfile=t_inc;
				strictMode = wasStrict;
			}
			break;
		case IDENT:
			{
				string ident=toker->text();
				toker->next();string tag=parseTypeTag();
				if( arrayDecls.find(ident)==arrayDecls.end() 
					&& toker->curr()!='=' && toker->curr()!='\\' && toker->curr()!='[' ){
					if (test && scope == STMTS_PROG) ex("Test files cannot call functions in global scope.");
					//must be a function
					ExprSeqNode *exprs;
					if( toker->curr()=='(' ){
						//ugly lookahead for optional '()' around statement params
						int nest=1,k;
						for( k=1;;++k ){
							int c=toker->lookAhead( k );
							if( isTerm( c ) ) ex( "Mismatched brackets" );
							else if( c=='(' ) ++nest;
							else if( c==')' && !--nest ) break;
						}
						if( isTerm( toker->lookAhead( ++k ) ) ){
							toker->next();
							exprs=parseExprSeq();
							if( toker->curr()!=')' ) exp( "')'" );
							toker->next();
						}else exprs=parseExprSeq();
					}else exprs=parseExprSeq();
					CallNode *call=d_new CallNode( ident,tag,exprs );
					result=d_new ExprStmtNode( call );
				}else{
					//must be a var
					bool isField = false;
					if (strictMode && toker->curr()=='\\') {
						isField = true;
					}
					a_ptr<VarNode> var( parseVar( ident,tag ) );
					if (strictMode) {
						bool isGlobal = std::find(globalIdents.begin(), globalIdents.end(), ident) != globalIdents.end();
						bool isLocal = std::find(stmts->localIdents.begin(), stmts->localIdents.end(), ident) != stmts->localIdents.end();
						if (!isField && !isGlobal && !isLocal) ex( ident + " assignment should start with local, global or const modifier");
					}
					if( toker->curr()!='=' ) exp( "variable assignment" );
					toker->next();ExprNode *expr=parseExpr( false );
					result=d_new AssNode( var.release(),expr );
				}
			}
			break;
		case IF:
			{
				toker->next();result=parseIf(stmts->localIdents);
				if( toker->curr()==ENDIF ) toker->next();
			}
			break;
		case WHILE:
			{
				if (test && scope == STMTS_PROG) ex("Test files cannot construct loops in global scope.");
				toker->next();
				a_ptr<ExprNode> expr( parseExpr( false ) );
				vector<string> loopLocalIdents = stmts->localIdents;  // Copy existing local identifiers
				a_ptr<StmtSeqNode> loopStmts( parseStmtSeq( STMTS_BLOCK, loopLocalIdents ) );
				int pos=toker->pos();
				if( toker->curr()!=WEND ) exp( "'Wend'" );
				toker->next();
				stmts->localIdents = loopStmts->localIdents;
				result=d_new WhileNode( expr.release(),loopStmts.release(),pos );
			}
			break;
		case REPEAT:
			{
				if (test && scope == STMTS_PROG) ex("Test files cannot construct loops in global scope.");
				toker->next();ExprNode *expr=0;
				vector<string> loopLocalIdents = stmts->localIdents;  // Copy existing local identifiers
				a_ptr<StmtSeqNode> loopStmts( parseStmtSeq( STMTS_BLOCK, loopLocalIdents ) );
				int curr=toker->curr();
				int pos=toker->pos();
				if( curr!=UNTIL && curr!=FOREVER ) exp( "'Until' or 'Forever'" );
				toker->next();if( curr==UNTIL ) expr=parseExpr( false );
				stmts->localIdents = loopStmts->localIdents;
				result=d_new RepeatNode( loopStmts.release(),expr,pos );
			}
			break;
		case SELECT:
			{
				toker->next();ExprNode *expr=parseExpr( false );
				a_ptr<SelectNode> selNode( d_new SelectNode( expr ) );
				vector<string> loopLocalIdents = stmts->localIdents;  // Copy existing local identifiers
				for(;;){
					while( isTerm( toker->curr() ) ) toker->next();
					if( toker->curr()==CASE ){
						toker->next();
						a_ptr<ExprSeqNode> exprs( parseExprSeq() );
						if( !exprs->size() ) exp( "expression sequence" );
						a_ptr<StmtSeqNode> loopStmts( parseStmtSeq( STMTS_BLOCK, loopLocalIdents ) );
						stmts->localIdents = loopStmts->localIdents;
						selNode->push_back( d_new CaseNode( exprs.release(),loopStmts.release() ) );
						continue;
					}else if( toker->curr()==DEFAULT ){
						toker->next();
						a_ptr<StmtSeqNode> loopStmts( parseStmtSeq( STMTS_BLOCK, loopLocalIdents ) );
						stmts->localIdents = loopStmts->localIdents;
						if( toker->curr()!=ENDSELECT ) exp( "'End Select'" );
						selNode->defStmts=loopStmts.release();
						break;
					}else if( toker->curr()==ENDSELECT ){
						break;
					}
					exp( "'Case', 'Default' or 'End Select'" );
				}
				toker->next();
				result=selNode.release();
			}
			break;
		case FOR:
			{
				if (test && scope == STMTS_PROG) ex("Test files cannot construct loops in global scope.");
				a_ptr<VarNode> var;
				vector<string> loopLocalIdents = stmts->localIdents;  // Copy existing local identifiers
				toker->next();var=parseVar();
				if( toker->curr()!='=' ) exp( "variable assignment" );
				if( toker->next()==EACH ){
					toker->next();
					string ident=parseIdent();
					a_ptr<StmtSeqNode> loopStmts( parseStmtSeq( STMTS_BLOCK, loopLocalIdents ) );
					stmts->localIdents = loopStmts->localIdents;
					int pos=toker->pos();
					if( toker->curr()!=NEXT ) exp( "'Next'" );
					toker->next();
					result=d_new ForEachNode( var.release(),ident,loopStmts.release(),pos );
				}else{
					a_ptr<ExprNode> from,to,step;
					from=parseExpr( false );
					if( toker->curr()!=TO ) exp( "'TO'" );
					toker->next();to=parseExpr( false );
					//step...
					if( toker->curr()==STEP ){
						toker->next();step=parseExpr( false );
					}else step=d_new IntConstNode( 1 );
					a_ptr<StmtSeqNode> loopStmts( parseStmtSeq( STMTS_BLOCK, loopLocalIdents ) );
					stmts->localIdents = loopStmts->localIdents;
					int pos=toker->pos();
					if( toker->curr()!=NEXT ) exp( "'Next'" );
					toker->next();
					result=d_new ForNode( var.release(),from.release(),to.release(),step.release(),loopStmts.release(),pos );
				}
			}
			break;
		case EXIT:
			{
				toker->next();result=d_new ExitNode();
			}
			break;
		case GOTO:
			{
				if (test && scope == STMTS_PROG) ex("Test files cannot use GOTO in global scope.");
				toker->next();string t=parseIdent();result=d_new GotoNode( t );
			}
			break;
		case GOSUB:
			{
				if (test && scope == STMTS_PROG) ex("Test files cannot use GOSUB in global scope.");
				toker->next();string t=parseIdent();result=d_new GosubNode( t );
			}
			break;
		case RETURN:
			{
				if (test && scope == STMTS_BLOCK_TEST) ex("Test blocks cannot return.");
				toker->next();result=d_new ReturnNode( parseExpr( true ) );
			}
			break;
		case BBDELETE:
			{
				if( toker->next()==EACH ){
					toker->next();string t=parseIdent();
					result=d_new DeleteEachNode( t );
				}else{
					ExprNode *expr=parseExpr( false );
					result=d_new DeleteNode( expr );
				}
			}
			break;
		case INSERT:
			{
				toker->next();
				a_ptr<ExprNode> expr1( parseExpr( false ) );
				if( toker->curr()!=BEFORE && toker->curr()!=AFTER ) exp( "'Before' or 'After'" );
				bool before=toker->curr()==BEFORE;toker->next();
				a_ptr<ExprNode> expr2( parseExpr( false ) );
				result=d_new InsertNode( expr1.release(),expr2.release(),before );
			}
			break;
		case READ:
			do{
				toker->next();VarNode *var=parseVar();
				StmtNode *stmt=d_new ReadNode( var );
				stmt->pos=pos;pos=toker->pos();
				stmts->push_back( stmt );
			}while( toker->curr()==',' );
			break;
		case RESTORE:
			if( toker->next()==IDENT ){
				result=d_new RestoreNode( toker->text() );toker->next();
			}else result=d_new RestoreNode( "" );
			break;
		case DATA:
			if( scope!=STMTS_PROG ) ex( "'Data' can only appear in main program" );
			do{
				toker->next();
				ExprNode *expr=parseExpr( false );
				datas->push_back( d_new DataDeclNode( expr ) );
			}while( toker->curr()==',' );
			break;
		case TYPE:
			if( scope!=STMTS_PROG ) ex( "'Type' can only appear in main program" );
			toker->next();structs->push_back( parseStructDecl(funcs) );
			break;
		case BBCONST:
			if( scope!=STMTS_PROG ) ex( "'Const' can only appear in main program" );
			do{
				toker->next();
				string ident;string tag;
				DeclNode* node = parseVarDecl( DECL_GLOBAL,true,ident,tag );
				globalIdents.push_back(ident);

				bool isBlitzType=false;
				for (int i=0; i<Type::blitzTypes.size(); i++) {
					if (tolower(Type::blitzTypes[i]->ident)==tolower(tag)) {
						isBlitzType=true;break;
					}
				}

				if ( tag.size()==0 || isBlitzType || tag=="%" || tag=="$" || tag=="#" || tag=="@" ) {
					consts->push_back( node );
				} else {
					structConsts->push_back( node );
				}
			}while( toker->curr()==',' );
			break;
		case FUNCTION:
			if( scope!=STMTS_PROG ) ex( "'Function' can only appear in main program" );
			toker->next();funcs->push_back( parseFuncDecl() );
			break;
		case TEST: {
			if (scope != STMTS_PROG) ex("'Test' can only appear in main program");
			toker->next(); FuncDeclNode* testDecl = parseTestDecl();
			if (test) {
				funcs->push_back(testDecl);
				std::string ident = testDecl->ident;
				std::string tag = testDecl->tag;

				CallNode* call = d_new CallNode(ident, tag, d_new ExprSeqNode());
				result = d_new ExprStmtNode(call);
				result->pos = pos;
				stmts->push_back(result);

				call = d_new CallNode("runtimestats", "", d_new ExprSeqNode());
				result = d_new ExprStmtNode(call);
			}
			break;
		}
		case ASSERT: {
			ExprNode* expr = parseExpr(false);
			result = d_new AssNode(parseVar("", ""), expr);
			break;
		}
		case RELEASE: {
			ExprNode* expr = parseExpr(false);
			result = d_new ExprStmtNode(expr);
			break;
		}
		case DIM:
			do{
				toker->next();
				StmtNode *stmt=parseArrayDecl();
				stmt->pos=pos;pos=toker->pos();
				stmts->push_back( stmt );
			}while( toker->curr()==',' );
			break;
		case LOCAL:
			do{
				toker->next();
				string tempident;string temptag;
				DeclNode *d=parseVarDecl( DECL_LOCAL,false,tempident,temptag );
				stmts->localIdents.push_back(tempident);
				StmtNode *stmt=d_new DeclStmtNode( d );
				stmt->pos=pos;pos=toker->pos();
				stmts->push_back( stmt );
			}while( toker->curr()==',' );
			break;
		case GLOBAL:
			if( scope!=STMTS_PROG ) ex( "'Global' can only appear in main program" );
			do{
				toker->next();
				string tempident;string temptag;
				DeclNode *d=parseVarDecl( DECL_GLOBAL,false,tempident,temptag );
				globalIdents.push_back(tempident);
				StmtNode *stmt=d_new DeclStmtNode( d );
				stmt->pos=pos;pos=toker->pos();
				stmts->push_back( stmt );
			}while( toker->curr()==',' );
			break;
		case USESTRICTTYPING:
			if (stmts->isStrict()) ex("'Strict' may only appear once per file");
			if (scope != STMTS_PROG || stmts->size() > 0) ex("'Strict' must be the first statement in the file");
			toker->next(); stmts->setStrict();
			strictMode = true;
			break;
		case NOTRACE:
			toker->next(); Toker::noTrace = true;
			break;
		case DISABLEGC:
			toker->next();
			result = d_new GCNode(false);
			collectingGarbage = false;
			break;
		case ENABLEGC:
			if (!strictMode) ex("'EnableGC' may only be used in strict mode");
			toker->next();
			result = d_new GCNode(true);
			collectingGarbage = true;
			break;
		case '.':
			{
				if (test && scope == STMTS_PROG) ex("Test files cannot set labels in global scope.");
				toker->next();string t=parseIdent();
				result=d_new LabelNode( t,datas->size() );
			}
			break;
		default:
			return;
			break;
		}

		if( result ){
			result->pos=pos;
			stmts->push_back( result );
		}
	}
}

string Parser::parseTypeTag(){
	switch( toker->curr() ){
	case '@':toker->next();return "@";
	case '%':toker->next();return "%";
	case '#':toker->next();return "#";
	case '$':toker->next();return "$";
	case '.':toker->next();return parseIdent();
	}
	return "";
}

VarNode *Parser::parseVar(){
	string ident=parseIdent();
	string tag=parseTypeTag();
	return parseVar( ident,tag );
}

VarNode *Parser::parseVar( const string &ident,const string &tag ){
	a_ptr<VarNode> var;
	if( toker->curr()=='(' ){
		toker->next();
		a_ptr<ExprSeqNode> exprs( parseExprSeq() );
		if( toker->curr()!=')' ) exp( "')'" );
		toker->next();
		var=d_new ArrayVarNode( ident,tag,exprs.release() );
	}else var=d_new IdentVarNode( ident,tag );

	for(;;){
		if( toker->curr()=='\\' ){
			toker->next();
			string ident=parseIdent();
			string tag=parseTypeTag();
			ExprNode *expr=d_new VarExprNode( var.release() );
			var=d_new FieldVarNode( expr,ident,tag );
		}else if( toker->curr()=='[' ){
			toker->next();
			a_ptr<ExprSeqNode> exprs( parseExprSeq() );
			if( exprs->exprs.size()!=1 || toker->curr()!=']' ) exp( "']'" );
			toker->next();
			ExprNode *expr=d_new VarExprNode( var.release() );
			var=d_new VectorVarNode( expr,exprs.release() );
		}else{
			break;
		}
	}
	return var.release();
}

DeclNode *Parser::parseVarDecl( int kind,bool constant,string &ident,string &tag ){
	int pos=toker->pos();
	if(ident.empty()) ident=parseIdent();
	if(tag.empty()) tag=parseTypeTag();
	DeclNode *d;
	if( toker->curr()=='[' ){
		if( constant ) ex( "Blitz arrays may not be constant" );
		toker->next();
		a_ptr<ExprSeqNode> exprs( parseExprSeq() );
		if( exprs->size()!=1 || toker->curr()!=']' ) exp( "']'" );
		toker->next();
		d=d_new VectorDeclNode( ident,tag,exprs.release(),kind );
	}else{
		ExprNode *expr=0;
		if( toker->curr()=='=' ){
			toker->next();expr=parseExpr( false );
		}else if( constant ) ex( "Constants must be initialized" );
		d=d_new VarDeclNode( ident,tag,kind,constant,expr );
	}
	d->pos=pos;d->file=incfile;
	return d;
}

DimNode *Parser::parseArrayDecl(){
	int pos=toker->pos();
	string ident=parseIdent();
	string tag=parseTypeTag();
	if( toker->curr()!='(' ) exp( "'('" );
	toker->next();a_ptr<ExprSeqNode> exprs( parseExprSeq() );
	if( toker->curr()!=')' ) exp( "')'" );
	if( !exprs->size() ) ex( "can't have a 0 dimensional array" );
	toker->next();
	DimNode *d=d_new DimNode( ident,tag,exprs.release() );
	arrayDecls[ident]=d;
	d->pos=pos;
	return d;
}

DeclNode *Parser::parseFuncDecl(){
	int pos=toker->pos();
	string ident=parseIdent();
	string tag=parseTypeTag();
	if( toker->curr()!='(' ) exp( "'('" );
	a_ptr<DeclSeqNode> params( d_new DeclSeqNode() );

	vector<string> paramIdents = vector<string>();
	if( toker->next()!=')' ){
		for(;;){
			string tempident;string temptag;
			params->push_back( parseVarDecl( DECL_PARAM,false,tempident,temptag ) );
			paramIdents.push_back(tempident);
			if( toker->curr()!=',' ) break;
			toker->next();
		}
		if( toker->curr()!=')' ) exp( "')'" );
	}
	toker->next();
	a_ptr<StmtSeqNode> stmts( parseStmtSeq( STMTS_BLOCK, paramIdents ) );
	if( toker->curr()!=ENDFUNCTION ) exp( "'End Function'" );

	StmtNode *ret=d_new ReturnNode(0);ret->pos=toker->pos();
	stmts->push_back( ret );toker->next();
	DeclNode *d=d_new FuncDeclNode( ident,tag,params.release(),stmts.release() );
	d->pos=pos;d->file=incfile;
	return d;
}

FuncDeclNode* Parser::parseTestDecl() {
	int pos = toker->pos();
	string ident = parseIdent();
	string tag = parseTypeTag();
	if (toker->curr() != '(') exp("'('");
	if (toker->next() != ')') exp("')'");
	a_ptr<DeclSeqNode> params(d_new DeclSeqNode()); // No params for Test blocks
	toker->inject("DebugLog(\"\"):DebugLog(\"Running test " + ident + "...\")");
	toker->next();
	a_ptr<StmtSeqNode> stmts(parseStmtSeq(STMTS_BLOCK_TEST));
	if (toker->curr() != ENDTEST) exp("'End Test'");

	StmtNode* ret = d_new ReturnNode(0); ret->pos = toker->pos();
	stmts->push_back(ret); toker->next();
	FuncDeclNode* d = d_new FuncDeclNode(ident, tag, params.release(), stmts.release());
	d->pos = pos; d->file = incfile;
	return d;
}

DeclNode *Parser::parseStructDecl(DeclSeqNode* &funcs){
	int pos=toker->pos();
	string className = toker->originalTextAt(toker->current_toke());
	string ident=parseIdent();
	string tag=parseTypeTag();
	while( toker->curr()=='\n' ) toker->next();
	a_ptr<DeclSeqNode> fields( d_new DeclSeqNode() );

	// Always declare className as first Field
	string idident; string idtag;
	toker->rollback();
	toker->inject("className$");
	toker->next();
	fields->push_back( parseVarDecl( DECL_FIELD,false,idident,idtag ) );

	// Inherited Fields
	for (int i=0;i<structs->size();++i) {
		StructDeclNode* s = dynamic_cast<StructDeclNode*>(structs->decls.at(i));
		if (s->ident == tag) {
			// Skip the first property from all parents as it is their className
			for (int j=1;j<s->fields->size();++j) {
				VarDeclNode* d = dynamic_cast<VarDeclNode*>(s->fields->decls.at(j));
				fields->push_back(parseVarDecl(DECL_FIELD,false,d->ident,d->tag));
			}
			break;
		}
	}

	// Declared Fields
	while( toker->curr()==FIELD ) {
		do{
			string tempident;string temptag;
			toker->next();
			fields->push_back( parseVarDecl( DECL_FIELD,false,tempident,temptag ) );
		}while( toker->curr()==',' );
		while( toker->curr()=='\n' ) toker->next();
	}

	// Create className Method
	toker->rollback();
	pos=toker->pos();
	toker->inject("$()\nreturn \"" + className + "\"\nEnd Method");
	toker->next();

	DeclNode* node = parseMethDecl(className, "classname", pos);
	funcs->push_back(node);
	while( toker->curr()=='\n' ) toker->next();

	bool hasCreate = false;
	while( toker->curr()==METHOD ) {
		toker->next();
		int pos=toker->pos();
		string methIdent = parseIdent();
		if (tolower(methIdent) == "create") hasCreate = true;
		DeclNode* node = parseMethDecl(className, methIdent, pos);
		funcs->push_back(node);
		while( toker->curr()=='\n' ) toker->next();
	}

	// Implicit constructor
	if (!hasCreate) {
		toker->rollback();
		int pos=toker->pos();
		toker->inject("." + ident + "()\nreturn self\nEnd Method");
		toker->next();

		DeclNode* node = parseMethDecl(className, "create", pos);
		funcs->push_back(node);
		while( toker->curr()=='\n' ) toker->next();
	}

	if (toker->curr()!=ENDTYPE) exp( "'Field' or 'End Type'" );
	toker->next();
	DeclNode *d=d_new StructDeclNode( ident,fields.release(),tag );
	d->pos=pos;d->file=incfile;
	return d;
}

DeclNode *Parser::parseMethDecl(string structIdent, string methIdent, int pos){
	string className=structIdent;
	structIdent=tolower(structIdent);
	string ident=structIdent + "::" + methIdent;
	string tag=parseTypeTag();
	if( toker->curr()!='(' ) exp( "'('" );
	a_ptr<DeclSeqNode> params( d_new DeclSeqNode() );

	vector<string> paramIdents = vector<string>();

	// Implicit self property
	bool processingSelf = true;
	toker->inject("self." + structIdent + "=Null,");

	if( toker->next()!=')' ){
		for(;;){
			string tempident;string temptag;
			params->push_back( parseVarDecl( DECL_PARAM,false,tempident,temptag ) );
			paramIdents.push_back(tempident);
			if( toker->curr()!=',' ) break;
			toker->next();
			if (toker->curr()==')' && processingSelf) break;
			processingSelf = false;
		}
		if( toker->curr()!=')' ) exp( "')'" );
	}

	// Always immediately set the className if constructor
	if (tolower(methIdent) == "create") {
		toker->inject("If (NOT self = Null)\nself\\className=\"" + className + "\"\nEnd If");
	}

	toker->next();
	a_ptr<StmtSeqNode> stmts( parseStmtSeq( STMTS_BLOCK, paramIdents ) );
	if( toker->curr()!=ENDMETHOD ) exp( "'End Method'" );

	StmtNode *ret=d_new ReturnNode(0);ret->pos=toker->pos();
	stmts->push_back( ret );toker->next();
	DeclNode *d=d_new FuncDeclNode( ident,tag,params.release(),stmts.release() );
	d->pos=pos;d->file=incfile;
	return d;
}

IfNode *Parser::parseIf(vector<string> localIdents){
	a_ptr<ExprNode> expr;
	a_ptr<StmtSeqNode> stmts,elseOpt;

	expr=parseExpr( false );
	if (toker->curr() == THEN) toker->next();

	bool blkif=isTerm( toker->curr() );
	stmts=parseStmtSeq( blkif ? STMTS_BLOCK : STMTS_LINE, localIdents );

	if( toker->curr()==ELSEIF ){
		int pos=toker->pos();
		toker->next();
		IfNode *ifnode=parseIf();
		ifnode->pos=pos;
		elseOpt=d_new StmtSeqNode( incfile );
		elseOpt->push_back( ifnode );
	}else if( toker->curr()==ELSE ){
		toker->next();
		elseOpt=parseStmtSeq( blkif ? STMTS_BLOCK : STMTS_LINE, localIdents );
	}
	if( blkif ){
		if( toker->curr()!=ENDIF ) exp( "'EndIf'" );
	}else if( toker->curr()!='\n' ) exp( "end-of-line" );

	return d_new IfNode( expr.release(),stmts.release(),elseOpt.release() );
}

ExprSeqNode *Parser::parseExprSeq(){
	a_ptr<ExprSeqNode> exprs( d_new ExprSeqNode() );
	bool opt=true;
	while( ExprNode *e=parseExpr( opt ) ){
		exprs->push_back( e );
		if( toker->curr()!=',' ) break;
		toker->next();opt=false;
	}
	return exprs.release();
}

ExprNode *Parser::parseExpr( bool opt ){
	if( toker->curr()==NOT ){
		toker->next();
		ExprNode *expr=parseExpr1( false );
		return d_new RelExprNode( '=',expr,d_new IntConstNode( 0 ) );
	}
	return parseExpr1( opt );
}

ExprNode *Parser::parseExpr1( bool opt ){

	a_ptr<ExprNode> lhs( parseExpr2( opt ) );
	if( !lhs ) return 0;
	for(;;){
		int c=toker->curr();
		if( c!=AND && c!=OR && c!=XOR ) return lhs.release();
		toker->next();ExprNode *rhs=parseExpr2( false );
		lhs=d_new BinExprNode( c,lhs.release(),rhs );
	}
}

ExprNode *Parser::parseExpr2( bool opt ){

	a_ptr<ExprNode> lhs( parseExpr3( opt ) );
	if( !lhs ) return 0;
	for(;;){
		int c=toker->curr();
		if( c!='<' && c!='>' && c!='=' && c!=LE && c!=GE && c!=NE ) return lhs.release();
		toker->next();ExprNode *rhs=parseExpr3( false );
		lhs=d_new RelExprNode( c,lhs.release(),rhs );
	}
}

ExprNode *Parser::parseExpr3( bool opt ){

	a_ptr<ExprNode> lhs( parseExpr4( opt ) );
	if( !lhs ) return 0;
	for(;;){
		int c=toker->curr();
		if( c!='+' && c!='-' ) return lhs.release();
		toker->next();ExprNode *rhs=parseExpr4( false );
		lhs=d_new ArithExprNode( c,lhs.release(),rhs );
	}
}

ExprNode *Parser::parseExpr4( bool opt ){
	a_ptr<ExprNode> lhs( parseExpr5( opt ) );
	if( !lhs ) return 0;
	for(;;){
		int c=toker->curr();
		if( c!=SHL && c!=SHR && c!=SAR ) return lhs.release();
		toker->next();ExprNode *rhs=parseExpr5( false );
		lhs=d_new BinExprNode( c,lhs.release(),rhs );
	}
}

ExprNode *Parser::parseExpr5( bool opt ){

	a_ptr<ExprNode> lhs( parseExpr6( opt ) );
	if( !lhs ) return 0;
	for(;;){
		int c=toker->curr();
		if( c!='*' && c!='/' && c!=MOD ) return lhs.release();
		toker->next();ExprNode *rhs=parseExpr6( false );
		lhs=d_new ArithExprNode( c,lhs.release(),rhs );
	}
}

ExprNode *Parser::parseExpr6( bool opt ){

	a_ptr<ExprNode> lhs( parseUniExpr( opt ) );
	if( !lhs ) return 0;
	for(;;){
		int c=toker->curr();
		if( c!='^' ) return lhs.release();
		toker->next();ExprNode *rhs=parseUniExpr( false );
		lhs=d_new ArithExprNode( c,lhs.release(),rhs );
	}
}

ExprNode *Parser::parseUniExpr( bool opt ){

	ExprNode *result=0;
	string t;

	int c=toker->curr();
	switch( c ){
	case BBINT:
		if( toker->next()=='%' ) toker->next();
		result=parseUniExpr( false );
		result=d_new CastNode( result,Type::int_type );
		break;
	case BBFLOAT:
		if( toker->next()=='#' ) toker->next();
		result=parseUniExpr( false );
		result=d_new CastNode( result,Type::float_type );
		break;
	case BBSTR:
		if( toker->next()=='$' ) toker->next();
		result=parseUniExpr( false );
		result=d_new CastNode( result,Type::string_type );
		break;
	case BBPOINTER:
		if (toker->next() == '@') toker->next();
		result = parseUniExpr(false);
		for (int i = 0; i < Type::blitzTypes.size(); i++) {
			if (tolower(Type::blitzTypes[i]->ident) == "bbpointer") {
				result = d_new CastNode(result, Type::blitzTypes[i]);
				break;
			}
		}
		break;
	case OBJECT:
		if( toker->next()=='.' ) toker->next();
		t=parseIdent();
		result=parseUniExpr( false );
		result=d_new ObjectCastNode( result,t );
		break;
	case BBHANDLE:
		toker->next();
		result=parseUniExpr( false );
		result=d_new ObjectHandleNode( result );
		break;
	case BEFORE:
		toker->next();
		result=parseUniExpr( false );
		result=d_new BeforeNode( result );
		break;
	case AFTER:
		toker->next();
		result=parseUniExpr( false );
		result=d_new AfterNode( result );
		break;
	case ASSERT:
		toker->next();
		if (toker->curr() != '(') exp("'('");
		result = parseUniExpr(false);
		result = d_new AssertNode(result);
		break;
	case RECAST:
		if( toker->next()=='.' ) toker->next();
		t=parseIdent();
		result=parseUniExpr( false );
		result=d_new RecastNode( result,t );
		break;
	case RELEASE:
		if( toker->next()=='.' ) toker->next();
		t=parseIdent();
		result=parseUniExpr( false );
		result=d_new ReleaseNode( result,t );
		break;
	case REFERENCE:
		toker->next();
		result=parseUniExpr( false );
		result=d_new ReferenceNode( result );
		break;
	case '+':case '-':case '~':case ABS:case SGN:
		toker->next();
		result=parseUniExpr( false );
		if( c=='~' ){
			result=d_new BinExprNode( XOR,result,d_new IntConstNode( -1 ) );
		}else{
			result=d_new UniExprNode( c,result );
		}
		break;
	default:
		result=parsePrimary( opt );
	}
	return result;
}

ExprNode *Parser::parsePrimary( bool opt ){

	a_ptr<ExprNode> expr;
	string t,ident,tag;
	ExprNode *result=0;
	int n,k;

	switch( toker->curr() ){
	case '(':
		toker->next();
		expr=parseExpr( false );
		if( toker->curr()!=')' ) exp( "')'" );
		toker->next();
		result=expr.release();
		break;
	case BBNEW:
		toker->next();t=parseIdent();
		result=d_new NewNode( t );
		if( toker->curr()=='(') { 
			toker->next();
			a_ptr<ExprSeqNode> exprs( parseExprSeq() );
			exprs->exprs.insert(exprs->exprs.begin(), result);
			if( toker->curr()!=')' ) exp( "')'" );
			toker->next();
			result=d_new CallNode( t + "::create",t,exprs.release() );
		} else {
			if (strictMode) {
				ex( "New must call constructor" );
			}
		}
		break;
	case FIRST:
		toker->next();t=parseIdent();
		result=d_new FirstNode( t );
		break;
	case LAST:
		toker->next();t=parseIdent();
		result=d_new LastNode( t );
		break;
	case NULLCONST:
		result=d_new NullConstNode();
		toker->next();
		break;
	case INTCONST:
		result=d_new IntConstNode( atoi( toker->text() ) );
		toker->next();
		break;
	case FLOATCONST:
		result=d_new FloatConstNode( (float)atof( toker->text() ) );
		toker->next();
		break;
	case STRINGCONST:
		t=toker->text();
		result=d_new StringConstNode( t.substr( 1,t.size()-2 ) );
		toker->next();
		break;
	case BINCONST:
		n=0;t=toker->text();
		for( k=1;k<(int)t.size();++k ) n=(n<<1)|(t[k]=='1');
		result=d_new IntConstNode( n );
		toker->next();
		break;
	case HEXCONST:
		n=0;t=toker->text();
		for( k=1;k<(int)t.size();++k ) n=(n<<4)|( isdigit(t[k]) ? t[k]&0xf : (t[k]&7)+9 );
		result=d_new IntConstNode( n );
		toker->next();
		break;
	case PI:
		result=d_new FloatConstNode( 3.1415926535897932384626433832795f );
		toker->next();break;
	case BBTRUE:
		result=d_new IntConstNode( 1 );
		toker->next();break;
	case BBFALSE:
		result=d_new IntConstNode( 0 );
		toker->next();break;
	case IDENT:
		ident=toker->text();
		toker->next();tag=parseTypeTag();
		if( toker->curr()=='(' && arrayDecls.find(ident)==arrayDecls.end() ){
			//must be a func
			toker->next();
			a_ptr<ExprSeqNode> exprs( parseExprSeq() );
			if( toker->curr()!=')' ) exp( "')'" );
			toker->next();
			result=d_new CallNode( ident,tag,exprs.release() );
		}else{
			//must be a var
			VarNode *var=parseVar( ident,tag );
			result=d_new VarExprNode( var );
		}
		break;
	default:
		if( !opt ) exp( "expression" );
	}
	return result;
}
