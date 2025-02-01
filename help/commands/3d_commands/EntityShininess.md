# EntityShininess Entity, Shininess#

# Parameters

  -----------------------------------------------------------------------
  Entity - entity handle\
  Shininess# - shininess of entity

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the specular shininess of an entity.\
  \
  The shininess# value should be a floting point number in the range 0-1.
  The default shininess setting is 0.\
  \
  Shininess is how much brighter certain areas of an object will appear
  to be when a light is shone directly at them.\
  \
  Setting a shininess value of 1 for a medium to high poly sphere,
  combined with the creation of a light shining in the direction of it,
  will give it the appearance of a shiny snooker ball.

  -----------------------------------------------------------------------

# [Example](../3d_examples/EntityShininess.bb)

  -----------------------------------------------------------------------
  ; EntityShininess Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  camera=CreateCamera()\
  ; Set ambient light to a low level, to increase effect of shininess\
  \
  AmbientLight 32,32,32\
  light=CreateLight()\
  RotateEntity light,45,45,0\
  \
  sphere=CreateSphere(100)\
  EntityColor Sphere,255,0,0\
  PositionEntity sphere,0,0,4\
  \
  ; Set initial shine value\
  \
  shine#=0\
  \
  While Not KeyDown(1)\
  ; Change shine value depending on key pressed\
  \
  If KeyDown(2)=True And shine#\>0 Then shine#=shine#-0.01\
  If KeyDown(3)=True And shine#\<1 Then shine#=shine#+0.01\
  \
  ; Set entity shininess using shine value\
  EntityShininess sphere,shine#\
  \
  RenderWorld\
  \
  Text 0,0,\"Press keys 1-2 to change EntityShininess Setting\"\
  Text 0,20,\"Entity Shininess: \"+shine#\
  \
  Flip\
  Wend\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=EntityShininess&ref=comments){target="_blank"}
to view the latest version of this page online
