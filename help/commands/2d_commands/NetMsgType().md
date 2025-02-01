[  NetMsgType()   ]{.Command}

[Definition:]{.header}\
\

  --------------------------------------------------------------
  Returns the type of message receieved during a network game.
  --------------------------------------------------------------

[\
Parameter Description:]{.header}\
\

  -------
  None.
  -------

Command Description:\
\

  -----------------------------------------------------------------------
  First off, this ONLY works when you have joined a network game via
  [StartNetGame](StartNetGame.htm) or [JoinNetGame](JoinNetGame.htm) and
  you have created a player via [CreateNetPlayer](CreateNetPlayer.htm)
  (you must create a player, even if it is just to lurk). You must\'ve
  received the message already, determined by the
  [RecvNetMsg()](RecvNetMsg().htm) command.\
  \
  The value returned from this command denotes the message; 1-99 means it
  is a user message, 100 means a new player has joined the game, 101
  means a player has been deleted from the network game
  ([NetMsgFrom()](NetMsgFrom().htm) returns the player deleted), 102
  means the original host has left the game and THIS machine is now the
  new host.\
  \
  If you receive a 200, this means a fatal exception has occurred and you
  need to exit the game.\
  \
  You will use [NetMsgFrom](NetMsgFrom.htm), [NetMsgTo](NetMsgTo.htm),
  and [NetMsgData\$](NetMsgData$.htm) to get the important information
  from the message and act on it.\
  \
  The example requires that you run it on a remote machine while the
  local computer runs the example in the [SendNetMsg](SendNetMsg.htm)
  command.

  -----------------------------------------------------------------------

Example:\
\

  -----------------------------------------------------------------------
  ; NetMsgType() example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  ; Run this program on the REMOTE computer to \'watch\'\
  ; the activity of the SendNetMsg example. Run that\
  ; example on local machine.\
  ;\
  ; This program will tell you when a player involved in\
  ; the game hits a wall \...\
  \
  ; We\'ll use this instead of JoinHostGame - make it easier\
  StartNetGame()\
  \
  ; Create a player - a player must be created to\
  ; receive mesages!\
  playerID=CreateNetPlayer(\"Shane\")\
  \
  ; Loop and get status\
  While Not KeyHit(1)\
  \
  ; Check to see if we\'ve received a message\
  If RecvNetMsg() Then\
  \
  ; if we did, let\'s figure out what type it is\
  ; we know it will be a user message, though\
  msgType=NetMsgType()\
  \
  ; 1-99 means a user message\
  If msgType\>0 And msgType\<100 Then\
  \
  ; Let\'s see who the message was from\
  msgFrom=NetMsgFrom()\
  \
  ; Let\'s get the message!\
  msgData\$=NetMsgData\$()\
  \
  ; Print the message\
  Print msgData\$\
  End If\
  End If\
  Wend\

  -----------------------------------------------------------------------

**[Index](../index.htm){target="_top"}**
