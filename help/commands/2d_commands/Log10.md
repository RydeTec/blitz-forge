# Log# ( x# )

# Parameters

  --------------------------
  x = any positive number.
  --------------------------

# Description

  -----------------------------------------------------------------------
  Common logarithm of x. This is the inverse of raising 10 to a power.\
  \
  y = Log10( x ) means y satifies x = 10 \^ y.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Log10.bb)

  -----------------------------------------------------------------------
  ; Log10 example\
  \
  ; NaN means \"Not a Number\", the numerical result is invalid.\
  \
  HidePointer\
  \
  ; First, what happens with non-positive numbers\...\
  \
  Print \"Log10( -1 ) = \" + Log10( -1 )\
  Print \"Log10( 0 ) = \" + Log10( 0 )\
  Print\
  \
  ; \... and now some well behaved numbers\...\
  \
  x# = 1.0 / 4096.0 ; small positive number\
  \
  For n = 1 To 15\
  \
  Print \"Log10( \" + LSet( x, 13 ) + \" ) = \" + Log10( x )\
  x = 8 \* x\
  \
  Next\
  \
  WaitKey() : End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Log10&ref=comments){target="_blank"}
to view the latest version of this page online
