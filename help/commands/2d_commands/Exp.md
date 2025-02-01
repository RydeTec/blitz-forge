# Exp# ( x# )

# Parameters

  -----------------
  x = any number.
  -----------------

# Description

  -----------------------------------------------------------------------
  This is e\^x where e = 2.71828\...\
  \
  For the curious, e is defined by the infinite sum:\
  \
  2 + 1/(2) + 1/(2\*3) + 1/(2\*3\*4) + 1/(2\*3\*4\*5) + \...\
  \
  See also Log()

  -----------------------------------------------------------------------

# [Example](../2d_examples/Exp.bb)

  -----------------------------------------------------------------------
  ; Exp example\
  \
  ; Exp(x) is the same as e\^x.\
  \
  ; Actually, due to the approximate nature of floating point arithmetic\
  ; these will not be exactly equal. But in the following example\
  ; the difference is so small you can\'t see it.\
  \
  e# = 2.71828182845905 ; over-specified, might help and can\'t hurt.\
  \
  x# = .125 ; = 1/8\
  \
  Print \" x Exp(x) e\^x \"\
  Print \"======= ========= =========\"\
  \
  For k = 1 To 7\
  \
  Write \" \" + LSet( x , 7 )\
  Write RSet( Exp( x ), 10 )\
  Print RSet( e\^x , 12 )\
  x = 2 \* x\
  \
  Next\
  \
  WaitKey() : End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Exp&ref=comments){target="_blank"}
to view the latest version of this page online
