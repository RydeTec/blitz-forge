# FreeTimer (timer_variable)

# Parameters

  ------------------------------------------------------------------------
  timer = any valid timer variable created with the CreateTimer command.
  ------------------------------------------------------------------------

# Description

  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command will destroy a timer variable created with the with the CreateTimer command and free the memory it was using. It is a good practice to destroy elements in your game you are no longer using.
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/FreeTimer.bb)

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
  Wend\
  \
  ; Kill the timer\
  FreeTimer(frameTimer)\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=FreeTimer&ref=comments){target="_blank"}
to view the latest version of this page online
