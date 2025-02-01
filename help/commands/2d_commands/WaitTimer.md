# WaitTimer (timer_variable)

# Parameters

  ------------------------------------------------------------------------
  timer = any valid timer variable created with the CreateTimer command.
  ------------------------------------------------------------------------

# Description

  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Use this in conjunction with the CreateTimer command. This command will halt execution until the timer reaches its value. This is useful to control the execution speed of your program. Check out the CreateTimer command for more.
  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/WaitTimer.bb)

  -----------------------------------------------------------------------
  ; Create the timer to track speed\
  frameTimer=CreateTimer(60)\
  \
  ; Your main screen draw loop\
  While Not KeyHit(1)\
  WaitTimer(frameTimer) ; Pause until the timer reaches 60\
  Cls\
  ; Draw your screen stuff\
  Flip\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=WaitTimer&ref=comments){target="_blank"}
to view the latest version of this page online
