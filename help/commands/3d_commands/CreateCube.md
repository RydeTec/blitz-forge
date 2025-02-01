# CreateCube( \[parent\] )

# Parameters

  ---------------------------------------------------------------------------
  \[parent\] (optional) - This allows you to set the parent entity of Cube.
  ---------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a cube mesh/entity and returns its handle.\
  \
  The cube will extend from -1,-1,-1 to +1,+1,+1.\
  \
  The optional parent parameter allow you to specify a parent entity for
  the cube so that when the parent is moved the child cube will move with
  it. However, this relationship is one way; applying movement commands
  to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the cube being created
  at position 0,0,0 rather than at the parent entity\'s position.\
  \
  Creation of cubes, cylinders and cones are a great way of getting
  scenes set up quickly, as they can act as placeholders for more complex
  pre-modeled meshes later on in program development.\
  \
  See also: CreateSphere(), CreateCylinder(), CreateCone().

  -----------------------------------------------------------------------

# [Example](../3d_examples/CreateCube.bb)

  -----------------------------------------------------------------------
  ; CreateCube Example\
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
  ; Create cube\
  cube=CreateCube()\
  \
  PositionEntity cube,0,0,5\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateCube&ref=comments){target="_blank"}
to view the latest version of this page online
