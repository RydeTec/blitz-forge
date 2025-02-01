# CopyMesh ( mesh\[,parent\] )

# Parameters

  -----------------------------------------------------------------------
  mesh - handle of mesh to be copied\
  parent (optional) - handle of entity to be made parent of mesh

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a copy of a mesh and returns the newly-created mesh\'s handle.\
  \
  The difference between CopyMesh and CopyEntity is that CopyMesh
  performs a \'deep\' copy of a mesh.\
  \
  CopyMesh is identical to performing new_mesh=CreateMesh() : AddMesh
  mesh,new_mesh

  -----------------------------------------------------------------------

# [Example](../3d_examples/CopyMesh.bb)

  -----------------------------------------------------------------------
  ; CopyMesh Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  crate1=LoadMesh(\"media/wood-crate/wcrate1.3ds\")\
  PositionEntity crate1,-40,0,100\
  \
  crate2=CopyMesh(crate1)\
  PositionEntity crate2,40,0,100\
  \
  While Not KeyDown(1)\
  \
  TurnEntity crate1,1,1,1\
  TurnEntity crate2,1,1,-1\
  \
  RenderWorld\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CopyMesh&ref=comments){target="_blank"}
to view the latest version of this page online
