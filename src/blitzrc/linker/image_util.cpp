
#include "std.h"
#include "image_util.h"

#ifndef DEMO

#pragma pack( push,1 )
struct Head{
	short machine,num_sects;
	int timedata,sym_table,num_syms;
	short opt_size,chars;
};

struct Opts{
	short magic;
	char major,minor;
	int code_size,data_size,udata_size;
	int entry,code_base,data_base;

	int image_base,sect_align,file_align;
	short major_os,minor_os,major_image,minor_image,major_subsys,minor_subsys;

	int reserved;
	int image_size,headers_size,checksum;

	short subsys,dllchars;

	int stack_reserve,stack_commit,heap_reserve,heap_commit,loadflags;
	
	int dir_entries;
};

struct DDir{
	int rva,size;
};

struct Sect{
	char name[8];
	int virt_size,virt_addr;	//in mem
	int data_size,data_addr;	//on disk
	int relocs,lines;			//file ptrs
	short num_relocs,num_lines;
	int chars;
};

struct Rdir{
	int chars,timedata;
	short major,minor,num_names,num_ids;
};

struct Rent{
	int id,data;
};

struct Rdat{
	int addr,size,cp,zero;
};

struct IcoHeader{
	unsigned short reserved;
	unsigned short type;
	unsigned short count;
};

struct IcoEntry{
	unsigned char width;
	unsigned char height;
	unsigned char color_count;
	unsigned char reserved;
	unsigned short planes;
	unsigned short bit_count;
	unsigned int bytes_in_res;
	unsigned int image_offset;
};

struct GroupIconEntry{
	unsigned char width;
	unsigned char height;
	unsigned char color_count;
	unsigned char reserved;
	unsigned short planes;
	unsigned short bit_count;
	unsigned int bytes_in_res;
	unsigned short resource_id;
};

#pragma pack( pop )

struct Rsrc{
	int id;
	void *data;
	int data_sz;
	vector<Rsrc*> kids;

	Rsrc( int id,Rsrc *p ):id(id),data(0),data_sz(0){
		if( p ) p->kids.push_back( this );
//		cout<<"res id:"<<dec<<id<<hex<<endl;
	}

	~Rsrc(){
		for( ;kids.size();kids.pop_back() ) delete kids.back();
		delete[] (char*)data;
	}
};

struct Section{
	Sect sect;
	char *data;

	Section():data(0){}
	~Section(){ delete[] data; }
};

static char *stub;
static int stub_sz;

static Head *head;
static int head_sz;

static Opts *opts;
static int opts_sz;

static DDir *ddir;
static int ddir_sz;

static vector<Section*> sections;

static Rsrc *rsrc_root;

static const char *img_file;

static void openRsrcDir( Section *s,int off,Rsrc *p ){
	char *data=(char*)s->data;

	Rdir *dir=(Rdir*)(data+off);
	Rent *ent=(Rent*)(dir+1);
	for( int k=0;k<dir->num_ids;++ent,++k ){
		Rsrc *r=d_new Rsrc( ent->id,p );
		if( ent->data<0 ){	//a node - offset is another dir
			openRsrcDir( s,ent->data&0x7fffffff,r );
		}else{				//a leaf
			Rdat *dat=(Rdat*)( data+ent->data );
//			cout<<"dat addr:"<<dat->addr<<" size:"<<dat->size<<endl;
			int sz=dat->size;
			void *src=dat->addr-s->sect.virt_addr+data;
			void *dest=d_new char[sz];
			memcpy( dest,src,sz );
			r->data=dest;
			r->data_sz=sz;
		}
	}
}

static void openRsrcTree( Section *s ){
	rsrc_root=d_new Rsrc( 0,0 );
	openRsrcDir( s,0,rsrc_root );
}

static int rsrcSize( Rsrc *r ){
	if( r->data ) return (sizeof(Rdat)+r->data_sz+7)&~7;
	int sz=sizeof( Rdir );
	for( int k=0;k<(int)r->kids.size();++k ){
		sz+=sizeof( Rent )+rsrcSize( r->kids[k] );
	}
	return sz;
}

