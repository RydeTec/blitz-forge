# ImagesCollide (image1,x1,y1,frame1,image2,x2,y2,frame2)

# Parameters

  -----------------------------------------------------------------------
  image1 - first image to test\
  x1 - image1\'s x location\
  y1 - image1\'s y location\
  frame1 - image1\'s frame to test (optional)\
  \
  image2 - second image to test\
  x2 - image2\'s x location\
  y2 - image2\'s y location\
  frame2 - image2\'s frame to test (optional)

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This is THE command to get pixel-perfect collisions between images. It
  will not consider transparent pixels during the collision check
  (basically, only the \'meat\' of the image will invoke a collision).
  This makes it perfect for most situations where you have odd-shaped
  graphics to text against.\
  \
  The ImagesOverlap command is MUCH faster, however, but can only
  determine if ANY of the two images have overlapped (this INCLUDES
  transparent pixels). This method works if you have graphics that
  completely fill their container and/or you don\'t plan on needing
  pinpoint accuracy.\
  \
  As with any collision detection system in Blitz, you will need to know
  the variable names of the two images, and their X and Y locations at
  the moment collision checking occurs.\
  \
  The example blatently uses graphics that are much smaller than their
  container to show you how accurate this command really is. The
  ImagesOverlap example is identical to this one - and shows how
  inaccurate the overlapping method can be with graphics of this nature.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ImagesCollide.bb)

  -----------------------------------------------------------------------
  ; ImagesCollide Example\
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
  ; We test the locations of our box and circle to see if they have pixel
  collided\
  Until ImagesCollide (gfxBox,boxX,boxY,0,gfxCircle,circleX,circleY,0)\
  \
  ; Loop is over, we must\'ve collided!\
  Text 0,0, \"WE\'VE HAD A COLLISION! PRESS A MOUSE BUTTON\"\
  ; Can\'t see the text until we flip ..\
  Flip\
  ; Wait for a mouse click\
  WaitMouse()\
  ; End our graphics mode\
  EndGraphics

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ImagesCollide&ref=comments){target="_blank"}
to view the latest version of this page online
