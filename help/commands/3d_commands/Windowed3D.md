# Windowed3D()

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  Windowed3D returns true if the host machine\'s selected graphics card
  is capable of rendering 3D graphics in a window using the current
  desktop colour depth.\
  \
  This mainly concerns older graphics cards, some of which may not be
  able to render 3D in a window at all, while others maybe be able to
  only render in a window if the user\'s desktop is set to a certain
  colour depth such as 16.

  -----------------------------------------------------------------------

# [Example](../3d_examples/Windowed3D.bb)

  -----------------------------------------------------------------------
  If Windowed3D ()\
  Graphics3D 640, 480, 0, 2\
  Print \"Windowed mode!\"\
  Else\
  Graphics3D 640, 480, 0, 1\
  Print \"Full screen modes only!\"\
  EndIf\
  \
  MouseWait\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Windowed3D&ref=comments){target="_blank"}
to view the latest version of this page online
