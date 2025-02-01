# StartNetGame()

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  Displays a Windows dialog with option to join or start a new
  multiplayer network game, via modem, serial connection or TCP/IP
  (Internet).\
  \
  Note: This command must be started before any other network commands,
  otherwise they will fail.\
  \
  A return value of 0 indicates failure, 1 means a game was joined and 2
  means a game was created and is being hosted on the local machine.

  -----------------------------------------------------------------------

# [Example](../2d_examples/StartNetGame.bb)

  -----------------------------------------------------------------------
  newGame = StartNetGame()\
  ; Check the status of the new game.\
  If newGame = 0 Then\
  print \"Could not start or join net game.\"\
  ElseIf newGame = 1\
  print \"Successfully joined the network game.\"\
  ElseIf newGame = 2\
  print \"A new network game was started.\"\
  EndIf\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=StartNetGame&ref=comments){target="_blank"}
to view the latest version of this page online
