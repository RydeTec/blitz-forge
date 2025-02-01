# TileBlock image \[,x,y,frame\]

# Parameters

  -----------------------------------------------------------------------
  image = file handle variable holding the loaded image\
  x = x coordinate offset(optional)\
  y = y coordinate offset (optional)\
  frame = frame of the image to use (optional)

  -----------------------------------------------------------------------

# Description

  ------------------------------------------------------------------------------------------------------------------------------------
  Similar to TileImage but ignores transparency. Use this to tile an entire or portion of the screen with a single repetative image.
  ------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/TileBlock.bb)

  -----------------------------------------------------------------------
  ; TileBlock example\
  Graphics 800,600,16\
  \
  ; Load an image to tile (your location might vary)\
  gfxBall=LoadImage(\"C:Program FilesBlitz Basicsamplesall.bmp\")\
  \
  ; Tile the graphic without transparency\
  TileBlock gfxBall\
  \
  ; Wait for ESC to hit\
  While Not KeyHit(1)\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TileBlock&ref=comments){target="_blank"}
to view the latest version of this page online
