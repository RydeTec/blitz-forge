# RotateEntity entity,pitch#,yaw#,roll#,\[,global\]

# Parameters

  -----------------------------------------------------------------------
  entity - name of the entity to be rotated\
  pitch# - angle in degrees of pitch rotation\
  yaw# - angle in degrees of yaw rotation\
  roll# - angle in degrees of roll rotation\
  global (optional) - true if the angle rotated should be relative to
  0,0,0 rather than a parent entity\'s orientation. False by default.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Rotates an entity so that it is at an absolute orientation.\
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
  See also: [TurnEntity](TurnEntity.htm){.small},
  [RotateMesh](RotateMesh.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/RotateEntity.bb)

  -----------------------------------------------------------------------
  ; RotateEntity Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
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
  ; Change rotation values depending on the key pressed\
  If KeyDown( 208 )=True Then pitch#=pitch#-1\
  If KeyDown( 200 )=True Then pitch#=pitch#+1\
  If KeyDown( 203 )=True Then yaw#=yaw#-1\
  If KeyDown( 205 )=True Then yaw#=yaw#+1\
  If KeyDown( 45 )=True Then roll#=roll#-1\
  If KeyDown( 44 )=True Then roll#=roll#+1\
  \
  ; Rotate cone using rotation values\
  RotateEntity cone,pitch#,yaw#,roll#\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor/Z/X keys to change cone rotation\"\
  Text 0,20,\"Pitch: \"+pitch#\
  Text 0,40,\"Yaw : \"+yaw#\
  Text 0,60,\"Roll : \"+roll#\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=RotateEntity&ref=comments){target="_blank"}
to view the latest version of this page online
