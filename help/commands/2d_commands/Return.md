# Return value

# Parameters

  -----------------------------------------------------------------------
  Return can pass a value back to the calling function of the type
  declared in the function name.\
  \
  No value can be returned when used with Gosub.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  When called inside a FUNCTION structure, the RETURN command immediately
  returns from the function back to the calling code. An optional value
  may be returned. See FUNCTION for more information. Remember, after a
  Return, the remaining code of the Function is not executed. See
  example. It also returns execution from a subroutine called with the
  Gosub command.\
  \
  See also: [Function](Function.htm){.small}, [Gosub](Gosub.htm){.small},
  [Goto](Goto.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Return.bb)

  -----------------------------------------------------------------------
  ; RETURN example\
  \
  ; Set result to the return value of the function \'testme\'\
  result=testme(Rnd(0,10));\
  \
  ; The program effectively ends here.\
  \
  ; The actual function\
  Function testme(test);\
  \
  ; If the random number passed = 0\
  If test=0 Then\
  Print \"Value was 0\"\
  Return False ; The Function ends immediately\
  Else\
  Print \"The value was greater than 0\"\
  Return True ; The Function ends immediately\
  End If\
  Print \"This line never gets printed!\"\
  End Function

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Return&ref=comments){target="_blank"}
to view the latest version of this page online
