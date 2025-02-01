# MouseZSpeed()

# Parameters

  -------
  None.
  -------

# Description

  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  MouseZSpeed returns -1 if the mousewheel on a suitable mouse is being rolled backwards (towards user), 0 if it is not being moved, and 1 if it is being rolled forwards.
  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/MouseZSpeed.bb)

  -----------------------------------------------------------------------
  Graphics 640, 480, 0, 2\
  SetBuffer BackBuffer ()\
  \
  Repeat\
  \
  Cls\
  \
  Select MouseZSpeed ()\
  Case -1\
  result\$ = \"Backwards\"\
  Case 0\
  ; result\$ = \"No movement\"\
  Case 1\
  result\$ = \"Forwards\"\
  End Select\
  \
  Text 20, 10, \"NOTE: MouseZSpeed () = 0 is not listed here, to avoid
  confusion!\"\
  Text 20, 40, result\$\
  Flip\
  \
  Until KeyHit (1)\
  \
  End\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=MouseZSpeed&ref=comments){target="_blank"}
to view the latest version of this page online
