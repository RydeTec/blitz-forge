# LightColor light,red#,green#,blue#

# Parameters

  -----------------------------------------------------------------------
  light - light handle\
  red# - red value of light\
  green# - green value of light\
  blue# - blue value of light

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the color of a light.\
  \
  An r,g,b value of 255,255,255 will brighten anything the light shines
  on.\
  \
  An r,g,b value of 0,0,0 will have no affect on anything it shines on.\
  \
  An r,g,b value of -255,-255,-255 will darken anything it shines on.
  This is known as \'negative lighting\', and is useful for shadow
  effects.\
  \
  See also: [CreateLight](CreateLight.htm){.small},
  [LightRange](LightRange.htm){.small},
  [LightConeAngles](LightConeAngles.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/LightColor.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480\
  \
  camera = CreateCamera()\
  MoveEntity camera,0,0,-3\
  \
  ball = CreateSphere()\
  \
  lite = CreateLight() ; try different lights 1 to 3\
  MoveEntity lite,5,0,-15\
  PointEntity lite,ball\
  \
  \
  While Not KeyDown(1)\
  RenderWorld:Flip\
  If KeyHit(57) Then LightColor lite,Rnd(255),Rnd(255),Rnd(255) ; press
  SPACEBAR to try different light colors\
  Wend\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LightColor&ref=comments){target="_blank"}
to view the latest version of this page online
