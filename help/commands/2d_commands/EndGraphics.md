# EndGraphics

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  Returns a program to a non-Graphics mode state. This is the same state
  that a program starts up in, before the Graphics command is used.\
  \
  One possible use for EndGraphics is to switch between full-screen and
  windowed states.\
  \
  See also: [Graphics](Graphics.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/EndGraphics.bb)

  -----------------------------------------------------------------------
  Graphics 640,480,0,1\
  SetBuffer BackBuffer()\
  \
  Text 0,0,\"EndGraphics Example Stage 1/2\"\
  Text 0,20,\"Currently in Graphics mode\"\
  Text 0,40,\"Press a key to deactive Graphics mode with EndGraphics
  command\"\
  \
  Flip\
  \
  WaitKey()\
  \
  EndGraphics\
  \
  Print \"EndGraphics Example Stage 2/2\"\
  Print \"Currently in non-Graphics mode\"\
  \
  WaitKey()\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=EndGraphics&ref=comments){target="_blank"}
to view the latest version of this page online
