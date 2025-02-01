[  AutoMidHandle true/false  ]{.Command}

[Definition:]{.header}\
\

  ----------------------------------------------------------------------------------
  Automatically sets the image handle of loaded images to the middle of the image.
  ----------------------------------------------------------------------------------

[\
Parameter Description:]{.header}\
\

  -----------------------------------------------------------------------
  true = images load with midhandle set as default\
  false = images load with default image handles at 0,0\

  -----------------------------------------------------------------------

Command Description:\
\

  -----------------------------------------------------------------------
  When an image is loaded with [LoadImage](LoadImage.htm), the image
  handle (the location within the image where the image is \'drawn
  from\') is always defaulted to the top left corner (coordinates 0,0).
  This means if you draw an image that is 50x50 pixels at screen location
  200,200, the image will begin to be drawn at 200,200 and extend to
  250,250.\
  \
  The [MidHandle](MidHandle.htm) command moves the image\'s handle to the
  middle of the image. See this command for more information about the
  image\'s handle.\
  \
  This command eliminates the need for the [MidHandle](MidHandle.htm)
  command by making ALL subsequently loaded images default to having
  their image handles set to mid.\
  \
  Note about the term \'handle\'. There are two types of \'handles\' we
  discuss in these documents. One is the location within an image - as
  discussed in this command. The other is a \'file handle\', a variable
  used to hold an image, sound, or font loaded with a command. See
  [LoadImage](LoadImage.htm) for more information about file handles.

  -----------------------------------------------------------------------

Example:\
\

  -----------------------------------------------------------------------
  ; MidHandle/ImageXHandle()/ImageYHandle()/AutoMidHandle\
  \
  ; Initiate Graphics Mode\
  Graphics 640,480,16\
  \
  ; Set up the image file handle as a global\
  Global gfxBall\
  \
  ; Load the image - you may need to change the location of the file\
  gfxBall=LoadImage (\"C:\\Program Files\\Blitz
  Basic\\samples\\ball.bmp\")\
  \
  ; Until the user presses ESC key \...\
  While Not KeyHit(1)\
  Text 0,0,\"Default Image Handle for gfxBall\... Press ESC \...\"\
  Text 0,14,\"X handle-\" + ImageXHandle(gfxBall) ; Print the location of
  the image handle x location\
  Text 0,28,\"Y handle-\" + ImageYHandle(gfxBall) ; Print the location of
  the image handle y location\
  DrawImage gfxBall,200,200,0 ; draw the image at 200,200\
  Wend\
  \
  ; Clear the screen\
  Cls\
  \
  ; Set the ball\'s handle to the center of the image\
  MidHandle gfxBall\
  \
  ; Until the user presses ESC key \... show the new information\
  While Not KeyHit(1)\
  Text 0,0,\"New Image Handle for gfxBall\... Press ESC \...\"\
  Text 0,14,\"X handle-\" + ImageXHandle(gfxBall)\
  Text 0,28,\"Y handle-\" + ImageYHandle(gfxBall)\
  DrawImage gfxBall,200,200,0\
  Wend\
  \
  ; Makes all images load up with their handles in the center of the
  image\
  AutoMidHandle True\
  Cls\
  \
  ; Load the image again\
  gfxBall=LoadImage (\"C:\\Program Files\\Blitz
  Basic\\samples\\ball.bmp\")\
  \
  ; Until the user presses ESC key \... show the new information\
  While Not KeyHit(1)\
  Text 0,0,\"Automatic image handle of gfxBall\... Press ESC \...\"\
  Text 0,14,\"X handle-\" + ImageXHandle(gfxBall)\
  Text 0,28,\"Y handle-\" + ImageYHandle(gfxBall)\
  DrawImage gfxBall,200,200,0\
  Wend\

  -----------------------------------------------------------------------

**[Index](../index.htm){target="_top"}**
