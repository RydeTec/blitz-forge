# CopyRect src_x,src_y,src_width,src_height,dest_x,dest_y,\[src_buffer\],\[dest_buffer\]

# Parameters

  -----------------------------------------------------------------------
  src_x = source top left x location to begin copying from\
  src_y = source top left y location to begin copying from\
  src_width = width of source area to copy\
  src_height = height of source area to copy\
  dest_x = destination top left x location to copy to\
  dest_y = destination top left y location to copy to\
  src_buffer = handle to the source image buffer (optional)\
  dest_buffer = handle to the destination image buffer (optional)

  -----------------------------------------------------------------------

# Description

  ----------------------------------------------------------------------------------------------------------------
  Copies a rectangle of graphics from one buffer to another. If a buffer is omitted, the current buffer is used.
  ----------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/CopyRect.bb)

  -----------------------------------------------------------------------
  ; CopyRect Example\
  \
  ; Turn on graphics mode\
  Graphics 800,600,16\
  \
  ; create a blank image\
  gfxBlank=CreateImage (300,300)\
  \
  ; Fill the screen with random boxes in random colors\
  For t = 1 To 1000\
  Rect Rand(800),Rand(600),Rand(100),Rand(100),Rand(0,1)\
  Color Rand(255),Rand(255),Rand(255)\
  Next\
  \
  ; Wait a couple of seconds so the user can see it\
  Delay 2000\
  \
  ; Copy graphics randomly from the front buffer to the blank image\
  CopyRect
  Rand(800),Rand(600),300,300,0,0,FrontBuffer(),ImageBuffer(gfxBlank)\
  \
  ; Clear the screen, draw the copied to image, wait for user to hit a
  key\
  Cls\
  DrawImage gfxBlank,0,0\
  WaitKey\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CopyRect&ref=comments){target="_blank"}
to view the latest version of this page online
