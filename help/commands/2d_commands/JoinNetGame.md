# JoinNetGame (gamename\$,serverIP\$)

# Parameters

  -----------------------------------------------------------------------
  gamename\$ = valid string containing game name to join\
  serverIP\$ = IP address of computer hosting game

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Use this command to join a network game, bypassing the dialog box
  normally endured with the StartNetGame command.\
  This returns 0 if the command failed, or 1 if the game was joined
  successfully.

  -----------------------------------------------------------------------

# [Example](../2d_examples/JoinNetGame.bb)

  -----------------------------------------------------------------------
  ; JoinNetGame example\
  ; Note; run the HostNetGame example code on the other computer\
  ; you wish to join with\
  \
  gamename\$=\"ShaneGame\"\
  ; Change this to match the other computer\'s IP!\
  serverIP\$=\"0.0.0.0\"\
  \
  ; Make the join attempt\
  joinResults=JoinNetGame(gamename\$,serverIP\$)\
  \
  Select joinResults\
  Case 1\
  Print \"Joined the game successfully!\"\
  Default\
  Print \"Joining the game was unsuccessful.\"\
  End Select\
  WaitKey()\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=JoinNetGame&ref=comments){target="_blank"}
to view the latest version of this page online
