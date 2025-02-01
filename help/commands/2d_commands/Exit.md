# Exit

# Parameters

  ------
  None
  ------

# Description

  -----------------------------------------------------------------------
  This command will allow you to leave a For .. Next loop as well as many
  other types of loops prematurely (assuming a condition was met or other
  planned event). It exits the IMMEDIATE loop - you\'ll need one for each
  \'nest\' of loops you want to exit from.\
  \
  See also: [For](For.htm){.small}, [While](While.htm){.small},
  [Repeat](Repeat.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Exit.bb)

  -----------------------------------------------------------------------
  ; EXIT Command sample\
  \
  For t = 1 To 100\
  Print t\
  If t = 50 Then Exit\
  Next

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Exit&ref=comments){target="_blank"}
to view the latest version of this page online
