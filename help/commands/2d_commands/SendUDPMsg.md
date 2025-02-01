# SendUDPMsg udp_stream,dest_ip\[,dest_port\]

# Parameters

  -----------------------------------------------------------------------
  udp_stream - UDP stream handle\
  dest_ip - destination IP address\
  dest_port (optional) - destination port number

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Transmits all the data written to the UDP stream to the specified IP
  address and port. Data is written using the standard stream commands.
  If no destination port is specified, the port number used to create the
  UDP Stream is used.\
  \
  Note that IP addresses must be in integer format, NOT in dotted IP
  format.

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SendUDPMsg&ref=comments){target="_blank"}
to view the latest version of this page online
