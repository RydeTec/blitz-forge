# AmbientLight red#,green#,blue#

# Parameters

  -----------------------------------------------------------------------
  red# - red ambient light value\
  green# - green ambient light value\
  blue# - blue ambient light value\
  \
  The green, red and blue values should be in the range 0-255. The
  default ambient light colour is 127,127,127.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the ambient lighting colour.\
  \
  Ambient light is a light source that affects all points on a 3D object
  equally. So with ambient light only, all 3D objects will appear flat,
  as there will be no shading.\
  \
  Ambient light is useful for providing a certain level of light, before
  adding other lights to provide a realistic lighting effect.\
  \
  An ambient light level of 0,0,0 will result in no ambient light being
  displayed.\
  \
  See also: [CreateLight](CreateLight.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/AmbientLight.bb)

  -----------------------------------------------------------------------
  ; AmbientLight Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  sphere=CreateSphere( 32 )\
  PositionEntity sphere,-2,0,5\
  \
  cone=CreateCone( 32 )\
  PositionEntity cone,2,0,5\
  \
  ; Set initial ambient light colour values\
  red#=127\
  green#=127\
  blue#=127\
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
  ; Set ambient light color using red, green, blue values\
  AmbientLight red#,green#,blue#\
  \
  RenderWorld\
  \
  Text 0,0,\"Press keys 1-6 to change AmbientLight red#,green#,blue#
  values\
  Text 0,20,\"Ambient Red: \"+red#\
  Text 0,40,\"Ambient Green: \"+green#\
  Text 0,60,\"Ambient Blue: \"+blue#\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=AmbientLight&ref=comments){target="_blank"}
to view the latest version of this page online
