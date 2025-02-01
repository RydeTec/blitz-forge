# GetMouse()

# Parameters

  -------
  None.
  -------

# Description

  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Unlike the other similar commands (MouseDown and MouseHit), this command doesn\'t need to know which button you are trying to test for. It looks for any mouse button, then returns the number the user clicked. Since you are polling all the mouse buttons instead of just a specific one, this may be a tad less efficient than using MouseDown or MouseHit. Use this command in conjunction with Select/Case for maximum efficiency!
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/GetMouse.bb)

  -----------------------------------------------------------------------
  ; GetMouse Example\
  \
  While Not KeyHit(1)\
  button=GetMouse()\
  If button \<\> 0 Then\
  Print \"You pressed mouse button #\" + button\
  End If\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GetMouse&ref=comments){target="_blank"}
to view the latest version of this page online
