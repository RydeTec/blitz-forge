#include "std.h"
#include "bbsys.h"

//dummy for error tolerance (worst hack ever)
static int dummyPtr;

//how many strings allocated
static int stringCnt;

//how many objects new'd but not deleted
static int objCnt;

//how many objects deleted but not released
static int unrelObjCnt;

//how many lists have not been deleted
static int listCnt;

//how many objects to alloc per block
static const int OBJ_NEW_INC=512;

//how many strings to alloc per block
static const int STR_NEW_INC=512;

//current data ptr
static BBData *dataPtr;

//chunks of mem - WHAT THE FUCK WAS I ON?!?!?!?
//static list<char*> memBlks;

//strings
static BBStr usedStrs,freeStrs;

//object handle number
static int next_handle;

//object<->handle maps
static map<int,BBObj*> handle_map;
static map<BBObj*,int> object_map;

//reference counts
static map<int, int> reference_map;

//garbage collection switch
static bool gcEnabled = false;

static BBType _bbIntType( BBTYPE_INT );
static BBType _bbFltType( BBTYPE_FLT );
static BBType _bbStrType( BBTYPE_STR );
static BBType _bbCStrType( BBTYPE_CSTR );

static void *bbMalloc( int size ){
	return malloc(size);
/*
	char *c=d_new char[ size ];
	memBlks.push_back( c );
	return c;
*/
}

static void bbFree( void *q ){
	free(q);
/*
	if( !q ) return;
	char *c=(char*)q;
	memBlks.remove( c );
	delete [] c;
*/
}

static void removeStr( BBStr *str ){
	str->next->prev=str->prev;
	str->prev->next=str->next;
}

static void insertStr( BBStr *str,BBStr *next ){
	str->next=next;
	str->prev=next->prev;
	str->prev->next=str;
	next->prev=str;
}

void *BBStr::operator new( size_t size ){
	if( freeStrs.next==&freeStrs ){
		BBStr *t=(BBStr*)bbMalloc( sizeof(BBStr)*STR_NEW_INC );
		for( int k=0;k<STR_NEW_INC;++k ) insertStr( t++,&freeStrs );
	}
	BBStr *t=freeStrs.next;
	removeStr( t );insertStr( t,&usedStrs );
	return t;
}

void BBStr::operator delete( void *q ){
	if( !q ) return;
	BBStr *t=(BBStr*)q;
	removeStr( t );insertStr( t,&freeStrs );
}

BBStr::BBStr(){
	++stringCnt;
}

BBStr::BBStr( const char *s ):string(s){
	++stringCnt;
}

BBStr::BBStr( const char *s,int n ):string(s,n){
	++stringCnt;
}

BBStr::BBStr( const BBStr &s ):string(s){
	++stringCnt;
}

BBStr::BBStr( const string &s ):string(s){
	++stringCnt;
}

BBStr &BBStr::operator=( const char *s ){
	string::operator=( s );return *this;
}

BBStr &BBStr::operator=( const BBStr &s ){
	string::operator=( s );return *this;
}

BBStr &BBStr::operator=( const string &s ){
	string::operator=( s );return *this;
}

BBStr::~BBStr(){
	--stringCnt;
}

BBStr *_bbStrLoad( BBStr **var ){
	return *var ? d_new BBStr( **var ) : d_new BBStr();
}

void _bbStrRelease( BBStr *str ){
	delete str;
}

void _bbStrStore( BBStr **var,BBStr *str ){
	_bbStrRelease( *var );*var=str;
}

BBStr *_bbStrConcat( BBStr *s1,BBStr *s2 ){
	*s1+=*s2;delete s2;return s1;
}

int _bbStrCompare( BBStr *lhs,BBStr *rhs ){
	int n=lhs->compare( *rhs );
	delete lhs;delete rhs;return n;
}

int _bbStrToInt( BBStr *s ){
	int n=atoi( *s );
	delete s;return n;
}

BBStr *_bbStrFromInt( int n ){
	return d_new BBStr( itoa( n ) );
}

float _bbStrToFloat( BBStr *s ){
	float n=(float)atof( *s );
	delete s;return n;
}

BBStr *_bbStrFromFloat( float n ){
	return d_new BBStr( ftoa( n ) );
}

BBStr *_bbStrConst( const char *s ){
	return d_new BBStr( s );
}

void * _bbVecAlloc( BBVecType *type ){
	void *vec=bbMalloc( type->size*4 );
	memset( vec,0,type->size*4 );
	return vec;
}

