# AnimateMD2 md2\[,mode\]\[,speed#\]\[,first_frame\]\[,last_frame\]\[,transition#\]

# Parameters

  -----------------------------------------------------------------------
  md2 - md2 handle\
  \
  mode (optional) - mode of animation\
  0: stop animation\
  1: loop animation (default)\
  2: ping-pong animation\
  3: one-shot animation\
  \
  speed# (optional) - speed of animation. Defaults to 1.\
  first_frame (optional) - first frame of animation. Defaults to 1.\
  last_frame# (optional) - last frame of animation. Defaults to last
  frame of all md2 animations.\
  transition# (optional) - smoothness of transition between last frame
  shown of previous animation and first frame of next animation. Defaults
  to 0.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Animates an md2 entity.\
  \
  The md2 will actually move from one frame to the next when UpdateWorld
  is called.

  -----------------------------------------------------------------------

# [Example](../3d_examples/AnimateMD2.bb)

  -----------------------------------------------------------------------
  ; AnimateMD2 Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
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
  gargoyle=LoadMD2( \"media/gargoyle/gargoyle.md2\" )\
  \
  ; Load md2 texture\
  garg_tex=LoadTexture( \"media/gargoyle/gargoyle.bmp\" )\
  \
  ; Apply md2 texture to md2\
  EntityTexture gargoyle,garg_tex\
  \
  ; Animate md2\
  AnimateMD2 gargoyle,1,0.1,32,46\
  \
  PositionEntity gargoyle,0,-45,100\
  RotateEntity gargoyle,0,180,0\
  \
  While Not KeyDown( 1 )\
  UpdateWorld\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=AnimateMD2&ref=comments){target="_blank"}
to view the latest version of this page online
