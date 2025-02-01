# PositionEntity entity,x#,y#,z#,\[,global\]

# Parameters

  -----------------------------------------------------------------------
  entity - name of entity to be positioned\
  x# - x co-ordinate that entity will be positioned at\
  y# - y co-ordinate that entity will be positioned at\
  z# - z co-ordinate that entity will be positioned at\
  global (optional) - true if the position should be relative to 0,0,0
  rather than a parent entity\'s position. False by default.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Positions an entity at an absolute position in 3D space.\
  \
  Entities are positioned using an x,y,z coordinate system. x, y and z
  each have their own axis, and each axis has its own set of values. By
  specifying a value for each axis, you can position an entity anywhere
  in 3D space. 0,0,0 is the centre of 3D space, and if the camera is
  pointing in the default positive z direction, then positioning an
  entity with a z value of above 0 will make it appear in front of the
  camera, whereas a negative z value would see it disappear behind the
  camera. Changing the x value would see it moving sideways, and changing
  the y value would see it moving up/down.\
  \
  Of course, the direction in which entities appear to move is relative
  to the position and orientation of the camera.\
  \
  See also: [MoveEntity](MoveEntity.htm){.small},
  [TranslateEntity](TranslateEntity.htm){.small},
  [PositionMesh](PositionMesh.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/PositionEntity.bb)

  -----------------------------------------------------------------------
  ; PositionEntity Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  light=CreateLight()\
  \
  cone=CreateCone( 32 )\
  \
  ; Set position values so that cone is positioned in front of camera, so
  we can see it to begin with\
  x#=0\
  y#=0\
  z#=10\
  \
  While Not KeyDown( 1 )\
  \
  ; Change position values depending on key pressed\
  If KeyDown( 203 )=True Then x#=x#-0.1\
  If KeyDown( 205 )=True Then x#=x#+0.1\
  If KeyDown( 208 )=True Then y#=y#-0.1\
  If KeyDown( 200 )=True Then y#=y#+0.1\
  If KeyDown( 44 )=True Then z#=z#-0.1\
  If KeyDown( 30 )=True Then z#=z#+0.1\
  \
  ; Position cone using position values\
  PositionEntity cone,x#,y#,z#\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor/A/Z keys to change cone position\"\
  Text 0,20,\"X Position: \"+x#\
  Text 0,40,\"Y Position: \"+y#\
  Text 0,60,\"Z Position: \"+z#\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=PositionEntity&ref=comments){target="_blank"}
to view the latest version of this page online
