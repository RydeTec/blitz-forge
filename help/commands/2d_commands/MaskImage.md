# MaskImage handle,red,green,blue

# Parameters

  -----------------------------------------------------------------------
  handle=the variable you assigned the handle to when you loaded the
  image.\
  red=the red color value (0-255)\
  green=the green color value (0-255)\
  blue=the blue color value (0-255)

  -----------------------------------------------------------------------

# Description

  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Blitz Basic assumes that when you load an image (using LoadImage or LoadAnimImage) for drawing (using DrawImage command), you want the color black (RGB color 0,0,0) on your image to be transparent (or see through). There WILL come a time when you want some other color to be that masked color. This command will let you set that mask color using the color\'s RGB values (I use Paint Shop Pro to determing the Red, Green, and Blue values). The example is a bit bloated for other commands, but I\'m pretty sure you\'ll understand.
  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/MaskImage.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=MaskImage&ref=comments){target="_blank"}
to view the latest version of this page online
