# GfxDriverCaps3D()

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  Returns the \'caps level\' of the current graphics driver. Values are:\
  \
  100: card supports all \'standard\' Blitz3D operations.\
  110: card supports all standard ops plus cubic environment mapping.\
  \
  3D Graphics code must be set before you call this command in order for
  it to work.\
  \
  See also: [CreateTexture](CreateTexture.htm){.small},
  [Graphics3D](Graphics3D.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/GfxDriverCaps3D.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480,0,2\
  \
  caps=GfxDriverCaps3D()\
  \
  Select caps\
  \
  Case 100 msg\$=\"Your graphics card does not support cubic environment
  mapping.\"\
  \
  Case 110 msg\$=\"Your graphics card does support cubic environment
  mapping.\"\
  \
  End Select\
  \
  Print msg\$\
  \
  WaitKey()

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GfxDriverCaps3D&ref=comments){target="_blank"}
to view the latest version of this page online
