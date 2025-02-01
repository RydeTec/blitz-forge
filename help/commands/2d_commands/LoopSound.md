# LoopSound sound_variable

# Parameters

  -------------------------------------------------------------------------
  sound_variable = variable previously assigned with a LoadSound command.
  -------------------------------------------------------------------------

# Description

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command sets up play back a sound file (.WAV or .MP3) in an endless loop (like for background music). You must load a variable with a sound file using the LoadSound command. Use a Global variable to ensure your sound loop can be played from anywhere in your program. Note: This command doesn\'t actually PLAY the sound loop, just sets it up for looping. You still need to execute the PlaySound command to hear the sound.
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/LoopSound.bb)

  -----------------------------------------------------------------------
  ; Assign a global variable for the sound loop\
  Global sndMusicLoop\
  \
  ; Load the sound loop file into memory\
  \
  sndMusicLoop=LoadSound(\"sounds/loop1.wav\")\
  \
  ; Set the sound loop\
  \
  LoopSound sndMusicLoop\
  \
  PlaySound sndMusicLoop\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoopSound&ref=comments){target="_blank"}
to view the latest version of this page online
