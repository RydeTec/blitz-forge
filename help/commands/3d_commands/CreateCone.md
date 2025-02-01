# CreateCone ( \[segments\]\[,solid\]\[,parent\] )

# Parameters

  -----------------------------------------------------------------------
  segments (optional) - cone detail. Defaults to 8.\
  solid (optional) - true for a cone with a base, false for a cone
  without a base. Defaults to true.\
  parent (optional) - parent entity of cone

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a cone mesh/entity and returns its handle.\
  \
  The cone will be centred at 0,0,0 and the base of the cone will have a
  radius of 1.\
  \
  The segments value must be in the range 3-100 inclusive, although this
  is only checked in debug mode. A common mistake is to leave debug mode
  off and specify the parent parameter (usually an eight digit memory
  address) in the place of the segments value. As the amount of polygons
  used to create a cone is exponentially proportional to the segments
  value, this will result in Blitz trying to create a cone with
  unimaginable amounts of polygons! Depending on how unlucky you are,
  your computer will then crash.\
  \
  Example segments values (solid=true):\
  4: 6 polygons - a pyramid\
  8: 14 polygons - bare minimum amount of polygons for a cone\
  16: 30 polygons - smooth cone at medium-high distances\
  32: 62 polygons - smooth cone at close distances\
  \
  The optional parent parameter allow you to specify a parent entity for
  the cone so that when the parent is moved the child cone will move with
  it. However, this relationship is one way; applying movement commands
  to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the cone being created
  at position 0,0,0 rather than at the parent entity\'s position.\
  \
  See also: CreateCube, CreateSphere, CreateCylinder.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CreateCone.bb)

  -----------------------------------------------------------------------
  ; CreateCone Example\
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
  ; Create cone\
  cone=CreateCone()\
  \
  PositionEntity cone,0,0,5\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateCone&ref=comments){target="_blank"}
to view the latest version of this page online
