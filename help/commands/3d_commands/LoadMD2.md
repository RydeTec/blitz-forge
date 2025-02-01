# LoadMD2 ( md2_file\$\[,parent\] )

# Parameters

  -----------------------------------------------------------------------
  md2_file\$ - filename of md2\
  parent (optional) - parent entity of md2

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Loads an md2 entity and returns its handle.\
  \
  An md2\'s texture has to be loaded and applied to the md2 separately,
  otherwise the md2 will appear untextured.\
  \
  Md2\'s have their own set of animation commands, and will not work with
  normal animation commands.\
  \
  The optional parent parameter allows you to specify a parent entity for
  the md2 so that when the parent is moved the child md2 will move with
  it. However, this relationship is one way; applying movement commands
  to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the md2 being created
  at position 0,0,0 rather than at the parent entity\'s position.

  -----------------------------------------------------------------------

# [Example](../3d_examples/LoadMD2.bb)

  -----------------------------------------------------------------------
  ; LoadMD2 Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\--\
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
  PositionEntity gargoyle,0,-45,100\
  RotateEntity gargoyle,0,180,0\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadMD2&ref=comments){target="_blank"}
to view the latest version of this page online
