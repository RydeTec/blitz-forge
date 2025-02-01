# WriteString (filehandle/stream, mystring\$)

# Parameters

  -----------------------------------------------------------------------
  filehandle/stream = a valid variable set with the OpenFile, WriteFile
  command, or OpenTCPStream (v1.52+)\
  mystring\$ = any string variable or text contained between quotes.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Once you\'ve opened a disk file (or stream) for writing, use this
  command to write a string variable to the file.\
  \
  Each string is stored in the file as a 4 byte (32bit) integer followed
  by the characters that form the string. The integer contains the number
  of characters in the string, i.e. its length. Note, that Carriage
  Return, Line Feed and Null characters are NOT use to indicate the end
  of the string. A file of strings cannot be read like a text file, since
  it contains string variables and not text. A null string, i.e. a string
  of zero length (\"\") is stored as 4 bytes, an integer count with a
  value = zero, followed by no Characters. Note strings are not limited
  to 255 characters as in some languages. Reading beyond the end of file
  does not result in an error, but each value read will be a zero length
  string.\
  \
  Streams can only be used in Blitz Basic v1.52 or greater.

  -----------------------------------------------------------------------

# [Example](../2d_examples/WriteString.bb)

  -----------------------------------------------------------------------
  ; Reading and writing a file using ReadString\$ and WriteString
  functions\
  \
  ; Initialise some variables for the example\
  String1\$ = \"A short string\"\
  String2\$ = \"A longer string since these are variables lengths\"\
  String3\$ = \"This is string3 \"\
  String4\$ = \"joined to string4\"\
  \
  ; Open a file to write to\
  fileout = WriteFile(\"mydata.dat\")\
  \
  ; Write the information to the file\
  WriteString( fileout, String1 )\
  WriteString( fileout, String2 )\
  WriteString( fileout, String3 + String4)\
  WriteString( fileout, \"Just to show you don\'t have to use variables\"
  )\
  \
  ; Close the file\
  CloseFile( fileout )\
  \
  ; Open the file to Read\
  filein = ReadFile(\"mydata.dat\")\
  \
  Read1\$ = ReadString\$( filein )\
  Read2\$ = ReadString\$( filein )\
  Read3\$ = ReadString\$( filein )\
  Read4\$ = ReadString\$( filein )\
  \
  ; Close the file once reading is finished\
  CloseFile( filein )\
  \
  Print \"String Variables Read From File - mydata.dat \"\
  Print\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=WriteString&ref=comments){target="_blank"}
to view the latest version of this page online
