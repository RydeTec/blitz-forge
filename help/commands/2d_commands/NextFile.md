# NextFile\$ (filehandle)

# Parameters

  -----------------------------------------------------------------
  filehandle = valid filehandle assigned from the ReadDir command
  -----------------------------------------------------------------

# Description

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command will return the NEXT file or folder from the currently open directory (use ReadDir to open the desired folder for reading). This will return a string containing the folder name or the filename plus extention. Use FILETYPE to determine if it is a file or folder. See ReadDir and CloseDir for more. You cannot move \'backwards\' through a directory, only forward. You might want to parse the contents of a directory into an array for display, processing, etc.
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/NextFile.bb)

  -----------------------------------------------------------------------
  ; ReadDir/NextFile\$/CloseDir example\
  \
  ; Define what folder to start with \...\
  folder\$=\"C:\"\
  \
  ; Open up the directory, and assign the handle to myDir\
  myDir=ReadDir(folder\$)\
  \
  ; Let\'s loop forever until we run out of files/folders to list!\
  Repeat\
  ; Assign the next entry in the folder to file\$\
  file\$=NextFile\$(myDir)\
  \
  ; If there isn\'t another one, let\'s exit this loop\
  If file\$=\"\" Then Exit\
  \
  ; Use FileType to determine if it is a folder (value 2) or a file and
  print results\
  If FileType(folder\$+\"\\\"+file\$) = 2 Then\
  Print \"Folder:\" + file\$\
  Else\
  Print \"File:\" + file\$\
  End If\
  Forever\
  \
  ; Properly close the open folder\
  CloseDir myDir\
  \
  ; We\'re done!\
  Print \"Done listing files!\"

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=NextFile&ref=comments){target="_blank"}
to view the latest version of this page online
