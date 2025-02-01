# Repeat

# Parameters

  ------
  None
  ------

# Description

  -----------------------------------------------------------------------
  The REPEAT \... UNTIL loop allows you to perform a series of commands
  until a specific condition has been met. This lets the conditional
  appear after the commands have been executed before checking, not
  before like the WHILE \... WEND loop does. In general, use REPEAT \...
  UNTIL if you know you will have the commands enclosed between them
  execute at least once.\
  \
  See also: [Until](Until.htm){.small}, [Forever](Forever.htm){.small},
  [Exit](Exit.htm){.small}, [While](While.htm){.small},
  [For](For.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Repeat.bb)

  -----------------------------------------------------------------------
  ; Repeat until user hits ESC key\
  \
  Repeat\
  print \"Press ESC to quit this!\"\
  Until KeyHit(1)

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Repeat&ref=comments){target="_blank"}
to view the latest version of this page online
