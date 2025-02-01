# Oval x,y,width,height\[,solid\]

# Parameters

  -----------------------------------------------------------------------
  x = x coordinate on the screen to draw the oval\
  y = y coordinate on the screen to draw the oval\
  width = how wide to make the oval\
  height = how high to make the oval\
  \[solid\] = 1 to make the oval solid

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------------------------------------------------
  Use this to draw an oval shape at the screen coordinates of your choice. You can make the oval solid or hollow.
  -----------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/Oval.bb)

  -----------------------------------------------------------------------
  ; Oval example\
  Graphics 800,600,16\
  \
  ; Wait for ESC to hit\
  While Not KeyHit(1)\
  ; Set a random color\
  Color Rnd(255),Rnd(255),Rnd(255)\
  ; Draw a random oval\
  Oval Rnd(800),Rnd(600),Rnd(100),Rnd(100),Rnd(0,1)\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Oval&ref=comments){target="_blank"}
to view the latest version of this page online
