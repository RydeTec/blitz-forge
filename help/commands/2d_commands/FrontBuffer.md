# FrontBuffer()

# Parameters

  ------
  None
  ------

# Description

  -----------------------------------------------------------------------
  It\'s important to understand buffers when writing a game.\
  \
  What the player can see at any given time is usually the front buffer.
  Anything you draw to this buffer is IMMEDIATELY visible to the player.
  This sounds fast (and it is) but the problem is that when you are
  drawing to the front buffer - like a piece of paper and pencil -
  anything you draw on the screen overwrites anything else that exists in
  the same space. So, if you want to \'save\' any portion of the screen
  from being overwritten by another drawing operation, YOU - the
  programmer - have to copy the area \'under\' the location of the new
  operation to an image so you can replace it later. Imagine taking a
  piece of paper with a picture of some mountains, and making an airplane
  pass in front of them, inch by inch. Every time the plane moves, you
  have to draw the new area that will be under the plane next on another
  sheet of paper (so you know what it looked like) then draw the plane
  over the new place. Next time you move, you will repeat this, then draw
  the image back in the OLD plane location. This process is
  labor-intensive and largely unnecessary thanks to a process called
  DOUBLE BUFFERING (see BackBuffer(). Double buffering is used for pretty
  much all games for high-action with lots of objects on the screen.\
  \
  So, if double buffering rocks so much, why would you WANT to ever draw
  to the front buffer? Sometimes, you just want to draw stuff to the
  screen, without caring what you overwrite. You don\'t have to worry
  about redrawing the screen over and over again in double buffering in
  this case. Just set the buffer to FrontBuffer() and you can write
  directly to the screen in real time.

  -----------------------------------------------------------------------

# [Example](../2d_examples/FrontBuffer.bb)

  -----------------------------------------------------------------------
  ; FrontBuffer()/Rect Example\
  \
  ; Engage graphics mode\
  Graphics 640,480,16\
  \
  ; Set the drawing buffer to front - instant drawing ops!\
  SetBuffer FrontBuffer()\
  \
  ; Repeat this until user presses ESC\
  While Not KeyHit(1)\
  ; Set a random color\
  Color Rnd(255),Rnd(255),Rnd(255)\
  ; Draw a rectangle at a random location, with a random width and
  height\
  ; plus randomly choose if the rectangle is solid or just an outline\
  Rect Rnd(640),Rnd(480),Rnd(50),Rnd(50),Rnd(0,1)\
  ; Blitz is so dang fast, we need a delay so you can watch it draw!\
  Delay 10\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=FrontBuffer&ref=comments){target="_blank"}
to view the latest version of this page online
