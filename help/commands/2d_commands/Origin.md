# Origin x,y

# Parameters

  -----------------------------------------------------------------------
  x = x offset value\
  y = y offset value

  -----------------------------------------------------------------------

# Description

  ------------------------------------------------------------------------------------------------------------
  This command sets a point of origin for all subsequent drawing commands. This can be positive or negative.
  ------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/Origin.bb)

  -----------------------------------------------------------------------
  ; Origin example\
  Graphics 800,600,16\
  \
  ; Offset drawing options with origin command -200 in each direction\
  Origin -200,-200\
  \
  ; Wait for ESC to hit\
  While Not KeyHit(1)\
  \
  ; Draw an oval - SHOULD be at the exact center, but it isn\'t!\
  Oval 400,300,50,50,1\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Origin&ref=comments){target="_blank"}
to view the latest version of this page online
