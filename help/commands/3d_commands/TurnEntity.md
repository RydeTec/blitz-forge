# TurnEntity entity,pitch#,yaw#,roll#,\[,global\]

# Parameters

  -----------------------------------------------------------------------
  entity - name of entity to be rotated\
  pitch# - angle in degrees that entity will be pitched\
  yaw# - angle in degrees that entity will be yawed\
  roll# - angle in degrees that entity will be rolled\
  global (optional) -

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Turns an entity relative to its current orientation.\
  \
  Pitch is the same as the x angle of an entity, and is equivalent to
  tilting forward/backwards.\
  \
  Yaw is the same as the y angle of an entity, and is equivalent to
  turning left/right.\
  \
  Roll is the same as the z angle of an entity, and is equivalent to
  tilting left/right.\
  \
  See also: [RotateEntity](RotateEntity.htm){.small},
  [RotateMesh](RotateMesh.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/TurnEntity.bb)

  -----------------------------------------------------------------------
  ; TurnEntity Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  light=CreateLight()\
  \
  cone=CreateCone( 32 )\
  PositionEntity cone,0,0,5\
  \
  While Not KeyDown( 1 )\
  \
  ; Reset turn values - otherwise, the cone will not stop turning!\
  pitch#=0\
  yaw#=0\
  roll#=0\
  \
  ; Change movement values depending on the key pressed\
  If KeyDown( 208 )=True Then pitch#=-1\
  If KeyDown( 200 )=True Then pitch#=1\
  If KeyDown( 203 )=True Then yaw#=-1\
  If KeyDown( 205 )=True Then yaw#=1\
  If KeyDown( 45 )=True Then roll#=-1\
  If KeyDown( 44 )=True Then roll#=1\
  \
  ; Move sphere using movement values\
  TurnEntity cone,pitch#,yaw#,roll#\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor/Z/X keys to turn cone\"\
  Text 0,20,\"Pitch: \"+pitch#\
  Text 0,40,\"Yaw: \"+yaw#\
  Text 0,60,\"Roll: \"+roll#\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TurnEntity&ref=comments){target="_blank"}
to view the latest version of this page online
