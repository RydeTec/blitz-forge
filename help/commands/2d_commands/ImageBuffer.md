# ImageBuffer (handle\[,frame\])

# Parameters

  -----------------------------------------------------------------------
  handle=variable holding the image\'s handle\
  frame=optional frame to draw to if using an imagestrip image

  -----------------------------------------------------------------------

# Description

  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  There are 1000 reasons for this command. Simply put, you may want to \'draw\' on an existing image you\'ve loaded (LoadImage or LoadAnimImage) or created (CreateImage). You could, for example, have a blank wall graphic and you want to add \'graffiti\' to it based on the user action (Jet Grind Radio baybeee! Sorry\...). Instead of trying to draw a dozen images all over the wall, just use the SetBuffer command to denote the wall graphic as the \'target\' buffer, and draw away! Next time you display that graphic (DrawImage), you will see your changes! This is a powerful command!
  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/ImageBuffer.bb)

  -----------------------------------------------------------------------
  ; CreateImage/TileImage/ImageBuffer example\
  \
  ; Again, we\'ll use globals even tho we don\'t need them here\
  ; One variable for the graphic we\'ll create, one for a timer\
  Global gfxStarfield, tmrScreen\
  \
  ; Declare graphic mode\
  Graphics 640,480,16\
  \
  ; Create a blank image that is 320 pixels wide and 32 high with 10
  frames of 32x32\
  gfxStarfield=CreateImage(32,32,10)\
  \
  ; loop through each frame of the graphic we just made\
  For t = 0 To 9\
  ; Set the drawing buffer to the graphic frame so we can write on it\
  SetBuffer ImageBuffer(gfxStarfield,t)\
  ; put 50 stars in the frame at random locations\
  For y = 1 To 50\
  Plot Rnd(32),Rnd(32)\
  Next\
  Next\
  \
  ; Double buffer mode for smooth screen drawing\
  SetBuffer BackBuffer()\
  \
  ; Loop until ESC is pressed\
  While Not KeyHit(1)\
  \
  ; Only update the screen every 300 milliseconds. Change 300 for faster
  or\
  ; slower screen updates\
  If MilliSecs() \> tmrScreen+300 Then\
  Cls ; clear the screen\
  \
  ; Tile the screen with a random frame from our new graphic starting at\
  ; x=0 and y=0 location.\
  TileImage gfxStarfield,0,0,Rnd(9)\
  Flip ; Flip the screen into view\
  tmrScreen=MilliSecs() ; reset the time\
  End If\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ImageBuffer&ref=comments){target="_blank"}
to view the latest version of this page online
