# ImageHeight (image handle)

# Parameters

  ------------------------------------------------------------
  image handle = variable assigned when the image was loaded
  ------------------------------------------------------------

# Description

  -------------------------------------------------------------------------------------------------------------------------------------------------------
  Use this command and ImageWidth to return the size of the given image (using the handle assigned when the image was loaded with LoadImage) in pixels.
  -------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/ImageHeight.bb)

  -----------------------------------------------------------------------
  ; ImageHeight/ImageWidth Example\
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
  ; Print the image dimensions\
  Print \"The image height is: \" + ImageHeight(gfxPlayer)\
  Print \"The image width is: \" + ImageWidth(gfxPlayer)\
  \
  ; Wait until ESC is pressed so you can see the output\
  While Not KeyHit(1)\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ImageHeight&ref=comments){target="_blank"}
to view the latest version of this page online
