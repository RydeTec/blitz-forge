# AntiAlias

# Parameters

  -----------------------------------------------------------------------
  enable - True to enable fullscreen antialiasing, False to disable.\
  \
  The default AntiAlias mode is False.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Enables or disables fullscreen antialiasing.\
  \
  Fullscreen antialiasing is a technique used to smooth out the entire
  screen, so that jagged lines are made less noticeable.\
  \
  Some 3D cards have built-in support for fullscreen antialiasing, which
  should allow you to enable the effect without much slowdown. However,
  for cards without built-in support for fullscreen antialiasing,
  enabling the effect may cause severe slowdown.

  -----------------------------------------------------------------------

# [Example](../3d_examples/AntiAlias.bb)

  -----------------------------------------------------------------------
  ; AntiAlias Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  sphere=CreateSphere()\
  PositionEntity sphere,0,0,2\
  \
  While Not KeyDown( 1 )\
  \
  ; Toggle antialias enable value between true and false when spacebar is
  pressed\
  If KeyHit( 57 )=True Then enable=1-enable\
  \
  ; Enable/disable antialiasing\
  AntiAlias enable\
  \
  RenderWorld\
  \
  Text 0,0,\"Press spacebar to toggle between AntiAlias True/False\"\
  If enable=True Then Text 0,20,\"AntiAlias True\" Else Text
  0,20,\"AntiAlias False\"\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=AntiAlias&ref=comments){target="_blank"}
to view the latest version of this page online
