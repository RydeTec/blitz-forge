# FreeImage handle

# Parameters

  --------------------------------------------------------------------------
  handle=variable that holds the image handle of a previously loaded image
  --------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  When you are done with an image, use this command to delete the image
  from memory and free up that memory for other use. Good housekeeping.
  Get in the habit!\
  \
  Note that the variable to the Image is not reset to 0 upon using
  FreeImage, nor will any other variables pointing to the same Image. If
  you try to access the handle again, it will result in an error. It is
  good practice to set the handle to zero immediately after freeing the
  image, as you can then check whether an image has already been freed or
  not.\
  \
  See also: [LoadImage](LoadImage.htm){.small},
  [SaveImage](SaveImage.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/FreeImage.bb)

  -----------------------------------------------------------------------
  ; FreeImage command\
  \
  ; Global, as always, for graphics\
  Global gfxPlayer\
  \
  ; Enter graphics mode and start double buffering\
  Graphics 640,480,16\
  SetBuffer BackBuffer()\
  \
  ; Load the image-assign the handle to gfxPlayer\
  gfxPlayer=LoadImage(\"player.bmp\")\
  \
  ; draw the image at random until ESC pressed\
  While Not KeyHit(1)\
  Cls\
  DrawImage gfxPlayer,Rnd(640),Rnd(480)\
  Flip\
  Wend\
  \
  ; Erase the image from memory!\
  FreeImage gfxPlayer

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=FreeImage&ref=comments){target="_blank"}
to view the latest version of this page online
