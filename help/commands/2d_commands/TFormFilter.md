# TFormFilter enable

# Parameters

  ---------------------------------------------------
  enable = 0 to turn off filtering; 1 to turn it on
  ---------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command will enable or disable bi-linear filtering on images that
  are convoluted (altered) by commands like TFormImage and RotateImage.\
  \
  This filtering allows the convoluted graphics to have smoother, more
  aliased edges. This also makes the operations slower. The bi-linear
  filtering can also create non-transparent edges what will mess with
  your transparency. Experiment for the best results.\
  \
  Try changing the example to see the difference.

  -----------------------------------------------------------------------

# [Example](../2d_examples/TFormFilter.bb)

  -----------------------------------------------------------------------
  ; RotateImage/TFormFilter Example\
  \
  ; Turn on graphics mode\
  Graphics 640,480,16\
  \
  ; Remove the line below to see the difference\
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
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TFormFilter&ref=comments){target="_blank"}
to view the latest version of this page online
