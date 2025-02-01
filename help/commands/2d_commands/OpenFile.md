# OpenFile (filename\$)

# Parameters

  -------------------------------------------------------------------------------------------------------------------------------
  filename\$ = any valid path and filename. The returned value is the filehandle which is used by other file handling commands.
  -------------------------------------------------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command opens the designated file and prepares it to be updated.
  The file must exists since this function will not create a new file.\
  \
  By using FilePos and SeekFile the position within the file that is
  being read or written can be determined and also changed. This allows a
  file to be read and updated without having to make a new copy of the
  file or working through the whole file sequentially. This could be
  useful if you have created a database file and you want to find and
  update just a few records within it.\
  \
  The file handle that is returned is an integer value that the operating
  system uses to identify which file is to be read and written to and
  must be passed to the functions such as ReadInt() and WriteInt().\
  \
  Note extreme care needs to be exercised when updating files that
  contain strings since these are not fixed in length.\
  \
  See also: [ReadFile](ReadFile.htm){.small},
  [WriteFile](WriteFile.htm){.small}, [CloseFile](CloseFile.htm){.small},
  [SeekFile](SeekFile.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/OpenFile.bb)

  -----------------------------------------------------------------------
  ; Changing part of a file using OpenFile, SeekFile and WriteInt\
  \
  ; Open/create a file to Write\
  fileout = WriteFile(\"mydata.dat\")\
  \
  ; Write the information to the file\
  WriteInt( fileout, 1 )\
  WriteInt( fileout, 2 )\
  WriteInt( fileout, 3 )\
  WriteInt( fileout, 4 )\
  WriteInt( fileout, 5 )\
  \
  ; Close the file\
  CloseFile( fileout )\
  \
  DisplayFile( \"The file as originally written\", mydata.dat\" )\
  ; Open the file and change the Third Integer\
  \
  file = OpenFile(\"mydata.dat\")\
  SeekFile( file, 8 ) ; Move to the third integer in the file\
  WriteInt( file, 9999 ) ; Replace the original value with 9999\
  CloseFile( file )\
  \
  DisplayFile( \"The file after being midified\", \"mydata.dat\" )\
  WaitKey()\
  ; \*\*\*\* Function Definitions follow \*\*\*\*\
  \
  ; Read the file and print it\
  Function DisplayFile( Tittle\$, Filename\$ )\
  Print tittle\$\
  filein = ReadFile( Filename\$ )\
  While Not Eof( filein )\
  Number = ReadInt( filein )\
  Print Number\
  Wend\
  CloseFile( filein )\
  Print\
  End Function

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=OpenFile&ref=comments){target="_blank"}
to view the latest version of this page online
