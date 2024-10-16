
#ifndef TYPE_H
#define TYPE_H

#include "decl.h"

struct FuncType;
struct ArrayType;
struct StructType;
struct ConstType;
struct VectorType;
struct BlitzType;

struct Type{
	virtual ~Type(){}

	virtual bool intType(){ return 0;}
	virtual bool floatType(){ return 0; }
	virtual bool stringType(){ return 0; }

	virtual string name(){ return "N/A"; }

	//casts to inherited types
	virtual FuncType *funcType(){ return 0; }
	virtual ArrayType *arrayType(){ return 0; }
	virtual StructType *structType(){ return 0; }
	virtual ConstType *constType(){ return 0; }
	virtual VectorType *vectorType(){ return 0; }
	virtual BlitzType *blitzType(){ return 0; }

	//operators
	virtual bool canCastTo( Type *t ){ return this==t; }
	virtual bool canPointTo(Type* t) { return false; }
	virtual bool isPointer() { return false; }

	//built in types
	static Type *void_type,*int_type,*float_type,*string_type,*null_type;

	static vector<BlitzType*> blitzTypes;
	bool strict = false;
};

struct FuncType : public Type{
	Type *returnType;
	DeclSeq *params;
	bool userlib,cfunc,custom;
	FuncType( Type *t,DeclSeq *p,bool ulib,bool cfn ):returnType(t),params(p),userlib(ulib),cfunc(cfn){}
	~FuncType(){ delete params; }
	FuncType *funcType(){ return this; }
	string name(){ return returnType->name()+" function"; }
};

struct ArrayType : public Type{
	Type *elementType;int dims;
	ArrayType( Type *t,int n ):elementType(t),dims(n){}
	ArrayType *arrayType(){ return this; }
	string name(){ return elementType->name()+" array"; }
};

struct StructType : public Type{
	string ident;
	string tag;
	DeclSeq *fields;
	StructType( const string &i ):ident(i),fields(0){}
	StructType( const string &i,DeclSeq *f, const string &t ):ident(i),fields( f ),tag(t){}
	~StructType(){ delete fields; }
	StructType *structType(){ return this; }
	virtual bool canCastTo( Type *t );
	string name(){ return "Custom type \""+ident+"\""; }
};

struct BlitzType : public Type{
	string ident;
	BlitzType( const string &i ):ident(i){Type::blitzTypes.push_back(this);}
	BlitzType *blitzType(){ return this; }
	virtual bool canCastTo( Type *t );
	virtual bool canPointTo(Type* t);
	string name(){ return "Blitz type \""+ident+"\""; }
	virtual bool isPointer() { return ident == "BBPointer"; }
};

struct ConstType : public Type{
	Type *valueType;
	int intValue;
	float floatValue;
	string stringValue;
	ConstType( int n ):intValue(n),valueType(Type::int_type){}
	ConstType( float n ):floatValue(n),valueType(Type::float_type){}
	ConstType( const string &n ):stringValue(n),valueType(Type::string_type){}
	ConstType( Type* t ):valueType(t){}
	ConstType *constType(){ return this; }
	string name(){ return valueType->name()+" constant"; }
};

struct VectorType : public Type{
	string label;
	Type *elementType;
	vector<int> sizes;
	VectorType( const string &l,Type *t,const vector<int> &szs ):label(l),elementType(t),sizes(szs){}
	VectorType *vectorType(){ return this; }
	virtual bool canCastTo( Type *t );
	string name(){ return elementType->name()+" vector"; }
};

#endif
