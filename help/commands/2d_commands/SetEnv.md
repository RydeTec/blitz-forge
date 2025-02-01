# SetEnv env_var\$,value\$

# Parameters

  -----------------------------------------------------------------------
  env_var\$ - the name of the environment variable\
  value\$ - the value of the environment variable

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets an environment variable.\
  \
  However, it should be noted that the command does not set a Windows
  environment variable. It only sets an environment variable which is
  local to a single Blitz program - i.e. from the moment the SetEnv
  command is used, it will only exist for the duration of time the source
  code file is open in the Blitz IDE, or the program EXE is running.\
  \
  See also: GetEnv

  -----------------------------------------------------------------------

# [Example](../2d_examples/SetEnv.bb)

  -----------------------------------------------------------------------
  ; SetEnv Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  SetEnv \"mypath\",\"c:\\program files\\my game\\\"\
  \
  Print GetEnv\$(\"mypath\")\
  \
  WaitKey()

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SetEnv&ref=comments){target="_blank"}
to view the latest version of this page online
