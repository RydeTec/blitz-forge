# FileType (filename\$)

# Parameters

  ----------------------------------------------------
  filename\$ = any valid variable with path/filename
  ----------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command checks the filename you pass and determines if it exists
  and whether or not it is a valid filename or if it is a directory. Here
  are the values it returns:\
  \
  1 = The filename exists\
  0 = The filename doesn\'t exist\
  2 = The filename is not a file - but a directory\
  \
  Use this to validate that a file exists before you do something to it.

  -----------------------------------------------------------------------

# [Example](../2d_examples/FileType.bb)

  -----------------------------------------------------------------------
  ; Windows 9x users will need to change location of calc.exe\
  \
  filename\$=\"c:winntsystem32calc.exe\"\
  \
  if fileType(filename\$)=1 then Print \"The file exists!\"\
  if fileType(filename\$)=0 then Print \"File not found!\"\
  if fileType(filename\$)=2 then Print \"This is a directory!\"\
  \
  Print \"Press any key to quit.\"\
  \
  WaitKey()

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=FileType&ref=comments){target="_blank"}
to view the latest version of this page online
