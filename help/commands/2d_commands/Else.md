# Else

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  There are times during an IF \... THEN conditional that you will want
  code to execute in the event that the conditional is NOT met. The ELSE
  command begins that block of code, and is terminated by the END IF
  command. See example.\
  \
  See also: [If](If.htm){.small}, [Then](Then.htm){.small},
  [ElseIf](ElseIf.htm){.small}, [EndIf](EndIf.htm){.small},
  [Select](Select.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Else.bb)

  -----------------------------------------------------------------------
  ; ELSE example\
  \
  name\$=Input\$(\"What is your name?\")\
  \
  If name\$=\"Shane\" then\
  Print \"Hello Shane!\"\
  Else\
  Print \"I have NO clue who you are!\"\
  End If

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Else&ref=comments){target="_blank"}
to view the latest version of this page online
