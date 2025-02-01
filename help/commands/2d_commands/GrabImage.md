# GrabImage image,x,y,\[frame\]

# Parameters

  -----------------------------------------------------------------------
  image = variable of image handle\
  x = starting x location to grab\
  y = starting y location to grab\
  frame = optional frame to insert the grabbed image into

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Quite possibly one of the most useful yet underdocumented, confusing
  commands in the Blitz Basic language is GrabImage.\
  \
  This command allows you to grab a portion of the current drawing buffer
  (this could be an image too if you\'ve set it as an ImageBuffer) and
  stick it into an image.\
  \
  There are a million and one uses for this command, so I\'ll let you use
  your imagination. However, the command doesn\'t make much sense as it
  is documented, so let me clarify.\
  \
  First, you must use CreateImage to create a new blank image in which to
  grab to. Whatever size you create the image, THAT is how much of the
  buffer will be grabbed from the x,y location you specify in GrabImage.\
  \
  For example, you create a new image with a size of 50 pixels by 50
  pixels. When you call GrabImage, you choose x=100, y=100. The area you
  will grab and put into your new image will be 100,100 to 150,150. If
  you attempt to GrabImage into an image variable that hasn\'t been
  created yet, you will get an error.\
  \
  Note: You can REPLACE an existing image with a grabbed one.\
  \
  See the example for a more practical look.

  -----------------------------------------------------------------------

# [Example](../2d_examples/GrabImage.bb)

  -----------------------------------------------------------------------
  ; GrabImage example\
  \
  ; Turn on graphics\
  Graphics 640,480,16\
  \
  ; We\'ll be drawing right to the front buffer\
  SetBuffer FrontBuffer()\
  \
  ; You must create an empty image to grab from first\
  gfxGrab=CreateImage(100,100)\
  \
  ; Draw random rectangles on the screen until the\
  ; user presses ESC\
  While Not KeyHit(1)\
  ; random color\
  Color Rnd(255),Rnd(255),Rnd(255)\
  ; super random rectangles\
  Rect Rnd(640),Rnd(480),Rnd(100),Rnd(100),Rnd(1)\
  Delay 50\
  Wend\
  \
  ; Now, grab an image, starting at 100,100 and put it in gfxGrab\
  GrabImage gfxGrab,100,100\
  \
  ; Clear screen and show user the grabbed image\
  Cls\
  Text 0,0, \"Here is your grabbed image! Press a mouse key \...\"\
  DrawImage gfxgrab,50,50\
  \
  ; Wait for a mouse press\
  WaitMouse()\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GrabImage&ref=comments){target="_blank"}
to view the latest version of this page online
