# ShowPointer

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  ShowPointer is for use in windowed display modes, and simply shows the
  Windows pointer after it\'s been hidden (via HidePointer). It has no
  effect in full-screen modes.\
  \
  See also: [HidePointer](HidePointer.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ShowPointer.bb)

  -----------------------------------------------------------------------
  ; HidePointer / ShoPointer Example\
  \
  ; draw a simple screen, cut in half by a white line\
  Graphics 800,600,0,2\
  Color 255,255,255\
  Line 400,0,400,600\
  Text 200,300,\"ShowPointer\",True,True\
  Text 600,300,\"HidePointer\",True,True\
  \
  ; and a simple loop in which we hide / show the pointer dependent on\
  ; which side of the screen the mouse is on!\
  Repeat\
  If MouseX()\<400 Then\
  ShowPointer\
  Else\
  HidePointer\
  End If\
  If KeyHit(1) Then Exit ; ESCAPE to exit\
  Forever\
  \
  End ; bye!

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ShowPointer&ref=comments){target="_blank"}
to view the latest version of this page online