static void closeRsrcDir( Section *s,int off,Rsrc *p ){

	int t,k;

	char *data=(char*)s->data;

	Rdir *dir=(Rdir*)(data+off);
	memset( dir,0,sizeof(Rdir) );
	dir->num_ids=p->kids.size();
	Rent *ent=(Rent*)(dir+1);

	//to end of dir...
	off+=sizeof(Rdir)+sizeof(Rent)*p->kids.size();

	t=off;

	//write entries
	for( k=0;k<(int)p->kids.size();++ent,++k ){
		Rsrc *r=p->kids[k];
		ent->id=r->id;
		ent->data=t;
		if( !r->data ) ent->data|=0x80000000;
		t+=rsrcSize( r );
	}

	t=off;

	//write kids...
	for( k=0;k<(int)p->kids.size();++k ){
		Rsrc *r=p->kids[k];
		if( !r->data ){
			closeRsrcDir( s,t,r );
		}else{
			Rdat *dat=(Rdat*)(data+t);
			dat->addr=s->sect.virt_addr+t+sizeof(Rdat);
			dat->size=r->data_sz;
			dat->zero=dat->cp=0;
			memcpy( data+t+sizeof(Rdat),r->data,r->data_sz );
		}
		t+=rsrcSize( r );
	}
}

static int fileAlign( int n ){
	return (n+(opts->file_align-1))&~(opts->file_align-1);
}

static int sectAlign( int n ){
	return (n+(opts->sect_align-1))&~(opts->sect_align-1);
}

static void closeRsrcTree( Section *s ){

	int virt_sz=rsrcSize( rsrc_root );
	int data_sz=fileAlign( virt_sz );

	int virt_delta=sectAlign(virt_sz)-sectAlign(s->sect.virt_size);
	int data_delta=fileAlign(virt_sz)-fileAlign(s->sect.virt_size);


	for( int k=0;k<(int)sections.size();++k ){
		Section *t=sections[k];
		if( t->sect.virt_addr>s->sect.virt_addr ){
			t->sect.virt_addr+=virt_delta;
			if( !strcmp( t->sect.name,".reloc" ) ){
				ddir[5].rva=t->sect.virt_addr;
			}
		}
		if( t->sect.data_addr>s->sect.data_addr ){
			t->sect.data_addr+=data_delta;
		}
	}

	ddir[2].size=virt_sz;
	opts->image_size+=virt_delta;

	s->sect.virt_size=virt_sz;
	s->sect.data_size=data_sz;

	delete[] s->data;
	s->data=d_new char[data_sz];
	closeRsrcDir( s,0,rsrc_root );

	delete rsrc_root;
	rsrc_root=0;
}

static Rsrc *findRsrc( int id,Rsrc *p ){
	for( int k=0;k<(int)p->kids.size();++k ){
		if( p->kids[k]->id==id ) return p->kids[k];
	}
	return 0;
}

static Rsrc *findRsrc( int type,int id,int lang ){
	Rsrc *r=findRsrc( type,rsrc_root );if( !r ) return 0;
	r=findRsrc( id,r );if( !r ) return 0;
	return findRsrc( lang,r );
}

static bool replaceRsrcData( Rsrc *r,const void *data,int data_sz ){
	if( !r || !data || data_sz<0 ) return false;
	delete[] (char*)r->data;
	r->data_sz=data_sz;
	r->data=d_new char[data_sz];
	memcpy( r->data,data,data_sz );
	return true;
}

static bool readFileData( const char *file,vector<char> &data ){
	ifstream in( file,ios_base::binary|ios_base::ate );
	if( !in.is_open() ) return false;

	streamoff sz=in.tellg();
	if( sz<0 ) return false;

	data.resize( (size_t)sz );
	in.seekg( 0,ios_base::beg );

	if( !data.empty() && !in.read( &data[0],data.size() ) ) return false;
	return true;
}

static int iconDim( unsigned char dim ){
	return dim ? dim : 256;
}

