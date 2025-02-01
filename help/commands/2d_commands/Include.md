# Include filename\$

# Parameters

  ---------------------------------------------------------------
  filename\$ - name of .bb file to be \'Include(d)\' in quotes.
  ---------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Includes the contents of the specified .bb file in the current source
  code file.\
  \
  Useful for when you\'re code gets too big, or you just want to organise
  functions that you\'ve finalised and functions that you\'re working
  on.\
  \
  The Include command effectively \"cuts and pastes\" the contents of the
  .bb file to be included into the current file at the point of the
  Include function call, temporarily, before being passed to the compiler
  to Execute.\
  \
  Note that each .bb file can only be included once.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Include.bb)

  -----------------------------------------------------------------------
  ; Include Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  ;include finished code files\
  Include \"include_globals.bb\"\
  Include \"include_menu.bb\"\
  Include \"include_game.bb\"\
  Include \"include_music.bb\"\
  Include \"include_sound.bb\"\
  Include \"include_ai.bb\"\
  \
  ;Main program loop here\
  ;End program

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Include&ref=comments){target="_blank"}
to view the latest version of this page online
