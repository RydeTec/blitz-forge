# JoyX (\[port\])

# Parameters

  ----------------------------------------------------
  port = number of joystick port to check (optional)
  ----------------------------------------------------

# Description

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command returns the value of the x-axis of the joystick. The range is -1 to 1 (full left to full right). The value returned is a floating point number. See the example.
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/JoyX.bb)

  -----------------------------------------------------------------------
  ; JoyX()/JoyY() example\
  \
  While Not KeyHit(1)\
  Cls\
  Text 0,0,\"Joy X Value: \" + JoyX() + \" - Joy Y Value:\" + JoyY()\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=JoyX&ref=comments){target="_blank"}
to view the latest version of this page online
