# Const variablename

# Parameters

  -----------------------------------------------------------
  variablename - any valid variable name and its assignment
  -----------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This declares a variable as a constant (a variable whose value will
  never change and cannot be changed) and assigns the value to it.\
  \
  Assign constants to values you know will not change in your game
  (screen width, height - scoring values - etc). This reduces the load of
  the variable memory, since Blitz knows it will never change size unlike
  other variables which can grow and shrink in size based on what value
  it holds.\
  \
  See also: [Global](Global.htm){.small}, [Local](Local.htm){.small},
  [Dim](Dim.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Const.bb)

  -----------------------------------------------------------------------
  ; CONST Example\
  \
  Const scrWidth=640\
  Const scrHeight=480\
  Const alienscore=100\
  \
  Graphics scrWidth,scrHeight\
  \
  Print \"The point value for shooting any alien is always \" +
  alienscore

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Const&ref=comments){target="_blank"}
to view the latest version of this page online
