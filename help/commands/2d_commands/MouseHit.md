# MouseHit (button)

# Parameters

  --------------------------------------------------
  button = button code (1=Left, 2=Right, 3-Middle)
  --------------------------------------------------

# Description

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command returns the number of times a specified mouse button has been hit since the last time you called the MouseHit() command. Also see KeyHit and JoyHit.
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/MouseHit.bb)

  -----------------------------------------------------------------------
  ; MouseHit Example\
  \
  ; Set up the timer\
  current=MilliSecs()\
  Print \"Press left mouse button a bunch of times for five
  seconds\...\"\
  \
  ; Wait 5 seconds\
  While MilliSecs() \< current+5000\
  Wend\
  \
  ; Print the results\
  Print \"Pressed left button \" + MouseHit(1) + \" times.\"\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=MouseHit&ref=comments){target="_blank"}
to view the latest version of this page online
