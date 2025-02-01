# EntityColor entity,red#,green#,blue#

# Parameters

  -----------------------------------------------------------------------
  entity - entity handle\
  Red# - red value of entity\
  Green# - green value of entity\
  Blue# - blue value of entity

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the color of an entity.\
  \
  The Red, Green and Blue values should be in the range 0-255 with 0
  being darkest and 255 brightest. The default entity color is
  255,255,255 (White).

  -----------------------------------------------------------------------

# [Example](../3d_examples/EntityColor.bb)

  -----------------------------------------------------------------------
  ; EntityColor Example\
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
  PositionEntity cube,0,0,5\
  \
  ; Set initial entity color values\
  red#=255\
  green#=255\
  blue#=255\
  \
  While Not KeyDown( 1 )\
  \
  ; Change red, green, blue values depending on key pressed\
  If KeyDown( 2 )=True And red#\>0 Then red#=red#-1\
  If KeyDown( 3 )=True And red#\<255 Then red#=red#+1\
  If KeyDown( 4 )=True And green#\>0 Then green#=green#-1\
  If KeyDown( 5 )=True And green#\<255 Then green#=green#+1\
  If KeyDown( 6 )=True And blue#\>0 Then blue#=blue#-1\
  If KeyDown( 7 )=True And blue#\<255 Then blue#=blue#+1\
  \
  ; Set entity color using red, green, blue values\
  EntityColor cube,red#,green#,blue#\
  \
  TurnEntity cube,0.1,0.1,0.1\
  \
  RenderWorld\
  \
  Text 0,0,\"Press keys 1-6 to change EntityColor red#,green#,blue#
  values\
  Text 0,20,\"Entity Red: \"+red#\
  Text 0,40,\"Entity Green: \"+green#\
  Text 0,60,\"Entity Blue: \"+blue#\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=EntityColor&ref=comments){target="_blank"}
to view the latest version of this page online
