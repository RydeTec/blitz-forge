
#include "std.h"
#include "gxmovie.h"
#include "gxgraphics.h"

gxMovie::gxMovie( gxGraphics *g,IMultiMediaStream *mm )
:gfx(g),mm_stream( mm ),playing(true),
 vid_stream(0),dd_stream(0),dd_surf(0),dd_sample(0),canvas(0){

	// Every COM call below used to be unchecked: a failure (codec
	// missing, no audio device, surface query rejected) silently left
	// a null pointer that the subsequent call crashed on. Check each
	// HRESULT and bail to a non-playing state on any failure so the
	// dtor can release whatever did get allocated.
	if( mm_stream->GetMediaStream( MSPID_PrimaryVideo,&vid_stream )!=S_OK || !vid_stream ){ playing=false; return; }
	if( vid_stream->QueryInterface( IID_IDirectDrawMediaStream,(void**)&dd_stream )!=S_OK || !dd_stream ){ playing=false; return; }

	DDSURFACEDESC desc={sizeof(desc)};
	if( dd_stream->GetFormat( &desc,0,0,0 )!=S_OK ){ playing=false; return; }

	canvas=gfx->createCanvas( desc.dwWidth,desc.dwHeight,0 );	//gxCanvas::CANVAS_NONDISPLAY );
	if( !canvas ){ playing=false; return; }
	if( canvas->getSurface()->QueryInterface( IID_IDirectDrawSurface,(void**)&dd_surf )!=S_OK || !dd_surf ){ playing=false; return; }

	src_rect.left=src_rect.top=0;
	src_rect.right=desc.dwWidth;src_rect.bottom=desc.dwHeight;

	if( dd_stream->CreateSample( dd_surf,&src_rect,0,&dd_sample )!=S_OK || !dd_sample ){ playing=false; return; }

	mm_stream->SetState( STREAMSTATE_RUN );
}

gxMovie::~gxMovie(){
	if( mm_stream ) mm_stream->SetState( STREAMSTATE_STOP );

	// Null-check each release path -- ctor may have bailed partway.
	if( dd_sample ) dd_sample->Release();
	if( dd_surf ) dd_surf->Release();
	if( dd_stream ) dd_stream->Release();
	if( vid_stream ) vid_stream->Release();
	if( mm_stream ) mm_stream->Release();

	if( canvas ) gfx->freeCanvas( canvas );
}

bool gxMovie::draw( gxCanvas *dest,int x,int y,int w,int h ){
	if( !playing ) return false;
	if( !dd_sample ){ playing=false; return false; }
	if( !dd_sample->Update( 0,0,0,0 ) ){
		RECT dest_rect={x,y,x+w,y+h};
		dest->getSurface()->Blt( &dest_rect,canvas->getSurface(),&src_rect,DDBLT_WAIT,0 );
		dest->damage( dest_rect );
	}else{
		playing=false;
	}
	return playing;
}