void _bbVecFree( void *vec,BBVecType *type ){
	if( type->elementType->type==BBTYPE_STR ){
		BBStr **p=(BBStr**)vec;
		for( int k=0;k<type->size;++p,++k ){
			if( *p ) _bbStrRelease( *p );
		}
	}else if( type->elementType->type==BBTYPE_OBJ ){
		BBObj **p=(BBObj**)vec;
		for( int k=0;k<type->size;++p,++k ){
			if( *p ) _bbObjRelease( *p );
		}
	}
	bbFree( vec );
}

void _bbVecBoundsEx(){
	RTEX( "Blitz array index out of bounds" );
}

void _bbUndimArray( BBArray *array ){
	if( void *t=array->data ){
		if( array->elementType==BBTYPE_STR ){
			BBStr **p=(BBStr**)t;
			int size=array->scales[array->dims-1];
			for( int k=0;k<size;++p,++k ){
				if( *p ) _bbStrRelease( *p );
			}
		}else if( array->elementType==BBTYPE_OBJ ){
			BBObj **p=(BBObj**)t;
			int size=array->scales[array->dims-1];
			for( int k=0;k<size;++p,++k ){
				if( *p ) _bbObjRelease( *p );
			}
		}
		bbFree( t );
		array->data=0;
	}
}

void _bbDimArray( BBArray *array ){
	int k;
	for( k=0;k<array->dims;++k ) ++array->scales[k];
	for( k=1;k<array->dims;++k ){
		array->scales[k]*=array->scales[k-1];
	}
	int size=array->scales[array->dims-1];
	array->data=bbMalloc( size*4 );
	memset( array->data,0,size*4 );
}

void _bbArrayBoundsEx(){
	RTEX( "Array index out of bounds" );
}

static void unlinkObj( BBObj *obj ){
	obj->next->prev=obj->prev;
	obj->prev->next=obj->next;
}

static void insertObj( BBObj *obj,BBObj *next ){
	obj->next=next;
	obj->prev=next->prev;
	next->prev->next=obj;
	next->prev=obj;
}

BBObj *_bbObjNew( BBObjType *type ){
	if( type->free.next==&type->free ){
		int obj_size=sizeof(BBObj)+type->fieldCnt*4;
		BBObj *o=(BBObj*)bbMalloc( obj_size*OBJ_NEW_INC );
		for( int k=0;k<OBJ_NEW_INC;++k ){
			insertObj( o,&type->free );
			o=(BBObj*)( (char*)o+obj_size );
		}
	}
	BBObj *o=type->free.next;
	unlinkObj( o );
	o->type=type;
	o->ref_cnt=1;
	o->fields=(BBField*)(o+1);
	for( int k=0;k<type->fieldCnt;++k ){
		switch( type->fieldTypes[k]->type ){
		case BBTYPE_VEC:
			o->fields[k].VEC=_bbVecAlloc( (BBVecType*)type->fieldTypes[k] );
			break;
		default:
			o->fields[k].INT=0;
		}
	}
	insertObj( o,&type->used );
	++unrelObjCnt;
	++objCnt;
	return o;
}

void _bbObjDelete( BBObj *obj ){
	if( !obj ) return;
	BBField *fields=obj->fields;
	if( !fields ) return;
	BBObjType *type=obj->type;
	for( int k=0;k<type->fieldCnt;++k ){
		switch( type->fieldTypes[k]->type ){
		case BBTYPE_STR:
			_bbStrRelease( fields[k].STR );
			break;
		case BBTYPE_OBJ:
			_bbObjRelease( fields[k].OBJ );
			break;
		case BBTYPE_VEC:
			_bbVecFree( fields[k].VEC,(BBVecType*)type->fieldTypes[k] );
			break;
		}
	}
	map<BBObj*,int>::iterator it=object_map.find( obj );
	if( it!=object_map.end() ){
		handle_map.erase( it->second );
		object_map.erase( it );
	}
	obj->fields=0;
	_bbObjRelease( obj );
	--objCnt;
}

void _bbObjDeleteEach( BBObjType *type ){
	BBObj *obj=type->used.next;
	while( obj->type ){
		BBObj *next=obj->next;
		if( obj->fields ) _bbObjDelete( obj );
		obj=next;
	}
}

extern void bbDebugLog( BBStr *t );
extern void bbStop( );

void _bbObjRelease( BBObj *obj ){
	if( !obj || --obj->ref_cnt ) return;
	unlinkObj( obj );
	insertObj( obj,&obj->type->free );
	--unrelObjCnt;
}

void _bbObjStore( BBObj **var,BBObj *obj ){
	if( obj ) ++obj->ref_cnt;	//do this first incase of self-assignment
	_bbObjRelease( *var );
	*var=obj;
}

BBObj *_bbObjLoad(void *var){
	BBObj** var1 = (BBObj**)var;
	if (var1 && *var1) {
		return *var1;
	}
	return 0;
}

