# BackBuffer()

# Parameters

  -------
  None.
  -------

# Description

  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This is a value usually used with SETBUFFER to denote the secondary non-visible drawing buffer called the Back Buffer. In MOST gaming situations, you will want to be using the BackBuffer() for drawing operations then using Flip to bring that buffer to the FrontBuffer() where it can be seen. There are other uses for the command, but this is the biggie. See SETBUFFER for more info, and check out the example. Once again - if you set drawing operations to the BackBuffer() you will NOT see any of them until you call FLIP.
  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/BackBuffer.bb)

  -----------------------------------------------------------------------
  ; Flip/Backbuffer()/Rect Example\
  \
  ; Set Graphics Mode\
  Graphics 640,480\
  \
  ; Go double buffering\
  SetBuffer BackBuffer()\
  \
  ; Setup initial locations for the box\
  box_x = -20 ; negative so it will start OFF screen\
  box_y = 100\
  \
  While Not KeyHit(1)\
  Cls ; Always clear screen first\
  Rect box_x,box_y,20,20,1 ; Draw the box in the current x,y location\
  Flip ; Flip it into view\
  box_x = box_x + 1 ; Move the box over one pixel\
  If box_x = 640 Then box_x=-20 ; If it leaves the Right edge, reset its
  x location\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=BackBuffer&ref=comments){target="_blank"}
to view the latest version of this page online
