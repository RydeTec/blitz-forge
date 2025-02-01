[  MouseWait()   ]{.Command}

[Definition:]{.header}\
\

  --------------------------------------------------------------------------------------
  Halts program execution until a mouse button is pressed and returns its button code.
  --------------------------------------------------------------------------------------

[\
Parameter Description:]{.header}\
\

  -------
  None.
  -------

Command Description:\
\

  -----------------------------------------------------------------------
  This command makes your program halt until a mouse button is pressed on
  the mouse. Used alone, it simply halts and waits for a button press. It
  can also be used to assign the pressed button\'s code value to a
  variable. See example.\
  \
  In MOST CASES, you are not going to want to use this command because
  chances are likely you are going to want things on the screen still
  happening while awaiting the button press. In that situation, you\'ll
  use a WHILE \... WEND awaiting a [MouseHit](MouseHit.htm) value -
  refreshing your screen each loop.

  -----------------------------------------------------------------------

Example:\
\

  -----------------------------------------------------------------------
  ; MouseWait Example\
  \
  Print \"Press a mouse button please\...\"\
  mseButton=MouseWait()\
  Print \"You pressed mouse button #\" + mseButton\

  -----------------------------------------------------------------------

**[Index](../index.htm){target="_top"}**
