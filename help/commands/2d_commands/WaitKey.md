# WaitKey()

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  This command makes your program halt until a key is pressed on the
  keyboard. Used alone, it simply halts and waits for a key press. It can
  also be used to assign the pressed key\'s ASCII value to a variable.
  See example.\
  \
  In MOST CASES, you are not going to want to use this command because
  chances are likely you are going to want things on the screen still
  happening while awaiting the keypress. In that situation, you\'ll use a
  WHILE \... WEND awaiting a KeyHit value - refreshing your screen each
  loop.

  -----------------------------------------------------------------------

# [Example](../2d_examples/WaitKey.bb)

  -----------------------------------------------------------------------
  ; WaitKey() sample\
  \
  Print \"Press any key to continue.\"\
  \
  key=WaitKey()\
  \
  Print \"The ASCII code of the key you pressed was: \" + key\
  Print \"Now press a key to quit.\"\
  \
  WaitKey()\
  \
  End\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=WaitKey&ref=comments){target="_blank"}
to view the latest version of this page online
