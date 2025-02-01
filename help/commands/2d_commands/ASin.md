# ASin# ( s# )

# Parameters

  -----------------------------------------------------------------------
  s = a number in the range -1 to +1\
  \
  It is considered to be the sine of an angle.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  ASin( s ) is an angle which has sine = s.\
  It is in the range -90 to +90 degrees.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ASin.bb)

  -----------------------------------------------------------------------
  ; ASin example\
  \
  ; NaN means \"Not a Number\", the numerical result is invalid.\
  \
  Const width = 640, height = 480\
  \
  Graphics width, height\
  \
  \
  Local S#, AS# ; Sin and ASin\
  \
  Print \" S ASin( S )\"\
  Print \"==================\"\
  \
  For n = -11 To 11\
  \
  S = n / 10.0\
  AS = ASin( S )\
  Print RSet(S, 6) + RSet( AS, 10)\
  \
  Next\
  \
  WaitKey() : End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ASin&ref=comments){target="_blank"}
to view the latest version of this page online
