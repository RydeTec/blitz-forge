# ElseIf

# Parameters

  ------
  None
  ------

# Description

  -----------------------------------------------------------------------
  During a standard IF \... THEN conditional structure, you may wish to
  check another condition if the original condition fails. This \'nested
  IF\' situation can get WAY out of hand, and once you have more than two
  nested conditionals, you should consider aSELECT/CASE structure. See
  the example.\
  \
  See also: [If](If.htm){.small}, [Then](Then.htm){.small},
  [Else](Else.htm){.small}, [ElseIf](ElseIf.htm){.small},
  [EndIf](EndIf.htm){.small}, [Select](Select.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ElseIf.bb)

  -----------------------------------------------------------------------
  ; ELSEIF Example\
  \
  ; Input the user\'s name\
  name\$=Input\$(\"What is your name? \")\
  \
  ; Doesn\'t the person\'s name equal SHANE?\
  If name\$ = \"Shane\" Then\
  Print \"You are recognized, Shane! Welcome!\"\
  \
  ElseIf name\$=\"Ron\" Then\
  Print \"You are recognized too, Ron! Welcome!\"\
  \
  Else\
  Print \"Sorry, you don\'t belong here!\"\
  \
  ; End of the condition checking\
  End If

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ElseIf&ref=comments){target="_blank"}
to view the latest version of this page online
