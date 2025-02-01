# GraphicsBuffer()

# Parameters

  ------
  None
  ------

# Description

  ---------------------------------------------------------------------
  Use this command to get which buffer Blitz is currently writing to.
  ---------------------------------------------------------------------

# [Example](../2d_examples/GraphicsBuffer.bb)

  -----------------------------------------------------------------------
  ; GraphicsWidth(), GraphicsHeight(), GraphicsDepth(), GraphicsBuffer()
  example\
  \
  ; Set a graphics mode and buffer\
  Graphics 640,480,16\
  SetBuffer FrontBuffer()\
  \
  ; Print the details\
  Print \"Screen width is: \" + GraphicsWidth()\
  Print \"Screen height is: \" + GraphicsHeight()\
  Print \"Screen color depth is: \" + GraphicsDepth()\
  Print \"Current buffer handle is: \" + GraphicsBuffer()\
  \
  ; Wait for ESC before exiting\
  While Not KeyHit(1)\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GraphicsBuffer&ref=comments){target="_blank"}
to view the latest version of this page online
