# CloseTCPStream streamhandle

# Parameters

  ------------------------------------------------------------
  streamhandle = handle assigned when the stream was opened.
  ------------------------------------------------------------

# Description

  -----------------------------------------------------------------------------------------------------------------------------
  Once you\'ve completed the use of your TCP/IP stream, close the connection you opened with OpenTCPStream with this command.
  -----------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/CloseTCPStream.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CloseTCPStream&ref=comments){target="_blank"}
to view the latest version of this page online
