[  GfxModeExists  ]{.Command}

[Parameters:]{.header}\

  -----------------------------------------------------------------------
  width - the width of the display mode you want to check for\
  height - the height of the display mode you want to check for\
  depth - the depth of the display mode you want to check for\
  gfx3d - optional parameter to check if mode is 3D-capable (defaults to
  0 for backwards compatibility)

  -----------------------------------------------------------------------

Description:\
\

+-----------------------------------------------------------------------+
| GfxModeExists is a sadly under-used command which will allow you to   |
| check if a given display mode exists on your player\'s graphics card  |
| before calling the Graphics or Graphics3D commands. Note that         |
| supplying the optional gfx3d parameter will also check if the         |
| specified mode is 3D-capable.                                         |
|                                                                       |
| GfxModeExists will return True if a display mode fitting the given    |
| parameters is available, and False if the specified mode is not       |
| available. If it returns False, a game should either use a different  |
| mode or exit politely (eg. RuntimeError \"I have been lazy and        |
| hard-coded my game to use a particular display mode which your system |
| doesn\'t have. I should really use CountGfxModes and                  |
| GfxModeWidth/Height/Depth to find out what your system can do. Oh,    |
| well, this saved 10 minutes out of my life!\"\... note subtle         |
| sarcastic hint!).                                                     |
+-----------------------------------------------------------------------+

[Example:](../3d_examples/GfxModeExists.bb)\
\

  -----------------------------------------------------------------------
  If GfxModeExists (2500, 2500, 64)\
  Graphics 2500, 2500, 64\
  Else\
  RuntimeError \"Domestic PC display hardware isn\'t yet capable of silly
  resolutions like 2500 x 2500 x 64-bit!\"\
  EndIf

  -----------------------------------------------------------------------
