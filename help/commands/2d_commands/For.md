# For variable

# Parameters

  ------------------------------------
  variable = any valid variable name
  ------------------------------------

# Description

  -----------------------------------------------------------------------
  The first command of the FOR \... NEXT loop, this command is used to
  assign a variable to a range of numbers in sequence and execute a set
  of code that many times. Using the STEP command allows you to skip a
  certain value between each loop of the code. Note that the STEP amount
  cannot be a variable.\
  \
  This is frequently used when a specific pattern of numbers is needed to
  perform an evolution (moving something from point A to point B, adding
  a value to a score incrementally, etc). This allows you to assign a
  variable with the current value of the loop. See the example for more.\
  Note: Unlike many BASIC languages, the NEXT command does NOT use the
  FOR command\'s variable as an identifier. If you have nested FOR \...
  NEXT commands, the language will automatically match the NEXT with the
  nearest FOR command.\
  \
  See also: [To](To.htm){.small}, [Step](Step.htm){.small},
  [Each](Each.htm){.small}, [Next](Next.htm){.small},
  [Exit](Exit.htm){.small}, [While](While.htm){.small},
  [Repeat](Repeat.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/For.bb)

  -----------------------------------------------------------------------
  ; Print the values 1 through 10\
  For t = 1 To 10\
  Print t\
  Next\
  \
  ; Print the values 1,3,5,7,9\
  For t = 1 To 10 Step 2\
  Print t\
  Next

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=For&ref=comments){target="_blank"}
to view the latest version of this page online
