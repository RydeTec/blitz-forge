# GfxDriver3D(driver)

# Parameters

  -----------------------------------------------------------------------
  driver - display driver number to check, from 1 to CountGfxDrivers ()
  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  GfxDriver3D returns True if the specified graphics driver is
  3D-capable.\
  \
  GfxDriver3D is generally used after obtaining a list of available
  graphics drivers from a user\'s system via CountGfxDrivers(). You apply
  this to each driver in turn (or a selected driver) to see if it is
  3D-capable. If this returns False, the driver can not perform 3D
  operations.\
  \
  On systems with more than one display driver, you can use this to check
  each for 3D capability before choosing one via the SetGfxDriver
  command.

  -----------------------------------------------------------------------

# [Example](../3d_examples/GfxDriver3D.bb)

  -----------------------------------------------------------------------
  For a = 1 To CountGfxDrivers ()\
  If GfxDriver3D (a)\
  Print GfxDriverName (a) + \" is 3D-capable\"\
  Else\
  Print GfxDriverName (a) + \" is NOT 3D-capable\"\
  EndIf\
  Delay 100\
  Next

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GfxDriver3D&ref=comments){target="_blank"}
to view the latest version of this page online
