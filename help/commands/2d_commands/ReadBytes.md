# ReadBytes bank,file/stream,offset,count

# Parameters

  -----------------------------------------------------------------------
  bank = variable containing handle to valid bank\
  file/stream = file handle of previously opened file or stream\
  offset = offset in bytes to write the value\
  count = how many bytes to write from the offset

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  You can read the contents of a disk file (or stream) to a memory bank
  using this command.\
  \
  Note: The file handle must be opened with OpenFile or OpenTCPStream and
  subsequently closed with CloseFile or CloseTCPStream after the reading
  operations are complete.\
  \
  Return how many bytes successfully read from a stream.\
  \
  Streams can only be used in Blitz Basic v1.52 or greater.\
  \
  See also: [WriteBytes](WriteBytes.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ReadBytes.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ReadBytes&ref=comments){target="_blank"}
to view the latest version of this page online
