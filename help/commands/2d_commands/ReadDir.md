# ReadDir (directory)

# Parameters

  ------------------------------------------------------------
  directory = full path and name of folder/directory to open
  ------------------------------------------------------------

# Description

  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  In file operations, you will often need to parse through a directory/folder and retrieve unknown filenames and other folders from it. This command opens a specified folder to begin these operations. The command returns a file handle which is used by the other commands to perform other services (like most file operators). You will use the NextFile\$ to iterate through each entry (use FILETYPE to see if it is a file or folder). Remember, once completed, good programming practice dictates that you CloseDir the open folder. The example should help out alot.
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/ReadDir.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ReadDir&ref=comments){target="_blank"}
to view the latest version of this page online
