# MouseY()

# Parameters

  ------
  None
  ------

# Description

  -----------------------------------------------------------------------
  This command returns the Y location of the mouse on the screen. This
  position is always from the range 0 to GraphicsHeight( ) - 1. You can
  use this command in combination with DrawImage to make a custom mouse
  pointer, or to control something on the screen directly with the
  mouse.\
  \
  See also: [MouseX](MouseX.htm){.small}, [MouseZ](MouseZ.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/MouseY.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=MouseY&ref=comments){target="_blank"}
to view the latest version of this page online
