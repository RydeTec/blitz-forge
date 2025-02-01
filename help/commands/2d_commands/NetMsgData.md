# NetMsgData\$()

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  First off, this ONLY works when you have joined a network game via
  StartNetGame or JoinNetGame and you have created a player via
  CreateNetPlayer (you must create a player, even if it is just to lurk).
  You must\'ve received the message already, determined by the
  RecvNetMsg() command - and probably determined the type of message with
  (NetMsgType().\
  \
  The string value returned from this command is the actual message text
  that was sent.\
  \
  You will use NetMsgType(), NetMsgFrom(), and NetMsgTo() to get other
  important information from the message and act on it.\
  \
  The example requires that you run it on a remote machine while the
  local computer runs the example in the SendNetMsg command.

  -----------------------------------------------------------------------

# [Example](../2d_examples/NetMsgData.bb)

  -----------------------------------------------------------------------
  ; NetMsgData\$() example\
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
  Print \"(Message was to:\"+ NetMsgTo() + \")\"\
  End If\
  End If\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=NetMsgData&ref=comments){target="_blank"}
to view the latest version of this page online
