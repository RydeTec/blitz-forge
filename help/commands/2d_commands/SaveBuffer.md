# SaveBuffer (buffer,filename\$)

# Parameters

  -----------------------------------------------------------------------
  buffer = The buffer to save; FrontBuffer() or BackBuffer()\
  filename\$ = valid Windows path/filename

  -----------------------------------------------------------------------

# Description

  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Typically, this is used to take a screen snapshot. This will save the screen buffer you specify to a .bmp file you specify. Remember, use the proper name for the buffer you wish to save; FrontBuffer() for the current visible screen, and BackBuffer() for the back or invisible drawing buffer. The filename must be valid Windows filename syntax.
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/SaveBuffer.bb)

  -----------------------------------------------------------------------
  ; Save the screen when player pushes F10\
  \
  If KeyHit(10) Then\
  SaveBuffer(FrontBuffer(),\"screenshot.bmp\")\
  End If\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SaveBuffer&ref=comments){target="_blank"}
to view the latest version of this page online
