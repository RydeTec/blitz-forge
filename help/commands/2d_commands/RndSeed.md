# RndSeed ()

# Parameters

  --
  --

# Description

  -----------------------------------------------------------------------
  Returns the current random number seed value.\
  \
  This allows you to \'catch\' the state of the random generator, usually
  for the purpose of restoring it later.

  -----------------------------------------------------------------------

# [Example](../2d_examples/RndSeed.bb)

  -----------------------------------------------------------------------
  ;\'randomize\' the random number generator!\
  SeedRnd MilliSecs()\
  For k=1 To 12345\
  Rnd(1)\
  Next\
  \
  ;capture random number generator state\
  state=RndSeed()\
  \
  ;generate a bunch of numbers:\
  Print \"First set:\"\
  For k=1 To 5\
  Print Rnd(1)\
  Next\
  \
  ;restore random nummber generator state\
  SeedRnd state\
  \
  ;generate another bunch:\
  Print \"Second set (same as the first set\...):\"\
  For k=1 To 5\
  Print Rnd(1)\
  Next

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=RndSeed&ref=comments){target="_blank"}
to view the latest version of this page online
