# Text x,y,string\$,\[center x\],\[center y\]

# Parameters

  -----------------------------------------------------------------------
  x = starting x coordinate to print text\
  y = starting 4 coordinate to print text\
  string\$ = string/text to print\
  center x = optional; true = center horizontally\
  center y = optional; true = center vertically

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Prints a string at the designated screen coordinates. You can center
  the text on the coordiates by setting center x/center y to TRUE. This
  draws the text in the current drawing color.\
  \
  Note: Printing a space with text will NOT render a block - a space is
  an empty value. So printing \" \" will not make a box appear.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Text.bb)

  -----------------------------------------------------------------------
  ; Text example\
  \
  ; enable graphics mode\
  Graphics 800,600,16\
  \
  ; wait for ESC key before ending\
  While Not KeyHit(1)\
  ;print the text, centered horizontally at x=400, y=0\
  Text 400,0,\"Hello There!\",True,False\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Text&ref=comments){target="_blank"}
to view the latest version of this page online
