# LoadBuffer (buffer, filename\$)

# Parameters

  -----------------------------------------------------------------------
  buffer = system or image buffer\
  filename\$ = string containing full path and filename of image

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  There are a hundred and one uses for this command, but probably most
  often used would be to display a title screen or some other \'one time
  viewing only\' image to the front buffer (as in our example).\
  \
  You can also load to an image buffer or back buffer. The image is
  scaled to match the buffer size. This command returns 1 if the command
  was successful, 0 if there was an error.

  -----------------------------------------------------------------------

# [Example](../2d_examples/LoadBuffer.bb)

  -----------------------------------------------------------------------
  ; LoadBuffer example\
  \
  ; Set graphics mode\
  Graphics 800,600,16\
  \
  ; Load an image directly to the front buffer (your location may be
  different)\
  LoadBuffer (FrontBuffer(),\"C:Program FilesBlitz
  Basicsampleslitzanoidgfx itle.bmp\")\
  \
  ; wait for ESC so user gets to see the screen\
  While Not KeyHit(1)\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadBuffer&ref=comments){target="_blank"}
to view the latest version of this page online
