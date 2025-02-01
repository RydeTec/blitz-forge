# SeekFile (filehandle, offset)

# Parameters

  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  filehandle = the variable returned by the Readfile, WriteFile or OpenFile when the file was opened. The value returned is the offset from the start of the file. ( 0 = Start of the file )
  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command allows the position in a file to be changed. This allows
  random access to data within files and can be used with files opened by
  ReadFile, WriteFile and OpenFile. Note, the offset is the number of
  bytes from the start of the file, where the first byte is at offset 0.
  It is important to take account of the size of the data elements in
  your file.\
  \
  For instance Integers are 4 bytes long so the first integer in the file
  is at offset 0 and the second at offset 4. If you write Custom Data
  types out then you must work out haw many bytes each takes so that you
  can move about the file correctly. Seeking beyond the end of a file
  does not generate an error but the data is not read or written to the
  file, and may course unknown side effects.\
  \
  By using FilePos and SeekFile the position within the file that is
  being read or written can be determined and also changed. This allows a
  file to be read and updated without having to make a new copy of the
  file or working through the whole file sequentially. This could be
  useful if you have created a database file and you want to find and
  update just a few records within it. It is also possible to create an
  index file that contains pointers to where each record starts in a data
  file.\
  \
  To calculate an offset you need to know how long each data element is;
  Offset = Wanted_Element \* size_of_element - size_of_element\
  \
  For example a file of integers which are 4 bytes long is calculated
  by:\
  \
  The 7th integer is at offset 7 \* 4 - 4 i.e. 24\
  \
  Note, extreme care needs to be exercised when updating files that
  contain strings since these are not fixed in length.

  -----------------------------------------------------------------------

# [Example](../2d_examples/SeekFile.bb)

  -----------------------------------------------------------------------
  ; Changing part of a file using OpenFile, SeekFile, FilePos\
  \
  ; Open/create a file to Write\
  fileout = WriteFile(\"mydata.dat\")\
  \
  ; Write the information to the file\
  WriteInt( fileout, 100 )\
  WriteInt( fileout, 200 )\
  WriteInt( fileout, 300 )\
  WriteInt( fileout, 400 )\
  WriteInt( fileout, 500 )\
  \
  ; Close the file\
  CloseFile( fileout )\
  \
  DisplayFile( \"The file as originally written\", mydata.dat\" )\
  \
  Print \"Data read in random order\"\
  ; Open the file to read just the 4th and 2nd elements from\
  \
  file = OpenFile(\"mydata.dat\")\
  \
  ; read and print the 4th integer ie 4\*4-4 = 12 byte from the start of
  the file\
  SeekFile( file, 12 ) ; Move to the found location\
  Number = ReadInt( file )\
  Print Number\
  \
  ; read and print the 2th integer ie 2\*4-4 = 4 bytes from the start of
  the file\
  SeekFile( file, 4 ) ; Move to the found location\
  Number = ReadInt( file )\
  Print Number\
  \
  CloseFile( file )\
  \
  Waitkey()\
  End ; End of program\
  \
  ; \*\*\*\* Function Definitions follow \*\*\*\*\
  ; Read the file and print it\
  Function DisplayFile( Tittle\$, Filename\$ )\
  Print tittle\$\
  file = ReadFile( Filename\$ )\
  While Not Eof( file )\
  Number = ReadInt( file )\
  Print Number\
  Wend\
  CloseFile( file )\
  Print\
  End Function

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SeekFile&ref=comments){target="_blank"}
to view the latest version of this page online
