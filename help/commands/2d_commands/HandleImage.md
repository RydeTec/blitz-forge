# HandleImage image,x,y

# Parameters

  -----------------------------------------------------------------------
  image = variable holding the file handle to the image\
  x = x location of the new image handle location\
  y = y location of the new image handle location

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  When an image is loaded with LoadImage, the image handle (the location
  within the image where the image is \'drawn from\') is always defaulted
  to the top left corner (coordinates 0,0). This means if you draw an
  image that is 50x50 pixels at screen location 200,200, the image will
  begin to be drawn at 200,200 and extend to 250,250.\
  \
  This command moves the image handle from the 0,0 coordinate of the
  image to the specified x and y location in the image. You can retrieve
  an image\'s current location handle using the ImageXHandle and
  ImageYHandle. Finally, you can make all images automatically load with
  the image handle set to middle using the AutoMidHandle command.\
  \
  Note about the term \'handle\'. There are two types of \'handles\' we
  discuss in these documents. One is the location within an image - as
  discussed in this command. The other is a \'file handle\', a variable
  used to hold an image, sound, or font loaded with a command. See
  LoadImage for more information about file handles.\
  \
  Also See: MidHandle

  -----------------------------------------------------------------------

# [Example](../2d_examples/HandleImage.bb)

  -----------------------------------------------------------------------
  ;HandleImage Example\
  \
  Graphics 800,600,16\
  \
  gfxPlayer=LoadImage(\"player.bmp\")\
  HandleImage gfxPlayer,20,20\
  DrawImage gfxPlayer,0,0\
  WaitKey\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=HandleImage&ref=comments){target="_blank"}
to view the latest version of this page online
