# RectsOverlap (rect1 X,rect1 Y,rect1 Width,rect1 Height,rect2 X,rect2 Y,rect2 Width,rect2 Height)

# Parameters

  -----------------------------------------------------------------------
  rect1 X = rectangle 1 x location\
  rect1 Y = rectangle 1 y location\
  rect1 Width = rectangle 1 width\
  rect1 Height = rectangle 1 height\
  rect2 X = rectangle 2 x location\
  rect2 Y = rectangle 2 y location\
  rect2 Width = rectangle 2 width\
  rect2 Height = rectangle 2 height

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command will take two rectangular locations on the screen and see
  if they overlap. You will need to know the x, y, width, and height of
  both regions to test.\
  \
  I\'m still trying to find a real good logical use for this command with
  all the other collision commands available to you like ImagesOverlap,
  ImagesCollide, ImageRectOverlap, and ImageRectCollide. My guess is that
  this is the absolute fastest possible collision method available and
  useful to those wishing to write their own collision routines.\
  \
  Unlike the other collision commands, there is no image to detect a
  collision with - simply one rectangular location overlapping another.
  You could probably use this command instead of the ImageRectOverlap
  command, as they are really basically doing the same thing (and I
  betcha this is faster).\
  \
  This would be useful for very easy-going \'Monkey Island\' games to
  check the position of your pointer against a screen location (or \'hot
  spot\') when pixel perfect accuracy (heck, image graphics in general)
  are not really needed.

  -----------------------------------------------------------------------

# [Example](../2d_examples/RectsOverlap.bb)

  -----------------------------------------------------------------------
  ; RectsOverlap Example\
  ; Flashing graphics warning! Gets hypnotic \...\
  \
  ; Turn on graphics mode\
  Graphics 640,480,16\
  \
  ; Double buffering, and randomize the randomizer\
  SetBuffer BackBuffer()\
  SeedRnd MilliSecs()\
  \
  ; Repeat the loop until ESC pressed\
  While Not KeyHit(1)\
  \
  ; Generate a random rectangle\
  rect1X=Rnd(50,610)\
  rect1Y=Rnd(50,430)\
  rect1W=20\
  rect1H=20\
  \
  ; And another\
  rect2X=Rnd(50,610)\
  rect2Y=Rnd(50,430)\
  rect2W=20\
  rect2H=20\
  ; Clear the screen standard double buffering\
  Cls\
  ; Draw our rectangle2 in random colors\
  Color Rnd(255),Rnd(255),Rnd(255)\
  Rect rect1X,rect1Y,rect1W,rect1H,0\
  Color Rnd(255),Rnd(255),Rnd(255)\
  Rect rect2X,rect2Y,rect2W,rect2H,0\
  \
  ; Did they collide? If so, print a message and exit the loop!\
  If RectsOverlap
  (rect1X,rect1Y,rect1W,rect1H,rect2X,rect2Y,rect2W,rect2H) Then\
  Text 0,0, \"Our boxes finally collided! Press a mouse button\...\"\
  ; We do a flip here to ensure the text message gets seen too!\
  Flip\
  Exit ; exit the While/Wend loop\
  End If\
  ; Flip the rects into view, wait 1/10th of a sec, repeat\
  Flip\
  Delay 100\
  Wend\
  ; Wait for a mouse click\
  WaitMouse()\
  ; End our graphics mode\
  EndGraphics\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=RectsOverlap&ref=comments){target="_blank"}
to view the latest version of this page online
