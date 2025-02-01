# JoyYDir (\[port\])

# Parameters

  ----------------------------------------------------
  port = number of joystick port to check (optional)
  ----------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command returns the direction of the Y-axis of the joystick being
  pressed. The value is -1 (up) or 1 (down). The value returned is an
  integer number. See the example. Perfect for digital joysticks.\
  \
  As with any joystick command, you MUST have a DirectX compatible
  joystick plugged in and properly configured within Windows for it to
  work. See your joystick documentation for more information.

  -----------------------------------------------------------------------

# [Example](../2d_examples/JoyYDir.bb)

  -----------------------------------------------------------------------
  ; JoyYDir() example\
  \
  While Not KeyHit(1)\
  Cls\
  Text 0,0,\"Joy Y Direction: \" + JoyYDir()\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=JoyYDir&ref=comments){target="_blank"}
to view the latest version of this page online
