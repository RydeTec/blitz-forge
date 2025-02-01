# True

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  TRUE is a keyword to denote a positive result in a conditional
  statement. Often times, TRUE is implied and doesn\'t need to be
  directly referenced. TRUE can also be used as a RETURN value from
  aFUNCTION. See the example.\
  \
  See also: [False](False.htm){.small}, [If](If.htm){.small},
  [Select](Select.htm){.small}, [While](While.htm){.small},
  [Repeat](Repeat.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/True.bb)

  -----------------------------------------------------------------------
  ; TRUE example\
  \
  ; Assign test a random number of 0 or 1\
  test= Rnd(0,1)\
  \
  ; TRUE is implied; This statement REALLY means: if test=1 is TRUE then
  proceed\
  If test=1 Then\
  Print \"Test was valued at 1\"\
  End If\
  \
  ; Let\'s set test to be true\
  test=True\
  \
  ; Pointlessly test it\
  If test=True Then\
  Print \"Test is true\"\
  End If

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=True&ref=comments){target="_blank"}
to view the latest version of this page online
