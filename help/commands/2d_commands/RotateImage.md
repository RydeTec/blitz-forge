# RotateImage image,value#

# Parameters

  -----------------------------------------------------------------------
  image = variable containing the image handle\
  value# = floating number from 0 to 360 degrees

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  I\'m going to start this description off with:\
  \
  This command is not fast enough to render rotations in real time!\
  \
  Now, the purpose of this command is to rotate an image a specified
  number of degrees. Since it is slow, you will need to pre-calculate
  rotated images with this command. This means, before the program
  actually displays the images you rotate, you will want to rotate them
  ahead of time.\
  \
  This command automatically dithers/anti-aliases the rotated graphic
  image, so it might mess with your transparency. To avoid this issue,
  use the TFormFilter command. This will render the rotated images with
  bi-linear filtering.\
  \
  I\'m going to end this command with:\
  \
  This command is not fast enough to render rotations in real time!

  -----------------------------------------------------------------------

# [Example](../2d_examples/RotateImage.bb)

  -----------------------------------------------------------------------
  ; RotateImage/TFormFilter Example\
  \
  ; Turn on graphics mode\
  Graphics 640,480,16\
  \
  ; Change the 0 to a 1 to see the difference\
  ; between filter on and off.\
  TFormFilter 0\
  \
  ; Create new empty graphic to store our circle in\
  gfxBox=CreateImage(50,50)\
  \
  ; Draw the box image\
  ; Set drawing operations to point to our new empty graphic\
  SetBuffer ImageBuffer(gfxBox)\
  Color 255,0,0\
  ; Note the extra space between the box and the edge of the graphic\
  Rect 10,10,30,30,1\
  SetBuffer FrontBuffer()\
  \
  While Not KeyHit(1)\
  ; Make a copy of the image so we are always using a fresh one each
  time\
  ; we rotate it.\
  gfxTemp=CopyImage(gfxBox)\
  ; Rotate it a random value and draw it at a random location\
  RotateImage gfxTemp,Rnd(360)\
  DrawImage gfxTemp,Rnd(640),Rnd(480)\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=RotateImage&ref=comments){target="_blank"}
to view the latest version of this page online
