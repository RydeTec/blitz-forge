# MD2Animating ( md2 )

# Parameters

  ------------------
  md2 - md2 handle
  ------------------

# Description

  -------------------------------------------------------------------
  Returns 1 (True) if md2 is currently animating, 0 (False) if not.
  -------------------------------------------------------------------

# [Example](../3d_examples/MD2Animating.bb)

  -----------------------------------------------------------------------
  ; MD2Animating Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
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
  ; Toggle animation stop/start when spacebar pressed\
  If KeyHit( 57 )=True start=1-start : AnimateMD2
  gargoyle,start,0.1,32,46\
  \
  UpdateWorld\
  RenderWorld\
  \
  Text 0,0,\"Press spacebar to stop/start md2 animation\"\
  \
  ; Output current md2 animation status to screen\
  Text 0,20,\"MD2Animating: \"+MD2Animating( gargoyle )\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=MD2Animating&ref=comments){target="_blank"}
to view the latest version of this page online
