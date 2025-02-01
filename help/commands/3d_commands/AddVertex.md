# AddVertex ( surface,x#,y#,z#\[,u#\]\[,v#\]\[,w#\] )

# Parameters

  -----------------------------------------------------------------------
  surface - surface handle\
  x# - x coordinate of vertex\
  y# - y coordinate of vertex\
  z# - z coordinate of vertex\
  u# (optional) - u texture coordinate of vertex\
  v# (optional) - v texture coordinate of vertex\
  w# (optional) - w texture coordinate of vertex - not used, included for
  future expansion

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Adds a vertex to the specified surface and returns the vertices\' index
  number, starting from 0.\
  \
  x,y,z are the geometric coordinates of the vertex, and u,v,w are
  texture mapping coordinates.\
  \
  A vertex is a point in 3D space which is used to connect edges of a
  triangle together. Without any vertices, you can\'t have any triangles.
  At least three vertices are needed to create one triangle; one for each
  corner.\
  \
  The optional u, v and w parameters allow you to specify texture
  coordinates for a vertex, which will determine how any triangle created
  using those vertices will be texture mapped. The u, v and w parameters
  specified will take effect on both texture coordinate sets (0 and 1).
  This works on the following basis:\
  \
  The top left of an image has the uv coordinates 0,0.\
  The top right has coordinates 1,0\
  The bottom right is 1,1.\
  The bottom left 0,1.\
  \
  Thus, uv coordinates for a vertex correspond to a point in the image.
  For example, coordinates 0.9,0.1 would be near the upper right corner
  of the image.\
  \
  So now imagine you have a normal equilateral triangle. By assigning the
  bottom left vertex a uv coordinate of 0,0, the bottom right a
  coordinate of 1,0 and the top centre 0.5,1, this will texture map the
  triangle with an image that fits it.\
  \
  When adding a vertex its default color is 255,255,255,255.

  -----------------------------------------------------------------------

# [Example](../3d_examples/AddVertex.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=AddVertex&ref=comments){target="_blank"}
to view the latest version of this page online
