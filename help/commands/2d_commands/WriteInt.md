# WriteInt (filehandle/stream, myinteger)

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
  command to write a single integer value to the file. Note, each value
  written uses 4 bytes and is written least significant byte first. The
  range of the value saved is -2147483648 to 2147483647\
  \
  Streams can only be used in Blitz Basic v1.52 or greater.

  -----------------------------------------------------------------------

# [Example](../2d_examples/WriteInt.bb)

  -----------------------------------------------------------------------
  ; Reading and writing a file using ReadInt and WriteInt functions\
  \
  ; Initialise some variables for the example\
  Int1% = 10 ; store 10\
  Int2% = 365 ; store 365\
  Int3% = 2147483647; store 2147483647 the largest positive Integer Value
  in BlitzBasic )\
  Int4% = - 2147483648 ; store -2147483648 the largest negative Integer
  Value in BlitzBasic )\
  \
  ; Open a file to write to\
  fileout = WriteFile(\"mydata.dat\")\
  \
  ; Write the information to the file\
  WriteInt( fileout, Int1 )\
  WriteInt( fileout, Int2 )\
  WriteInt( fileout, Int3 )\
  WriteInt( fileout, Int4 )\
  \
  ; Close the file\
  CloseFile( fileout )\
  \
  ; Open the file to Read\
  filein = ReadFile(\"mydata.dat\")\
  \
  Read1 = ReadInt( filein )\
  Read2 = ReadInt( filein )\
  Read3 = ReadInt( filein )\
  Read4 = ReadInt( filein )\
  \
  ; Close the file once reading is finished\
  CloseFile( filein )\
  \
  Print \"Integer Data Read From File - mydata.dat \"\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=WriteInt&ref=comments){target="_blank"}
to view the latest version of this page online
