# TFormImage image,a#,b#,c#,d#

# Parameters

  -----------------------------------------------------------------------
  image - image handle\
  a# - 1,1 element of 2x2 matrix\
  b# - 2,1 element of 2x2 matrix\
  c# - 1,2 element of 2x2 matrix\
  d# - 2,2 element of 2x2 matrix

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Transforms an image based on a 2x2 matrix. The input parameters relate
  to the elements of the matrix as below:\
  \
  ( a# b# )\
  ( c# d# )\
  \
  The image is treated as a matrix and is multiplied by the matrix above.
  Unless you understand matrix manipulation thoroughly, it is unlikely
  that you will find this command useful.\
  \
  Common uses for this command might be to rotate or scale images. These
  are available independently using
  [ScaleImage](../2d_commands/ScaleImage.htm){.small} and
  [RotateImage](../2d_commands/RotateImage.htm){.small} However, using
  this command it is possible to do both operations simultaneously. It is
  also possible to shear images using this command.\
  \
  It is strongly recommended that you run the (rather lengthy) example
  below to better understand how this command can be used.\
  \
  See also: [ScaleImage](ScaleImage.htm){.small},
  [RotateImage](RotateImage.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/TFormImage.bb)

  ---------------------------------------------------------------------------------
  ; Set up the Blitz evironment and user controlled input parameters\
  Global a#=1 ; This controls the horizontal scale, negative values cause the image
  to flip. Zero is invalid.\
  Global b#=0 ; This shears the image vertically\
  Global c#=0 ; This shears the image horizontally\
  Global d#=1 ; This controls the vertical scale, negative values cause the image
  to flip. Zero is invalid.\
  Graphics 600,400,16\
  \
  ; Create an image that we can manipulate\
  Global img=CreateImage(50,50)\
  SetBuffer ImageBuffer(img)\
  Color 255,0,0 : Rect 0,0,25,25,1\
  Color 255,255,0 : Rect 25,25,25,25,1\
  Color 0,255,0 : Rect 25,0,25,25,1\
  Color 0,0,255 : Rect 0,25,25,25,1\
  \
  ; Set up drawing & timing stuff\
  SetBuffer BackBuffer()\
  timer=CreateTimer(30)\
  \
  Repeat\
  ; deal with keyboard inputs\
  If KeyDown(42) Or KeyDown(54) Then inc#=-0.1 Else inc#=0.1\
  If KeyDown(30) Then a#=a#+inc#\
  If KeyDown(48) Then b#=b#+inc#\
  If KeyDown(46) Then c#=c#+inc#\
  If KeyDown(32) Then d#=d#+inc#\
  If KeyHit(57) Then RunDemo()\
  If KeyHit(1) Then Exit\
  \
  ; draw the screen\
  DrawScreen()\
  WaitTimer timer\
  Forever\
  End\
  \
  ; Rolling Demo\
  Function RunDemo()\
  degrees=0 ; the number of degrees by which to rotate the image\
  scale#=1.0 ; the scale of the image\
  timer=CreateTimer(10)\
  Repeat\
  a#=scale#:b#=0:c#=0:d#=scale# ; reset the matrix\
  ; increase the degrees (or reset if \>360)\
  If degrees\<360 Then degrees=degrees+4 Else degrees=0\
  ; and set up the matrix multipliers. The explanation as to why these work can be
  found in most good\
  ; mathematics text books, and is too involved to go into here!\
  ma#=Cos(degrees)\
  mb#=Sin(degrees)\
  mc#=-Sin(degrees)\
  md#=Cos(degrees)\
  ; multiply the two matrices.\
  na#=a#\*ma#+b#\*mc#\
  nb#=a#\*mb#+b#\*md#\
  nc#=c#\*ma#+d#\*mc#\
  nd#=c#\*mb#+d#\*md#\
  a#=na#:b#=nb#:c#=nc#:d#=nd#\
  \
  ; handle input and draw the screen\
  If KeyHit(1) Then Exit\
  If KeyDown(200) And scale#\<2.0 Then scale#=scale#+0.1\
  If KeyDown(208) And scale#\>0.1 Then scale#=scale#-0.1\
  DrawScreen(True)\
  WaitTimer timer\
  Forever\
  a#=1:b#=0:c#=0:d#=1 ; reset the matrix\
  FlushKeys()\
  End Function\
  \
  ; Draw stuff on the screen, including transforming the image\
  Function DrawScreen(demo=False)\
  ClsColor 0,0,0\
  Cls\
  DrawBlock img,10,10\
  Text 100,5,\"TFORMIMAGE EXAMPLE\"\
  Text 100,20,\"The command format is: TFormImage image, a, b, c, d\"\
  If Not demo Then\
  Text 100,35,\"Press \'Space\' for a rolling demo\"\
  Text 100,50,\"Use the keys a, b, c, and d to increase the parameters\"\
  Text 100,65,\"Also hold down shift to decrease each of these parameters\"\
  Else\
  Text 100,35,\"Use the up and down arrow keys to change the scale\"\
  End If\
  Text 100,80,\"Press \'Escape\' to exit\"\
  Text 10,110,\"Current Parameters:\"\
  \
  ; \######################## This is where we actually use TFormImage using the
  input parameters\
  Text 20,125,\"TFormImage image, \"+a#+\", \"+b#+\", \"+c#+\", \"+d#\
  img2=CopyImage(img)\
  TFormImage img2,a#,b#,c#,d#\
  ; \#############################################################################\
  \
  If demo Then\
  DrawBlock img2,300,280\
  Else\
  DrawBlock img2,300-ImageWidth(img2)/2,280-ImageHeight(img2)/2\
  EndIf\
  Flip\
  End Function

  ---------------------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TFormImage&ref=comments){target="_blank"}
to view the latest version of this page online
