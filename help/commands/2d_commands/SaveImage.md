# SaveImage (image,bmpfile\$\[,frame\] )

# Parameters

  -----------------------------------------------------------------------
  image = variable handle to the image to save\
  bmpfile\$ = string with full path and filename to save to\
  frame = optional; which frame of the image to save

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------------------------------------------------------------------------------------
  Saves an image or one of its frames to hard drive. You will need an image in memory to save. This returns a 1 if the save was successful, 0 if not.
  -----------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/SaveImage.bb)

  -----------------------------------------------------------------------
  ; SaveImage example\
  \
  ; Set Graphics Mode\
  Graphics 800,600,16\
  \
  ; Load an image to tile (your location might vary)\
  gfxBall=LoadImage(\"C:Program Files\\Blitz Basic\\samples\\ball.bmp\")\
  \
  ; Save the image to the c: drive \...\
  ok=SaveImage (gfxBall,\"c:\
  ewball.bmp\")\
  \
  ; Print results\
  If ok=1 Then\
  Print \"Save successful!\"\
  Else\
  Print \"There was an error saving!\"\
  End If\
  \
  ; Wait for ESC to hit\
  While Not KeyHit(1)\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SaveImage&ref=comments){target="_blank"}
to view the latest version of this page online
