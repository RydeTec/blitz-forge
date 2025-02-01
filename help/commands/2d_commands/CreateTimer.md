# CreateTimer (frequency)

# Parameters

  -----------------------------------------------
  frequency = usually a framerate like 50 or 60
  -----------------------------------------------

# Description

  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Use this command in conjunction with the WaitTimer command to control the speed of program execution (fps). You will use this in your main screen redraw loop to control the playback speed to match the proper speed. This will prevent your games from playing back too fast on computers faster than yours. Use of this system is VERY GOOD practice, as your game will be played on a variety of computers.
  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/CreateTimer.bb)

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

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateTimer&ref=comments){target="_blank"}
to view the latest version of this page online
