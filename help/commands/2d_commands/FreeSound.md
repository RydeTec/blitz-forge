# FreeSound sound_variable

# Parameters

  ------------------------------------------------------------------------------------
  sound_variable - and valid variable previously created with the LoadSound command.
  ------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  When you are finished using a sound effect, you should free up the
  memory its using and delete the sound. This command will delete a sound
  instance variable created with the LoadSound command.\
  \
  Why would you want to do this? Perhaps you have different sets of
  sounds for different levels of your game? Perhaps your music loop
  changes from level to level. You want to do the RIGHT thing and manage
  your own resources. Just because you CAN load every sample for the
  whole game at once, consider someone that doesn\'t have as much memory
  as you do. You want to ensure that your game appeals to the widest
  audience possible. Note: You don\'t have to manually free these
  resources when your program terminates - Blitz does this automatically.

  -----------------------------------------------------------------------

# [Example](../2d_examples/FreeSound.bb)

  -----------------------------------------------------------------------
  ; Load a sound into memory\
  sndOneUp=LoadSound(\"audio1up.wav\")\
  \
  ; Free the memory up and delete the sound\
  FreeSound sndOneUp\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=FreeSound&ref=comments){target="_blank"}
to view the latest version of this page online
