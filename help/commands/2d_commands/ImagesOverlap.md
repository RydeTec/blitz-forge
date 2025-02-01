# ImagesOverlap (image1,x1,y1,image2,x2,y2)

# Parameters

  -----------------------------------------------------------------------
  image1 = first image to test\
  x1 = image1\'s x location\
  y1 = image1\'s y location\
  image2 = second image to test\
  x2 = image2\'s x location\
  y2 = image2\'s y location

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This is a very fast, simple collision type command that will allow you
  to determine whether or not two images have overlapped each other. This
  does not take into account any transparent pixels (see ImagesCollide).\
  \
  As with any collision detection system in Blitz, you will need to know
  the variable names of the two images, and their X and Y locations at
  the moment collision checking occurs.\
  \
  In many cases, you might be able to get away with using this more
  crude, yet quite fast method of collision detection. For games where
  your graphics are very squared off and pixel-perfect accuracy isn\'t a
  must, you can employ this command to do quick and dirty overlap
  checking.\
  \
  The example blatently uses graphics that are much smaller than their
  container to show you how inaccurate this command can be - if not used
  wisely. The ImagesCollide example is identical to this one - and shows
  how pixel-perfect collision works.\
  \
  You might be able to get away with this on some more classical games
  like Robotron, Defender, Dig Dug, etc.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ImagesOverlap.bb)

  -----------------------------------------------------------------------
  ; ImagesOverlap Example\
  \
  ; Turn on graphics mode\
  Graphics 640,480,16\
  \
  ; Create two new empty graphics to store our circle and box in\
  gfxBox=CreateImage(50,50)\
  gfxCircle=CreateImage(50,50)\
  \
  ; Draw the box image first\
  ; Set drawing operations to point to our new empty graphic\
  SetBuffer ImageBuffer(gfxBox)\
  ; Change drawing color to blue\
  Color 0,0,255\
  ;Draw our box (note that it has a 10 pixel space around it)\
  Rect 10,10,30,30,1\
  \
  ; Repeat for the circle graphic\
  SetBuffer ImageBuffer(gfxCircle)\
  Color 255,0,0\
  ; Note the extra space between the circle and the edge of the graphic\
  Oval 10,10,30,30,1\
  \
  ; Let\'s not forget to put the drawing buffer back!\
  SetBuffer BackBuffer()\
  \
  ; Locate our box to a random, visible screen location\
  boxX=Rnd(50,610)\
  boxY=Rnd(50,430)\
  \
  ; Repeat the loop until we\'ve had a collision\
  Repeat\
  ; Attach our mouse to the circle to move it\
  circleX=MouseX()\
  circleY=MouseY()\
  ; Standard double buffer technique; clear screen first\
  Cls\
  ; Draw our objects at the designated location\
  DrawImage gfxBox,boxX,boxY\
  DrawImage gfxCircle,circleX,circleY\
  ; Standard double buffer technique; flip after all drawing is done\
  Flip\
  ; We test the locations of our box and circle to see if they have
  overlapped\
  Until ImagesOverlap (gfxBox,boxX,boxY,gfxCircle,circleX,circleY)\
  \
  ; Loop is over, we must\'ve collided!\
  Text 0,0, \"WE\'VE HAD A COLLISION! PRESS A MOUSE BUTTON\"\
  ; Can\'t see the text until we flip ..\
  Flip\
  ; Wait for a mouse click\
  WaitMouse()\
  ; End our graphics mode\
  EndGraphics\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ImagesOverlap&ref=comments){target="_blank"}
to view the latest version of this page online