void *_bbFieldPtrAdd(void *var,int shft){
	//WHAT IS THIS POINTER ARITHMETIC
	if ((BBObj*)var) {
		char *retVal = (char*)(var);
		for (int i=0;i<shft;i++){
			retVal++;
		}
		return retVal;
	}
	if (debug) {
		RTEX("Object does not exist");
	} else {
		errorLog.push_back("Field reference: Object does not exist");
	}
	dummyPtr = 0;
	return &dummyPtr;
}

int _bbObjCompare( BBObj *o1,BBObj *o2 ){
	return (o1 ? o1->fields : 0)!=(o2 ? o2->fields : 0);
}

BBObj *_bbObjNext( BBObj *obj ){
	if (!obj) {
		if (debug) {
			RTEX("Object does not exist");
		} else {
			errorLog.push_back("ObjNext: Object does not exist");
		}
		return 0;
	}
	do{
		obj=obj->next;
		if( !obj->type ) return 0;
	}while( !obj->fields );
	return obj;
}

BBObj *_bbObjPrev( BBObj *obj ){
	if (!obj) {
		if (debug) {
			RTEX("Object does not exist");
		} else {
			errorLog.push_back("ObjPrev: Object does not exist");
		}
		return 0;
	}
	do{
		obj=obj->prev;
		if( !obj->type ) return 0;
	}while( !obj->fields );
	return obj;
}

BBObj *_bbObjFirst( BBObjType *type ){
	return _bbObjNext( &type->used );
}

BBObj *_bbObjLast( BBObjType *type ){
	return _bbObjPrev( &type->used );
}

void _bbObjInsBefore( BBObj *o1,BBObj *o2 ){
	if (!o1) {
		if (debug) {
			RTEX("Object does not exist (o1)");
		} else {
			errorLog.push_back("ObjInsBefore (o1): Object does not exist");
		}
		return;
	}
	if (!o2) {
		if (debug) {
			RTEX("Object does not exist (o2)");
		} else {
			errorLog.push_back("ObjInsBefore (o2): Object does not exist");
		}
		return;
	}
	if( o1==o2 ) return;
	unlinkObj( o1 );
	insertObj( o1,o2 );
}

void _bbObjInsAfter( BBObj *o1,BBObj *o2 ){
	if (!o1) {
		if (debug) {
			RTEX("Object does not exist (o1)");
		} else {
			errorLog.push_back("ObjInsAfter (o1): Object does not exist");
		}
		return;
	}
	if (!o2) {
		if (debug) {
			RTEX("Object does not exist (o2)");
		} else {
			errorLog.push_back("ObjInsAfter (o2): Object does not exist");
		}
		return;
	}
	if( o1==o2 ) return;
	unlinkObj( o1 );
	insertObj( o1,o2->next );
}

int _bbObjEachFirst( BBObj **var,BBObjType *type ){
	_bbObjStore( var,_bbObjFirst( type ) );
	return *var!=0;
}

int _bbObjEachNext( BBObj **var ){
	_bbObjStore( var,_bbObjNext( *var ) );
	return *var!=0;
}

int _bbObjEachFirst2( BBObj **var,BBObjType *type ){
	*var=_bbObjFirst( type );
	return *var!=0;
}

int _bbObjEachNext2( BBObj **var ){
	*var=_bbObjNext( *var );
	return *var!=0;
}

BBStr *_bbObjToStr( BBObj *obj ){
	if( !obj || !obj->fields ) return d_new BBStr( "[NULL]" );

	static BBObj *root;
	static int recurs_cnt;

	if( obj==root ) return d_new BBStr( "[ROOT]" );
	if( recurs_cnt==8 ) return d_new BBStr( "...." );

	++recurs_cnt;
	BBObj *oldRoot=root;
	if( !root ) root=obj;

	BBObjType *type=obj->type;
	BBField *fields=obj->fields;
	BBStr *s=d_new BBStr("["),*t;
	for( int k=0;k<type->fieldCnt;++k ){
		if( k ) *s+=',';
		switch( type->fieldTypes[k]->type ){
		case BBTYPE_INT:
			t=_bbStrFromInt( fields[k].INT );*s+=*t;delete t;
			break;
		case BBTYPE_FLT:
			t=_bbStrFromFloat( fields[k].FLT );*s+=*t;delete t;
			break;
		case BBTYPE_STR:
			if( fields[k].STR ) *s+='\"'+*fields[k].STR+'\"';
			else *s+="\"\"";
			break;
		case BBTYPE_OBJ:
			t=_bbObjToStr( fields[k].OBJ );*s+=*t;delete t;
			break;
		default:
			*s+="???";
		}
	}
	*s+=']';
	root=oldRoot;
	--recurs_cnt;
	return s;
}

