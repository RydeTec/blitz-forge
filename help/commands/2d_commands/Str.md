# Str variable/value

# Parameters

  --------------------------------------------
  variable/value = numeric value or variable
  --------------------------------------------

# Description

  -----------------------------------------------------------------------
  Use this command to transform an numeric value to a string value for
  use with string commands. Blitz prints numeric values just fine, but
  should you want to do functions like LEFT\$, you\'ll need to convert
  your numeric variable to a string variable. Note: during the
  conversion, all 6 decimal places will be represented on floating point
  number conversions.\
  \
  If you wish to convert from a string to a number, there is no
  equivalent Val command. Instead, simply assign the string variable into
  a numeric variable, and Blitz will implicitly convert it.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Str.bb)

  -----------------------------------------------------------------------
  ; STR example\
  \
  num#=2.5\
  mynum\$=str num#\
  \
  Print mynum\$

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Str&ref=comments){target="_blank"}
to view the latest version of this page online
