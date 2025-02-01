# VWait \[frames\]

# Parameters

  ---------------------------------------------------------------------
  \[frames\] = optional number of frames to wait. Default is 1 frame.
  ---------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  VWait will cause the CPU to wait for the next (or specified number of)
  vertical blank event on the monitor\
  \
  There are times when you can draw too fast, and your drawing operations
  happen so fast that you get undesireable effects. This command forces
  the CPU to wait until the drawing scan line reaches the bottom of the
  screen before proceeding. Try the example with and without the VWait
  command.\
  \
  Note that this command is different to the vertical blank waiting
  mechanism in Flip because Flip will cause the graphics card (as opposed
  to the CPU) to wait for the next vertical blank. The vertical blank can
  be disabled on some graphics cards, hence it is quite common to use
  \"VWait : Flip False\" to ensure consistent updates on all setups.\
  \
  See also: [Flip](Flip.htm){.small}, [ScanLine](ScanLine.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/VWait.bb)

  -----------------------------------------------------------------------
  ; Vwait example\
  Graphics 800,600,16\
  \
  ; Wait for ESC to hit\
  While Not KeyHit(1)\
  ; Set a random color\
  Color Rnd(255),Rnd(255),Rnd(255)\
  ; Draw a random line\
  Line Rnd(800),Rnd(600),Rnd(800),Rnd(600)\
  ; Wait For a vertical blank to happen before looping\
  VWait\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=VWait&ref=comments){target="_blank"}
to view the latest version of this page online
