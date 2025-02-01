[  .variable  ]{.Command}

[Definition:]{.header}\
\

  --------------------------------------------
  Sets a callable label within your program.
  --------------------------------------------

[\
Parameter Description:]{.header}\
\

  ----------------------------------------------
  variable = any valid existing label variable
  ----------------------------------------------

Command Description:\
\

  -----------------------------------------------------------------------
  A label is like a callable bookmark within your program. It basically
  tells Blitz where to start something; either program execution (through
  a [Goto](Goto.htm) or [Gosub](Gosub.htm) command) or where to start
  reading [Data](Data.htm) (through the [Read](Read.htm) command). While
  most \'professional\' programmers loathe and hate the use of
  \'branching\' commands like [Goto](Goto.htm)/[Gosub](Gosub.htm), you
  will likely find it useful regardless. Use [Return](Return.htm) to jump
  back to the original code that called the label via the
  [Gosub](Gosub.htm) command (you cannot [Return](Return.htm) from a
  [Goto](Goto.htm) command).\
  In addition, you MUST use labels to read [Data](Data.htm) values using
  the [Read](Read.htm) command. Check out the [Read](Read.htm) command
  for use of it in a [Data](Data.htm) statement environment.

  -----------------------------------------------------------------------

Example:\
\

  -----------------------------------------------------------------------
  ; The program starts here, but we\'ll branch to other labels\
  \
  Gosub start\
  Gosub label3\
  Gosub label2\
  \
  ; Let\'s wait for ESC to be pressed before ending\
  While Not KeyHit(1)\
  Wend\
  End\
  \
  .start\
  Print \"We start here \...\"\
  Return\
  \
  .label2\
  Print \"This is label 2\"\
  Return\
  \
  .label3\
  Print \"This is label 3\"\
  Return\

  -----------------------------------------------------------------------

**[Index](../index.htm){target="_top"}**
