# WaitJoy (\[port\])

# Parameters

  -------------------------------
  port = joystick port to check
  -------------------------------

# Description

  -----------------------------------------------------------------------
  This command makes your program halt until a jpystick button is pressed
  on the joystick. Used alone, it simply halts and waits for a button
  press. It can also be used to assign the pressed button\'s code value
  to a variable. See example.\
  \
  In MOST CASES, you are not going to want to use this command because
  chances are likely you are going to want things on the screen still
  happening while awaiting the button press. In that situation, you\'ll
  use a WHILE \... WEND awaiting a JoyHit value - refreshing your screen
  each loop.\
  \
  As with any joystick command, you MUST have a DirectX compatible
  joystick plugged in and properly configured within Windows for it to
  work. See your joystick documentation for more information.

  -----------------------------------------------------------------------

# [Example](../2d_examples/WaitJoy.bb)

  -----------------------------------------------------------------------
  ; WaitJoy() sample\
  \
  Print \"Press a joystick button to continue.\"\
  \
  button=WaitJoy()\
  \
  Print \"The joystick button code of the button you pressed was: \" +
  button\
  Print \"Now press a button to quit.\"\
  \
  WaitJoy()\
  \
  End\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=WaitJoy&ref=comments){target="_blank"}
to view the latest version of this page online
