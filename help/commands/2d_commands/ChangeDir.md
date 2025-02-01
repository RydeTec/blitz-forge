# ChangeDir directory/path

# Parameters

  ------------------------------------------------
  directory/path = full path to directory/folder
  ------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command will change the currently selected directory for disk
  operations, useful for advanced file operations. Use CURRENTDIR\$() to
  see what the current directory is.\
  Use a directory/path of \"..\" to change to the parent of the current
  directory, unless you are at the root directory of the drive, then no
  change happens.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ChangeDir.bb)

  -----------------------------------------------------------------------
  ; ChangeDir example\
  \
  ChangeDir \"c:winntsystem32\"\
  Print \"The folder has been changed to: \" + currentdir\$()

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ChangeDir&ref=comments){target="_blank"}
to view the latest version of this page online
