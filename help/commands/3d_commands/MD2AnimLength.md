# MD2AnimLength ( md2 )

# Parameters

  ------------------
  md2 - md2 handle
  ------------------

# Description

  -----------------------------------------------------------------------
  Returns the animation length of an md2 model.\
  \
  The animation length is the total amount of animation frames that
  consist within the md2 file.

  -----------------------------------------------------------------------

# [Example](../3d_examples/MD2AnimLength.bb)

  -----------------------------------------------------------------------
  ; MD2AnimLength Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  ; Load md2\
  gargoyle=LoadMD2( \"media/gargoyle/gargoyle.md2\")\
  \
  ; Load md2 texture\
  garg_tex=LoadTexture( \"media/gargoyle/gargoyle.bmp\" )\
  \
  ; Apply md2 texture to md2\
  EntityTexture gargoyle,garg_tex\
  \
  PositionEntity gargoyle,0,-45,100\
  RotateEntity gargoyle,0,180,0\
  \
  While Not KeyDown( 1 )\
  \
  RenderWorld\
  \
  ; Output animation length to screen\
  Text 0,0,\"MD2AnimLength: \"+MD2AnimLength( gargoyle )\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=MD2AnimLength&ref=comments){target="_blank"}
to view the latest version of this page online
