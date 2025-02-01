# StringHeight (string)

# Parameters

  ----------------------------------------------
  string = any valid string or string variable
  ----------------------------------------------

# Description

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This will return the size, in pixels, the height of the indicated string. This is useful for determining screen layout, scrolling of text, and more. This is calculated based on the size of the currently loaded font.
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/StringHeight.bb)

  -----------------------------------------------------------------------
  ; StringWidth/Height Example\
  \
  a\$=\"Hello Shane!\"\
  Print \"A\$=\" + a\$\
  Print \"This string is \"+ StringWidth(a\$) + \" pixels wide and\"\
  Print \"it is \" + StringHeight(a\$) + \" tall, based on the current
  font!\"\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=StringHeight&ref=comments){target="_blank"}
to view the latest version of this page online
