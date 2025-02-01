# GfxMode3DExists( width, height, depth)

# Parameters

  -----------------------------------------------------------------------
  width - the width of the display mode you want to check for\
  height - the height of the display mode you want to check for\
  depth - the depth of the display mode you want to check for

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  GfxMode3DExists will test to see whether the host machine\'s graphics
  card supports a certain video mode, as specified by the width, height
  and depth parameters, and also whether the specified video mode is
  3D-capable.\
  \
  If these answer to both these things is yes, then the command will
  return true, otherwise false.\
  \
  Most, if not all modern graphics cards are 3D-capable in all available
  graphics modes, however a few older ones are 2D-capable only in certain
  video modes hence the need for GfxMode3DExists as well as
  GfxModeExists.

  -----------------------------------------------------------------------

# [Example](../3d_examples/GfxMode3DExists.bb)

  -----------------------------------------------------------------------
  If GfxMode3DExists (2500, 2500, 64)\
  Graphics3D 2500, 2500, 64\
  Else\
  RuntimeError \"Domestic PC display hardware isn\'t yet capable of silly
  resolutions like 2500 x 2500 x 64-bit!\"\
  EndIf

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GfxMode3DExists&ref=comments){target="_blank"}
to view the latest version of this page online
