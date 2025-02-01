# LSet\$ (string\$, length)

# Parameters

  -----------------------------------------------------------------------
  string\$ = any valid string or string variable\
  length = how long you want the new string to be (including padding)

  -----------------------------------------------------------------------

# Description

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  If you have a string that is say, 10 letters long, but you want to make it a full 25 letters, padding the rest of the string with spaces, this command will do so, leaving the original string value left justified.
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/LSet.bb)

  -----------------------------------------------------------------------
  name\$=\"Shane R. Monroe\"\
  Print \"New Padded Name: \'\" + LSet\$(name\$,40) + \"\'\"\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LSet&ref=comments){target="_blank"}
to view the latest version of this page online
