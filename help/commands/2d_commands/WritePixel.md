# WritePixel x,y,argb,\[buffer\]

# Parameters

  -----------------------------------------------------------------------
  x - y-coordinate of pixel\
  y - y-coordinate of pixel\
  argb - argb colour value of pixel (alpha, red, green, blue)\
  buffer (optional) - name of buffer to write colour value to, e.g.
  BackBuffer()

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Writes a color value to either the current buffer or the specified
  buffer.\
  \
  You can use this command on a locked buffer for a slight speed-up.\
  \
  See also: [Plot](Plot.htm){.small},
  [WritePixelFast](WritePixelFast.htm){.small},
  [LockBuffer](LockBuffer.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/WritePixel.bb)

  -----------------------------------------------------------------------
  ; ReadPixel/WritePixel Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics 640,480,16\
  \
  Print \"Press a key to read color values (this may take a few
  seconds)\"\
  WaitKey()\
  \
  ; Load and draw an image on to the screen - can be anything\
  pic=LoadImage(\"media/blitz_pic.bmp\")\
  DrawImage pic,0,0\
  \
  ; Initialise an array big enough to fit all the color information of
  the screen\
  Dim pix(GraphicsWidth(),GraphicsHeight())\
  \
  ; Use ReadPixel to get all the color information of the screen\
  For y=0 To GraphicsHeight()\
  For x=0 To GraphicsWidth()\
  pix(x,y)=ReadPixel(x,y)\
  Next\
  Next\
  \
  Cls\
  Locate 0,0\
  Print \"Press a key to write pixels (this may takes a few seconds)\"\
  Print \"Once this has finished, you can then press a key to end the
  program\"\
  \
  WaitKey()\
  \
  ; Use WritePixel to redraw the screen using the color information we
  got earlier\
  For y=0 To GraphicsHeight()\
  For x=0 To GraphicsWidth()\
  WritePixel x,y,pix(x,GraphicsHeight()-y) ; get y array value in
  backwards order, to flip screen\
  Next\
  Next\
  \
  WaitKey()

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=WritePixel&ref=comments){target="_blank"}
to view the latest version of this page online
