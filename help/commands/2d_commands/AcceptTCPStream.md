# AcceptTCPStream (serverhandle)

# Parameters

  -----------------------------------------------------------------------
  serverhandle = the server handle assigned when the server was created
  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Accepts an incoming TCP/IP stream, and returns a TCP/IP stream if one
  is available, or 0 if not.\
  \
  See CreateTCPServer and CloseTCPServer.

  -----------------------------------------------------------------------

# [Example](../2d_examples/AcceptTCPStream.bb)

  -----------------------------------------------------------------------
  ; CreateTCPServer, CloseTCPServer, AcceptTCPStream Example\
  ; This code is in two parts, and needs to be run seperately on the same
  machine\
  \
  ; \-\-- Start first code set \-\--\
  ; Create a server and listen for push\
  \
  svrGame=CreateTCPServer(8080)\
  \
  If svrGame\<\>0 Then\
  Print \"Server started successfully.\"\
  Else\
  Print \"Server failed to start.\"\
  End\
  End If\
  \
  While Not KeyHit(1)\
  strStream=AcceptTCPStream(svrGame)\
  If strStream Then\
  Print ReadString\$(strStream)\
  Delay 2000\
  End\
  Else\
  Print \"No word from Apollo X yet \...\"\
  Delay 1000\
  End If\
  Wend\
  \
  End\
  \
  ; \-\-- End first code set \-\--\
  \
  \
  ; \-\-- Start second code set \-\--\
  ; Copy this code to another instance of Blitz Basic\
  ; Run the above code first, then run this \... they will \'talk\'\
  \
  ; Create a Client and push data\
  \
  strmGame=OpenTCPStream(\"127.0.0.1\",8080)\
  \
  If strmGame\<\>0 Then\
  Print \"Client Connected successfully.\"\
  Else\
  Print \"Server failed to connect.\"\
  WaitKey\
  End\
  End If\
  \
  ; write stream to server\
  WriteString strmGame,\"Mission Control, this is Apollo X \...\"\
  Print \"Completed sending message to Mission control\...\"\
  \
  ; \-\-- End second code set \-\--\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=AcceptTCPStream&ref=comments){target="_blank"}
to view the latest version of this page online
