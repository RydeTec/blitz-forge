# ScaleSprite sprite,x_scale#,y_scale#

# Parameters

  -----------------------------------------------------------------------
  sprite - sprite handle\
  x_scale# - x scale of sprite\
  y scale# - y scale of sprite

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Scales a sprite.\
  \
  See also: [LoadSprite](LoadSprite.htm){.small},
  [CreateSprite](CreateSprite.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/ScaleSprite.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480\
  \
  cam = CreateCamera()\
  MoveEntity cam,0,0,-5\
  \
  sp = CreateSprite()\
  \
  size# = 1.0\
  While Not KeyDown(1)\
  RenderWorld:Flip\
  ScaleSprite sp,size,size\
  size = size + 0.01\
  Wend\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ScaleSprite&ref=comments){target="_blank"}
to view the latest version of this page online
