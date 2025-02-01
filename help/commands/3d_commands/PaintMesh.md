# PaintMesh mesh,brush

# Parameters

  -----------------------------------------------------------------------
  mesh - mesh handle\
  brush - brush handle

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Paints a mesh with a brush.\
  \
  This has the effect of instantly altering the visible appearance of the
  mesh, assuming the brush\'s properties are different to what was was
  applied to the surface before.\
  \
  The reason for using PaintMesh to apply specific properties to a mesh
  using a brush rather than just using EntityTexture, EntityColor,
  EntityShininess etc, is that you can pre-define one brush, and then
  paint meshes over and over again using just the one command rather than
  lots of separate ones.\
  \
  See also: PaintEntity, PaintSurface.

  -----------------------------------------------------------------------

# [Example](../3d_examples/PaintMesh.bb)

  -----------------------------------------------------------------------
  ; PaintMesh Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
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
  ; And some other effects\
  BrushColor brush,0,0,255\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=PaintMesh&ref=comments){target="_blank"}
to view the latest version of this page online
