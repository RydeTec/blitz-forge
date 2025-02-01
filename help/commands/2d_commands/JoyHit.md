# JoyHit (button,\[port\])

# Parameters

  -----------------------------------------------------------------------
  button = number of joystick button to check\
  port = number of joystick port to check (optional)

  -----------------------------------------------------------------------

# Description

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command returns the number of times a specified joystick button has been hit since the last time you called the JoyHit() command. Also see KeyHit and MouseHit.
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/JoyHit.bb)

  -----------------------------------------------------------------------
  ; JoyHit Example\
  \
  ; Set up the timer\
  current=MilliSecs()\
  Print \"Press FireButton 1 a bunch of times for five seconds\...\"\
  \
  ; Wait 5 seconds\
  While MilliSecs() \< current+5000\
  Wend\
  \
  ; Print the results\
  Print \"Pressed button \" + JoyHit(1) + \" times.\"\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=JoyHit&ref=comments){target="_blank"}
to view the latest version of this page online
