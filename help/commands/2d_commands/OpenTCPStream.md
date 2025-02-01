# OpenTCPStream (ip\$,port)

# Parameters

  -----------------------------------------------------------------------
  ip\$=Address of stream\
  port=TCP/IP Port Number

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Use this command to open up a TCP/IP stream to the designated server
  and port. If the open command was successful, the command returns a
  stream handle. Otherwise it returns 0.\
  \
  You can use this for a multitude of different \'internet\' options.
  Obviously to contact a TCP/IP host outside your own network, you\'ll
  need to be connected to the Internet.\
  \
  The IP address can be in the form of 1.2.3.4 or \"www.domain.com\".

  -----------------------------------------------------------------------

# [Example](../2d_examples/OpenTCPStream.bb)

  -----------------------------------------------------------------------
  ; OpenTCPStream/CloseTCPStream Example\
  \
  Print \"Connecting\...\"\
  tcp=OpenTCPStream( \"www.blitzbasement.com\",80 )\
  \
  If Not tcp Print \"Failed.\":WaitKey:End\
  \
  Print \"Connected! Sending request\...\"\
  \
  WriteLine tcp,\"GET http://www.blitzbasement.com HTTP/1.0\"\
  WriteLine tcp,Chr\$(10)\
  \
  If Eof(tcp) Print \"Failed.\":WaitKey:End\
  \
  Print \"Request sent! Waiting for reply\...\"\
  \
  While Not Eof(tcp)\
  Print ReadLine\$( tcp )\
  Wend\
  \
  If Eof(tcp)=1 Then Print \"Success!\" Else Print \"Error!\"\
  \
  CloseTCPStream tcp\
  \
  WaitKey\
  End\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=OpenTCPStream&ref=comments){target="_blank"}
to view the latest version of this page online
