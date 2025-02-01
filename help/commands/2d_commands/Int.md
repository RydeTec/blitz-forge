# Int( value )

# Parameters

  ---------------------------------------------------------
  value = a number, or a string which represents a number
  ---------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Converts the value to the nearest integer.\
  \
  This is the same as Blitz\'s automatic type conversion.\
  So the two commands\...\
  \
  n = value\
  n = Int( value )\
  \
  \... do exactly the same thing when n is an integer variable.\
  \
  If Int is applied to a string it converts as much as possible:\
  \
  Int( \"10\" ) \...\..... result is 10\
  Int( \"3.7\" ) \...\.... result is 3, stops at \".\" which can\'t be
  part of an integer\
  Int( \"junk3\" ) \.... result is 0, stops at \"j\"\
  \
  Int converts floating point numbers by rounding to the nearest
  integer.\
  NOTE: This is not the traditional meaning of Int in Basic.\
  \
  What about numbers exactly halfway between integers?\
  The rounding is to the nearest even integer:\
  \
  Int( 2.5 ) \... produces 2\
  Int( 3.5 ) \... produces 4\
  \
  See also Floor and Ceil for other types of rounding.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Int.bb)

  -----------------------------------------------------------------------
  ; Ceil / Floor / Int example, three kinds of rounding.\
  \
  ; Move mouse. Escape quits.\
  \
  Graphics 640, 480\
  \
  Const KEY_ESC = 1\
  \
  SetBuffer BackBuffer()\
  Origin 320, 240\
  \
  MoveMouse 320, 240 : HidePointer\
  \
  While Not KeyDown( KEY_ESC )\
  \
  Cls\
  \
  my = MouseY() - 240\
  Color 100, 100, 0\
  Line -320, my, 319, my\
  \
  DrawNumberLine\
  \
  y# = Float( -my ) / 32\
  \
  Text 100, 50, \" y = \" + y\
  Text 100, 70, \" Ceil( y ) = \" + Ceil( y )\
  Text 100, 90, \" Floor( y ) = \" + Floor( y )\
  Text 100, 110, \" Int( y ) = \" + Int( y )\
  \
  Flip\
  \
  Wend\
  End\
  \
  Function DrawNumberLine( ) ; vertical line with numeric labels\
  \
  Color 255, 255, 255\
  Line 0, -240, 0, 239\
  \
  For n = -7 To 7\
  yn = -32 \* n\
  Line -2, yn, 2, yn\
  Text -30, yn - 6, RSet( n, 2 )\
  Next\
  \
  End Function

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Int&ref=comments){target="_blank"}
to view the latest version of this page online
