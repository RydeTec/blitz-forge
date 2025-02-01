# WriteShort (filehandle/stream, myinteger)

# Parameters

  -----------------------------------------------------------------------
  filehandle/stream = a valid variable set with the OpenFile, WriteFile
  command, or OpenTCPStream (v1.52+)\
  myinteger = an integer variable (a floating point number can be used
  but this will be converted to an integer before saving so only the
  integer part will be saved)

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Once you\'ve opened a disk file (or stream) for writing, use this
  command to write a single short integer (16 bit) value to the file.
  Note, each value written uses 2 bytes and is written least significant
  byte first. The range of the value saved is 0-65535\
  \
  Streams can only be used in Blitz Basic v1.52 or greater.

  -----------------------------------------------------------------------

# [Example](../2d_examples/WriteShort.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=WriteShort&ref=comments){target="_blank"}
to view the latest version of this page online
