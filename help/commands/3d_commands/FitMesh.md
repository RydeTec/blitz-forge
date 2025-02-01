# FitMesh mesh,x#,y#,z#,width#,height#,depth#\[,uniform\]

# Parameters

  -----------------------------------------------------------------------
  mesh - mesh handle\
  x# - x position of mesh\
  y# - y position of mesh\
  z# - z position of mesh\
  width# - width of mesh\
  height# - height of mesh\
  depth# - depth of mesh\
  uniform (optional) - if true, the mesh will be scaled by the same
  amounts in x, y and z, so will not be distorted. Defaults to false.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Scales and translates all vertices of a mesh so that the mesh occupies
  the specified box.\
  \
  Do not use a width#, height# or depth# value of 0, otherwise all mesh
  data will be destroyed and your mesh will not be displayed. Use a value
  of 0.001 instead for a flat mesh along one axis.\
  \
  See also: [ScaleMesh](ScaleMesh.htm){.small},
  [ScaleEntity](ScaleEntity.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/FitMesh.bb)

  -----------------------------------------------------------------------
  ; FitMesh Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  ; In this example we will demonstrate the use of the FitMesh command.\
  \
  ; First we will use FitMesh on a semi-transparent blue box. This will
  represent the dimensions we will\
  ; be using with FitMesh.\
  \
  ; Then we will use these dimensions on a red cone, so that it appears
  to fit inside the box when the\
  ; space bar is pressed.\
  \
  ; The first time the space bar is pressed a uniform FitMesh will be
  performed, which means the cone\
  ; will be scaled equally along all axis so that at least one axis fits
  the dimensions specified.\
  \
  ; The second time the space bar is pressed a non-unifrom FitMesh will
  be performed, meaning the cone\
  ; will be scaled non-equally along all axes so that all axes fit the
  dimensions specified.\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  light=CreateLight()\
  \
  ; Create cube\
  cube=CreateCube()\
  \
  ; Set cube colour to blue\
  EntityColor cube,0,0,255\
  \
  ; Make cube semi-transparent so we will be able to see cone inside it
  later\
  EntityAlpha cube,0.5\
  \
  ; Use FitMesh on cube to make it a cuboid\
  FitMesh cube,-1,-.5,-1,2,1,2\
  \
  ; Position cube in front of camera so we can see it\
  PositionEntity cube,0,-1,5\
  \
  ; Create cone\
  cone=CreateCone()\
  \
  ; Set cone color to red\
  EntityColor cone,255,0,0\
  \
  ; Position cone in front of camera so we can see it\
  PositionEntity cone,0,-1,5\
  \
  ; Set uniform value to 1 so when space is first pressed, FitMesh will
  be uniform\
  uniform=1\
  \
  While Not KeyDown(1)\
  \
  ; If space bar pressed\....\
  If KeyHit(57)=True\
  \
  ; Set syntax string to show syntax useage\
  syntax\$=\"FitMesh cone,-1,-.5,-1,2,1,2,\"+uniform\
  \
  ; Use FitMesh with cone, using same values as used with cube earlier.
  Cone should now fit in cube.\
  FitMesh cone,-1,-.5,-1,2,1,2,uniform\
  \
  ; Change uniform value from 1 to 0 so when space bar is pressed again
  FitMesh will be non-uniform\
  uniform=0\
  \
  EndIf\
  \
  RenderWorld\
  \
  Text 0,0,\"Press space to use uniform FitMesh with cone\"\
  Text 0,20,\"Press space again to use non-uniform FitMesh with cone\"\
  Text 0,40,syntax\$\
  \
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=FitMesh&ref=comments){target="_blank"}
to view the latest version of this page online
