# LockBuffer buffer

# Parameters

  ---------------------------------------------------
  buffer = any valid screen/image buffer (optional)
  ---------------------------------------------------

# Description

  -----------------------------------------------------------------------
  After you use LockBuffer on a buffer, the only graphics commands you
  can use are the read/write pixel commands ReadPixel, WritePixel,
  ReadPixelFast, WritePixelFast, CopyPixelFast, and CopyPixel. You must
  UnlockBuffer before using other graphics commands or API calls, and you
  are advised to only keep the buffer locked for as long as it is
  needed.\
  \
  The buffer parameter isn\'t required. If omitted, the default buffer
  set with SetBuffer will be used.\
  \
  See the other commands for more information.\
  \
  See also: [LockedPitch](LockedPitch.htm){.small},
  [LockedFormat](LockedFormat.htm){.small},
  [LockedPixels](LockedPixels.htm){.small},
  [ReadPixelFast](ReadPixelFast.htm){.small},
  [WritePixelFast](WritePixelFast.htm){.small},
  [UnlockBuffer](UnlockBuffer.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/LockBuffer.bb)

  -----------------------------------------------------------------------
  ; High Speed Graphics Commands\
  \
  Graphics 640,480,16\
  \
  ; Draw a bunch of stuff on the screen\
  For t= 1 To 1000\
  Color Rnd(255),Rnd(255),Rnd(255)\
  Rect Rnd(640),Rnd(480),Rnd(150),Rnd(150),Rnd(1)\
  Next\
  \
  Delay 3000\
  \
  ; Copy the top half of the screen over the bottom half\
  ; using fast pixels and locked buffers\
  For x = 1 To 640\
  For y = 1 To 240\
  LockBuffer FrontBuffer()\
  WritePixelFast x,y+241,ReadPixelFast(x,y)\
  UnlockBuffer FrontBuffer()\
  Next\
  Next\
  \
  Delay 3000\
  \
  ; Draw the left half of the screen over the right half\
  ; using the slower direct pixel access\
  For x = 1 To 320\
  For y = 1 To 480\
  WritePixel x+320,y,ReadPixel(x,y)\
  Next\
  Next

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LockBuffer&ref=comments){target="_blank"}
to view the latest version of this page online
