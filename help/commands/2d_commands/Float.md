# Float#( value )

# Parameters

  ---------------------------------------------------------
  value = a number, or a string which represents a number
  ---------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This is the same as Blitz\'s automatic type conversion.\
  So the two commands\...\
  \
  y# = value\
  y# = Float( value )\
  \
  \... do exactly the same thing.\
  \
  If Float is applied to a string it converts as much as possible:\
  \
  Float( \"10\" ) \...\... result is 10.0\
  Float( \"3junk\" ) .. result is 3.0\
  Float( \"junk3\" ) .. result is 0.0\
  \
  In the latter two examples Float() stops converting when it sees
  \"j\".\
  \
  The typical use is to force floating point arithmetic:\
  \
  y# = 12 / 5 \...\...\...\..... result is 2.0 because 12 / 5 = 2 (
  integer division )\
  y# = float( 12 ) / 5 \.... result is 2.4

  -----------------------------------------------------------------------

# [Example](../2d_examples/Float.bb)

  -----------------------------------------------------------------------
  ; Float example\
  ; =============\
  \
  Print \"Enter some text to be converted using Float().\"\
  Print \"Hit ENTER to exit.\"\
  \
  Repeat\
  s\$ = Input\$(\"\>\")\
  If s\$\<\>\"\" Then\
  Print \"Float(\"+Chr\$(34)+s\$+Chr\$(34)+\")=\" + Float(s\$)\
  End If\
  Until s\$=\"\"\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Float&ref=comments){target="_blank"}
to view the latest version of this page online
