# CreateDir path/name

# Parameters

  --------------------------------------------------
  path/name = full path and name for new directory
  --------------------------------------------------

# Description

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Creates a directory (file folder) at the destination specified. Do not use a trailing slash at the end of the path/name parameter. You cannot be sure the directory was created with this command, so you will need to verify its existance yourself (use the FILETYPE command).
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/CreateDir.bb)

  -----------------------------------------------------------------------
  ; CREATEDIR example\
  \
  fldr\$=\"c:winntsystem32myfolder\"\
  createDir fldr\$\
  Print \"Folder created!\"

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateDir&ref=comments){target="_blank"}
to view the latest version of this page online
