# EntityAlpha entity,alpha#

# Parameters

  -----------------------------------------------------------------------
  Entity - entity handle\
  Alpha# - alpha level of entity

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the entity alpha level of an entity.\
  \
  The alpha# value should be in a floating point value in the range 0-1.
  The default entity alpha setting is 1.\
  \
  The alpha level is how transparent an entity is. A value of 1 will mean
  the entity is opaque. A value of 0 will mean the entity is completely
  transparent, i.e. invisible. Values between 0 and 1 will cause varying
  amount of transparency. This is useful for imitating the look of
  objects such as glass and other translucent materials.\
  \
  An EntityAlpha value of 0 is especially useful as Blitz3D will not
  render entities with such a value, but will still involve the entities
  in collision tests. This is unlike HideEntity, which doesn\'t involve
  entities in collisions.

  -----------------------------------------------------------------------

# [Example](../3d_examples/EntityAlpha.bb)

  -----------------------------------------------------------------------
  ; EntityAlpha Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  cube=CreateCube()\
  Back=CreateCube()\
  \
  PositionEntity cube,0,0,5\
  PositionEntity back,0,0,15\
  ScaleEntity Back,10,2,1\
  EntityColor back,255,0,0\
  ; Set initial entity color values\
  Alpha#=1\
  \
  While Not KeyDown( 1 )\
  \
  ; Change alpha value depending on key pressed\
  If alpha#\<0.01 Then alpha# = 0\
  If alpha#\>1 Then alpha# = 1\
  If KeyDown( 2 )=True And Alpha#\>0 Then Alpha#=Alpha#-0.01\
  If KeyDown( 3 )=True And alpha#\<1 Then Alpha#=Alpha#+0.01\
  ; Set entity alpha value\
  EntityAlpha cube,Alpha#\
  \
  \
  TurnEntity cube,0.1,0.1,0.1\
  TurnEntity back,1,0,0\
  \
  RenderWorld\
  \
  Text 0,0,\"Press keys 1-2 to change EntityAlpha\"\
  Text 0,20,\"Entity Alpha: \"+Alpha\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=EntityAlpha&ref=comments){target="_blank"}
to view the latest version of this page online
