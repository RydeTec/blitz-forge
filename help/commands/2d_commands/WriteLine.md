# WriteLine\$ (filehandle/stream, string\$)

# Parameters

  -----------------------------------------------------------------------
  filehandle/stream = a valid variable set with the OpenFile, WriteFile
  command, or OpenTCPStream (v1.52+). The value returned is a text
  string.\
  string\$ = valid string value.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Once you\'ve opened a disk file (or stream) for writing, use this
  command to right a whole line of text to the file. Each line of text is
  automatically terminated with an \"end-of-line\" mark, consisting of a
  Carriage Return character followed by a LineFeed character. (i.e. 0Dh,
  0Ah ) This function can be used to make plain text files.\
  \
  Streams can only be used in Blitz Basic v1.52 or greater.

  -----------------------------------------------------------------------

# [Example](../2d_examples/WriteLine.bb)

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
  Read1\$ = ReadLine( filein )\
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
  WaitKey()\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=WriteLine&ref=comments){target="_blank"}
to view the latest version of this page online
