# JoyZ (\[port\])

# Parameters

  ----------------------------------------------------
  port = number of joystick port to check (optional)
  ----------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command returns the value of the x-axis of the joystick. The range
  is -1 to 1 (Max to none). The value returned is a floating point
  number. See the example.\
  As with any joystick command, you MUST have a DirectX compatible
  joystick plugged in and properly configured within Windows for it to
  work. See your joystick documentation for more information.

  -----------------------------------------------------------------------

# [Example](../2d_examples/JoyZ.bb)

  -----------------------------------------------------------------------
  ; JoyZ() example\
  \
  While Not KeyHit(1)\
  Cls\
  Text 0,0,\"Joy Z Value: \" + JoyZ()\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=JoyZ&ref=comments){target="_blank"}
to view the latest version of this page online
