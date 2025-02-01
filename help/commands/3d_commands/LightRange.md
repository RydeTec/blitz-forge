# LightRange light,range#

# Parameters

  -----------------------------------------------------------------------
  light - light handle\
  range# - range of light (default: 1000.0)

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the range of a light.\
  \
  The range of a light is how far it reaches. Everything outside the
  range of the light will not be affected by it.\
  \
  The value is very approximate, and should be experimented with for best
  results.\
  \
  See also: [CreateLight](CreateLight.htm){.small},
  [LightColor](LightColor.htm){.small},
  [LightConeAngles](LightConeAngles.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/LightRange.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480\
  \
  camera = CreateCamera()\
  MoveEntity camera,0,0,-3\
  \
  ball = CreateSphere()\
  \
  lite = CreateLight(2) ; try different lights 1 to 3\
  MoveEntity lite,5,0,-5\
  PointEntity lite,ball\
  \
  range# = 0.5\
  LightRange lite,range\
  \
  While Not KeyDown(1)\
  RenderWorld:Flip\
  If KeyHit(57) Then ; hit SPACEBAR to increase light range\
  range = range + 0.5\
  LightRange lite,range\
  EndIf\
  Wend\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LightRange&ref=comments){target="_blank"}
to view the latest version of this page online
