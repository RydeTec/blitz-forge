# ImageYHandle image

# Parameters

  ---------------------------------------------------
  image = variable holding the image\'s file handle
  ---------------------------------------------------

# Description

  -----------------------------------------------------------------------
  It is occasionally useful to determine the location of an image\'s
  image handle. This command returns the Y coordinate. Use ImageXHandle
  to get the X coordinate. Please see MidHandle for more information on
  the image\'s image handle.\
  Note about the term \'handle\'. There are two types of \'handles\' we
  discuss in these documents. One is the location within an image - as
  discussed in this command. The other is a \'file handle\', a variable
  used to hold an image, sound, or font loaded with a command. See
  LoadImage for more information about file handles.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ImageYHandle.bb)

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
  gfxBall=LoadImage (\"C:Program FilesBlitz Basicsamplesall.bmp\")\
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
  gfxBall=LoadImage (\"C:Program FilesBlitz Basicsamplesall.bmp\")\
  \
  ; Until the user presses ESC key \... show the new information\
  While Not KeyHit(1)\
  Text 0,0,\"Automatic image handle of gfxBall\... Press ESC \...\"\
  Text 0,14,\"X handle-\" + ImageXHandle(gfxBall)\
  Text 0,28,\"Y handle-\" + ImageYHandle(gfxBall)\
  DrawImage gfxBall,200,200,0\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ImageYHandle&ref=comments){target="_blank"}
to view the latest version of this page online