static int bestIcoEntry( const vector<char> &ico_data,const IcoHeader *header,const IcoEntry *entries ){
	int best_index=-1;
	long best_score=-1;

	for( int k=0;k<header->count;++k ){
		const IcoEntry &entry=entries[k];
		if( !entry.bytes_in_res ) continue;
		size_t image_end=(size_t)entry.image_offset + (size_t)entry.bytes_in_res;
		if( image_end>ico_data.size() ) continue;

		int width=iconDim( entry.width );
		int height=iconDim( entry.height );
		int bit_count=entry.bit_count ? entry.bit_count : 32;
		long score=(long)width * (long)height * 256L + (long)bit_count * 1024L + (long)entry.bytes_in_res;
		if( score>best_score ){
			best_score=score;
			best_index=k;
		}
	}

	return best_index;
}

static bool buildGroupIconData( const char *ico_file,unsigned short resource_id,vector<char> &group_data,vector<char> &icon_data ){
	vector<char> ico_data;
	if( !readFileData( ico_file,ico_data ) ) return false;
	if( ico_data.size()<sizeof(IcoHeader) ) return false;

	const IcoHeader *header=(const IcoHeader*)&ico_data[0];
	if( header->reserved!=0 || header->type!=1 || !header->count ) return false;

	size_t dir_size=sizeof(IcoHeader) + sizeof(IcoEntry) * header->count;
	if( ico_data.size()<dir_size ) return false;

	const IcoEntry *entries=(const IcoEntry*)( &ico_data[sizeof(IcoHeader)] );
	int best_index=bestIcoEntry( ico_data,header,entries );
	if( best_index<0 ) return false;

	const IcoEntry &entry=entries[best_index];
	if( !entry.bytes_in_res ) return false;
	size_t image_end=(size_t)entry.image_offset + (size_t)entry.bytes_in_res;
	icon_data.assign( ico_data.begin()+entry.image_offset,ico_data.begin()+image_end );

	group_data.resize( sizeof(IcoHeader) + sizeof(GroupIconEntry) );
	IcoHeader *group_header=(IcoHeader*)&group_data[0];
	group_header->reserved=0;
	group_header->type=1;
	group_header->count=1;

	GroupIconEntry *group_entry=(GroupIconEntry*)( &group_data[sizeof(IcoHeader)] );
	group_entry->width=entry.width;
	group_entry->height=entry.height;
	group_entry->color_count=entry.color_count;
	group_entry->reserved=0;
	group_entry->planes=entry.planes;
	group_entry->bit_count=entry.bit_count;
	group_entry->bytes_in_res=entry.bytes_in_res;
	group_entry->resource_id=resource_id;

	return true;
}

static void loadImage( istream &in ){

	int k;

	//read stub
	in.seekg( 0x3c );
	in.read( (char*)&stub_sz,4 );
	stub_sz+=4;stub=d_new char[stub_sz];
	in.seekg( 0 );in.read( stub,stub_sz );

	//read head
	head=d_new Head;
	head_sz=sizeof(Head);
	in.read( (char*)head,head_sz );

	//read opts
	opts=d_new Opts;
	opts_sz=sizeof(Opts);
	in.read( (char*)opts,opts_sz );

	//read data dirs
	ddir_sz=opts->dir_entries * sizeof(DDir);
	ddir=(DDir*)d_new char[ddir_sz];
	in.read( (char*)ddir,ddir_sz );

	//read sects...
	for( k=0;k<head->num_sects;++k ){
		Section *s=d_new Section;
		in.read( (char*)&s->sect,sizeof(Sect) );
		sections.push_back( s );
	}

	for( k=0;k<head->num_sects;++k ){
		Section *s=sections[k];
		if( !s->sect.data_addr ) continue;
		int data_sz=s->sect.data_size;
		s->data=d_new char[data_sz];//char[s->sect.virt_size];
		//memset( s->data,0,s->sect.virt_size );
		in.seekg( s->sect.data_addr );
		in.read( s->data,data_sz );
	}
}

