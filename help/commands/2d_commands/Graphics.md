# Graphics width, height, color depth,\[mode\]

# Parameters

  -----------------------------------------------------------------------
  width = width of screen in pixels (640, 800, etc)\
  height = height of screen in pixels (480, 600, etc)\
  color depth = depth in bits (0, 16, 24, or 32 bit)\
  mode = Video mode (see description); Optional

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command sets Blitz into \'graphics\' mode with the specified
  width, height, and color depth (in bits). This command must be executed
  before any graphic related commands can be used. Every time this
  command is used, any images loaded will be lost, and all handles to
  images will become invalid.\
  \
  Color depth is OPTIONAL and should be left blank or set to zero if
  possible - Blitz will then determine the best color mode automatically
  for the user\'s video card. In general 16 bit should be sufficient for
  most games and should be used wherever possible to ensure greatest
  compatibility.\
  \
  Valid graphic width and height varies GREATLY from card to card so to
  be sure your users can display the mode you wish, use the GfxModeExists
  command to be sure before you try and set the mode yourself. Common
  resolutions that are probably safe are 640x480 and 800x600. Try to
  avoid odd screen modes like 640x400 as MANY MANY cards cannot display
  them. Chances are if you can set your Windows screen resolution to a
  particular resolution, then Blitz will behave in that resolution also.\
  \
  Remember, each step higher in resolution and color depth mark a large
  jump in system requirements and may be inversely proportionate to
  system performance. If you use the lowest resolution and depth possible
  to meet your desired game parameters, it will allow more people with
  lesser systems than your own still play and enjoy your game. This is
  why many games still support a 640x480 mode :)\
  \
  An extra parameter is used at the end of the command after the color
  depth. Here are the parameters:\
  \
  0 : auto - windowed in debug, fullscreen in non-debug (this is the
  default)\
  1 : fullscreen mode\
  2 : windowed mode\
  3 : scaled window mode\
  \
  See also: [EndGraphics](EndGraphics.htm){.small},
  [Graphics3D](Graphics3D.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Graphics.bb)

  -----------------------------------------------------------------------
  ;GRAPHICS Example\
  \
  ; Set The Graphic Mode\
  Graphics 800,600\
  \
  ; Now print something on the graphic screen\
  Text 0,0, \"This is some text printed on the graphic screen (and a
  white box)! Press ESC \...\"\
  \
  ; Now for a box\
  Rect 100,100,200,200,1\
  \
  While Not KeyHit(1)\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Graphics&ref=comments){target="_blank"}
to view the latest version of this page online
