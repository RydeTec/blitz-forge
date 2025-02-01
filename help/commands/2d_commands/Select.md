# Select variable

# Parameters

  -------------------------------
  variable - any valid variable
  -------------------------------

# Description

  -----------------------------------------------------------------------
  This command allows you to set up a selection structure that, based on
  the value of the variable you feed it, will execute different commands
  in different CASEs. You can also specify a DEFAULT set of commands to
  happen if NONE of the CASEs are met. The selection structure is ended
  with an END SELECT command.\
  \
  \
  This selection structure removes the need for large nested IF/THEN
  condition checking. See the example for more.\
  \
  See also: [Case](Case.htm){.small}, [Default](Default.htm){.small},
  [True](True.htm){.small}, [False](False.htm){.small},
  [If](If.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Select.bb)

  -----------------------------------------------------------------------
  ; SELECT/CASE/DEFAULT/END SELECT Example\
  \
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Select&ref=comments){target="_blank"}
to view the latest version of this page online
