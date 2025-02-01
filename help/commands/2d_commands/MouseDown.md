# MouseDown (button)

# Parameters

  ------------------------------------------------------------
  button = 1: Left Button, 2: Right Button, 3: Middle Button
  ------------------------------------------------------------

# Description

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command (and its counterparts KeyDown and JoyDown) is used to detect if a mouse button is being held down. You must check for each mouse button independantly with its corresponding number (unlike KeyDown which returns WHICH key is being held down). Also see MouseHit.
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/MouseDown.bb)

  -----------------------------------------------------------------------
  ; MouseDown Example\
  \
  ; Until user presses ESC, show the mouse button pressed\
  While Not KeyHit(1)\
  button\$=\"No\"\
  If MouseDown(1) Then button\$=\"Left\"\
  If MouseDown(2) Then button\$=\"Right\"\
  If MouseDown(3) Then button\$=\"Middle\"\
  \
  Print button\$ + \" mouse button pressed!\"\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=MouseDown&ref=comments){target="_blank"}
to view the latest version of this page online
