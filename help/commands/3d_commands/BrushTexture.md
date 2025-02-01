# BrushTexture brush,texture\[,frame\]\[,index\]

# Parameters

  -----------------------------------------------------------------------
  brush - brush handle\
  texture - texture handle\
  frame (optional) - texture frame. Defaults to 0.\
  index (optional) - texture index. Defaults to 0.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Assigns a texture to a brush.\
  \
  The optional frame parameter specifies which animation frame, if any
  exist, should be assigned to the brush.\
  \
  The optional index parameter specifies texture layer that the texture
  should be assigned to. Brushes have up to four texture layers, 0-3
  inclusive.

  -----------------------------------------------------------------------

# [Example](../3d_examples/BrushTexture.bb)

  -----------------------------------------------------------------------
  ; BrushTexture Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
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
  ; Create brush\
  brush=CreateBrush()\
  \
  ; Apply texture to brush\
  BrushTexture brush,tex\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=BrushTexture&ref=comments){target="_blank"}
to view the latest version of this page online
