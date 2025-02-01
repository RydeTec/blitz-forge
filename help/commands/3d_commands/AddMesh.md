# AddMesh source_mesh,dest_mesh

# Parameters

  -----------------------------------------------------------------------
  source_mesh - source mesh handle\
  dest_mesh - destination mesh handle

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Adds the source mesh to the destination mesh.\
  \
  AddMesh works best with meshes that have previously only had mesh
  commands used with them.\
  \
  So if you want to manipulate a mesh before adding it to another mesh,
  make sure you use ScaleMesh, PositionMesh, PaintMesh etc rather than
  ScaleEntity, PositionEntity, EntityTexture etc before using AddMesh.\
  \
  However, something to be aware of when using commands such as
  RotateMesh is that all mesh commands work from a global origin of
  0,0,0. Therefore it is generally a good idea to scale and rotate a mesh
  before positioning it, otherwise your mesh could end up in unexpected
  positions. Also, when using AddMesh, the origin of the new all-in-one
  mesh will be set at 0,0,0.\
  \
  After using AddMesh, the original source_mesh will still exist,
  therefore use FreeEntity to delete it if you wish to do so.

  -----------------------------------------------------------------------

# [Example](../3d_examples/AddMesh.bb)

  -----------------------------------------------------------------------
  ; AddMesh Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  PositionEntity camera,0,0,-10\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  ; Create tree mesh (upper half)\
  tree=CreateCone()\
  green_br=CreateBrush(0,255,0)\
  PaintMesh tree,green_br\
  ScaleMesh tree,2,2,2\
  PositionMesh tree,0,1.5,0\
  \
  ; Create trunk mesh\
  trunk=CreateCylinder()\
  brown_br=CreateBrush(128,64,0)\
  PaintMesh trunk,brown_br\
  PositionMesh trunk,0,-1.5,0\
  \
  ; Add trunk mesh to tree mesh, to form one whole tree\
  AddMesh trunk,tree\
  \
  ; Free trunk mesh - we don\'t need it anymore\
  FreeEntity trunk\
  \
  While Not KeyDown( 1 )\
  \
  TurnEntity tree,1,1,1\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=AddMesh&ref=comments){target="_blank"}
to view the latest version of this page online
