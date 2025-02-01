# CopyPixelFast src_x,src_y,src_buffer,dest_x,dest_y,\[dest_buffer\]

# Parameters

  -----------------------------------------------------------------------
  src_x - x-coordinate of source pixel to copy from\
  src_y - y-coordinate of source pixel to copy from\
  src_buffer - name of buffer to copy from, e.g. ImageBuffer()\
  dest_x - x-coordinate of destination pixel to copy to\
  dest_y - y-coordinate of destination pixel to copy to\
  dest_buffer (optional) - name of buffer to copy to, e.g. BackBuffer()

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  IMPORTANT:\
  \
  You \*must\* use this command on a locked buffer, otherwise the command
  will fail. See LockBuffer.\
  \
  Also, you must make sure that the coordinates that you are copying from
  and to are valid, otherwise you will end up overwriting other areas of
  memory.\
  \
  WARNING:\
  \
  By not following the above advice, you may cause your computer to
  crash.\
  \
  See also: CopyPixel.

  -----------------------------------------------------------------------

# [Example](../2d_examples/CopyPixelFast.bb)

  -----------------------------------------------------------------------
  ; CopyPixel/CopyPixelFast Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics 640,480,16\
  \
  Print \"Press a key to use CopyPixel to copy the top half of an image
  to the frontbuffer\"\
  WaitKey()\
  \
  ; Load an image - can be anything\
  pic=LoadImage(\"media/blitz_pic.bmp\")\
  \
  ; Use CopyPixel to copy the top half of the image to the frontbuffer\
  For y=0 To ImageHeight(pic)/2\
  For x=0 To ImageWidth(pic)\
  CopyPixel x,y,ImageBuffer(pic),x,y\
  Next\
  Next\
  \
  Locate 0,GraphicsHeight()/2\
  Print \"Press a key to use CopyPixelFast to copy the bottom half of the
  image\"\
  Print \"Once this has finished, you can then press a key to end the
  program\"\
  \
  WaitKey()\
  \
  ; Lock buffer before using CopyPixelFast\
  LockBuffer\
  \
  ; Use CopyPixelFast to copy the bottom half of the image to the
  frontbuffer\
  For y=0 To (ImageHeight(pic)/2)+ImageHeight(pic)\
  For x=0 To ImageWidth(pic)\
  CopyPixelFast x,y,ImageBuffer(pic),x,y\
  Next\
  Next\
  \
  ; Unlock buffer after using CopyPixelFast\
  UnlockBuffer\
  \
  WaitKey()

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CopyPixelFast&ref=comments){target="_blank"}
to view the latest version of this page online
