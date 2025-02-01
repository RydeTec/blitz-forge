# EndIf

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  Terminates an IF \... THEN condition structure. See END IF for more
  information.\
  \
  See also: [If](If.htm){.small}, [Then](Then.htm){.small},
  [Else](Else.htm){.small}, [ElseIf](ElseIf.htm){.small},
  [Select](Select.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/EndIf.bb)

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
  EndIf

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=EndIf&ref=comments){target="_blank"}
to view the latest version of this page online
