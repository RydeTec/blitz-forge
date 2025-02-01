# JoyHat ( \[port\] )

# Parameters

  ------------------------------------------------------------------------------------------
  port (optional) - an integer value representing the port to be checked for joystick data
  ------------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  JoyHat returns the state of a joystick \'hat\' or \'pov\'
  (point-of-view) control.\
  \
  If the returned value is -1, the hat is currently centred.\
  \
  Otherwise, the returned value gives the direction as an angle in the
  range 0 to 360 relative to \'up\'.

  -----------------------------------------------------------------------

# [Example](../2d_examples/JoyHat.bb)

  -----------------------------------------------------------------------
  While Not KeyHit(1)\
  Print JoyHat()\
  Wend\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=JoyHat&ref=comments){target="_blank"}
to view the latest version of this page online
