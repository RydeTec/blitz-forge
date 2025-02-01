# CameraFogMode camera,mode

# Parameters

  -----------------------------------------------------------------------
  camera - camera handle\
  \
  mode -\
  0: no fog (default)\
  1: linear fog

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the camera fog mode.\
  \
  This will enable/disable fogging, a technique used to gradually fade
  out graphics the further they are away from the camera. This can be
  used to avoid \'pop-up\', the moment at which 3D objects suddenly
  appear on the horizon.\
  \
  The default fog colour is black and the default fog range is 1-1000,
  although these can be changed by using CameraFogColor and
  CameraFogRange respectively.\
  \
  Each camera can have its own fog mode, for multiple on-screen fog
  effects.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CameraFogMode.bb)

  -----------------------------------------------------------------------
  ; CameraFogMode Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  PositionEntity camera,0,1,0\
  CameraFogRange camera,1,10\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  plane=CreatePlane()\
  grass_tex=LoadTexture( \"media/mossyground.bmp\" )\
  EntityTexture plane,grass_tex\
  \
  While Not KeyDown( 1 )\
  \
  ; Toggle camera fog mode between 0 and 1 when spacebar is pressed\
  If KeyHit( 57 )=True Then fog_mode=1-fog_mode : CameraFogMode
  camera,fog_mode\
  \
  If KeyDown( 205 )=True Then TurnEntity camera,0,-1,0\
  If KeyDown( 203 )=True Then TurnEntity camera,0,1,0\
  If KeyDown( 208 )=True Then MoveEntity camera,0,0,-0.05\
  If KeyDown( 200 )=True Then MoveEntity camera,0,0,0.05\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor keys to move about the infinite plane\"\
  Text 0,20,\"Press spacebar to toggle between CameraFogMode 0/1\"\
  If fog_mode=False Then Text 0,40,\"CameraFogMode 0\" Else Text
  0,40,\"CameraFogMode 1\"\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CameraFogMode&ref=comments){target="_blank"}
to view the latest version of this page online
