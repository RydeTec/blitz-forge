# StopNetGame

# Parameters

  -------
  None.
  -------

# Description

  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Use this command to terminate the network game currently in progress (started with the StartNetGame() command). If possible, the hosting session will transfer to another machine connected to the network game.
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/StopNetGame.bb)

  -----------------------------------------------------------------------
  ; stopNetGame() example\
  \
  newGame = StartNetGame()\
  ; Check the status of the new game.\
  If newGame = 0 Then\
  print \"Could not start or join net game.\"\
  ElseIf newGame = 1\
  print \"Successfully joined the network game.\"\
  ElseIf newGame = 2\
  print \"A new network game was started.\"\
  EndIf\
  waitkey()\
  StopNetGame()\
  print \"The Network game was stopped.\"\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=StopNetGame&ref=comments){target="_blank"}
to view the latest version of this page online
