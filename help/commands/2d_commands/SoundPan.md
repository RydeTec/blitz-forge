# SoundPan sound_variable,pan#

# Parameters

  -----------------------------------------------------------------------
  sound_variable = any valid sound variable previously created with the
  LoadSound command.\
  pan# = floating point number from -1 (left) to 0 (center) to 1 (right)

  -----------------------------------------------------------------------

# Description

  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Use this command to pan your sound effect between the left and right speakers (or restore the panning to the center). Use this for cool panning stereo sounds during your game.
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/SoundPan.bb)

  -----------------------------------------------------------------------
  ; Load sound sample\
  sndDeath=LoadSound(\"audiodeath.wav\")\
  \
  ; Pan sound effect half to the left\
  SoundPan sndDeath,-.5\
  \
  ; Play sound\
  PlaySound sndDeath\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SoundPan&ref=comments){target="_blank"}
to view the latest version of this page online
