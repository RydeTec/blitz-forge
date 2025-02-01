# CameraFogRange camera,near#,far#

# Parameters

  -----------------------------------------------------------------------
  camera - camera handle\
  near# - distance in front of camera that fog starts\
  far# - distance in front of camera that fog ends

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets camera fog range.\
  \
  The near parameter specifies at what distance in front of the camera
  that the fogging effect will start; all 3D object before this point
  will not be faded.\
  \
  The far parameter specifies at what distance in front of the camera
  that the fogging effect will end; all 3D objects beyond this point will
  be completely faded out.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CameraFogRange.bb)

  -----------------------------------------------------------------------
  ; CameraFogRange Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  PositionEntity camera,0,1,0\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  plane=CreatePlane()\
  grass_tex=LoadTexture( \"media/mossyground.bmp\" )\
  EntityTexture plane,grass_tex\
  \
  ; Set camera fog to 1 (linear fog)\
  CameraFogMode camera,1\
  \
  ; Set intial fog range value\
  fog_range=10\
  \
  While Not KeyDown( 1 )\
  \
  ; If square brackets keys pressed then change fog range value\
  If KeyDown( 26 )=True Then fog_range=fog_range-1\
  If KeyDown( 27 )=True Then fog_range=fog_range+1\
  \
  ; Set camera fog range\
  CameraFogRange camera,1,fog_range\
  \
  If KeyDown( 205 )=True Then TurnEntity camera,0,-1,0\
  If KeyDown( 203 )=True Then TurnEntity camera,0,1,0\
  If KeyDown( 208 )=True Then MoveEntity camera,0,0,-0.05\
  If KeyDown( 200 )=True Then MoveEntity camera,0,0,0.05\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor keys to move about the infinite plane\"\
  Text 0,20,\"Press \[ or \] to change CameraFogRange value\"\
  Text 0,40,\"CameraFogRange camera,1,\"+fog_range\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CameraFogRange&ref=comments){target="_blank"}
to view the latest version of this page online
