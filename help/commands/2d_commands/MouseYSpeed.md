# MouseYSpeed()

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  Often you\'d like to find the difference between where the mouse WAS to
  where it is NOW. You can use this command and MouseYSpeed() in pairs to
  find out the changes in the mouse location between calls.\
  \
  You really have to use these commands TWICE to get anything out of
  them. Each call you make returns the difference in location since the
  LAST time you called it.\
  \
  In this example it\'s called every loop and therefore allows you to
  have infinite mouse movement without the screen size restrictions.\
  \
  See also: [MouseXSpeed](MouseXSpeed.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/MouseYSpeed.bb)

  -----------------------------------------------------------------------
  Graphics 640,480\
  \
  SetBuffer BackBuffer()\
  \
  x=320\
  y=240\
  \
  ; infinite mouse movement\
  \
  Repeat\
  Cls\
  \
  xs=MouseXSpeed() ; see how far the mouse has been moved\
  ys=MouseYSpeed()\
  MoveMouse 320,240 ;put the mouse back in the middle of the screen\
  \
  x=x+xs ;adjust mouse co-ords\
  y=y+ys\
  \
  If x\>GraphicsWidth()-1 Then x=x-GraphicsWidth() ;wrap screen\
  If x\<0 Then x=x+GraphicsWidth()\
  If y\<0 Then y=y+GraphicsHeight()\
  If y\>GraphicsHeight()-1 Then y=y-GraphicsHeight()\
  \
  Text x,y,\"X\",True,True\
  \
  Flip\
  \
  Until KeyHit(1)\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=MouseYSpeed&ref=comments){target="_blank"}
to view the latest version of this page online
