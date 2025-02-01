[  CPUTimer()   ]{.Command}

[Definition:]{.header}\
\

  ---------------------------------------------
  Returns the current value of the CPU timer.
  ---------------------------------------------

[\
Parameter Description:]{.header}\
\

  -------
  None.
  -------

Command Description:\
\

  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This returns a constantly counting up timer, apparently from the CPU. Seems to always be negative. This could be useful for soem randomize seed, but for any sort of \'time tracking\', I would use [Millisecs()](Millisecs().htm). This command could be outdated, and just not removed from the product.
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Example:\
\

  -----------------------------------------------------------------------
  ;CPUTIMER() example\
  \
  While Not keyhit(1)\
  Print CPUTimer()\
  Wend\
  \

  -----------------------------------------------------------------------

**[Index](../index.htm){target="_top"}**
