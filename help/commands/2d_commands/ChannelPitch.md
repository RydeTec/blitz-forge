# ChannelPitch channel_handle, hertz

# Parameters

  -----------------------------------------------------------------------
  channel_handle = variable assigned to the channel when played\
  hertz = pitch to apply to the channel (try 8000-44000)

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  You can alter the pitch of a sound channel that is playing (or in use and just paused). I\'m sure you can think of numerous uses for this command! Use the frequency of your sound as the \'baseline\' for pitch change. So if your sample is at 11025 hertz, increase the pitch to 22050 to make the pitch twice as high, 8000 to make it lower, etc. While similar to SoundPitch, this command let\'s you change the pitch individually of each and every channel in use.
  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/ChannelPitch.bb)

  -----------------------------------------------------------------------
  ; Channel examples\
  \
  Print \"Loading sound \...\"\
  ; Load the sample - you\'ll need to point this to a sound on your
  computer\
  ; For best results, make it about 5-10 seconds\...\
  sndWave=LoadSound(\"level1.wav\")\
  ; Prepare the sound for looping\
  LoopSound sndWave\
  \
  chnWave=PlaySound(sndWave)\
  \
  Print \"Playing sound for 2 seconds \...\"\
  Delay 2000\
  \
  Print \"Pausing sound for 2 seconds \...\"\
  PauseChannel chnWave\
  Delay 2000\
  \
  Print \"Restarting sound \...\"\
  ResumeChannel chnWave\
  Delay 2000\
  \
  Print \"Changing Pitch of sound \...\"\
  ;StopChannel chnWave\
  ChannelPitch chnWave, 22000\
  Delay 2000\
  \
  Print \"Playing new pitched sound \...\"\
  Delay 2000\
  \
  Print \"Left speaker only\"\
  ChannelPan chnWave,-1\
  Delay 2000\
  \
  Print \"Right speaker only\"\
  ChannelPan chnWave,1\
  Delay 2000\
  \
  Print \"All done!\"\
  StopChannel chnWave

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ChannelPitch&ref=comments){target="_blank"}
to view the latest version of this page online
