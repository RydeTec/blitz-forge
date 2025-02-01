# TileImage handle,\[x\],\[y\],\[frames\]

# Parameters

  -----------------------------------------------------------------------
  handle= variable holding the image\'s handle\
  x=starting x location of the tile; assumed 0\
  y=starting y location of the tile; assumed 0\
  frames=the frame of the image to tile; optional with imagestrip

  -----------------------------------------------------------------------

# Description

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  If you want to make a starfield or other easy tiled background, this is YOUR command. All you have to do is specify the image handle (an image loaded with the LoadImage or LoadAnimImage command). Optionally, you can specify a starting x and y location, as well as an optional frame. You can milk some serious parallax effects with a simple imagestrip with a couple of various starfields and the TileImage command.
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/TileImage.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TileImage&ref=comments){target="_blank"}
to view the latest version of this page online
