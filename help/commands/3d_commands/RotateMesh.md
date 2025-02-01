# RotateMesh mesh,pitch#,yaw#,roll#

# Parameters

  -----------------------------------------------------------------------
  mesh - mesh handle\
  pitch# - pitch of mesh\
  yaw# - yaw of mesh\
  roll# - roll of mesh

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Rotates all vertices of a mesh by the specified rotation.\
  \
  See also: [RotateEntity](RotateEntity.htm){.small},
  [TurnEntity](TurnEntity.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/RotateMesh.bb)

  -----------------------------------------------------------------------
  ; RotateMesh Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  ; In this example we will demonstrate the use of the RotateMesh
  command.\
  \
  ; Unlike RotateEntity, RotateMesh actually modifies the actual mesh
  structure.\
  \
  ; So whereas using RotateEntity 0,45,0 would only rotate an entity by
  45 degrees the first time it was\
  ; used, RotateMesh 0,45,0 will rotate the mesh every time it is used.\
  \
  ; This is because RotateEntity rotates an entity based on a fixed mesh
  structure, whereas RotateMesh\
  ; actually modifies the mesh structure itself.\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  light=CreateLight()\
  \
  ; Rotate light to give better lighting of cube\
  RotateEntity light,60,30,0\
  \
  ; Create cube mesh\
  cube=CreateCube()\
  \
  ; Position cube in front of camera so we can see it\
  PositionEntity cube,0,0,5\
  \
  While Not KeyDown(1)\
  \
  ; If space bar pressed then rotate mesh by 45 degrees on the y axis.
  Also set syntax\$ text.\
  If KeyHit(57)=True Then RotateMesh cube,0,45,0 : syntax\$=\"RotateMesh
  0,45,0\"\
  \
  RenderWorld\
  \
  Text 0,0,\"Press space to rotate mesh by 45 degrees on the y axis\"\
  Text 0,20,syntax\$\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=RotateMesh&ref=comments){target="_blank"}
to view the latest version of this page online
