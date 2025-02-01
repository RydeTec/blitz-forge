# ImageRectOverlap (image,x,y,rect x,rect y,rect width,rect height)

# Parameters

  -----------------------------------------------------------------------
  image = Image to test collision against\
  x = image\'s x location\
  y = image\'s y location\
  rect x = x location start of the rect to test\
  rect y = y location start of the rect\
  rect width = width of the rect\
  rect height = height of the rect

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  There are many times when you need to see if an image has collided with
  (or is touching) a specific rectangular area of the screen. This
  command performs a collision detection between the image of your choice
  and a specified rectangle on the screen. Transparent pixels are ignored
  during the collision process, making this command a bit inaccurate for
  odd shaped graphics. See ImageRectCollide for pixel perfect collisions
  between an image and rectangular area of the screen.\
  \
  The usefulness of this comes into play when you think of a game like
  Monkey Island - when you might have a backdrop on the screen showing a
  room that has items in it the player can interact with using a mouse
  pointer graphic. In some cases, the items on the screen you wish to
  interact with will be seperate (often animated or moving) images of
  their own. For this situation, you would be better off using
  ImagesCollide or ImagesOverlap to detect the collision between pointer
  graphic and the image.\
  \
  Howevever, should your program just need to detect a graphic (like a
  mouse pointer) over at a particular location/region of the screen
  (often called a \'hot spot\'), this command works great!\
  \
  As with any collision in Blitz, you will need to know the PRECISE
  location of the graphic you wish to test collision with, as well as the
  x, y, width, and height of the screen area (rect) you wish to test.\
  \
  The example blatently uses graphics that are much smaller than their
  container to show you how inaccurate this command can really be - if
  not used carefully. The ImageRectCollide example is identical to this
  one - and shows how accurate the pixel-perfect collision method can be
  with graphics of this nature.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ImageRectOverlap.bb)

  -----------------------------------------------------------------------
  ; ImageRectOverlap Example\
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
  \
  ; Let\'s not forget to put the drawing buffer back!\
  SetBuffer BackBuffer()\
  Color 0,0,255\
  \
  ; Locate our box to a random, visible screen location\
  hotX=Rnd(50,610)\
  hotY=Rnd(50,430)\
  hotW=Rnd(20,100)\
  hotH=Rnd(20,100)\
  \
  ; Repeat the loop until we\'ve had a collision\
  Repeat\
  ; Attach our mouse to the circle to move it\
  circleX=MouseX()\
  circleY=MouseY()\
  ; Standard double buffer technique; clear screen first\
  Cls\
  ; Draw our rectangle\
  Rect hotX,hotY,hotW,hotH,0\
  DrawImage gfxCircle,circleX,circleY\
  ; Standard double buffer technique; flip after all drawing is done\
  Flip\
  ; We test the locations of our rectangle area and circle to see if they
  have overlapped\
  Until ImageRectOverlap (gfxCircle,circleX,circleY,hotX,hotY,hotW,hotH)\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ImageRectOverlap&ref=comments){target="_blank"}
to view the latest version of this page online
