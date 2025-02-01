# LoadImage (Filename)

# Parameters

  ----------------------------------------------------------------
  filename = string designating full path and filename to image.
  ----------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command loads an image from disk and assigns it a file handle. You
  will use the DrawImage command to display the graphic later. The demo
  version of Blitz Basic supports BMP files; the full retail version of
  Blitz Basic supports JPG and PNG format as well.\
  \
  Many multimedia loading commands for fonts, graphics, and sounds
  require the use of FILE HANDLES. You\'ll need to have a good
  understanding of file handles if you are going to successfully use
  Blitz Basic.\
  \
  A file handle is a variable (usually GLOBAL) that holds a unique
  identifier for a loaded item (font, image, sound, music, etc.). This
  unique number is used later for subsequent operations to designate the
  loaded item. This file handle allocates the memory to hold the item.\
  \
  You will find file handles used all over Blitz. See the example for
  some well-documented code.\
  \
  Note that when you change the graphics mode using the Graphics command,
  all images that were loaded will be lost, and all handles will become
  invalid. However, with BlitzPlus, the images will sometimes be
  retained. Expect more control over this in the next BlitzPlus update.\
  \
  See also: [LoadAnimImage](LoadAnimImage.htm){.small},
  [CreateImage](CreateImage.htm){.small},
  [FreeImage](FreeImage.htm){.small}, [SaveImage](SaveImage.htm){.small},
  [DrawImage](DrawImage.htm){.small}, [Graphics](Graphics.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/LoadImage.bb)

  -----------------------------------------------------------------------
  ; LoadImage and DrawImage example\
  \
  ; Declare a variable to hold the graphic file handle\
  Global gfxPlayer\
  \
  ; Set a graphics mode\
  Graphics 640,480,16\
  \
  ; Set drawing operations for double buffering\
  SetBuffer BackBuffer()\
  \
  ; Load the image and assign its file handle to the variable\
  ; - This assumes you have a graphic called player.bmp in the\
  ; same folder as this source code\
  gfxPlayer=LoadImage(\"player.bmp\")\
  \
  ; Let\'s do a loop where the graphic is drawn wherever the\
  ; mouse is pointing. ESC will exit.\
  While Not KeyHit(1)\
  Cls ; clear the screen\
  DrawImage gfxPlayer,MouseX(),MouseY() ; Draw the image!\
  Flip ; flip the image into view and clear the back buffer\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadImage&ref=comments){target="_blank"}
to view the latest version of this page online
