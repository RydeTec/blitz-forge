# HostNetGame (gamename\$)

# Parameters

  --------------------------------------------------------
  gamename\$ = string value designating the game\'s name
  --------------------------------------------------------

# Description

  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This allows you to bypass the \'standard\' networked game dialog box (normally using StartNetGame) and start a hosted game directly. A value of 2 is returned if the hosted game has started successfully.
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/HostNetGame.bb)

  -----------------------------------------------------------------------
  ; HostNetGame example\
  \
  joinResults=HostNetGame(\"ShaneGame\")\
  \
  Select joinResults\
  Case 2\
  Print \"Successfully started host game!\"\
  Default\
  Print \"Game was unable to start!\"\
  End Select\
  waitkey()\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=HostNetGame&ref=comments){target="_blank"}
to view the latest version of this page online
