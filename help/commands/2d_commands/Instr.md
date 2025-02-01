# Instr (string1\$, string2\$, offset)

# Parameters

  -----------------------------------------------------------------------
  string1\$ = the string you wish to search\
  string2\$ = the string to find\
  offset = valid integer starting position to being search (optional)

  -----------------------------------------------------------------------

# Description

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command will allow you to search for an occurance of a string within another string. The command returns the location (number of characters from the left) of the string you are looking for. Command returns a Zero if no matches are found.
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/Instr.bb)

  -----------------------------------------------------------------------
  name\$=\"Shane R. Monroe\"\
  location = Instr( name\$,\"R.\",1)\
  Print \"Your string contains \'R.\' at position number \" + location +
  \"!\"\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Instr&ref=comments){target="_blank"}
to view the latest version of this page online
