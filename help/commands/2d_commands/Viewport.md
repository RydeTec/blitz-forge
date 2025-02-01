# Viewport x, y, width, height

# Parameters

  -----------------------------------------------------------------------
  x = the topmost left corner to start the port x coordinate\
  y = the topmost left corner to start the port y coordinate\
  width = how wide the port is (in pixels)\
  height = how tall the port is (in pixels)

  -----------------------------------------------------------------------

# Description

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  There are MANY MANY times you want to draw graphics (aliens, ships, etc) ONLY on a certain area of the screen while leaving the other areas alone. This is often referred to as \'windowing\' or \'portaling\' or \'clipping\' an area. Usually, you will perform all your drawing operations first (background, controls, etc), then section off the restricted area of the screen with VIEWPORT to do drawing in that area. There are a million uses for this; overhead map radar screens, Ultima style display windows, onscreen scrollers, etc. This is a bit more complex than most graphic commands, so be sure you have a good handle on it before trying to use it. The biggest tip I can give you about this command is: REMEMBER TO CLEAR THE VIEWPORT WHEN YOU ARE DONE! Do this by setting the viewport to include the whole screen (i.e. Viewport 0,0,640,480 if your game was in 640x480). Look carefully at the example. Remember, the second set of numbers isn\'t the ENDING location of the port - rather the SIZE of the port starting at the first coordinates.
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/Viewport.bb)

  -----------------------------------------------------------------------
  ; ViewPort Example\
  \
  ; Set Up Graphics Mode\
  Graphics 800,600\
  \
  ; Set up Double Buffering\
  SetBuffer BackBuffer()\
  \
  ; Set viewport starting at 100,100 and make it 200,200 pixels in size\
  Viewport 100,100,200,200\
  \
  ; Infinately draw random rectangles with random colors\
  While Not KeyHit(1)\
  Cls ; Clear screen in \'blitting\' technique\
  For t = 1 To 100 ; Do 100 rectangles each time\
  Color Rnd(255),Rnd(255),Rnd(255) ; Random color\
  Rect Rnd(800),Rnd(600),Rnd(300),Rnd(300),Rnd(0,1) ; Random sized and
  located box, some filled\
  Next ; repeat that drawing loop\
  Flip ; Flip to the back buffer\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Viewport&ref=comments){target="_blank"}
to view the latest version of this page online
