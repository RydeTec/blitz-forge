# CreateSphere ( \[segments\]\[,parent\] )

# Parameters

  -----------------------------------------------------------------------
  segments (optional) - sphere detail. Defaults to 8.\
  parent (optional) - parent entity of sphere

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a sphere mesh/entity and returns its handle.\
  \
  The sphere will be centred at 0,0,0 and will have a radius of 1.\
  \
  The segments value must be in the range 2-100 inclusive, although this
  is only checked in debug mode. A common mistake is to leave debug mode
  off and specify the parent parameter (usually an eight digit memory
  address) in the place of the segments value. As the amount of polygons
  used to create a sphere is exponentially proportional to the segments
  value, this will result in Blitz trying to create a sphere with
  unimaginable amounts of polygons! Depending on how unlucky you are,
  your computer will then crash.\
  \
  Example segments values:\
  8: 224 polygons - bare minimum amount of polygons for a sphere\
  16: 960 polygons - smooth looking sphere at medium-high distances\
  32: 3968 polygons - smooth sphere at close distances\
  \
  The optional parent parameter allow you to specify a parent entity for
  the sphere so that when the parent is moved the child sphere will move
  with it. However, this relationship is one way; applying movement
  commands to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the sphere being
  created at position 0,0,0 rather than at the parent entity\'s
  position.\
  \
  See also: CreateCube, CreateCylinder, CreateCone.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CreateSphere.bb)

  -----------------------------------------------------------------------
  ; CreateSphere Example\
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
  ; Create sphere\
  sphere=CreateSphere()\
  \
  PositionEntity sphere,0,0,5\
  \
  While Not KeyDown( 1 )\
  RenderWorld\
  Flip\
  Wend\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateSphere&ref=comments){target="_blank"}
to view the latest version of this page online
