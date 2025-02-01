# ResizeImage image,width#,height#

# Parameters

  -----------------------------------------------------------------------
  image = file handle for previously loaded image\
  width# = new width in pixels\
  height# = new height in pixels

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Similar to ScaleImage, but uses pixel values instead of percentages.
  Use this command to resize an image previously loaded with LoadImage or
  LoadAnimImage.\
  \
  This is NOT intended for REAL TIME scaling of images! Precalculate your
  images before running your program, or you will likely see massively
  slow renderings of graphics.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ResizeImage.bb)

  -----------------------------------------------------------------------
  ; ResizeImage example\
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
  ; Size it randomly from 300 to -300 both x and y\
  ResizeImage gfxBall,Rnd(-300,300),Rnd(-300,300)\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ResizeImage&ref=comments){target="_blank"}
to view the latest version of this page online
