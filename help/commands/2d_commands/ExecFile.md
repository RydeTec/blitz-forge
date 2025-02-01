# ExecFile( file\$ )

# Parameters

  ----------------------------------
  file\$ - the file to be executed
  ----------------------------------

# Description

  -----------------------------------------------------------------------
  The usefulness of this command is really mostly for calling some system
  level command, launching a browser, etc.\
  \
  Note: This command uses ShellExecute to allow you to \'open\' any file
  (like a .doc or .txt) file with its default associated program.\
  \
  Also: if the filename path has spaces in it you may will need to chenge
  the call like so:\
  \
  ExecFile (Chr\$(34)+\"example file name.exe\"+Chr\$(34))\
  \
  This adds the \" (quotation mark) character to the beginning and end of
  the text string.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ExecFile.bb)

  -----------------------------------------------------------------------
  ; ExecFile sample - RUN THIS WINDOWED!\
  Graphics 320,240,0,2\
  \
  filename\$ = Chr\$(34) + \"calc.exe\" + Chr\$(34)\
  \
  Text 5,5, \"press any key to run CALC.EXE!\"\
  Flip\
  WaitKey\
  \
  ExecFile(filename\$)\
  \
  Text 5,25, \"Press any key to quit.\"\
  Flip\
  WaitKey\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ExecFile&ref=comments){target="_blank"}
to view the latest version of this page online
