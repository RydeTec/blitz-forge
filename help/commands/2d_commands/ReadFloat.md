# ReadFloat (filehandle/stream)

# Parameters

  --------------------------------------------------------------------------------------------------------------------------------------------------------
  filehandle/stream = a valid variable set with the OpenFile, ReadFile command, or OpenTCPStream (v1.52+) The value returned is a floating point number.
  --------------------------------------------------------------------------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Once you\'ve opened a disk file (or stream) for reading, use this
  command to read a single floating point number from the file. Note,
  each value written uses 4 bytes of space. Reading beyond the end of
  file does not result in an error, but each value read will be zero.\
  \
  Streams can only be used in Blitz Basic v1.52 or greater.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ReadFloat.bb)

  -----------------------------------------------------------------------
  ; Reading and writing a file using ReadFloat and WriteFloat functions\
  \
  ; Initialise some variables for the example\
  Num1# = 10.5 ; store 10.5\
  Num2# = 365.25 ; store 365.25\
  Num3# = 32767.123 ; 32767.123 is the largest positive Short Integer
  Value in BlitzBasic )\
  Num4# = -32768.123 ; -32768.123 the largest negative Short Integer
  Value in BlitzBasic )\
  \
  ; Open a file to write to\
  fileout = WriteFile(\"mydata.dat\")\
  \
  ; Write the information to the file\
  WriteFloat( fileout, Num1 )\
  WriteFloat( fileout, Num2 )\
  WriteFloat( fileout, Num3 )\
  WriteFloat( fileout, Num4 )\
  \
  ; Close the file\
  CloseFile( fileout )\
  \
  ; Open the file to Read\
  filein = ReadFile(\"mydata.dat\")\
  \
  Read1# = ReadFloat( filein )\
  Read2# = ReadFloat( filein )\
  Read3# = ReadFloat( filein )\
  Read4# = ReadFloat( filein )\
  \
  ; Close the file once reading is finished\
  CloseFile( filein )\
  \
  Print \"Floating Point Data Read From File - mydata.dat \"\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ReadFloat&ref=comments){target="_blank"}
to view the latest version of this page online
