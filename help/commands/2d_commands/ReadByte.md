# ReadByte ( filehandle/stream )

# Parameters

  ---------------------------------------------------------------------------------------------------------
  filehandle/stream = a valid variable set with the OpenFile, ReadFile command, or OpenTCPStream (v1.52+)
  ---------------------------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Once you\'ve opened a disk file (or stream) for reading, use this
  command to read a single byte at a time from the file/stream. Note, a
  byte is an integer that can take the values 0..255 and occupies 8 bits
  of storage. Since characters are stored as byte values this function
  can be used to read a file one character at a time. Reading beyond the
  end of file does not result in an error, but each value read will be
  zero.\
  \
  Advanced notes\
  \
  The number that is stored by WriteByte is actually the least
  significant byte of an integer so negative numbers and numbers above
  255 will still have a value between 0..255. Unless you understand how
  32 bit integers are stored in 2\'s compliment notation this will seem
  strange but it is NOT a bug.\
  \
  Streams can only be used in Blitz Basic v1.52 or greater.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ReadByte.bb)

  -----------------------------------------------------------------------
  ; Reading and writing a file using ReadByte and WriteByte functions\
  \
  ; Initialise some variables for the example\
  Byte1% = 10 ; store 10\
  Byte2% = 100 ; store 100\
  Byte3% = 255 ; store 255 ( the maximum value that can be stored in a
  Byte)\
  Byte4% = 256 ; try to store 256 this will end up as 0 ( i.e. 256 - 256
  = 0 )\
  Byte5% = 257 ; try to store 257 this will end up as 1 ( i.e. 257 - 256
  = 1 )\
  Byte6% = -1 ; try to store -1 this will end up as 255 ( i.e. 256 -1 =
  255 )\
  Byte7% = -2 ; try to store 256 this will end up as 254 ( i.e. 256 - 2 =
  254 )\
  Byte8% = Asc(\"A\") ; Store the ASCII value for the Character \"A\" (
  i.e. 65 )\
  \
  ; Open a file to write to\
  fileout = WriteFile(\"mydata.dat \")\
  \
  ; Write the information to the file\
  WriteByte( fileout, Byte1 )\
  WriteByte( fileout, Byte2 )\
  WriteByte( fileout, Byte3 )\
  WriteByte( fileout, Byte4 )\
  WriteByte( fileout, Byte5 )\
  WriteByte( fileout, Byte6 )\
  WriteByte( fileout, Byte7 )\
  WriteByte( fileout, Byte8 )\
  \
  ; Close the file\
  CloseFile( fileout )\
  \
  ; Open the file to Read\
  filein = ReadFile(\"mydata.dat\")\
  \
  Read1 = ReadByte( filein )\
  Read2 = ReadByte( filein )\
  Read3 = ReadByte( filein )\
  Read4 = ReadByte( filein )\
  Read5 = ReadByte( filein )\
  Read6 = ReadByte( filein )\
  Read7 = ReadByte( filein )\
  Read8 = ReadByte( filein )\
  \
  ; Close the file once reading is finished\
  CloseFile( filein )\
  \
  Print \"Written - Read\"\
  Write Byte1 + \" - \" : Print Read1\
  Write Byte2 + \" - \" : Print Read2\
  Write Byte3 + \" - \" : Print Read3\
  Write Byte4 + \" - \" : Print Read4\
  Write Byte5 + \" - \" : Print Read5\
  Write Byte6 + \" - \" : Print Read6\
  Write Byte7 + \" - \" : Print Read7\
  Write Byte8 + \" - \" : Print Chr\$( Read8 )\
  \
  WaitKey()\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ReadByte&ref=comments){target="_blank"}
to view the latest version of this page online
