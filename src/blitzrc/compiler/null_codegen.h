#ifndef NULL_CODEGEN_H
#define NULL_CODEGEN_H

#include "codegen.h"

class NullCodegen : public Codegen{
public:
	NullCodegen( ostream &out,bool debug ):Codegen( out,debug ){}

	void enter( const string &l,int frameSize ) override{
		(void)l;
		(void)frameSize;
	}

	void code( TNode *code ) override{
		(void)code;
	}

	void leave( TNode *cleanup,int pop_sz ) override{
		(void)cleanup;
		(void)pop_sz;
	}

	void label( const string &l ) override{
		(void)l;
	}

	void i_data( int i,const string &l="" ) override{
		(void)i;
		(void)l;
	}

	void s_data( const string &s,const string &l="" ) override{
		(void)s;
		(void)l;
	}

	void p_data( const string &p,const string &l="" ) override{
		(void)p;
		(void)l;
	}

	void align_data( int n ) override{
		(void)n;
	}

	void flush() override{
	}
};

#endif
