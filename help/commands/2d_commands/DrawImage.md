# DrawImage image, x,y \[,frame\]

# Parameters

  -----------------------------------------------------------------------
  image = variable image pointer previously designated\
  x = the \'x\' location of the screen to display the graphic\
  y = the \'y\' location of the screen to display the graphic\
  frame = the frame number of the AnimImage to display (optional: default
  is 0)

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command draws a previously loaded image. This command draws both
  single image graphics (loaded with the LoadImage command) as well as
  animated images (loaded with the LoadAnimImage command).\
  \
  You specify where on the screen you wish the image to appear. You can
  actually \'draw\' off the screen as well by using negative values or
  positive values that are not visible \'on the screen\'.\
  \
  Finally, if you are using an animated image (loaded with the
  LoadAnimImage), you can specify which frame of the imagestrip is
  displayed with the DrawImage command.\
  \
  One of the most common problems new Blitz programmers face when using
  drawing commands is that even though they draw the graphic to the
  screen, they never see it!\
  \
  Remember, Blitz Basic is fast \... too fast for the human eye. You will
  have to either continuously draw the image over and over again (like
  the way a cartoon or TV works), clearing the screen each time a change
  is made (this is called double buffering); or you will need to delay
  Blitz\'s execution long enough in \'human time\' to let you SEE the
  picture. We will do the double buffering approach.\
  \
  See also: DrawBlock

  -----------------------------------------------------------------------

# [Example](../2d_examples/DrawImage.bb)

  -----------------------------------------------------------------------
  ; LoadImage and DrawImage example\
  \
  ; Declare a variable to hold the graphic file handle\
  Global gfxPlayer\
  \
  ; Set a graphics mode\
  Graphics 640,480,16\
  \
  ; Set drawing operations for double buffering\
  SetBuffer BackBuffer()\
  \
  ; Load the image and assign its file handle to the variable\
  ; - This assumes you have a graphic called player.bmp in the\
  ; same folder as this source code\
  gfxPlayer=LoadImage(\"player.bmp\")\
  \
  ; Let\'s do a loop where the graphic is drawn wherever the\
  ; mouse is pointing. ESC will exit.\
  While Not KeyHit(1)\
  Cls ; clear the screen\
  DrawImage gfxPlayer,MouseX(),MouseY() ; Draw the image!\
  Flip ; flip the image into view and clear the back buffer\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=DrawImage&ref=comments){target="_blank"}
to view the latest version of this page online
