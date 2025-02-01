# TCPTimeouts read_millis,accept_millis

# Parameters

  -----------------------------------------------------------------------
  read_millis - milliseconds value\
  accept_millis - milliseconds value

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  read_millis allows you to control how long reading data into a TCP
  stream can take before causing an error. By default, this is set to
  10,000 (10 seconds). This means that if data takes longer than 10
  seconds to arrive, an error occurs and the stream can not be used any
  more.\
  \
  accept_millis allows you to control how the AcceptTCPStream() function
  will wait for a new connection. By default, this value is 0, so
  AcceptTCPStream() will return immediately if there is no new connection
  available.

  -----------------------------------------------------------------------

# [Example](../2d_examples/TCPTimeouts.bb)

  -----------------------------------------------------------------------
  None.\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TCPTimeouts&ref=comments){target="_blank"}
to view the latest version of this page online
