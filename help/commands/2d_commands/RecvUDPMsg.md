# RecvUDPMsg( udp_stream )

# Parameters

  --------------------------------
  udp_stream - UDP stream handle
  --------------------------------

# Description

  -----------------------------------------------------------------------
  Receives a UDP message into the specified UDP stream. Standard stream
  read commands can then be used to examine the message.\
  \
  The return value is the integer IP address of the message source, or 0
  if no message was available. You can use UDPMsgPort() to find out the
  port number of the message source.

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=RecvUDPMsg&ref=comments){target="_blank"}
to view the latest version of this page online
