# CreateCylinder ( \[segments\]\[,solid\]\[,parent\] )

# Parameters

  -----------------------------------------------------------------------
  segments (optional) - cylinder detail. Defaults to 8.\
  solid (optional) - true for a cylinder, false for a tube. Defaults to
  true.\
  parent (optional) - parent entity of cylinder

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a cylinder mesh/entity and returns its handle.\
  \
  The cylinder will be centred at 0,0,0 and will have a radius of 1.\
  \
  The segments value must be in the range 3-100 inclusive, although this
  is only checked in debug mode. A common mistake is to leave debug mode
  off and specify the parent parameter (usually an eight digit memory
  address) in the place of the segments value. As the amount of polygons
  used to create a cylinder is exponentially proportional to the segments
  value, this will result in Blitz trying to create a cylinder with
  unimaginable amounts of polygons! Depending on how unlucky you are,
  your computer may then crash.\
  \
  Example segments values (solid=true):\
  3: 8 polygons - a prism\
  8: 28 polygons - bare minimum amount of polygons for a cylinder\
  16: 60 polygons - smooth cylinder at medium-high distances\
  32: 124 polygons - smooth cylinder at close distances\
  \
  The optional parent parameter allow you to specify a parent entity for
  the cylinder so that when the parent is moved the child cylinder will
  move with it. However, this relationship is one way; applying movement
  commands to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the cylinder being
  created at position 0,0,0 rather than at the parent entity\'s
  position.\
  \
  See also: CreateCube, CreateSphere, CreateCone.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CreateCylinder.bb)

  -----------------------------------------------------------------------
  ; CreateCylinder Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  ; Create cylinder\
  cylinder=CreateCylinder()\
  \
  PositionEntity cylinder,0,0,5\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateCylinder&ref=comments){target="_blank"}
to view the latest version of this page online
