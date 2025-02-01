# ChannelPan channel_handle, pan#

# Parameters

  -----------------------------------------------------------------------
  channel_handle = variable assigned to the channel when played\
  pan# = panning floating value to denote channel playback

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  When you want to do real sound panning effects, this is the command
  you\'ll use. This will allow you to pan the sound channels on a \'per
  channel\' basis between the left and right speakers. This command makes
  it very easy to produce some really killer stereophonic effects!\
  \
  The pan value is between -1 and 1 with 0 being perfect center. -1 is
  full left, and 1 is full right. To make it somewhere in between, try
  -.5 for 50% left or .75 for 75% right.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ChannelPan.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ChannelPan&ref=comments){target="_blank"}
to view the latest version of this page online