static bool _bbObjIsKindOf( BBObjType *actual,BBObjType *expected ){
	while( actual ){
		if( actual==expected ) return true;
		actual=actual->superType;
	}
	return false;
}

static BBObj *_bbObjCast( BBObj *obj,BBObjType *type ){
	if( !obj || !obj->fields ) return 0;
	return _bbObjIsKindOf( obj->type,type ) ? obj : 0;
}

int _bbObjToHandle( BBObj *obj ){
	if( !obj || !obj->fields ) { return 0; }
	map<BBObj*,int>::const_iterator it=object_map.find( obj );
	if( it!=object_map.end() ) return it->second;
	++next_handle;
	object_map[obj]=next_handle;
	handle_map[next_handle]=obj;
	return next_handle;
}

BBObj *_bbObjFromHandle( int handle,BBObjType *type ){
	map<int,BBObj*>::const_iterator it=handle_map.find( handle );
	if( it==handle_map.end() ) return 0;
	BBObj *obj=it->second;
	return _bbObjCast( obj,type );
}

BBObj *_bbObjFromPointer( int ptr,BBObjType *type ){
	if( !ptr ) return 0;
	BBObj *obj=(BBObj*)ptr;
	return _bbObjCast( obj,type );
}

BBStr *_bbObjTypeName( int ptr ){
	if( !ptr ) return d_new BBStr( "" );
	BBObj *obj=(BBObj*)ptr;
	if( !obj || !obj->fields || !obj->type ) return d_new BBStr( "" );
	if( obj->type->fieldCnt>0 && obj->type->fieldTypes[0]->type==BBTYPE_STR && obj->fields[0].STR ){
		return d_new BBStr( *obj->fields[0].STR );
	}
	if( !obj->type->typeName ) return d_new BBStr( "" );
	return d_new BBStr( obj->type->typeName );
}

int _bbAssertTrue(int t) {
	// Round 4 audit: return semantics used to be `t > 0` which reports
	// a *negative* int as failed (a perfectly valid truthy value in Blitz,
	// e.g. the -1 result of a "True" comparison). Treat any non-zero
	// value as truthy, matching the Blitz convention everywhere else.
	if (t == 0) {
		if (test) {
			gx_runtime->testFailed = true;
			if (debug) {
				gx_runtime->debugInfo("Failed assertion.");
			}
			else {
				cout << "Failed assertion." << endl;
			}
		} else if (debug) {
			gx_runtime->debugLog("Failed assertion.");
		}
	}
	return t != 0;
}

int _bbGetFunctionPointer() {
	intptr_t BasePointer, ReturnAddress, FunctionPointer;

	__asm { //ASM. Do touch if suicidal.
		mov BasePointer, ebp;		// Store current BasePointer
	}

	// Blitz uses X86 Call-Near (E8) instructions to call its own functions.
	// We can simply deduce the Return Address like this because of that.
	//-- Parent_EBP = *EBP
	//-- Parent_RP = Parent_EBP + 16
	ReturnAddress = *(intptr_t*)((*(intptr_t*)BasePointer) + 16);

	// And since it's a Call-Near, the call is offset to the return address.
	FunctionPointer = ReturnAddress + *(intptr_t*)(ReturnAddress - 4);

	return static_cast<int>(FunctionPointer);
}

template<typename T>
T _bbCallFunctionPointer(BBFunction<T> functionPtr, va_list args) {
	int32_t StackPointer;
	
	__asm { // Store Stack Pointer
		mov StackPointer, esp;
	}

	T returnValue = functionPtr(args);

	__asm { // Restore Stack Pointer
		mov esp, StackPointer;
	}

	va_end(args);
	return returnValue;
}

// KNOWN BUG: on MSVC x86 `va_list` is a `char*` into the *caller's*
// stack frame. `std::async(std::launch::async, functionPtr, args)`
// decay-copies the pointer into the worker task, but the worker
// reads through it after this function has returned and the
// caller's frame may have been torn down. That's a use-after-free
// with intermittent symptoms: works in FunctionPointerTest's tight
// Async-then-Poll-then-Await loops because the launching frame is
// still live, fails the moment the async handle escapes its
// launching function (e.g. caller stores the BBThread in a global
// and returns).
//
// The fix needs codegen to plumb a sized args buffer across the
// boundary so the worker has a self-contained copy. Until that
// lands, callers should treat the async API as "must Await before
// the launching function returns" and not store handles long-term.
//
// (Also note: the ESP save/restore brackets a `new` and a
// `std::async` -- both are C++ calls that should not need stack-
// pointer rescue, but the asm is preserved for now because removing
// it interacts with the va_list ABI in ways that warrant a focused
// look in the same future codegen PR.)
template<typename T>
int _bbAsyncCallFunctionPointer(BBFunction<T> functionPtr, va_list args) {
	int32_t StackPointer;

	__asm { // Store Stack Pointer
		mov StackPointer, esp;
	}

	// Create a std::future<int> and store it in a dynamically allocated object
	std::future<T>* futurePtr = new std::future<T>(std::async(std::launch::async, functionPtr, args));

	__asm { // Restore Stack Pointer
		mov esp, StackPointer;
	}

	va_end(args);

	// Return the pointer as intptr_t
	return reinterpret_cast<int>(futurePtr);
}

