# If

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  Use this to check the value of a variable or to see if a condition is
  true or false. The code between IF and END IF (or ENDIF) is executed if
  the condition is true. Using NOT, you can also act if the condition is
  NOT true - or use ELSE to perform a different set of commands than if
  the condition is met. Lastly, you can use \'nested\' or multiple IFs
  through the use of ELSE IF (or ELSEIF) to do LOTS of condition
  checking. If you get too deep in condition checking, consider using
  SELECT structures instead.\
  \
  See also: [Then](Then.htm){.small}, [Else](Else.htm){.small},
  [ElseIf](ElseIf.htm){.small}, [EndIf](EndIf.htm){.small},
  [True](True.htm){.small}, [False](False.htm){.small},
  [Select](Select.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/If.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=If&ref=comments){target="_blank"}
to view the latest version of this page online
