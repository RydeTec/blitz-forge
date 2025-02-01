# Flip \[vwait\]

# Parameters

  ----------------------------------------------------------------------------------------------------------
  vwait = set to FALSE to disable frame syncing, defaults to TRUE which waits for vertical blank to finish
  ----------------------------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Flip will switch the FrontBuffer() and BackBuffer().\
  \
  \
  This command should be used when you are using Double Buffering. Double
  Buffering is a technique used to ensure that screen updates are not
  visible to the user. If you draw directly to the FrontBuffer, the
  display may appear flickery as the updates are drawn directly to the
  screen. If you draw to the BackBuffer, the updates are drawn in memory.
  Flip is then used to make the BackBuffer the FrontBuffer, and hence
  show the updates on screen in one go. At the same time, the FrontBuffer
  becomes the BackBuffer, allowing you to draw the next screen update on
  the BackBuffer before Flipping again.\
  \
  \
  The vwait parameter defines whether or not the graphics card will wait
  for the vertical blank before flipping the screen. The vertical blank
  is an event that occurs at the frequency of the monitors refresh rate,
  and corresponds to the point in time when the last line on the screen
  has been drawn, and the top line is about to be drawn by the monitor.
  By waiting until the vertical blank, you ensure smooth updates to the
  screen as the image being drawn by the monitor will change only once
  the entire screen has been drawn by the monitor, and the screen is just
  about to start being refreshed.\
  \
  \
  However, it is worth noting that this setting applies to the graphics
  card only, and some graphics cards allow the user to disable the
  vertical blank event. Therefore, if you rely on Flip alone, the display
  may be corrupted on certain setups. The VWait command may be useful to
  you in this respect, as it forces the CPU to wait for the vertical
  blank (as opposed to the graphics card), and this cannot be disabled.
  Hence true silky smooth updates are best achieved using \"VWait : Flip
  False\".\
  \
  See also: [FrontBuffer](FrontBuffer.htm){.small},
  [BackBuffer](BackBuffer.htm){.small}, [VWait](VWait.htm){.small},
  [ScanLine](ScanLine.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Flip.bb)

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
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Flip&ref=comments){target="_blank"}
to view the latest version of this page online
