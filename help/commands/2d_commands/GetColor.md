# GetColor x, y

# Parameters

  -----------------------------------------------------------------------
  x = x coordinate of pixel\
  y = y coordinate of pixel

  -----------------------------------------------------------------------

# Description

  ------------------------------------------------------------------------------------------
  This command works like a \'color picker\' in your favorite paint program. By specifying
  ------------------------------------------------------------------------------------------

# [Example](../2d_examples/GetColor.bb)

  -----------------------------------------------------------------------
  ; GetColor Example\
  \
  Graphics 320,200\
  SetBuffer BackBuffer()\
  \
  For t = 1 To 1000\
  Color Rnd(255), Rnd(255), Rnd(255)\
  Rect Rnd(320),Rnd(200),10,10,1\
  Next\
  \
  GetColor 100,100\
  Print \"Box at 100,100 is RGB:\" + ColorRed() + \",\" + ColorGreen() +
  \",\" + ColorBlue() + \"!\"\
  Flip\
  While Not KeyHit(1)\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GetColor&ref=comments){target="_blank"}
to view the latest version of this page online
