# SoundPitch sound_variable, hertz

# Parameters

  -----------------------------------------------------------------------
  sound_variable = any valid sound variable previously created with the
  LoadSound command.\
  \
  hertz = valid playback hertz speed (up to 44000 hertz).

  -----------------------------------------------------------------------

# Description

  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Alters the pitch of a sound previously loaded with the LoadSound command. By changing the pitch, you can often reuse sounds for different uses or to simulate a \'counting up/down\' sound. To make the sound \'higher pitched\', increase the hertz. Conversely, decreasing the hertz will \'lower\' the pitch. Note: this is in relation to the original hertz frequency of the sound.
  ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/SoundPitch.bb)

  -----------------------------------------------------------------------
  ; Load the sound (11,000 hertz)\
  \
  snd1Up = LoadSound(\"audiooneup.wav\")\
  \
  ; Play the sound normally\
  PlaySound snd1Up\
  \
  ; Change the pitch UP and play it\
  SoundPitch snd1Up, 11000\*2 ;twice original frequency\
  PlaySound snd1Up\
  \
  ; Change the pitch down and play it\
  SoundPitch snd1Up, 11000/2 ;1/2 original frequency\
  PlaySound snd1Up

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SoundPitch&ref=comments){target="_blank"}
to view the latest version of this page online
