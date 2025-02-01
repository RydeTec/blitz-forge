# GfxModeExists (width,height,depth)

# Parameters

  -----------------------------------------------------------------------
  width = width, in pixels (i.e. 640)\
  height = height, in pixels (i.e. 480)\
  depth = color depth (i.e. 16, 24, 32)

  -----------------------------------------------------------------------

# Description

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Use this command to verify whether or not the user\'s video card can use this graphic mode. Returns TRUE if the mode exists, FALSE if not. If you want to know what mode number this mode is, use FindGFXMode.
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/GfxModeExists.bb)

  -----------------------------------------------------------------------
  ; GFXModeExists example\
  \
  ; If there is a mode, tell user\
  mode=GfxModeExists(800,800,16)\
  \
  If mode=1 Then\
  Print \"The mode you requested exists!\"\
  Else\
  Print \"Sorry, that mode doesn\'t exist.\"\
  End If\
  \
  ; Wait for ESC press from user\
  While Not KeyHit(1)\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GfxModeExists&ref=comments){target="_blank"}
to view the latest version of this page online
