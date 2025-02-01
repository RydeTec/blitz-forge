# PauseChannel channel_handle

# Parameters

  ---------------------------------------------------------------
  channel_handle = variable assigned to the channel when played
  ---------------------------------------------------------------

# Description

  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  When you are playing a sound channel, there may come a time you wish to pause the sound for whatever reason (like to play another sound effect). This command does this - and the channel can be resumed with the ResumeChannel command. You can use StopChannel to actually halt the sound. This works with any channel playback (WAV, MP3, MIDI, etc.).
  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/PauseChannel.bb)

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
  StopChannel chnWave\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=PauseChannel&ref=comments){target="_blank"}
to view the latest version of this page online
