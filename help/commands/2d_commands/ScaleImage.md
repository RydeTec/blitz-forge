# ScaleImage image,xscale#,yscale#

# Parameters

  -----------------------------------------------------------------------
  image = file handle variable to a previously loaded image\
  xscale# = the amount to scale the image horizontally\
  yscale# = the amount to scale the image vertically

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Use this command to rescale an image to a new size using a floating
  point percentage (1.0 = 100%, 2.0 = 200%, etc). Using a negative value
  perform image flipping. You must\'ve previously loaded the image with
  LoadImage or LoadAnimImage.\
  \
  This is NOT intended for REAL TIME scaling of images! Precalculate your
  images before running your program, or you will likely see massively
  slow renderings of graphics.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ScaleImage.bb)

  -----------------------------------------------------------------------
  ; ScaleImage example\
  \
  ; Set Graphics Mode\
  Graphics 800,600,16\
  \
  ; Randomize the random seed\
  SeedRnd MilliSecs()\
  \
  ; Load an image to tile (your location might vary)\
  gfxBall=LoadImage(\"C:Program FilesBlitz Basicsamplesall.bmp\")\
  \
  ; Scale it randomly from 50% to 150% both x and y\
  ScaleImage gfxBall,Rnd(-2.0,2.0),Rnd(-2.0,2.0)\
  \
  ; Wait for ESC to hit\
  While Not KeyHit(1)\
  DrawImage gfxball,Rnd(800),Rnd(600)\
  VWait\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ScaleImage&ref=comments){target="_blank"}
to view the latest version of this page online
