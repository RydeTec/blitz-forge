# ATan2# ( y#, x# )

# Parameters

  -----------------------------------------------------------------------
  y, x are any numbers.\
  \
  They are interpreted as corresponding to a point ( x, y ).

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  ATan2 gives the angle between the positive x-axis and a vector from the
  point (0,0) to the point (x,y).\
  \
  One common use is in 2d graphics. Suppose you have two objects and you
  want to aim the first at the second.\
  \
  ATan2( y2 - y1, x2 - x1 ) gives the proper orientation for object1.\
  You can use this angle to select an appropriately rotated image.\
  \
  Notice the reverse order, ATan2( y, x ) rather than ATan2( x, y).\
  ATan2( y, x ) is analogous to ATan( y / x), but covers 360 degrees.\
  \
  The angle satisfies: -180 \< ATan2 \<= +180

  -----------------------------------------------------------------------

# [Example](../2d_examples/ATan2.bb)

  -----------------------------------------------------------------------
  ; ATan2 example.\
  \
  ; Move mouse. Escape quits.\
  \
  Const width = 640, height = 480\
  Const radius# = .2 \* height\
  Const KEY_ESC = 1\
  \
  Graphics width, height\
  SetBuffer BackBuffer( )\
  Origin width / 2, height / 2\
  HidePointer\
  MoveMouse .75 \* width, height / 2\
  \
  While Not KeyDown( KEY_ESC )\
  \
  Cls\
  \
  Color 255, 255, 0\
  Line 0, 0, width / 2, 0 ; positive x-axis\
  \
  x = MouseX() - width / 2\
  y = MouseY() - height / 2\
  \
  Oval x - 3, y - 3, 7, 7, True\
  Line 0, 0, x, y\
  \
  Text .35 \* width, -80, \"x = \" + x\
  Text .35 \* width, -60, \"y = \" + y\
  \
  Text .35 \* width - 96, -40, \"ATan2( y, x ) = \" + ATan2( y, x )\
  \
  Flip\
  \
  Wend\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ATan2&ref=comments){target="_blank"}
to view the latest version of this page online
