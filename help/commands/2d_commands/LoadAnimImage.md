# LoadAnimImage (filename,width,height,first,count)

# Parameters

  -----------------------------------------------------------------------
  filename = string designating full path and filename to image.\
  width=width in pixels of each frame in the image.\
  height=height in pixels of each frame in the image.\
  first=the frame to start with (usually 0)\
  count=how many frames you are using of the imagestrip

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  While similar to LoadImage, the LoadAnimImage loads a single image that
  is made up of \'frames\' of seperate images (presumably to be used as
  frames of a graphic animation).\
  \
  Like the LoadImage command, this command returns a file handle - a
  unique number to denote the graphic. Use a variable (usually GLOBAL) to
  contain this number, as you will need it to actually DRAW the image
  with the DrawImage command. See LoadImage command for more details.\
  \
  The imagestrip itself consists of 2 or more frames, arranged in a
  single graphic image. There is no spaces between the frames, and each
  frame must be the same width and height. When loaded, the frames will
  be indexed in a left-to-right, top-to-bottom fashion, starting in the
  top left corner. For examples, look at the file kaboom.bmp or
  sparks.bmp included in the C:Program FilesBlitzBasicsamplesgraphics
  folder of your computer. There are some free utilities floating around
  to help you do this.\
  \
  When drawing the image to the screen with the DrawImage command, you
  specify which frame to draw with the frame parameter.\
  \
  To actually make your image animate, you\'ll need to cycle through the
  frames (like a flip book, cartoon, or any other video) quickly to give
  the illusion of motion. Our example will show you how to use one of the
  sample imagestrips and make it animate. While it may seem confusing, we
  are going to do some timer work as well as a little weird math.\
  \
  Please look over the example (if your like me, over and over :). Note:
  You may need to change the location of the file to suit your system.\
  \
  See also: [LoadImage](LoadImage.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/LoadAnimImage.bb)

  -----------------------------------------------------------------------
  ; LoadAnimImage/MaskImage Example\
  ; With animation timers\
  \
  ; Even though we don\'t have any functions, let\'s do variables global\
  ; One variable will hold the handle for the graphic, one will hold the\
  ; current frame we are displaying, and one will hold the milliseconds\
  ; timer so we can adjust the animation speed.\
  Global gfxSparks, frmSparks, tmrSparks\
  \
  ; Standard graphic declaration and double buffering setup\
  Graphics 640,480,16\
  SetBuffer BackBuffer()\
  \
  ; Load the imagestrip up and denote the frames 32x32 - for a total of 3
  frames\
  gfxSparks=LoadAnimImage(\"c:Program
  FilesBlitzBasicsamplesGraphicsspark.bmp\",32,32,0,3)\
  \
  ; We mask the image\'s color pink to be the \'transparent\' color -
  look at the\
  ; image in your favorite editor to see more why we use masking.\
  MaskImage gfxSparks,255,0,255\
  \
  ; Loop until ESC\
  While Not KeyHit(1)\
  Cls ; Standard clear screen\
  \
  ; The next statment checks to see if 100 milliseconds has passes since
  we\
  ; last changed frames. Change the 100 to higher and lower values to\
  ; make the animation faster or slower.\
  If MilliSecs() \> tmrSparks + 100 Then\
  tmrSparks=MilliSecs() ; \'reset\' the timer\
  frmSparks=( frmSparks + 1 ) Mod 3 ; increment the frame, flip to 0 if
  we are out\
  End If\
  DrawImage gfxSparks,MouseX(),MouseY(),frmSparks ; draw the image\
  Flip ; show the buffer\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadAnimImage&ref=comments){target="_blank"}
to view the latest version of this page online
