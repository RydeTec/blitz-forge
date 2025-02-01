# And

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  AND is a logical operator for doing conditional checks of multiple
  values and/or expressions. Use this to ensure that two or more
  conditions are true, usually in an IF \... THEN conditional. See
  example and see OR, NOT, and XOR.\
  \
  See also: [Or](Or.htm){.small}, [Not](Not.htm){.small},
  [Xor](Xor.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/And.bb)

  -----------------------------------------------------------------------
  ; AND example\
  \
  name\$=Input\$(\"Enter your name:\")\
  pw\$=Input\$(\"Password:\")\
  \
  if name\$=\"Shane\" and pw\$=\"bluedog\" then\
  print \"Access granted! Welcome!\"\
  else\
  print \"Your name or password was not recognized\"\
  end if

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=And&ref=comments){target="_blank"}
to view the latest version of this page online
