# Default

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  In a SELECT structure, you may wish to execute code if none of the
  cases you specify are met. All code after DEFAULT to END SELECT will be
  executed if no case is met. See SELECT, CASE, and the example for
  more.\
  \
  See also: [Select](Select.htm){.small}, [Case](Case.htm){.small}, [End
  Select](End){.small select.htm=""}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Default.bb)

  -----------------------------------------------------------------------
  ; SELECT/CASE/DEFAULT/END SELECT Example\
  ; Assign a random number 1-10\
  mission=Rnd(1,10)\
  \
  ; Start the selection process based on the value of \'mission\'
  variable\
  Select mission\
  \
  ; Is mission = 1?\
  Case 1\
  Print \"Your mission is to get the plutonium and get out alive!\"\
  \
  ; Is mission = 2?\
  Case 2\
  Print \"Your mission is to destroy all enemies!\"\
  \
  ; Is mission = 3?\
  Case 3\
  Print \"Your mission is to steal the enemy building plans!\"\
  \
  ; What do do if none of the cases match the value of mission\
  Default\
  Print \"Missions 4-10 are not available yet!\"\
  \
  ; End the selection process\
  End Select

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Default&ref=comments){target="_blank"}
to view the latest version of this page online