template<typename T>
T _bbAwaitAsyncCall(int threadPtr) {
	std::future<T>* futurePtr = reinterpret_cast<std::future<T>*>(threadPtr);
	// Guard against null / sentinel handle. Doesn't prevent the
	// double-Await UAF (caller still holds the original int value
	// after we delete) but at least bails on the obvious mis-use.
	if (futurePtr == nullptr) {
		return T();
	}
	T result = futurePtr->get();
	delete futurePtr;
	return result;
}

template<typename T>
int _bbPollAsyncCall(int threadPtr) {
	std::future<T>* futurePtr = reinterpret_cast<std::future<T>*>(threadPtr);
	if (futurePtr == nullptr) {
		return 1;  // null handle: "ready" so the caller stops polling
	}
	return futurePtr->wait_for(std::chrono::milliseconds(1)) == std::future_status::ready;
}

int _bbAsyncThenCall(va_list threadPtr, BBFunction<int> functionPtr) {
	return _bbAsyncCallFunctionPointer(functionPtr, threadPtr);
}

// Wrap thrown payloads in a tagged struct so `catch` doesn't
// accidentally claim stray `char*` exceptions thrown from elsewhere
// (e.g. `throw "literal"` or compiler-generated `bad_alloc` paths
// that wind up looking like `char*` because `va_list` IS `char*` on
// this target).
struct _BBThrown {
	va_list payload;
};

void _bbThrow(va_list args) {
	_BBThrown e{ args };
	throw e;
}

template<typename T>
int _bbTryCatch(BBFunction<T> t_ptr, BBFunction<T> c_ptr, va_list args) {
	try {
		// Call the try function with the provided arguments
		return _bbCallFunctionPointer(t_ptr, args);
	} catch (const _BBThrown& thrown) {
		// Call the catch function with the error code
		return _bbCallFunctionPointer(c_ptr, thrown.payload);
	}
}

int _bbReference(int vPtr) {
	if (vPtr == 0) {
		return 0;
	}

	//cout << "added ref " << vPtr << endl;
	++reference_map[vPtr];
	return vPtr;
}

int _bbRelease(int vPtr, const char *s) {
	if (vPtr == 0) {
		return 0;
	}

	// Round 4 audit: `int count = --reference_map[vPtr]` used to default-
	// construct the slot to 0 when vPtr had never been referenced, then
	// decrement to -1 -- which trips `count < 1` and (with GC on) frees
	// memory that the refcount system never owned. That happened any time
	// a release path ran against a stale handle, an uninitialised local,
	// or an object assigned from outside the refcount machinery. Now
	// treat "not present" as a no-op rather than synthesising an erroneous
	// release.
	auto it = reference_map.find(vPtr);
	if (it == reference_map.end()) {
		return vPtr;
	}

	int count = --(it->second);

	if (count < 1) {
		reference_map.erase(it);

		if (gcEnabled && count == 0) {
			//cout << "deleting ref" << endl;
			if (strcmp(s, "BBCustom") == 0) {
				void* objPtr = reinterpret_cast<void*>(vPtr);
				BBObj* obj = static_cast<BBObj*>(objPtr);

				_bbObjDelete(obj);
			} else if (strcmp(s, "BBList") == 0) {
				_bbVectorFree(vPtr);
			}
		}
	}

	return vPtr;
}

int _bbReferenceCount(int vPtr) {
	if (vPtr == 0) {
		return 0;
	}

	return reference_map[vPtr];
}

int _bbNewVector() {
	std::vector<int>* newVec = new std::vector<int>;
	++listCnt;
	int ptr = reinterpret_cast<int>(newVec);
	_bbReference(ptr);
	return ptr;
}

void _bbVectorPushBack(int aPtr, int valuePtr) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);
	vecPtr->push_back(valuePtr);
	_bbReference(valuePtr);
}

int _bbVectorBack(int aPtr) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);
	return static_cast<int>(vecPtr->back());
}

