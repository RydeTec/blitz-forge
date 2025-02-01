# WriteBytes bank,filehandle/stream,offset,count

# Parameters

  -----------------------------------------------------------------------
  bank = variable containing handle to valid bank\
  filehandle/stream = a valid variable set with the WriteFile or
  OpenTCPStream (v1.52+)\
  offset = offset in bytes to write the value\
  count = how many bytes to write from the offset

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  You can write the contents of a memory bank to a file on disk (or
  stream) using this command.\
  \
  Note: The file handle must be opened with WriteFile or OpenTCPStream
  and subsequently closed with CloseFile or CloseTCPStream after the
  writing operations are complete.\
  Return how many bytes successfully written to a stream.\
  \
  Streams can only be used in Blitz Basic v1.52 or greater.\
  \
  See also: [ReadBytes](ReadBytes.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/WriteBytes.bb)

  -----------------------------------------------------------------------
  ; Read/WriteBytes Commands Example\
  \
  ; Create a 50 byte memory bank\
  bnkTest=CreateBank(500)\
  \
  ; Let\'s fill the bank with random data\
  For t = 1 To 50\
  \
  PokeByte bnkTest,t,Rnd(255)\
  \
  Next\
  \
  ; Open a file to write to\
  fileBank=WriteFile(\"test.bnk\")\
  ; Write the bank to the file\
  WriteBytes bnkTest,fileBank,0,50\
  ; Close it\
  CloseFile fileBank\
  \
  ; Free the bank\
  FreeBank bnkTest\
  \
  ; Make a new one\
  bnkTest=CreateBank(500)\
  \
  ; Open the file to read from\
  fileBank=OpenFile(\"test.bnk\")\
  ; Write the bank to the file\
  ReadBytes bnkTest,fileBank,0,50\
  ; Close it\
  CloseFile fileBank\
  \
  ; Write back the results!\
  For t = 1 To 50\
  \
  Print PeekByte (bnkTest,t)\
  \
  Next

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=WriteBytes&ref=comments){target="_blank"}
to view the latest version of this page online
