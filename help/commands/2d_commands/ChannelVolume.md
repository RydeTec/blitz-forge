# ChannelVolume channel_handle, volume#

# Parameters

  -----------------------------------------------------------------------
  channel_handle = variable assigned to the channel when played\
  volume# = volume level floating value between 0 and 1

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  While SoundVolume happily changes the volume of the entire program,
  this command will let you adjust volume rates on a \'per channel\'
  basis. Extremely useful.\
  \
  The volume value is a floating point value between 0 and 1 (0 =
  silence, .5 = half volume, 1= full volume). You can do other cool stuff
  like ChannelPitch and ChannelPan too!

  -----------------------------------------------------------------------

# [Example](../2d_examples/ChannelVolume.bb)

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
  Print \"Changing volume of sound \...\"\
  ChannelVolume chnWave, .5\
  Delay 2000\
  \
  Print \"Playing new half-volume sound \...\"\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ChannelVolume&ref=comments){target="_blank"}
to view the latest version of this page online
