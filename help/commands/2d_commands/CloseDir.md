# CloseDir filehandle

# Parameters

  -----------------------------------------------------------------
  filehandle = valid filehandle assigned from the ReadDir command
  -----------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Once you are finished with NextFile\$ on the directory previously
  opened for read with the ReadDir command, use this command to close the
  directory. This is good programming practice!\
  \
  See also: ReadDir, NextFile\$

  -----------------------------------------------------------------------

# [Example](../2d_examples/CloseDir.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CloseDir&ref=comments){target="_blank"}
to view the latest version of this page online
