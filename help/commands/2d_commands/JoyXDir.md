# JoyXDir (\[port\])

# Parameters

  ----------------------------------------------------
  port = number of joystick port to check (optional)
  ----------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command returns the direction of the x-axis of the joystick being
  pressed. The value is -1 (left) or 1 (right). The value returned is an
  integer number. See the example. Perfect for digital joysticks.\
  \
  As with any joystick command, you MUST have a DirectX compatible
  joystick plugged in and properly configured within Windows for it to
  work. See your joystick documentation for more information.

  -----------------------------------------------------------------------

# [Example](../2d_examples/JoyXDir.bb)

  -----------------------------------------------------------------------
  ; JoyXDir() example\
  \
  While Not KeyHit(1)\
  Cls\
  Text 0,0,\"Joy X Direction: \" + JoyXDir()\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=JoyXDir&ref=comments){target="_blank"}
to view the latest version of this page online
