# Color red,green,blue

# Parameters

  -----------------------------------------------------------------------
  red = value of red component (0-255)\
  green = value of green component (0-255)\
  blue = value of blue component (0-255)

  -----------------------------------------------------------------------

# Description

  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command sets the drawing color (using RGB values) for all subsequent drawing commands (Line, Rect, Text, etc.) You must be in Graphics mode to execute this command.
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/Color.bb)

  -----------------------------------------------------------------------
  ; Color, ColorRed(), ColorBlue(), ColorGreen() Example\
  \
  ; Gotta be in graphics mode\
  Graphics 640,480\
  \
  ; Change the random seed\
  SeedRnd MilliSecs()\
  \
  ; Let\'s set the color to something random\
  Color Rnd(0,255),Rnd(0,255),Rnd(0,255)\
  \
  ; Now let\'s see what they are!\
  While Not KeyHit(1)\
  Text 0,0,\"This Text is printed in Red=\" + ColorRed() + \" Green=\" +
  ColorGreen() + \" Blue=\" + ColorBlue() + \"!\"\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Color&ref=comments){target="_blank"}
to view the latest version of this page online
