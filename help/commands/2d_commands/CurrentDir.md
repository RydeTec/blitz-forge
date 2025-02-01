# CurrentDir\$()

# Parameters

  -------
  None.
  -------

# Description

  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command will return the currently selected directory for disk operations, useful for advanced file operations. Use CHANGEDIR to change the current directory. The value returned doesn\'t have a trailing slash - aside from the root directory of the drive.
  --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/CurrentDir.bb)

  -----------------------------------------------------------------------
  ; CurrentDir\$() example\
  \
  ; Print the current directory until ESC key\
  While Not KeyHit(1)\
  Print CurrentDir\$()\
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CurrentDir&ref=comments){target="_blank"}
to view the latest version of this page online
