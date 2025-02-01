# BrushColor brush,red#,green#,blue#

# Parameters

  -----------------------------------------------------------------------
  brush - brush handle\
  red# - red value of brush\
  green# - green value of brush\
  blue# - blue value of brush

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the colour of a brush.\
  \
  The green, red and blue values should be in the range 0-255. The
  default brush color is 255,255,255.\
  \
  Please note that if EntityFX or BrushFX flag 2 is being used, brush
  colour will have no effect and vertex colours will be used instead.

  -----------------------------------------------------------------------

# [Example](../3d_examples/BrushColor.bb)

  -----------------------------------------------------------------------
  ; BrushColor Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
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
  ; Create brush\
  brush=CreateBrush()\
  \
  ; Set brush color\
  BrushColor brush,0,0,255\
  \
  ; Paint mesh with brush\
  PaintMesh cube,brush\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=BrushColor&ref=comments){target="_blank"}
to view the latest version of this page online
