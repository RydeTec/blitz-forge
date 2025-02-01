# CreateSurface ( mesh\[,brush\] )

# Parameters

  -----------------------------------------------------------------------
  mesh - mesh handle\
  brush (optional) - brush handle

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a surface attached to a mesh and returns the surface\'s
  handle.\
  \
  Surfaces are sections of mesh which are then used to attach triangles
  to. You must have at least one surface per mesh in order to create a
  visible mesh, however you can use as many as you like. Splitting a mesh
  up into lots of sections allows you to affect those sections
  individually, which can be a lot more useful than if all the surfaces
  are combined into just one.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CreateSurface.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  mesh = CreateMesh()\
  surf = CreateSurface(mesh)\
  \
  v0 = AddVertex (surf, -5,-5,0, 0 ,0)\
  v1 = AddVertex (surf, 5,-5,0, 1 ,0)\
  v2 = AddVertex (surf, 0, 5,0, 0.5,1)\
  \
  tri = AddTriangle (surf,v0,v2,v1)\
  \
  cam = CreateCamera()\
  MoveEntity cam, 0,0,-7\
  \
  RenderWorld\
  Flip\
  \
  WaitKey\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateSurface&ref=comments){target="_blank"}
to view the latest version of this page online
