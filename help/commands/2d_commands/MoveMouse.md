# MoveMouse x,y

# Parameters

  -----------------------------------------------------------------------
  x = the x coordinate on the screen to move the mouse\
  y = the y coordinate on the screen to move the mouse

  -----------------------------------------------------------------------

# Description

  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Although the mouse isn\'t visible on the screen, the mouse location is still being tracked and you can attach a graphic to it. However, there are times when you want to put the pointer to a specific location on the screen. Use this command to move the mouse to a designated location.
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/MoveMouse.bb)

  -----------------------------------------------------------------------
  Graphics 640,480\
  \
  SetBuffer BackBuffer()\
  \
  Repeat\
  Cls\
  \
  Text 320,0,\"Click to reset mouse\",True\
  \
  Text 0,0,\"Mouse X:\"+MouseX()\
  Text 0,10,\"Mouse Y:\"+MouseY()\
  \
  If MouseDown(1) Or MouseDown(2) Then MoveMouse 320,240\
  \
  Text MouseX(),MouseY(),\"X\",True,True\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=MoveMouse&ref=comments){target="_blank"}
to view the latest version of this page online
