# SendNetMsg type,data\$,from,to,reliable

# Parameters

  -----------------------------------------------------------------------
  type = value 1-99\
  data\$ = string containing message to send\
  from = player ID of the sender\
  to = player ID of the recipient (0=broadcast)\
  reliable = flag for sending message reliably

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  First off, this ONLY works when you have joined a network game via
  StartNetGame or JoinNetGame and you have created a player via
  CreateNetPlayer (you must create a player, even if it is just to
  lurk).\
  \
  This is probably the most complicated of the networking commands. This
  what you use to actually send a message to one or all of the players on
  the network game. The other players will use RecvNetMsg() to intercept
  your message.\
  The TYPE parameter is a number from 1 to 99. These values are denoted
  as \'user messages\'.\
  \
  The Data\$ parameter is the actual string that contains the message you
  want to send. Helpful to know that in order to keep traffic low, you
  will want to combine details of a message into a single message instead
  of sending multiple messages with a single element. For example, you
  might want to send X, Y, and FRAME in a single string like
  \"200,100,4\" and parse it out at the recipient\'s end.\
  \
  FROM is the player\'s ID that is sending the message. This is the value
  returned from the CreateNetPlayer() command.\
  \
  TO is the player\'s ID you wish to send the message to. A default value
  of 0 will broadcast to ALL players.\
  \
  The RELIABLE flag will put a priority on the message and it will ensure
  there is no packet loss in the delivery. However, it is at least 3
  times slower than a regular non-reliable message.\
  \
  The example requires that you run it on the local machine while the
  remote computer runs the example in the RecvNetMsg() command.

  -----------------------------------------------------------------------

# [Example](../2d_examples/SendNetMsg.bb)

  -----------------------------------------------------------------------
  ; SendNetMsg example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  ; Run this example on the local computer\
  ; run the example for RecvNetMsg() on a remote computer\
  \
  ; Graphics mode with double buffering\
  Graphics 640,480,16\
  SetBuffer BackBuffer()\
  \
  ; Create a network game with NO requester\
  joinStatus=HostNetGame(\"ShaneGame\")\
  \
  ; A type to hold all the player\'s information\
  Type multi\
  Field x\
  Field y\
  Field id\
  Field name\$\
  Field xspeed\
  Field boxColor\
  End Type\
  \
  ; make sure the game started ok\...\
  If joinStatus=2 Then\
  Print \"Hosted game started\... \"\
  Else\
  Print \"Hosted game could not be started!\"\
  End\
  End If\
  \
  ; Create 5 local players using TYPEs\
  For t = 1 To 5\
  ; New type instance\
  player.multi = New Multi\
  ; Assign the ID field with the created player ID and name him\
  playerID=CreateNetPlayer(\"Player\" + t)\
  \
  ; if the player was created ok \... assign some random parameters\
  If playerID \<\> 0 Then\
  player\
  ame\$=\"Player\" + t\
  playerx = Rand(640)\
  playery = Rand(480)\
  playeroxColor = Rand(255)\
  playerxspeed = Rand(1,5)\
  ; Print some text results\
  Print \"Player \" + t + \" has joined the game with ID=\" + playerID\
  Else\
  Print \"The player couldn\'t join! Aborting!\"\
  End If\
  Next\
  \
  ; We\'ve got them all! Wait for a key\
  Print \"All local players are joined! Press a key \...\"\
  WaitKey()\
  \
  ; Loop this routine\
  While Not KeyHit(1)\
  Cls\
  ; for each of the players, update their locations on the screen\
  For player = Each multi\
  Color playeroxColor,playeroxColor,playeroxColor\
  Rect playerx,playery,10,10,1\
  Text playerx-10,playery-15,player\
  ame\$\
  playerx = playerx + playerxspeed\
  If playerx \> 640 Or playerx \< 0 Then\
  playerxspeed=-playerxspeed\
  message\$=\"Player ID #\" + playerID + \" hit a wall!\"\
  ; Send a broadcast message if a player rebounds off the wall\
  ; this message will show up on the remote machine\
  SendNetMsg Rand(1,99),message\$,playerid,0\
  End If\
  Next\
  Flip\
  Wend\
  End\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SendNetMsg&ref=comments){target="_blank"}
to view the latest version of this page online
