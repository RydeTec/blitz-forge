# Case value \[,value \[,value \... \] \]

# Parameters

  ------------------------------------------------
  value = any valid value of the SELECT variable
  ------------------------------------------------

# Description

  -----------------------------------------------------------------------
  When using a SELECT structure, the CASE command defines the starting
  point of command execution if the SELECT value matches the CASE value.
  If the SELECT value doesn\'t match the CASE value, the commands
  following it are ignored until the next CASE, DEFAULT, or END SELECT
  command is encountered. See SELECT and the example for a better
  understanding.\
  \
  If multiple values should result in the same outcome, a single CASE
  statement can be used with each value separated by commas.\
  \
  If you wish to use ranges for a case statement, or if you wish to test
  more than one variable, you will need to use a programming trick
  illustrated in the example below. This involves using SELECT TRUE
  initially, and then in each Case statement specify a logical expression
  such as A \> 1 AND A \< 4.\
  \
  See also: [Select](Select.htm){.small}, [Default](Default.htm){.small},
  [End Select](End){.small select.htm=""}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Case.bb)

  -----------------------------------------------------------------------
  ; Advanced SELECT/CASE/DEFAULT/END SELECT Example\
  ; Assign a random number 1-10\
  mission=Rnd(1,10)\
  \
  ; Start the selection process based on the value of \'mission\'
  variable\
  Select True\
  \
  ; Is mission = 1?\
  Case mission=1\
  Print \"Your mission is to get the plutonium and get out alive!\"\
  \
  ; Is mission = 2?\
  Case mission=2\
  Print \"Your mission is to destroy all enemies!\"\
  \
  ; Is mission = 3 to 5\
  Case mission\>=3 And mission\<=5\
  Print \"Your mission is to steal the enemy building plans!\"\
  \
  ; What do do if none of the cases match the value of mission\
  Default\
  Print \"Missions 6-10 are not available yet!\"\
  \
  ; End the selection process\
  End Select

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Case&ref=comments){target="_blank"}
to view the latest version of this page online
