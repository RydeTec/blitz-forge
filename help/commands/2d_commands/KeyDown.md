# KeyDown (scancode)

# Parameters

  ---------------------------------------
  scancode = corresponding key scancode
  ---------------------------------------

# Description

  -----------------------------------------------------------------------
  This command (similar to its counterparts MouseDown and JoyDown) is
  used to detect if a key is being held down. This command returns a 0 if
  the key is not held down, a 1 if the key is held down.\
  \
  See Also: ScanCodes

  -----------------------------------------------------------------------

# [Example](../2d_examples/KeyDown.bb)

  -----------------------------------------------------------------------
  ; KeyDown() example\
  \
  Print \"Hold down ENTER key!\"\
  Delay 3000\
  While Not KeyHit(1)\
  If KeyDown(28) Then\
  Print \"Enter is being pressed!\"\
  Else\
  Print\
  End If\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=KeyDown&ref=comments){target="_blank"}
to view the latest version of this page online