int _bbVectorFirst(int aPtr) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);
	return static_cast<int>(vecPtr->front());
}

int _bbVectorEmpty(int aPtr) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);
	return vecPtr->empty();
}

int _bbVectorAt(int aPtr, int idx) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);
	return static_cast<int>(vecPtr->at(idx));
}

void _bbVectorRelease(int aPtr, int idx) {
	int ptr = _bbVectorAt(aPtr, idx);
	if (ptr != 0) {
		// NOTE: BBList is type-erased; the element pointer here could be a
		// BBObj, BBList, BBBank, or any other refcounted handle. The legacy
		// dynamic_cast<BBObj*> on a non-polymorphic struct is undefined
		// behaviour, so the value of the cast was effectively "always succeed"
		// — i.e. every element was released as BBCustom. That works for
		// BBObj-only lists (the common case) and is what existing tests
		// assume; lists holding non-BBObj elements (e.g. lists of lists)
		// invoke _bbObjDelete on garbage memory and may crash. A proper fix
		// requires per-element type tagging or a runtime label dispatch in
		// _bbRelease covering all BlitzTypes; both are tracked separately.
		// For now: keep the existing "release as BBCustom" semantics but
		// drop the meaningless dynamic_cast so the intent is honest in the
		// source.
		_bbRelease(ptr, "BBCustom");
	}
}

void _bbVectorClear(int aPtr, int idx) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);

	// Release all elements in the vector
	for (int idx = 0; idx < vecPtr->size(); ++idx) {
		_bbVectorRelease(aPtr, idx);
	}

	vecPtr->clear();
}

int _bbVectorSize(int aPtr) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);
	return vecPtr->size();
}

void _bbVectorInsert(int aPtr, int idx, int valuePtr) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);
	vecPtr->insert(vecPtr->begin() + idx, valuePtr);
	_bbReference(valuePtr);
}

void _bbVectorRemove(int aPtr, int idx) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);
	_bbVectorRelease(aPtr, idx);
	vecPtr->erase(vecPtr->begin() + idx);
}

void _bbVectorReplace(int aPtr, int idx, int valuePtr) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);
	// Ref new before releasing old. Otherwise, if valuePtr is the same pointer
	// already at idx, _bbVectorRemove drops its refcount to 0 and frees it;
	// _bbVectorInsert then references freed memory.
	_bbReference(valuePtr);
	_bbVectorRelease(aPtr, idx);
	vecPtr->erase(vecPtr->begin() + idx);
	vecPtr->insert(vecPtr->begin() + idx, valuePtr);
}

void _bbVectorFree(int aPtr) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);

	// Release all elements in the vector
	for (int idx = 0; idx < vecPtr->size(); ++idx) {
		_bbVectorRelease(aPtr, idx);
	}

	vecPtr->clear();
	delete vecPtr;
	--listCnt;
}

// User-callable FreeList: releases the caller's initial reference (taken by
// _bbNewVector) and lets the refcount system free the vector when the last
// reference drops. Prevents double-free when scope-exit __bbRelease also runs.
void _bbVectorFreeUserCall(int aPtr) {
	_bbRelease(aPtr, "BBList");
}

int _bbVectorFind(int aPtr, int vPtr) {
	void* arrayPtr = reinterpret_cast<void*>(aPtr);
	std::vector<int>* vecPtr = static_cast<std::vector<int>*>(arrayPtr);
	auto it = std::find(vecPtr->begin(), vecPtr->end(), vPtr);

	if (it != vecPtr->end()) {
		int index = std::distance(vecPtr->begin(), it);
		return index;
	}

	return -1;
}

void _bbSetGC(int enabled) {
	gcEnabled = enabled;
}

void _bbNullObjEx(){
	RTEX( "Object does not exist" );
}

void _bbRestore( BBData *data ){
	dataPtr=data;
}

int _bbReadInt(){
	switch( dataPtr->fieldType ){
	case BBTYPE_END:RTEX( "Out of data" );return 0;
	case BBTYPE_INT:return dataPtr++->field.INT;
	case BBTYPE_FLT:return dataPtr++->field.FLT;
	case BBTYPE_CSTR:return atoi( dataPtr++->field.CSTR );
	default:RTEX( "Bad data type" );return 0;
	}
}

float _bbReadFloat(){
	switch( dataPtr->fieldType ){
	case BBTYPE_END:RTEX( "Out of data" );return 0;
	case BBTYPE_INT:return dataPtr++->field.INT;
	case BBTYPE_FLT:return dataPtr++->field.FLT;
	case BBTYPE_CSTR:return atof( dataPtr++->field.CSTR );
	default:RTEX( "Bad data type" );return 0;
	}
}

