[  NetMsgFrom()   ]{.Command}

[Definition:]{.header}\
\

  ------------------------------------------------
  Returns the sender\'s ID of a network message.
  ------------------------------------------------

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
  [RecvNetMsg()](RecvNetMsg().htm) command - and probably determined the
  type of message with ([NetMsgType()](NetMsgType().htm).\
  \
  The value returned from this command denotes the sender\'s ID number
  assigned to them when they were created with
  [CreateNetPlayer](CreateNetPlayer.htm) command. Use this to perform
  actions on the player on the local machine.\
  \
  You will use [NetMsgType()](NetMsgType().htm),
  [NetMsgTo()](NetMsgTo().htm), and [NetMsgData\$()](NetMsgData$().htm)
  to get other important information from the message and act on it.\
  \
  The example requires that you run it on a remote machine while the
  local computer runs the example in the [SendNetMsg](SendNetMsg.htm)
  command.

  -----------------------------------------------------------------------

Example:\
\

  -----------------------------------------------------------------------
  ; NetMsgFrom() example\
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
