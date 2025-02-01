# CommandLine\$()

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  If you are writing an application or game that allows starting with
  special parameters on the command line, you can use this command to
  retrieve the parameters.\
  \
  For example, you might want to start the program with a debug variable
  set so you can track stuff during execution. So, you could offer the
  ability to run the executatble with a /debug parameter. If they execute
  the program with the parameter, then you can set a flag inside your
  game.\
  \
  To simulate the command line passing in the editor, select
  PROGRAM-\>PROGRAM COMMAND LINE from the pulldowns and enter a value to
  be passed at runtime.\
  \
  See the example.

  -----------------------------------------------------------------------

# [Example](../2d_examples/CommandLine.bb)

  -----------------------------------------------------------------------
  ; CommandLine\$() Example\
  ; Be sure to use PROGRAM-\>PROGRAM COMMAND LINE from the\
  ; pull down and put /debug in there to test with.\
  \
  a\$=CommandLine\$()\
  \
  If a\$=\"/debug\" Then\
  Print \"Debug mode is on!\"\
  debug=1\
  Else\
  Print \"No debugging activated.\"\
  debug=0\
  End If

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CommandLine&ref=comments){target="_blank"}
to view the latest version of this page online
