# ReadShort (filehandle/stream)

# Parameters

  -----------------------------------------------------------------------
  filehandle/stream = a valid variable set with the OpenFile, ReadFile
  command, or OpenTCPStream (v1.52+)\
  The value returned is an integer in the range 0-65535.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Once you\'ve opened a disk file (or stream) for reading, use this
  command to read a single short integer (16bit) value from the file.
  Note, each value written uses 2 bytes of disk space and is written
  least significant byte first. Reading beyond the end of file does not
  result in an error, but each value read will be zero.\
  \
  Streams can only be used in Blitz Basic v1.52 or greater.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ReadShort.bb)

  -----------------------------------------------------------------------
  ; Reading and writing a file using ReadShort and WriteShort functions\
  \
  ; Initialise some variables for the example\
  Int1% = 10 ; store 10\
  Int2% = 365 ; store 365\
  Int3% = 32767 ; 32767 is the largest positive Short Integer Value in
  BlitzBasic )\
  Int4% = -32768 ; -32768 the largest negative Short Integer Value in
  BlitzBasic )\
  \
  ; Open a file to write to\
  fileout = WriteFile(\"mydata.dat\")\
  \
  ; Write the information to the file\
  WriteShort( fileout, Int1 )\
  WriteShort( fileout, Int2 )\
  WriteShort( fileout, Int3 )\
  WriteShort( fileout, Int4 )\
  \
  ; Close the file\
  CloseFile( fileout )\
  \
  ; Open the file to Read\
  filein = ReadFile(\"mydata.dat\")\
  \
  Read1 = ReadShort( filein )\
  Read2 = ReadShort( filein )\
  Read3 = ReadShort( filein )\
  Read4 = ReadShort( filein )\
  \
  ; Close the file once reading is finished\
  CloseFile( filein )\
  \
  Print \"Short Integer Data Read From File - mydata.dat \"\
  Print Read1\
  Print Read2\
  Print Read3\
  Print Read4\
  \
  WaitKey()\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ReadShort&ref=comments){target="_blank"}
to view the latest version of this page online
