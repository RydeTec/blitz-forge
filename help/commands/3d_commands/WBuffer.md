# WBuffer enable

# Parameters

  -----------------------------------------------------------------------
  enable - True to enable w-buffering rendering, False to disable.\
  \
  The default WBuffer mode is True for 16-bit colour mode, False for
  24-bit and 32-bit.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Enables or disables w-buffering.\
  \
  W-buffering is a technique used to draw 3D object in order of their
  depth - i.e. the ones furthest away from the camera first, then the
  ones closer to the camera, and so on.\
  \
  Normally, z-buffering is used to perform such a technique, but a
  z-buffer can be slightly inaccurate in 16-bit colour mode, for which
  the level of precision is less than in 24-bit or 32-bit colour modes.
  This means that in some situations, objects will appear to overlap each
  other when they shouldn\'t and so on.\
  \
  To compensate for this, you can use w-buffering. This is a slightly
  more accurate technique than z-buffering, although it may be less
  compatible on some set-ups than z-buffering.

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=WBuffer&ref=comments){target="_blank"}
to view the latest version of this page online
