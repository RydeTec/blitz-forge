# EntityTexture entity,texture\[,frame\]\[,index\]

# Parameters

  -----------------------------------------------------------------------
  entity - entity handle\
  texture - texture handle\
  frame (optional) - frame of texture. Defaults to 0.\
  index (optional) - index number of texture. Should be in the range to
  0-7. Defaults to 0.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Applies a texture to an entity.\
  \
  The optional frame parameter specifies which texture animation frame
  should be used as the texture.\
  \
  The optional index parameter specifies which index number should be
  assigned to the texture. Index numbers are used for the purpose of
  multitexturing. See TextureBlend.\
  \
  A little note about multitexturing and slowdown. Graphics cards support
  a maximum amount of textures per object, which can be used with very
  little, if any, slowdown. For most cards this is two, but for a
  GeForce3 it is four. However, once you use more than this amount, Blitz
  will emulate the effect itself by duplicating objects and textures.
  Obviously, this may then cause slowdown.

  -----------------------------------------------------------------------

# [Example](../3d_examples/EntityTexture.bb)

  -----------------------------------------------------------------------
  ; EntityTexture Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
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
  ; Texture entity\
  EntityTexture cube,tex\
  \
  While Not KeyDown( 1 )\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=EntityTexture&ref=comments){target="_blank"}
to view the latest version of this page online
