# CountGFXModes()

# Parameters

  -------
  None.
  -------

# Description

  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Use this command to return the number of video modes the user\'s video card can display in. Use the GFXModeWidth, GFXModeHeight, and GFXModeDepth with each number of video mode to determine the width, height, and color depth capabilities of each mode. See example.
  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/CountGFXModes.bb)

  -----------------------------------------------------------------------
  ; CountGFXModes()/GfxModeWidth/GfxModeHeight/GfxModeDepth example\
  \
  intModes=CountGfxModes()\
  \
  Print \"There are \" + intModes + \"graphic modes available:\"\
  \
  ; Display all modes including width, height, and color depth\
  For t = 1 To intModes\
  Print \"Mode \" + t + \":\
  Print \"Width=\" + GfxModeWidth(t)\
  Print \"Height=\" + GfxModeHeight(t)\
  Print \"Height=\" + GfxModeDepth(t)\
  Next\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CountGFXModes&ref=comments){target="_blank"}
to view the latest version of this page online
