# ReadFile (filename\$)

# Parameters

  -----------------------------------------------------------------------------------------------------------
  filename\$ = any valid path and filename. The returned value is the filehandle which is an integer value.
  -----------------------------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command opens the designated filename and prepares it to be read from. Use this to read back your own configuration file, save game data, etc. also useful for reading custom types from a files. The filehandle that is returned is an integer value that the operating system uses to identify which file is to be read from and must be passed to the functions such as ReadInt(). If the file could not be opened, for instance, if it does not exists, then the filehandle is Zero.
  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/ReadFile.bb)

  -----------------------------------------------------------------------
  ; Reading and writing custom types to files using ReadFile, WriteFile
  and CloseFile\
  \
  ; Initialise some variables for the example\
  Type HighScore\
  Field Name\$\
  Field Score%\
  Field Level%\
  End Type\
  \
  Best.HighScore = New HighScore\
  BestName = \"Mark\"\
  BestScore = 11657\
  BestLevel = 34\
  \
  ; Open a file to write to\
  fileout = WriteFile(\"mydata.dat\")\
  \
  ; Write the information to the file\
  WriteString( fileout, BestName )\
  WriteInt( fileout, BestScore )\
  WriteByte( fileout, BestLevel )\
  \
  ; Close the file\
  CloseFile( fileout )\
  \
  ; Open the file to Read\
  filein = ReadFile(\"mydata.dat\")\
  \
  ; Lets read the Greatest score from the file\
  Greatest.HighScore = New HighScore\
  GreatestName\$ = ReadString\$( filein )\
  GreatestScore = ReadInt( filein )\
  GreatestLevel = ReadByte( filein )\
  \
  ; Close the file once reading is finished\
  CloseFile( filein )\
  \
  Print \"High score record read from - mydata.dat \"\
  Print\
  Write \"Name = \"\
  Print GreatestName\
  Write \"Score = \"\
  Print GreatestScore\
  Write \"Level = \"\
  Print GreatestLevel\
  \
  WaitKey()

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ReadFile&ref=comments){target="_blank"}
to view the latest version of this page online
