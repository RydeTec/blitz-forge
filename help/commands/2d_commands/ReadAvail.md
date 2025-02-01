# ReadAvail (filehandle/streamhandle)

# Parameters

  -----------------------------------------------------------------------------------------
  filehandle/streamhandle = handle assigned to the file or stream when originally opened.
  -----------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------------------------------------------------------------------------------------------
  In the case of file streams, this reflects how much data is internally buffered. In the case of TCP streams, this reflects how much data has \'arrived\'.
  -----------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/ReadAvail.bb)

  -----------------------------------------------------------------------
  ; OpenTCPStream/CloseTCPStream/ReadAvail Example\
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
  Print \"Bytes available:\" + ReadAvail(tcp)\
  Wend\
  \
  If Eof(tcp)=1 Then Print \"Success!\" Else Print \"Error!\"\
  \
  CloseTCPStream tcp\
  \
  WaitKey\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ReadAvail&ref=comments){target="_blank"}
to view the latest version of this page online
