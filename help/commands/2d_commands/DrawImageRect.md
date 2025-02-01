# DrawImageRect image,x,y,rect_x,rect_y,rect_width,rect_height,\[frame\]

# Parameters

  -----------------------------------------------------------------------
  image = variable holding the image handle\
  x = x location on the screen to draw the image\
  y = y location on the screen to draw the image\
  rect_x = starting x location within the image to draw\
  rect_y = starting y location within the image to draw\
  rect_width = the height of the area to draw\
  rect_height = the width of the area to draw\
  frame = optional frame number of image to draw

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command will let you draw a rectangular PORTION of an image to the
  designated location on the screen. The transparent/masked portions of
  the original image will be drawn transparent, just as you normally
  would draw an image.\
  \
  This is handy if you are doing something like revealing part of a
  screen when the player uncovers something (think QIX). You could load
  the \'picture\' into an image, then when the player clears a
  rectangular region, you could paste that portion of the final image
  onto the playfield. If you want to draw the image portion with no
  transparency or mask, use DrawBlockRect instead.

  -----------------------------------------------------------------------

# [Example](../2d_examples/DrawImageRect.bb)

  -----------------------------------------------------------------------
  ; DrawImageRect Example\
  \
  ; Turn on graphics mode\
  Graphics 640,480,16\
  \
  ; Create new empty graphic to store our circle in\
  gfxCircle=CreateImage(50,50)\
  \
  ; Draw the circle image\
  ; Set drawing operations to point to our new empty graphic\
  SetBuffer ImageBuffer(gfxCircle)\
  Color 255,0,0\
  ; Note the extra space between the circle and the edge of the graphic\
  Oval 10,10,30,30,1\
  SetBuffer FrontBuffer()\
  \
  \
  ; Let\'s draw portions of the circle randomly\
  While Not KeyHit(1)\
  ; We take random sized portions of the circle and put them\
  ; at a random location \... wash, rinse, and repeat\
  DrawImageRect gfxCircle,Rnd(640),Rnd(480),0,0,Rnd(50),Rnd(50),0\
  Delay 100\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=DrawImageRect&ref=comments){target="_blank"}
to view the latest version of this page online
