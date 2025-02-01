# Dither enable

# Parameters

  -----------------------------------------------------------------------
  enable - True to enable dithering, False to disable.\
  \
  The default Dither mode is True.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Enables or disables hardware dithering.\
  \
  Hardware dithering is useful when running games in 16-bit colour mode.
  Due to the fact that 16-bit mode offers less colours than the human eye
  can detect, separate bands of colour are often noticeable on shaded
  objects. However, hardware dithering will dither the entire screen to
  give the impression that more colours are being used than is actually
  the case.\
  \
  Due to the fact that 24-bit and 32-bit offer more colours than the
  human eye can detect, hardware dithering is made pretty much redundant
  in those modes.

  -----------------------------------------------------------------------

# [Example](../3d_examples/Dither.bb)

  -----------------------------------------------------------------------
  ; Dither Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480,16\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  light=CreateLight()\
  \
  ; Rotate light so that it creates maximum shading effect on sphere\
  RotateEntity light,90,0,0\
  \
  sphere=CreateSphere( 32 )\
  PositionEntity sphere,0,0,2\
  \
  While Not KeyDown( 1 )\
  \
  ; Toggle dither enable value between true and false when spacebar is
  pressed\
  If KeyHit( 57 )=True Then enable=1-enable\
  \
  ; Enable/disable hardware dithering\
  Dither enable\
  \
  RenderWorld\
  \
  Text 0,0,\"Press spacebar to toggle between Dither True/False\"\
  If enable=True Then Text 0,20,\"Dither True\" Else Text 0,20,\"Dither
  False\"\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Dither&ref=comments){target="_blank"}
to view the latest version of this page online
