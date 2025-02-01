# Xor

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  Often used for lightweight (meaning worthless ;) encryption, this will
  take two values and perform an exclusive OR with each bit following the
  basic rules of XOR. The result can be XORed with one of the original
  numbers to reveal the other number. See the example for more.\
  \
  See also: [And](And.htm){.small}, [Or](Or.htm){.small},
  [Not](Not.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Xor.bb)

  ------------------------------------------------------------------------------------------------------------------------------------------
  num=%11110000111100001111000011110000 ; Define a bit pattern which is easy to recognize\
  \
  bitmask=Rnd(-2147483648,2147483647) ; Define a RANDOM Xor 32bit wide bitmask\
  \
  ; This line prints the binary and decimal numbers before the Xor\
  Print \"Binary number is: \"+Bin\$(num)+\" (\"+num+\")\"\
  \
  ; This line prints the binary and decimal numbers of the Xor bitmask\
  Print \"Xor bitmask is: \"+Bin\$(bitmask)+\" (\"+bitmask+\")\"\
  \
  Print
  \"\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\"\
  \
  ; This line Xor\'s the number with the bitmask\
  xres=num Xor bitmask\
  \
  ; This line prints the binary and decimal numbers after the Xor\
  Print \"Xor result is: \"+Bin\$(xres)+\" (\"+xres+\")\"\
  Print
  \"\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\"\
  \
  ; This line Xor\'s the Xor result with the bitmask again\
  xres=xres Xor bitmask\
  ; This line prints the binary and decimal numbers after the second Xor. NOTE: This number is identical to the original number\
  Print \"Result Xor\'ed again: \"+Bin\$(xres)+\" (\"+xres+\")\"\
  \
  WaitMouse ; Wait for the mouse before ending.

  ------------------------------------------------------------------------------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Xor&ref=comments){target="_blank"}
to view the latest version of this page online
