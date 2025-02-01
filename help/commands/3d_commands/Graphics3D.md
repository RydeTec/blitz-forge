# Graphics3D

# Parameters

  -----------------------------------------------------------------------
  width - width of screen resolution\
  height - height of screen resolution\
  depth (optional) - colour depth of screen. Defaults to highest colour
  depth available.\
  \
  mode (optional) - mode of display. Defaults to 0.\
  0: windowed (if possible) in debug mode, fullscreen in non-debug mode\
  1: fullscreen always\
  2. windowed always\
  3: windowed/scaled always

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets 3D Graphics mode. This command must be executed before any other
  3D command, otherwise programs will return an error.\
  \
  Width and height set the resolution of the screen and common values are
  640,480 and 800,600. The resolution must be compatible with the 3D card
  and monitor being used.\
  \
  Depth sets the colour mode of the screen. If this value is omitted or
  set to 0, then the highest available colour depth available will be
  used. Other values usually available are 16, 24 and 32. 16-bit colour
  mode displays the least amount of colours, 65536. 24-bit and 32-bit
  colour modes display over 16 million colours and as a result offer a
  better quality picture, although may result in slower programs than
  16-bit.\
  \
  See also: [Graphics](Graphics.htm){.small},
  [EndGraphics](EndGraphics.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/Graphics3D.bb)

  -----------------------------------------------------------------------
  ; Graphics3D Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  ; Set 3D graphics mode\
  Graphics3D 640,480,16,0\
  \
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  light=CreateLight()\
  \
  cone=CreateCone( 32 )\
  PositionEntity cone,0,0,5\
  \
  While Not KeyDown( 1 )\
  RenderWorld\
  Flip\
  Wend\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Graphics3D&ref=comments){target="_blank"}
to view the latest version of this page online
