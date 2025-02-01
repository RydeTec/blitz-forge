# GfxMode3D(mode)

# Parameters

  -------------------------------------------------------
  mode - graphics mode number from 1 - CountGfxModes ()
  -------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  GfxMode3D returns True if the specified graphics mode is 3D-capable.\
  \
  GfxMode3D is generally used after obtaining a list of available display
  modes from a user\'s system via CountGfxModes (). You apply this to
  each mode in turn (or a selected mode) to see if you can enter 3D mode.
  If this returns False, calling Graphics3D () with this mode\'s details
  will fail!\
  \
  If you don\'t wish to perform this check, the only Graphics3D call you
  can make with a guarantee of working on 99% of 3D graphics cards is
  \'Graphics3D 640, 480\'. However, see GfxModeExists\' gfx3d parameter
  for another method of performing this check.

  -----------------------------------------------------------------------

# [Example](../3d_examples/GfxMode3D.bb)

  -----------------------------------------------------------------------
  For a = 1 To CountGfxModes ()\
  If GfxMode3D (a)\
  Print \"Mode \" + a + \" is 3D-capable\"\
  Else\
  Print \"Mode \" + a + \" is NOT 3D-capable\"\
  EndIf\
  Delay 100\
  Next

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GfxMode3D&ref=comments){target="_blank"}
to view the latest version of this page online
