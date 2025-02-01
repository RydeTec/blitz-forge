# Eof (filehandle/stream)

# Parameters

  ---------------------------------------------------------------------------------------------------------
  filehandle/stream = a valid variable set with the OpenFile, ReadFile command, or OpenTCPStream (v1.52+)
  ---------------------------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Checks to see if the End of File of an opened file or stream has been
  reached. Use this to determine if you should continue to pull more
  information from a file/stream or not. Use this to read a text file of
  unknown length (say a README.TXT) and display it. See example.\
  \
  Eof returns 1 if eof has been reached or, in the case of a TCP stream,
  the stream has been \'nicely\' closed.\
  \
  Eof returns -1 if something has gone wrong during stream processing.\
  \
  Streams can only be used in Blitz Basic v1.52 or greater.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Eof.bb)

  -----------------------------------------------------------------------
  ; Eof sample\
  \
  file\$=\"c:autoexec.bat\"\
  \
  filein = ReadFile(file\$)\
  \
  Print \"Here is your Autoexec.bat file \...\"\
  \
  ; Loop this until we reach the end of file\
  While Not Eof(filein)\
  Print ReadLine\$(filein)\
  Wend\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Eof&ref=comments){target="_blank"}
to view the latest version of this page online
