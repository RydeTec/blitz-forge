# RotateTexture texture,angle#

# Parameters

  -----------------------------------------------------------------------
  texture - texture handle\
  angle# - absolute angle of texture rotation

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Rotates a texture.\
  \
  This will have an immediate effect on all instances of the texture
  being used.\
  \
  Rotating a texture is useful for performing swirling texture effects,
  such as for smoke etc.

  -----------------------------------------------------------------------

# [Example](../3d_examples/RotateTexture.bb)

  -----------------------------------------------------------------------
  ; RotateTexture Example\
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
  ; Texture cube\
  EntityTexture cube,tex\
  \
  ; Set initial texture angle value\
  angle#=1\
  \
  While Not KeyDown( 1 )\
  \
  ; Change texture angle value depending on key pressed\
  If KeyDown( 205 )=True Then angle#=angle#-1\
  If KeyDown( 203 )=True Then angle#=angle#+1\
  \
  ; Rotate texture\
  RotateTexture tex,angle#\
  \
  TurnEntity cube,0.1,0.1,0.1\
  \
  RenderWorld\
  \
  Text 0,0,\"Use left and right cursor keys to change texture angle
  value\"\
  Text 0,20,\"angle#=\"+angle#\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=RotateTexture&ref=comments){target="_blank"}
to view the latest version of this page online
