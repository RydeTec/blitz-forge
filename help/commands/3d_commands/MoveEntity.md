# MoveEntity entity,x#,y#,z#

# Parameters

  -----------------------------------------------------------------------
  entity - name of entity to be moved\
  x# - x amount that entity will be moved by\
  y# - y amount that entity will be moved by\
  z# - z amount that entity will be moved by

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Moves an entity relative to its current position and orientation.\
  \
  What this means is that an entity will move in whatever direction it is
  facing. So for example if you have an game character is upright when
  first loaded into Blitz3D and it remains upright (i.e. turns left or
  right only), then moving it by a z amount will always see it move
  forward or backward, moving it by a y amount will always see it move up
  or down, and moving it by an x amount will always see it strafe.\
  \
  See also: [TranslateEntity](TranslateEntity.htm){.small},
  [PositionEntity](PositionEntity.htm){.small},
  [PositionMesh](PositionMesh.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/MoveEntity.bb)

  -----------------------------------------------------------------------
  ; MoveEntity Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  light=CreateLight()\
  \
  cone=CreateCone( 32 )\
  \
  ; Move cone in front of camera, so we can see it to begin with\
  MoveEntity cone,0,0,10\
  \
  While Not KeyDown( 1 )\
  \
  ; Reset movement values - otherwise, the cone will not stop!\
  x#=0\
  y#=0\
  z#=0\
  \
  ; Change rotation values depending on the key pressed\
  If KeyDown( 203 )=True Then x#=-0.1\
  If KeyDown( 205 )=True Then x#=0.1\
  If KeyDown( 208 )=True Then y#=-0.1\
  If KeyDown( 200 )=True Then y#=0.1\
  If KeyDown( 44 )=True Then z#=-0.1\
  If KeyDown( 30 )=True Then z#=0.1\
  \
  ; Move cone using movement values\
  MoveEntity cone,x#,y#,z#\
  \
  ; If spacebar pressed then rotate cone by random amount\
  If KeyHit( 57 )=True Then RotateEntity cone,Rnd( 0,360 ),Rnd( 0,360
  ),Rnd( 0,360 )\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor/A/Z keys to move cone, spacebar to rotate cone by
  random amount\"\
  Text 0,20,\"X Movement: \"+x#\
  Text 0,40,\"Y Movement: \"+y#\
  Text 0,60,\"Z Movement: \"+z#\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=MoveEntity&ref=comments){target="_blank"}
to view the latest version of this page online
