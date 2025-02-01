# JoyDown (button,\[port\])

# Parameters

  -----------------------------------------------------------------------
  button = number of joystick button to check\
  port = number of joystick port to check (optional)

  -----------------------------------------------------------------------

# Description

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command (and its counterparts KeyDown and MouseDown) is used to detect if a joystick button is being held down. You must check for each joystick button independantly with its corresponding number (unlike KeyDown which returns WHICH key is being held down). Also see JoyHit.
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/JoyDown.bb)

  -----------------------------------------------------------------------
  ; JoyDown Example\
  \
  ; Until user presses ESC, show the mouse button pressed\
  While Not KeyHit(1)\
  button\$=\"No\"\
  For t = 1 To 5\
  If JoyDown(t) Then button\$=Str(t)\
  Print button\$ + \" joystick button pressed!\"\
  Next\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=JoyDown&ref=comments){target="_blank"}
to view the latest version of this page online
