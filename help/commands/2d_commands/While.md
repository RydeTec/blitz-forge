# While condition

# Parameters

  ---------------------------------------------
  condition = any valid conditional statement
  ---------------------------------------------

# Description

  -----------------------------------------------------------------------
  The WHILE/WEND loop is used when you wish to execute a series of
  commands multiple times based on whether a condition is true or not.
  This is similar to the REPEAT/UNTIL loop, except the condition checking
  is at the beginning of the loop, instead of at the end. Usually you\'ll
  use WHILE/WEND when you aren\'t sure whether or not the looped commands
  will even need to be executed once, since you can actually stop the
  loop before any commands are executed if the condition check fails. If
  you need to execute the commands in the loop at least once before
  checking a condition, use REPEAT/UNTIL. See example.\
  \
  See also: [Wend](Wend.htm){.small}, [Exit](Exit.htm){.small},
  [Repeat](Repeat.htm){.small}, [For](For.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/While.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=While&ref=comments){target="_blank"}
to view the latest version of this page online
