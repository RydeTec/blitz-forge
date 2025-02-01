# PlaySound ( sound_variable )

# Parameters

  -------------------------------------------------------------------------
  sound_variable = variable previously assigned with a LoadSound command.
  -------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This plays a sound previously loaded and assigned to a variable using
  the LoadSound command. See example.\
  \
  You will need to assign a channel variable handle to the sound when you
  play it. All subsequent sound handling commands require you use the
  CHANNEL variable, not the sound variable to control the sound - such as
  StopChannel, PauseChannel, ResumeChannel, ChannelPitch, ChannelVolume,
  ChannelPan, and ChannelPlaying.

  -----------------------------------------------------------------------

# [Example](../2d_examples/PlaySound.bb)

  -----------------------------------------------------------------------
  ; Assign a global variable for the sound\
  Global sndPlayerDie\
  \
  ; Load the sound file into memory\
  \
  sndPlayerDie=LoadSound(\"sounds/die.wav\")\
  \
  ; Play the sound\
  \
  chnDie=PlaySound ( sndPlayerDie )

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=PlaySound&ref=comments){target="_blank"}
to view the latest version of this page online
