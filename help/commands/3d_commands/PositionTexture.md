# PositionTexture texture,u_position#,v_position#

# Parameters

  -----------------------------------------------------------------------
  texture - texture handle\
  u_position# - u position of texture\
  v_position# - v position of texture

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Positions a texture at an absolute position.\
  \
  This will have an immediate effect on all instances of the texture
  being used.\
  \
  Positioning a texture is useful for performing scrolling texture
  effects, such as for water etc.

  -----------------------------------------------------------------------

# [Example](../3d_examples/PositionTexture.bb)

  -----------------------------------------------------------------------
  ; PositionTexture Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
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
  ; Set initial uv position values\
  u_position#=1\
  v_position#=1\
  \
  While Not KeyDown( 1 )\
  \
  ; Change uv position values depending on key pressed\
  If KeyDown( 208 )=True Then u_position#=u_position#-0.01\
  If KeyDown( 200 )=True Then u_position#=u_position#+0.01\
  If KeyDown( 203 )=True Then v_position#=v_position#-0.01\
  If KeyDown( 205 )=True Then v_position#=v_position#+0.01\
  \
  ; Position texture\
  PositionTexture tex,u_position#,v_position#\
  \
  TurnEntity cube,0.1,0.1,0.1\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor keys to change uv position values\"\
  Text 0,20,\"u_position#=\"+u_position#\
  Text 0,40,\"v_position#=\"+v_position#\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=PositionTexture&ref=comments){target="_blank"}
to view the latest version of this page online
