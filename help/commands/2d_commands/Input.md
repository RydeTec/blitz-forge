# Input\$ (prompt\$)

# Parameters

  ----------------------------------------
  prompt\$ = any valid string (optional)
  ----------------------------------------

# Description

  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command will retrieve a string value from the user with an optional prompt on the screen (if not in a graphic mode) or on the current drawing buffer being used by the program. Usually you will assign this command\'s value to a string for later use.
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/Input.bb)

  -----------------------------------------------------------------------
  ; Get the user\'s name and print a welcome\
  \
  name\$=Input\$(\"What is your name?\")\
  Write \"Hello there, \" + name\$ + \"!\"\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Input&ref=comments){target="_blank"}
to view the latest version of this page online
