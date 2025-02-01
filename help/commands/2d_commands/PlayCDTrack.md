# PlayCDTrack( track,\[mode\] )

# Parameters

  -----------------------------------------------------------------------
  track = track number to play\
  mode = 1; play track once, 2; loop track, 3; play track to end of CD

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Plays a CD track and returns a sound channel.\
  \
  The optional mode parameter allows variations of playback. Remember,
  the playback happens through the CD cable inside the computer that
  attaches to the sound card. Many computers (for some reason), don\'t
  have this cable inside properly attached. If this is the case, you will
  NOT hear CD sound even though you hear other sound effects and music.

  -----------------------------------------------------------------------

# [Example](../2d_examples/PlayCDTrack.bb)

  -----------------------------------------------------------------------
  ; PlayCDTrack example\
  \
  ; Get a track to play from user\
  track=Input\$(\"Enter a CD track number to play:\")\
  \
  ; Play the track, assign a channel - just play once\
  chnCD=PlayCDTrack(track,1)\
  \
  ; Figure out what time it is now\
  oldTime=MilliSecs()\
  ; Play until the channel is over or ESC\
  While ChannelPlaying(chnCD) And (Not KeyHit(1))\
  ; clear and print the time elapsed\
  Cls\
  Locate 0,0\
  Print \"Time Elapsed (sec):\" + ((MilliSecs()-oldTime)/1000)\
  Wend\
  \
  ; Stop the channel\
  StopChannel chnCD

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=PlayCDTrack&ref=comments){target="_blank"}
to view the latest version of this page online