BBStr *_bbReadStr(){
	switch( dataPtr->fieldType ){
	case BBTYPE_END:RTEX( "Out of data" );return 0;
	case BBTYPE_INT:return d_new BBStr( itoa( dataPtr++->field.INT ) );
	case BBTYPE_FLT:return d_new BBStr( ftoa( dataPtr++->field.FLT ) );
	case BBTYPE_CSTR:return d_new BBStr( dataPtr++->field.CSTR );
	default:RTEX( "Bad data type" );return 0;
	}
}

int _bbAbs( int n ){
	return n>=0 ? n : -n;
}

int _bbSgn( int n ){
	return n>0 ? 1 : (n<0 ? -1 : 0);
}

int _bbMod( int x,int y ){
	return x%y;
}

float _bbFAbs( float n ){
	return n>=0 ? n : -n;
}

float _bbFSgn( float n ){
	return n>0 ? 1 : (n<0 ? -1 : 0);
}

float _bbFMod( float x,float y ){
	return (float)fmod( x,y );
}

float _bbFPow( float x,float y ){
	return (float)pow( x,y );
}

void bbRuntimeStats(){
	gx_runtime->debugLog( ("Active strings 	:"+itoa(stringCnt)).c_str() );
	gx_runtime->debugLog( ("Active objects 	:"+itoa(objCnt)).c_str() );
	gx_runtime->debugLog( ("Unreleased objs	:"+itoa(unrelObjCnt)).c_str() );
	gx_runtime->debugLog( ("Active lists	:"+itoa(listCnt)).c_str() );
	/*
	clog<<"Active strings:"<<stringCnt<<endl;
	clog<<"Active objects:"<<objCnt<<endl;
	clog<<"Unreleased Objects:"<<unrelObjCnt<<endl;
	for( BBStr *t=usedStrs.next;t!=&usedStrs;t=t->next ){
		clog<<"string@"<<(void*)t<<endl;
	}
	*/
}

bool basic_create(){
	next_handle=0;
//	memBlks.clear();
	handle_map.clear();
	object_map.clear();
	stringCnt=objCnt=unrelObjCnt=0;
	usedStrs.next=usedStrs.prev=&usedStrs;
	freeStrs.next=freeStrs.prev=&freeStrs;
	return true;
}

bool basic_destroy(){
	while( usedStrs.next!=&usedStrs ) delete usedStrs.next;
//	while( memBlks.size() ) bbFree( memBlks.back() );
	handle_map.clear();
	object_map.clear();
	return true;
}