static void saveImage( ostream &out ){

	int k;

	out.write( (char*)stub,stub_sz );
	out.write( (char*)head,head_sz );
	out.write( (char*)opts,opts_sz );
	out.write( (char*)ddir,ddir_sz );

	for( k=0;k<head->num_sects;++k ){
		Section *s=sections[k];
		out.write( (char*)&s->sect,sizeof(Sect) );
	}

	for( k=0;k<head->num_sects;++k ){
		Section *s=sections[k];
		if( !s->sect.data_addr ) continue;
		//assumes sect data is in order!!!!!
		while( out.tellp()<s->sect.data_addr ) out.put( (char)0xbb );
		out.seekp( s->sect.data_addr );
		out.write( s->data,s->sect.data_size );
	}
}

/********************** PUBLIC STUFF ***********************/

bool openImage( const char *img ){
	img_file=img;

	fstream in( img_file,ios_base::binary|ios_base::in );
	loadImage( in );
	in.close();
	return true;
}

bool makeExe( int entry ){
	if( !img_file ) return false;

	head->chars|=0x0002;		//executable
	head->chars&=~0x2000;		//not Dll
	opts->entry=entry;
//	opts->image_base=0x400000;	//have to deal to fix-ups to do this properly.
	return true;
}

bool replaceRsrc( int type,int id,int lang,void *data,int data_sz ){
	if( !img_file ) return false;

	for( int k=0;k<(int)sections.size();++k ){
		Section *s=sections[k];
		if( strcmp( s->sect.name,".rsrc" ) ) continue;

		openRsrcTree( s );
		if( Rsrc *r=findRsrc( type,id,lang ) ){
			delete[] r->data;
			r->data_sz=data_sz;
			r->data=d_new char[data_sz];
			memcpy( r->data,data,data_sz );
			closeRsrcTree( s );
			return true;
		}
		closeRsrcTree( s );
	}
	return false;
}

bool replaceIconRsrc( int group_id,int lang,const char *ico_file ){
	if( !img_file || !ico_file || !ico_file[0] ) return false;

	enum{
		RT_ICON_ID=3,
		RT_GROUP_ICON_ID=14
	};

	for( int k=0;k<(int)sections.size();++k ){
		Section *s=sections[k];
		if( strcmp( s->sect.name,".rsrc" ) ) continue;

		openRsrcTree( s );

		Rsrc *group=findRsrc( RT_GROUP_ICON_ID,group_id,lang );
		if( !group || group->data_sz<(int)(sizeof(IcoHeader)+sizeof(GroupIconEntry)) ){
			closeRsrcTree( s );
			return false;
		}

		IcoHeader *group_header=(IcoHeader*)group->data;
		if( group_header->reserved!=0 || group_header->type!=1 || !group_header->count ){
			closeRsrcTree( s );
			return false;
		}

		int group_size=sizeof(IcoHeader) + sizeof(GroupIconEntry) * group_header->count;
		if( group->data_sz<group_size ){
			closeRsrcTree( s );
			return false;
		}

		GroupIconEntry *group_entry=(GroupIconEntry*)((char*)group->data + sizeof(IcoHeader));
		Rsrc *icon=findRsrc( RT_ICON_ID,group_entry->resource_id,lang );
		if( !icon ){
			closeRsrcTree( s );
			return false;
		}

		vector<char> group_data,icon_data;
		if( !buildGroupIconData( ico_file,group_entry->resource_id,group_data,icon_data ) ){
			closeRsrcTree( s );
			return false;
		}

		bool ok=replaceRsrcData( icon,&icon_data[0],icon_data.size() ) &&
			replaceRsrcData( group,&group_data[0],group_data.size() );
		closeRsrcTree( s );
		return ok;
	}

	return false;
}

void closeImage(){
	if( !img_file ) return;

	fstream out( img_file,ios_base::binary|ios_base::out|ios_base::trunc );
	saveImage( out );
	out.close();

	for( ;sections.size();sections.pop_back() ) delete sections.back();
	delete[] ddir;
	delete opts;
	delete head;
	delete[] stub;
	img_file=0;
}

#endif
