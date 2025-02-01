# KeyHit (scancode)

# Parameters

  ---------------------------------------------
  scancode = the scancode for the key to test
  ---------------------------------------------

# Description

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command returns the number of times a specified key has been hit since the last time you called the KeyHit() command. Check the ScanCodes for a complete listing of scancodes.
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/KeyHit.bb)

  -----------------------------------------------------------------------
  ; KeyHit Example\
  \
  ; Set up the timer\
  current=MilliSecs()\
  Print \"Press ESC a bunch of times for five seconds\...\"\
  \
  ; Wait 5 seconds\
  While MilliSecs() \< current+5000\
  Wend\
  \
  ; Print the results\
  Print \"Pressed ESC \" + KeyHit(1) + \" times.\"\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=KeyHit&ref=comments){target="_blank"}
to view the latest version of this page online
