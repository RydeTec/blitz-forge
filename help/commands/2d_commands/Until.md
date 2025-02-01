# Until condition

# Parameters

  ------------------------------------------------
  condition = any valid expression (see example)
  ------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This portion of the REPEAT \... UNTIL loop dictates what condition must
  be met before the loop stops execution. All commands between the two
  commands will be executed endlessly until the UNTIL condition is met.\
  \
  See also: [Repeat](Repeat.htm){.small}, [Forever](Forever.htm){.small},
  [Exit](Exit.htm){.small}, [While](While.htm){.small},
  [For](For.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Until.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Until&ref=comments){target="_blank"}
to view the latest version of this page online
