# Forever

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  Replace the UNTIL command in aREPEAT \... UNTIL loop to make the loop
  run forever. Remember, the program execution will continue indefinately
  until either you break out of it programmatically! This is often known
  as \'the infinate loop\'. Once more for the cheap seats: \"Make sure
  you provide a means for breaking out of the loop\". Use EXIT (to leave
  the loop) or END to quit the program.\
  \
  See also: [Repeat](Repeat.htm){.small}, [Until](Until.htm){.small},
  [Exit](Exit.htm){.small}, [While](While.htm){.small},
  [For](For.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Forever.bb)

  -----------------------------------------------------------------------
  ; FOREVER Example\
  \
  Repeat\
  If KeyHit(1) Then Exit\
  Print \"You are trapped in an infinate loop! Press ESC!\"\
  Forever\
  \
  Print \"The infinate loop has ended!\"

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Forever&ref=comments){target="_blank"}
to view the latest version of this page online
