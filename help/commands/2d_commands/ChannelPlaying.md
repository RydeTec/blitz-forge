# ChannelPlaying (channel_handle)

# Parameters

  ---------------------------------------------------------------
  channel_handle = variable assigned to the channel when played
  ---------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Often you will need to know if a sound channel has completed playing or
  not. This command will return 1 if the sound is still playing or 0 if
  it has stopped. Use this to restart your background music or some other
  sound that might have stopped unintentionally.\
  \
  Note: This command currently doesn\'t seem to work with a channel
  assigned to CD track playback.

  -----------------------------------------------------------------------

# [Example](../2d_examples/ChannelPlaying.bb)

  -----------------------------------------------------------------------
  ; Channel examples\
  \
  Print \"Loading sound \...\"\
  ; Load the sample - you\'ll need to point this to a sound on your
  computer\
  ; For best results, make it about 5-10 seconds\...\
  sndWave=LoadSound(\"level1.wav\")\
  \
  Print \"Playing full sample until sound is done \...\"\
  chnWave=PlaySound(sndWave)\
  While ChannelPlaying(chnWave)\
  Wend\
  Print \"All done!\"\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ChannelPlaying&ref=comments){target="_blank"}
to view the latest version of this page online
