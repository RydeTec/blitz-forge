# Local variable

# Parameters

  ------------------------------------
  variable = any valid variable name
  ------------------------------------

# Description

  -----------------------------------------------------------------------
  This command is probably just here for compatibility with other BASIC
  languages. LOCAL will let you specify that the variable you are
  defining is available ONLY to the program or Function you are assigning
  it in. In order to get a variable to be accessible anywhere in your
  program, you need to make it GLOBAL. I say it is only here for
  compatibility because whenever you assign a variable that isn\'t
  Global, it is automatically assigned as a LOCAL variable. You can
  optionally assign a value to the variable at the time of declaration.
  See example.\
  \
  See also: [Global](Global.htm){.small}, [Const](Const.htm){.small},
  [Dim](Dim.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Local.bb)

  -----------------------------------------------------------------------
  ; Local example\
  \
  ; set lives to 5 for the main program loop\
  Local lives=5\
  \
  ; Call a function\
  while not keyhit(1)\
  showlives()\
  Wend\
  \
  Function showlives()\
  ; For this function, lives will be 10!\
  Local lives=10\
  Print lives\
  End Function

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Local&ref=comments){target="_blank"}
to view the latest version of this page online
