# End Function

# Parameters

  -------
  None.
  -------

# Description

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This line terminates a FUNCTION structure. Upon reaching this line, Blitz will branch program execution to the next command following the original call to the function. See the FUNCTION command for more information.
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/End){function.bb=""}

  -----------------------------------------------------------------------
  ; End Function Example\
  \
  ; Get the user\'s name\
  name\$=Input\$(\"Enter Your Name:\")\
  \
  ; Call a function to print how many letters the name has\
  numletters(name\$);\
  \
  ;The program basically ends here, because functions don\'t run unless
  called.\
  \
  ; The actual function\
  Function numletters(passedname\$)\
  Print \"Your name has \" + Len(passedname\$) + \" letters in it.\"\
  End Function\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=End){function&ref="comments"
target="_blank"} to view the latest version of this page online
