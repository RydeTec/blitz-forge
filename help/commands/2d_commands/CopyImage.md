# CopyImage (handle)

# Parameters

  ----------------------------------------------------------------------
  handle=the variable you gave the handle to when you loaded the image
  ----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Instead of loading a graphic twice in two different handles, you can
  load the image ONCE then use the CopyImage command to make as many
  copies in memory as you want.\
  \
  Why would you want to do this? So you still have a copy of the original
  image in case you want to alter a copy later for another purpose.

  -----------------------------------------------------------------------

# [Example](../2d_examples/CopyImage.bb)

  -----------------------------------------------------------------------
  ; CopyImage Example\
  \
  ; Load an image and give its handle to gfxOld variable\
  gfxOld=LoadImage(\"mypicture.bmp\")\
  \
  ; Duplicate the gfxOld image to a new handle variable\
  gfxNew=CopyImage(gfxOld)\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CopyImage&ref=comments){target="_blank"}
to view the latest version of this page online
