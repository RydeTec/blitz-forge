# TranslateEntity entity,x#,y#,z#,\[,global\]

# Parameters

  -----------------------------------------------------------------------
  entity - name of entity to be translated\
  x# - x amount that entity will be translated by\
  y# - y amount that entity will be translated by\
  z# - z amount that entity will be translated by\
  global (optional) -

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Translates an entity relative to its current position and not its
  orientation.\
  \
  What this means is that an entity will move in a certain direction
  despite where it may be facing. Imagine that you have a game character
  that you want to make jump in the air at the same time as doing a
  triple somersault. Translating the character by a positive y amount
  will mean the character will always travel directly up in their air,
  regardless of where it may be facing due to the somersault action.\
  \
  See also: [MoveEntity](MoveEntity.htm){.small},
  [PositionEntity](PositionEntity.htm){.small},
  [PositionMesh](PositionMesh.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/TranslateEntity.bb)

  -----------------------------------------------------------------------
  ; TranslateEntity Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  light=CreateLight()\
  \
  cone=CreateCone( 32 )\
  \
  ; Rotate cone by random amount to demonstrate that TranslateEntity is
  independent of entity orientation\
  RotateEntity cone,Rnd( 0,360 ),Rnd( 0,360 ),Rnd( 0,360 )\
  \
  ; Translate cone in front of camera, so we can see it to begin with\
  TranslateEntity cone,0,0,10\
  \
  While Not KeyDown( 1 )\
  \
  ; Reset translation values - otherwise, the cone will not stop!\
  x#=0\
  y#=0\
  z#=0\
  \
  ; Change translation values depending on the key pressed\
  If KeyDown( 203 )=True Then x#=-0.1\
  If KeyDown( 205 )=True Then x#=0.1\
  If KeyDown( 208 )=True Then y#=-0.1\
  If KeyDown( 200 )=True Then y#=0.1\
  If KeyDown( 44 )=True Then z#=-0.1\
  If KeyDown( 30 )=True Then z#=0.1\
  \
  ; Translate sphere using translation values\
  TranslateEntity cone,x#,y#,z#\
  \
  ; If spacebar pressed then rotate cone by random amount\
  If KeyHit( 57 )=True Then RotateEntity cone,Rnd( 0,360 ),Rnd( 0,360
  ),Rnd( 0,360 )\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor/A/Z keys to translate cone, spacebar to rotate
  cone by random amount\"\
  Text 0,20,\"X Translation: \"+x#\
  Text 0,40,\"Y Translation: \"+y#\
  Text 0,60,\"Z Translation: \"+z#\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TranslateEntity&ref=comments){target="_blank"}
to view the latest version of this page online
