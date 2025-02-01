# LightMesh mesh,red#,green#,blue#\[,range#\]\[,light_x#\]\[,light_y#\]\[,light_z#\]

# Parameters

  -----------------------------------------------------------------------
  mesh - mesh handle\
  red# - mesh red value\
  green# - mesh green value\
  blue# - mesh blue value\
  range# (optional) - light range\
  light_x# (optional) - light x position\
  light_y# (optional) - light y position\
  light_z# (optional) - light z position

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Performs a \'fake\' lighting operation on a mesh.\
  \
  You need to use EntityFX ent,2 to enable vertex colors on the target
  mesh before you can see any results.\
  \
  Since V1.83 the default vertex color for primitives is 255,255,255 and
  not 0,0,0, so for LightMesh to effect primitives as it did before, you
  will first need to reset the vertex colors to 0,0,0. You can do this
  using LightMesh mesh,-255,-255,-255.

  -----------------------------------------------------------------------

# [Example](../3d_examples/LightMesh.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480 ,16,2\
  camera=CreateCamera()\
  \
  ent=CreateSphere()\
  EntityFX ent,2 ; enable vertex colors\
  Lightmesh ent,-255,-255,-255 ; reset vertex colors from 255,255,255
  (default) to 0,0,0\
  LightMesh ent,255,255,0,50,-20,20,-20 ; apply fake lighting\
  \
  MoveEntity camera,0,2,-10\
  PointEntity camera,ent\
  \
  While Not KeyDown(1)\
  RenderWorld\
  Flip\
  Wend\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LightMesh&ref=comments){target="_blank"}
to view the latest version of this page online
