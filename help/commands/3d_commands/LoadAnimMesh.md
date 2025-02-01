# LoadAnimMesh( Filename\$, \[Parent\] )

# Parameters

  -----------------------------------------------------------------------
  Filename\$ - Name of the file containing the model to load.\
  Parent (optional) - Specify an entity to act as a Parent to the loaded
  mesh.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  LoadAnimMesh, similar to LoadMesh, Loads a mesh from an .X, .3DS or
  .B3D file and returns a mesh handle.\
  \
  The difference between LoadMesh and LoadAnimMesh is that any hierarchy
  and animation information present in the file is retained. You can then
  either activate the animation by using the Animate command or find
  child entities within the hierarchy by using the FindChild(),
  GetChild() functions.\
  \
  The optional parent parameter allows you to specify a parent entity for
  the mesh so that when the parent is moved the child mesh will move with
  it. However, this relationship is one way; applying movement commands
  to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the mesh being created
  at position 0,0,0 rather than at the parent entity\'s position.

  -----------------------------------------------------------------------

# [Example](../3d_examples/LoadAnimMesh.bb)

  -----------------------------------------------------------------------
  ; LoadAnimMesh Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  ; In this example we will demonstrate the use of the LoadAnimMesh
  command.\
  \
  ; Quite simply, we will load an anim mesh from file, animate it, and
  then view it.\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  PositionEntity camera,0,20,-100 ; position camera so that robot will be
  in view when loaded\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  ; Load anim mesh\
  robot=LoadAnimMesh(\"media/makbot/mak_robotic.3ds\")\
  \
  ; Animate mesh - this will begin an animation sequence which is updated
  when UpdateWorld is called\
  Animate robot,2\
  \
  While Not KeyDown(1)\
  \
  UpdateWorld ; Update anim - without this our anim mesh will freeze\
  RenderWorld ; Render everything\
  Flip ; Show everything\
  \
  Wend\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadAnimMesh&ref=comments){target="_blank"}
to view the latest version of this page online
