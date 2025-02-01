# Rand (\[low value\],high value)

# Parameters

  -----------------------------------------------------------------------
  low value = optional - defaults to 1; lowest number to generate\
  high value = highest number to generate

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Unlike the RND command, this command actually returns only integer
  values. The low value defaults to 1 if no value is specified. The high
  value is the highest number that can be randomly generated.\
  \
  If you need to generate floating point random numbers, use Rnd.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Rand.bb)

  -----------------------------------------------------------------------
  ; Rand example\
  \
  ; Set the randomizer seed for more true random numbers\
  SeedRnd (MilliSecs())\
  \
  ; Generate random numbers between 1 and 100\
  For t = 1 To 20\
  Print Rand(1,100)\
  Next\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Rand&ref=comments){target="_blank"}
to view the latest version of this page online
