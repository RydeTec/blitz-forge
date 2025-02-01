# MeshesIntersect (mesh_a,mesh_b )

# Parameters

  -----------------------------------------------------------------------
  mesh_a - mesh_a handle\
  mesh_b - mesh_b handle

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Returns true if the specified meshes are currently intersecting.\
  \
  This is a fairly slow routine - use with discretion\...\
  \
  This command is currently the only polygon-\>polygon collision checking
  routine available in Blitz3D.

  -----------------------------------------------------------------------

# [Example](../3d_examples/MeshesIntersect.bb)

  -----------------------------------------------------------------------
  ; MeshesIntersect Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  drum=LoadMesh(\"media/oil-drum/oildrum.3ds\")\
  PositionEntity drum,-20,0,100\
  \
  crate=LoadMesh(\"media/wood-crate/wcrate1.3ds\")\
  PositionEntity crate,20,0,100\
  \
  While Not KeyDown( 1 )\
  \
  TurnEntity drum,1,1,1\
  TurnEntity crate,-1,-1,-1\
  \
  RenderWorld\
  \
  ; Test to see if drum and crate meshes are intersecting; if so then
  display message to confirm this\
  If MeshesIntersect(drum,crate)=True Then Text 0,0,\"Meshes are
  intersecting!\"\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=MeshesIntersect&ref=comments){target="_blank"}
to view the latest version of this page online
