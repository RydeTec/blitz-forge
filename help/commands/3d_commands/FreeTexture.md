# FreeTexture texture

# Parameters

  --------------------------
  texture - texture handle
  --------------------------

# Description

  -----------------------------------------------------------------------
  Frees up a texture from memory.\
  \
  Freeing a texture means you will not be able to use it again; however,
  entities already textured with it will not lose the texture.

  -----------------------------------------------------------------------

# [Example](../3d_examples/FreeTexture.bb)

  -----------------------------------------------------------------------
  ; FreeTexture Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  cube=CreateCube()\
  PositionEntity cube,0,0,5\
  \
  ; Load texture\
  tex=LoadTexture( \"media/b3dlogo.jpg\" )\
  \
  ; Texture cube with texture\
  EntityTexture cube,tex\
  \
  While Not KeyDown( 1 )\
  \
  ; If spacebar pressed then free texture\
  If KeyHit( 57 )=True Then FreeTexture tex\
  \
  pitch#=0\
  yaw#=0\
  roll#=0\
  \
  If KeyDown( 208 )=True Then pitch#=-1\
  If KeyDown( 200 )=True Then pitch#=1\
  If KeyDown( 203 )=True Then yaw#=-1\
  If KeyDown( 205 )=True Then yaw#=1\
  If KeyDown( 45 )=True Then roll#=-1\
  If KeyDown( 44 )=True Then roll#=1\
  \
  TurnEntity cube,pitch#,yaw#,roll#\
  \
  RenderWorld\
  \
  Text 0,0,\"Press spacebar to free texture\"\
  Text 0,20,\"As you can see this will not affect already textured
  entities\"\
  \
  Flip\
  \
  Wend\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=FreeTexture&ref=comments){target="_blank"}
to view the latest version of this page online
