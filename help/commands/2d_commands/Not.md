# Not

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  The NOT operator is used to determine if a condition is FALSE instead
  of TRUE. This is frequently used in WHILE loops and IF statements to
  check for the NON-EXISTANCE of an event or value. We use NOT frequently
  in our examples, inside WHILE loops to test if the ESC key has been
  pressed.\
  \
  See also: [And](And.htm){.small}, [Or](Or.htm){.small},
  [Xor](Xor.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Not.bb)

  -----------------------------------------------------------------------
  ; NOT example\
  \
  ; Loop until they press ESC\
  While Not KeyHit(1) ; As long as ESC isn\'t pressed \...\
  Print \"Press ESC to quit!\"\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Not&ref=comments){target="_blank"}
to view the latest version of this page online
