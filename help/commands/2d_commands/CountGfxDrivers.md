# CountGfxDrivers()

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Some computers may have more than one video card and/or video driver installed (a good example is a computer system with a primary video card and a Voodoo2 or other pass-through card). Once you know how many drivers there are, you can iterate through them with GfxDriverName\$ and display them for the user to choose from. Once the user has chosen (or you decide), you can set the graphics driver with SetGfxDriver. Normally, this won\'t be necessary with 2D programming.
  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/CountGfxDrivers.bb)

  -----------------------------------------------------------------------
  ; GfxDriver Examples\
  \
  ; Count how many drivers there are\
  totalDrivers=CountGfxDrivers()\
  Print \"Choose a driver to use:\"\
  \
  ; Go through them all and print their names (most people will have only
  1)\
  For t = 1 To totalDrivers\
  Print t+\") \" + GfxDriverName\$(t)\
  Next\
  \
  ; Let the user choose one\
  driver=Input(\"Enter Selection:\")\
  \
  ; Set the driver!\
  SetGfxDriver driver\
  Print \"Your driver has been selected!\"\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CountGfxDrivers&ref=comments){target="_blank"}
to view the latest version of this page online
