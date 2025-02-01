# Wend

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  This is the command that tells program execution to branch to the
  beginning of the WHILE/WEND loop at the WHILE command. See the WHILE
  command for complete details.\
  \
  See also: [While](While.htm){.small}, [Exit](Exit.htm){.small},
  [Repeat](Repeat.htm){.small}, [For](For.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Wend.bb)

  -----------------------------------------------------------------------
  ; While/Wend Example\
  \
  ; The loop condition is at the TOP of the loop\
  While Not KeyHit(1) ; As long as the user hasn\'t hit ESC yet \...\
  Print \"Press Esc to end this mess!\" ; Print this\
  Wend ; Go back to the start of the WHILE loop

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Wend&ref=comments){target="_blank"}
to view the latest version of this page online
