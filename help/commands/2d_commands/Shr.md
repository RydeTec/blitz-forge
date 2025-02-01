# Shr repetitions

# Parameters

  ----------------------------------------------
  repetitions = number of shifts to make right
  ----------------------------------------------

# Description

  -----------------------------------------------------------------------
  This performs a left binary shift on the value the specified number of
  times. This basically is a faster method of dividing the value
  exponentially. By shifting right once, you are dividing the value by 2.
  By shifting right twice, you divide by 4, etc.\
  \
  The usefulness of this command is basically faster math execution.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Shr.bb)

  -----------------------------------------------------------------------
  ; shl, shr, sar examples\
  \
  value = 100\
  \
  ; multiple x 2\
  Print \"Shift 1 bit left; Value = \" + value Shl 1\
  ; multiple x 4\
  Print \"Shift 2 bits left; Value = \" + value Shl 2\
  ; multiple x 16\
  Print \"Shift 4 bits left; Value = \" + value Shl 4\
  ; divide by 2\
  Print \"Shift 1 bit right; Value = \" + value Shr 1\
  ; divide by 4\
  Print \"Shift 2 bits right; Value = \" + value Shr 2\
  ; divide by 16\
  Print \"Shift 4 bits right; Value = \" + value Shr 4\
  \
  Print \"Shift by SAR 4 times = \" + value Sar 4\
  \
  WaitKey()\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Shr&ref=comments){target="_blank"}
to view the latest version of this page online
