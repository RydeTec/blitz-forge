# Gosub label

# Parameters

  -----------------------------------
  label = any valid exisiting label
  -----------------------------------

# Description

  -----------------------------------------------------------------------
  This branches the flow of the program to a designated label, with the
  understanding that there will be a Return later on in the called code
  to resume execution of the program where the Gosub was called. With the
  use of Functions inside of Blitz, it isn\'t very practical to use
  Gosubs, but you may still find it useful. If you do not require the
  need to return execution back to the Gosub statement, you may use Goto
  instead. See the example.\
  \
  See also: [Return](Return.htm){.small}, [Goto](Goto.htm){.small},
  [Function](Function.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Gosub.bb)

  -----------------------------------------------------------------------
  Print \"The program starts \...\"\
  Gosub label1\
  Print \"The Program ends \...\"\
  \
  ; wait for ESC key before ending\
  While Not KeyHit(1)\
  Wend\
  \
  End\
  \
  .label1\
  Print \"We could do all sorts of things in this part of the
  program\...\"\
  Print \"But, we\'ll just go back to the original code, instead \...\"\
  Return

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Gosub&ref=comments){target="_blank"}
to view the latest version of this page online