void basic_link( void (*rtSym)( const char *sym,void *pc ) ){
	rtSym( "_bbIntType",&_bbIntType );
	rtSym( "_bbFltType",&_bbFltType );
	rtSym( "_bbStrType",&_bbStrType );
	rtSym( "_bbCStrType",&_bbCStrType );

	rtSym( "_bbStrLoad",_bbStrLoad );
	rtSym( "_bbStrRelease",_bbStrRelease );
	rtSym( "_bbStrStore",_bbStrStore );
	rtSym( "_bbStrCompare",_bbStrCompare );
	rtSym( "_bbStrConcat",_bbStrConcat );
	rtSym( "_bbStrToInt",_bbStrToInt );
	rtSym( "_bbStrFromInt",_bbStrFromInt );
	rtSym( "_bbStrToFloat",_bbStrToFloat );
	rtSym( "_bbStrFromFloat",_bbStrFromFloat );
	rtSym( "_bbStrConst",_bbStrConst );
	rtSym( "_bbDimArray",_bbDimArray );
	rtSym( "_bbUndimArray",_bbUndimArray );
	rtSym( "_bbArrayBoundsEx",_bbArrayBoundsEx );
	rtSym( "_bbVecAlloc",_bbVecAlloc );
	rtSym( "_bbVecFree",_bbVecFree );
	rtSym( "_bbVecBoundsEx",_bbVecBoundsEx );
	rtSym( "_bbObjNew",_bbObjNew );
	rtSym( "_bbObjDelete",_bbObjDelete );
	rtSym( "_bbObjDeleteEach",_bbObjDeleteEach );
	rtSym( "_bbObjRelease",_bbObjRelease );
	rtSym( "_bbObjStore",_bbObjStore );
	rtSym( "_bbObjLoad",_bbObjLoad );
	rtSym( "_bbFieldPtrAdd",_bbFieldPtrAdd );
	rtSym( "_bbObjCompare",_bbObjCompare );
	rtSym( "_bbObjNext",_bbObjNext );
	rtSym( "_bbObjPrev",_bbObjPrev );
	rtSym( "_bbObjFirst",_bbObjFirst );
	rtSym( "_bbObjLast",_bbObjLast );
	rtSym( "_bbObjInsBefore",_bbObjInsBefore );
	rtSym( "_bbObjInsAfter",_bbObjInsAfter );
	rtSym( "_bbObjEachFirst",_bbObjEachFirst );
	rtSym( "_bbObjEachNext",_bbObjEachNext );
	rtSym( "_bbObjEachFirst2",_bbObjEachFirst2 );
	rtSym( "_bbObjEachNext2",_bbObjEachNext2 );
	rtSym( "_bbObjToStr",_bbObjToStr );
	rtSym( "_bbObjToHandle",_bbObjToHandle );
	rtSym( "_bbObjFromHandle",_bbObjFromHandle );
	rtSym( "_bbObjFromPointer",_bbObjFromPointer );
	rtSym( "$ObjectType(BBPointer)v_ptr",_bbObjTypeName );
	rtSym("_bbAssertTrue", _bbAssertTrue);
	rtSym("_bbGetFunctionPointer", _bbGetFunctionPointer);
	rtSym("_bbCallFunctionPointer", _bbCallFunctionPointer<int>);
	rtSym("_bbAsyncCallFunctionPointer", _bbAsyncCallFunctionPointer<int>);
	rtSym("_bbAwaitAsyncCall", _bbAwaitAsyncCall<int>);
	rtSym("_bbPollAsyncCall", _bbPollAsyncCall<int>);
	rtSym( "_bbNullObjEx",_bbNullObjEx );
	rtSym( "_bbRestore",_bbRestore );
	rtSym( "_bbReadInt",_bbReadInt );
	rtSym( "_bbReadFloat",_bbReadFloat );
	rtSym( "_bbReadStr",_bbReadStr );
	rtSym( "_bbAbs",_bbAbs );
	rtSym( "_bbSgn",_bbSgn );
	rtSym( "_bbMod",_bbMod );
	rtSym( "_bbFAbs",_bbFAbs );
	rtSym( "_bbFSgn",_bbFSgn );
	rtSym( "_bbFMod",_bbFMod );
	rtSym( "_bbFPow",_bbFPow );
	rtSym( "RuntimeStats",bbRuntimeStats );
	rtSym("%Assert%expr", _bbAssertTrue);

	rtSym("(BBFunction)FunctionPtr", _bbGetFunctionPointer);

	rtSym("(BBPointer)Call(BBFunction)f_ptr(BBPointer)dto", _bbCallFunctionPointer<int>);
	rtSym("(BBThread)Async(BBFunction)f_ptr(BBPointer)dto", _bbAsyncCallFunctionPointer<int>);
	rtSym("(BBPointer)Await(BBThread)t_ptr", _bbAwaitAsyncCall<int>);
	rtSym("%Poll(BBThread)t_ptr", _bbPollAsyncCall<int>);
	rtSym("(BBThread)AsyncThen(BBThread)t_ptr(BBFunction)f_ptr", _bbAsyncThenCall);

	rtSym("(BBList)CreateList", _bbNewVector);
	rtSym("ListAdd(BBList)p_ptr(BBPointer)v_ptr", _bbVectorPushBack);
	rtSym("(BBPointer)ListLast(BBList)a_ptr", _bbVectorBack);
	rtSym("(BBPointer)ListFirst(BBList)a_ptr", _bbVectorFirst);
	rtSym("%ListIsEmpty(BBList)a_ptr", _bbVectorEmpty);
	rtSym("(BBPointer)ListAt(BBList)a_ptr%idx", _bbVectorAt);
	rtSym("ListClear(BBList)a_ptr", _bbVectorClear);
	rtSym("%ListSize(BBList)a_ptr", _bbVectorSize);
	rtSym("ListInsert(BBList)a_ptr%idx(BBPointer)v_ptr", _bbVectorInsert);
	rtSym("ListRemove(BBList)a_ptr%idx", _bbVectorRemove);
	rtSym("ListReplace(BBList)a_ptr%idx(BBPointer)v_ptr", _bbVectorReplace);
	rtSym("%ListFind(BBList)a_ptr(BBPointer)v_ptr", _bbVectorFind);
	rtSym("FreeList(BBList)", _bbVectorFreeUserCall);

	rtSym("_bbReference", _bbReference);
	rtSym("_bbRelease", _bbRelease);
	rtSym("%RefCount(BBPointer)v_ptr", _bbReferenceCount);

	rtSym("_bbSetGC", _bbSetGC);

	rtSym("Throw(BBPointer)dto", _bbThrow);
	rtSym("(BBPointer)TryCatch(BBFunction)t_ptr(BBFunction)c_ptr(BBPointer)dto", _bbTryCatch<int>);
}
