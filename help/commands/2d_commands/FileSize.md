# FileSize (filename\$)

# Parameters

  ----------------------------------------------------
  filename\$ = any valid variable with path/filename
  ----------------------------------------------------

# Description

  -------------------------------------------------------------------------------------------------------------------------------
  Often it will be useful to return the size of a file. File size is important for copying, reading, and other file evolutions.
  -------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/FileSize.bb)

  -----------------------------------------------------------------------
  ; Windows 2000/XP users will need to change location of calc.exe\
  \
  filename\$=\"c:\\windows\\calc.exe\"\
  \
  Print \"The size of the file is: \" + FileSize(filename\$)\
  \
  Print \"Press any key to quit.\"\
  \
  WaitKey()

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=FileSize&ref=comments){target="_blank"}
to view the latest version of this page online
