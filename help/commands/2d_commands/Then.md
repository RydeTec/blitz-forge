# Then

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  Used in an IF statement to denote the end of the conditional to be
  checked. Famous for its participation in the IF \... THEN structure.
  See example and IF statement for more information.\
  \
  See also: [If](If.htm){.small}, [Else](Else.htm){.small},
  [ElseIf](ElseIf.htm){.small}, [EndIf](EndIf.htm){.small},
  [Select](Select.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Then.bb)

  -----------------------------------------------------------------------
  ; IF THEN Example\
  \
  ; Input the user\'s name\
  name\$=Input\$(\"What is your name? \")\
  \
  ; Doesn\'t the person\'s name equal SHANE?\
  If name\$ = \"Shane\" Then\
  \
  Print \"You are recognized, Shane! Welcome!\"\
  \
  Else\
  \
  Print \"Sorry, you don\'t belong here!\"\
  \
  ; End of the condition checking\
  End If

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Then&ref=comments){target="_blank"}
to view the latest version of this page online
