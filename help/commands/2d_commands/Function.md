# Function name

# Parameters

  ------------------------------------------------------------
  name = any valid name text that is not a keyword of Blitz.
  ------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  A function is a routine of commands you may choose to call frequently
  within your program. Functions are considered \'proper\' practice in
  this situation, instead of using GOSUB commands.\
  \
  Functions are independant of your \'main code\' and will only execute
  if they are called. They have their own namespace, and variables
  created outside a function are NOT available within the function (this
  goes for TYPE structures as well) unless you declare the variable or
  Type structure as GLOBAL.\
  \
  You can pass variables into functions, as well as RETURN values back to
  the calling code. To return floats or strings from a function ensure
  your function name has a \# or \$ suffix.\
  \
  The practical use of functions is to help seperate your code into
  managable chunks for subsequent perfection of a routine. You may put
  all your screen updates into a single function, for example. Or perhaps
  your scoring system where you pass it what alien has been destroyed and
  it updates the player\'s score.\
  \
  Once the function reaches the END FUNCTION command, it returns program
  execution to the point immediately following the function call.\
  \
  Functions can be a bit daunting until you realize they really are their
  own little programs within your program. See the example for more.\
  \
  Note that if you want to be really clever, you can replace Blitz
  functions with your own function. This can be useful if you wish to add
  some error handling or additional processing around every instance of a
  Blitz function. Note that once you have replaced a Blitz function, you
  cannot call the real function - for example you cannot wrap a LoadImage
  command within a LoadImage function as it will recursively call
  itself!\
  \
  See also: [End Function](End){.small function.htm=""},
  [Return](Return.htm){.small}, [Gosub](Gosub.htm){.small},
  [Goto](Goto.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Function.bb)

  -----------------------------------------------------------------------
  ; Function Example\
  \
  ; Get the user\'s name\
  name\$=Input\$(\"Enter Your Name:\")\
  \
  ; Call a function to print how many letters the name has\
  numletters(name\$);\
  \
  ; Let\'s get something BACK from the function\
  thefirst\$=firstletter(name\$)\
  \
  ; Now print results\
  Print \"Was the first letter an \'S\'? (1=True/0=False)\" + thefirst\$\
  \
  ;The program basically ends here, because functions don\'t run unless
  called.\
  \
  ; The actual function\
  Function numletters(passedname\$)\
  Print \"Your name has \" + Len(passedname\$) + \" letters in it.\"\
  End Function\
  \
  ; Function to see if the first letter is S\
  Function firstletter(passedname\$)\
  \
  ; If the first letter is an \'S\' then return from the function a true
  value\
  If Left\$(passedname\$,1) = \"S\" Then\
  Return True\
  \
  ; Otherwise, return false\
  Else\
  \
  Return False\
  \
  End If\
  End Function

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Function&ref=comments){target="_blank"}
to view the latest version of this page online
