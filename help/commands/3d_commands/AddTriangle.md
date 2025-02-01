# AddTriangle ( surface,v0,v1,v2 )

# Parameters

  -----------------------------------------------------------------------
  surface - surface handle\
  v0 - index number of first vertex of triangle\
  v1 - index number of second vertex of triangle\
  v2 - index number of third vertex of triangle

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Adds a triangle to a surface and returns the triangle\'s index number,
  starting from 0.\
  \
  The v0, v1 and v2 parameters are the index numbers of the vertices
  created using AddVertex.\
  \
  Depending on how the vertices are arranged, then the triangle will only
  be visible from a certain side. Imagine that a triangle\'s vertex
  points are like dot-to-dot pattern, each numbered v0, v1, v2. If these
  dots, starting from v0, through to V2, form a clockwise pattern
  relative to the viewer, then the triangle will be visible. If these
  dots form an anti-clockwise pattern relative to the viewer, then the
  triangle will not be visible.\
  \
  The reason for having one-sided triangles is that it reduces the amount
  of triangles that need to be rendered when one side faces the side of
  an object which won\'t be seen (such as the inside of a snooker ball).
  However, if you wish for a triangle to be two-sided, then you can
  either create two triangles, using the same set of vertex numbers for
  both but assigning them in opposite orders, or you can use CopyEntity
  and FlipMesh together.

  -----------------------------------------------------------------------

# [Example](../3d_examples/AddTriangle.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=AddTriangle&ref=comments){target="_blank"}
to view the latest version of this page online
