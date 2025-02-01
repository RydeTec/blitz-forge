# CreateBrush ( \[red#\]\[,green#\]\[,blue#\] )

# Parameters

  -----------------------------------------------------------------------
  red# (optional) - brush red value\
  green# (optional) - brush green value\
  blue# (optional) - brush blue value

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a brush and returns a brush handle.\
  \
  The optional green, red and blue values allow you to set the colour of
  the brush. Values should be in the range 0-255. If omitted the values
  default to 255.\
  \
  A brush is a collection of properties such as Colour, Alpha, Shininess,
  Texture etc that are all stored as part of the brush. Then, all these
  properties can be applied to an entity, mesh or surface at once just by
  using PaintEntity, PaintMesh or PaintSurface.\
  \
  When creating your own mesh, if you wish for certain surfaces to look
  differently from one another, then you will need to use brushes to
  paint individual surfaces. Using commands such as EntityColor,
  EntityAlpha will apply the effect to all surfaces at once, which may
  not be what you wish to achieve.\
  \
  See also: LoadBrush.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CreateBrush.bb)

  -----------------------------------------------------------------------
  ; CreateBrush Example\
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
  tex=LoadTexture(\"media/b3dlogo.jpg\")\
  \
  ; Create brush\
  brush=CreateBrush()\
  \
  ; Apply texture to brush\
  BrushTexture brush,tex\
  \
  ; And some shininess\
  BrushShininess brush,1\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateBrush&ref=comments){target="_blank"}
to view the latest version of this page online
