[  JoyWait (\[port\])  ]{.Command}

[Definition:]{.header}\
\

  ---------------------------------------------------------------------
  Waits for a joystick button to be pressed, then returns the number.
  ---------------------------------------------------------------------

[\
Parameter Description:]{.header}\
\

  ----------------------------------------------------
  port = number of joystick port to check (optional)
  ----------------------------------------------------

Command Description:\
\

  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command makes your program halt until a jpystick button is pressed on the joystick. Used alone, it simply halts and waits for a button press. It can also be used to assign the pressed button\'s code value to a variable. See example. In MOST CASES, you are not going to want to use this command because chances are likely you are going to want things on the screen still happening while awaiting the button press. In that situation, you\'ll use a [WHILE](WHILE.htm) \... [WEND](WEND.htm) awaiting a [JoyHit](JoyHit.htm) value - refreshing your screen each loop. As with any joystick command, you MUST have a DirectX compatible joystick plugged in and properly configured within Windows for it to work. See your joystick documentation for more information.
  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Example:\
\

  -----------------------------------------------------------------------
  ; JoyWait() example\
  \
  ; Check to see what stick is present - print the proper message\
  Print \"Press a button on the joystick\"\
  \
  button=JoyWait()\
  \
  ; Wait for user to hit ESC\
  Print \"You pressed button \# \" + button + \"! Hit ESC To End\"\
  \
  While Not KeyHit(1)\
  Wend\

  -----------------------------------------------------------------------

**[Index](../index.htm){target="_top"}**
