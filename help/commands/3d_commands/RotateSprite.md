# RotateSprite sprite,angle#

# Parameters

  -----------------------------------------------------------------------
  sprite - sprite handle\
  angle# - absolute angle of sprite rotation

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Rotates a sprite.\
  \
  See also: [CreateSprite](CreateSprite.htm){.small},
  [LoadSprite](LoadSprite.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/RotateSprite.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480\
  \
  cam = CreateCamera()\
  MoveEntity cam,0,0,-5\
  \
  sp = CreateSprite()\
  \
  ang# = 0\
  While Not KeyDown(1)\
  RenderWorld:Flip\
  RotateSprite sp,ang\
  ang = ang +3\
  Wend\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=RotateSprite&ref=comments){target="_blank"}
to view the latest version of this page online
