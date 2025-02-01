# CreateNetPlayer (name\$)

# Parameters

  -------------------------------------------------------
  name\$ = any valid string holding the player\'s name.
  -------------------------------------------------------

# Description

  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Creates a new local player. This also causes a special message to be sent to all remote machines (see NetMsgType). This returns an integer player number to be used in sending/receiving messages. Note that you must create at least one player before you can send and receive messages.
  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/CreateNetPlayer.bb)

  -----------------------------------------------------------------------
  ; CreateNetPlayer example\
  \
  newGame = StartNetGame()\
  ; Check the status of the new game.\
  If newGame = 0 Then\
  Print \"Could not start or join net game.\"\
  End\
  \
  ElseIf newGame = 1\
  Print \"Successfully joined the network game\"\
  ElseIf newGame = 2\
  Print \"A new network game was started!\"\
  EndIf\
  \
  ; Create a random player name\
  name\$=\"Player\" + Rand(100)\
  \
  ; Get a unique player id number for the player\
  ; and create the player\
  playerID=CreateNetPlayer(name\$)\
  \
  If playerID = 0 Then\
  Print \"Player could not be created!\"\
  Else\
  Print \"Player \" + name\$ + \" was created and given ID#\" + playerID\
  End If\
  WaitKey()\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateNetPlayer&ref=comments){target="_blank"}
to view the latest version of this page online
