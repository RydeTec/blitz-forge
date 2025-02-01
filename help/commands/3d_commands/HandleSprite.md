# HandleSprite sprite,x_handle#,y_handle#

# Parameters

  ----------------------------------------------------------------------------------------------------------------------------------------------------
  sprite - sprite handle. Not to be confused with HandleSprite - ie. the handle used to position the sprite, rather than the sprite\'s actual handle
  ----------------------------------------------------------------------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets a sprite handle. Defaults to 0,0.\
  \
  A sprite extends from -1,-1 to +1,+1.\
  \
  See also: [LoadSprite](LoadSprite.htm){.small},
  [CreateSprite](CreateSprite.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/HandleSprite.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480\
  \
  cam = CreateCamera()\
  MoveEntity cam,0,0,-5\
  \
  sp = CreateSprite()\
  \
  handlepos# = 0.0\
  While Not KeyDown(1)\
  RenderWorld\
  Color 100,100,100\
  Plot 320,240\
  Text 320,250,\"Handle\",True\
  Flip\
  HandleSprite sp,handlepos,handlepos\
  handlepos = handlepos + 0.01\
  Wend\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=HandleSprite&ref=comments){target="_blank"}
to view the latest version of this page online
