# LoadMesh(filename\$,\[Parent\])

# Parameters

  -----------------------------------------------------------------------
  Filename\$ - Name of the file containing the model to load.\
  Parent (optional) - Specify an entity to act as a Parent to the loaded
  mesh.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  LoadMesh, as the name suggests, Loads a mesh from an .X, .3DS or .B3D
  file (Usually created in advance by one of a number of 3D model
  creation packages) and returns the mesh handle.\
  \
  Any hierarchy and animation information in the file will be ignored.
  Use LoadAnimMesh to maintain hierarchy and animation information.\
  \
  The optional parent parameter allows you to specify a parent entity for
  the mesh so that when the parent is moved the child mesh will move with
  it. However, this relationship is one way; applying movement commands
  to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the mesh being created
  at position 0,0,0 rather than at the parent entity\'s position.\
  \
  See also: LoadAnimMesh.

  -----------------------------------------------------------------------

# [Example](../3d_examples/LoadMesh.bb)

  -----------------------------------------------------------------------
  ; LoadMesh Example\
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
  ; Load mesh\
  drum=LoadMesh(\"media/oil-drum/oildrum.3ds\")\
  \
  PositionEntity drum,0,0,MeshDepth(drum)\*2\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadMesh&ref=comments){target="_blank"}
to view the latest version of this page online
