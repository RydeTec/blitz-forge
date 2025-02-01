# ReadLine\$ (filehandle/stream)

# Parameters

  -----------------------------------------------------------------------------------------------------------------------------------------------
  filehandle/stream = a valid variable set with the OpenFile, ReadFile command, or OpenTCPStream (v1.52+). The value returned is a text string.
  -----------------------------------------------------------------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Once you\'ve opened a disk file (or stream) for reading, use this
  command to read a whole line of text from a text file or stream. Each
  line of text is returned as a string variable. This function can be
  used to read plain text files.\
  \
  \
  Characters are read from the input file until an \"end-of-line\" mark
  is found. An \"end-of-line\" can be a single carriage return (0Dh) or a
  single linefeed (0Ah) or carriage return followed by a linefeed (0Dh,
  0Ah). Reading beyond the end of file does not result in an error, but
  each value read will be a zero length string.\
  \
  \
  ReadLine\$ returns all chars except chr\$(13)/chr\$(10).\
  \
  \
  Streams can only be used in Blitz Basic v1.52 or greater.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ReadLine.bb)

  -----------------------------------------------------------------------
  ; Reading and writing a file using ReadLine\$ and WriteLine functions\
  \
  ; Initialise some variables for the example\
  String1\$ = \"Line 1 is short\"\
  String2\$ = \"Line 2 is a longer line but they can be much longer\"\
  String3\$ = \"Line 3 is made up \"\
  String4\$ = \"of two parts joined together.\"\
  \
  ; Open a file to write to\
  fileout = WriteFile(\"mydata.txt\")\
  \
  ; Write the information to the file\
  WriteLine( fileout, String1 )\
  WriteLine( fileout, String2 )\
  WriteLine( fileout, String3 + String4)\
  WriteLine( fileout, \"Just to show you don\'t have to use variables\"
  )\
  \
  ; Close the file\
  CloseFile( fileout )\
  \
  ; Open the file to Read\
  filein = ReadFile(\"mydata.txt\")\
  \
  Read1\$ = ReadLine\$( filein )\
  Read2\$ = ReadLine\$( filein )\
  Read3\$ = ReadLine\$( filein )\
  Read4\$ = ReadLine\$( filein )\
  \
  ; Close the file once reading is finished\
  CloseFile( filein )\
  \
  Print \"Lines of text read from file - mydata.txt \"\
  Print\
  Print Read1\
  Print Read2\
  Print Read3\
  Print Read4\
  \
  WaitKey()

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ReadLine&ref=comments){target="_blank"}
to view the latest version of this page online
