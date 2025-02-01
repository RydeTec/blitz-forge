# Millisecs()

# Parameters

  ------
  None
  ------

# Description

  -----------------------------------------------------------------------
  This command will return to you the system timer value in
  milliseconds.\
  \
  This is incredibly useful for precision timing of events. By reading
  this value into a variable, and checking it against the CURRENT time in
  milliseconds, you can perform \'waits\' or check time lapses with a
  high degree of accuracy. A common use of this command is to seed the
  random number generator with the SeedRnd command.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Millisecs.bb)

  -----------------------------------------------------------------------
  ; This prints STILL WAITING! for three seconds then ends.\
  oldTime=MilliSecs()\
  While MilliSecs() \< oldTime + 3000\
  Print \"Still waiting!\"\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Millisecs&ref=comments){target="_blank"}
to view the latest version of this page online
