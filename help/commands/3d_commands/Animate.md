# Animate entity\[,mode\]\[,speed#\]\[,sequence\]\[,transition#\]

# Parameters

  -----------------------------------------------------------------------
  entity - entity handle\
  \
  mode (optional) - mode of animation.\
  0: stop animation\
  1: loop animation (default)\
  2: ping-pong animation\
  3: one-shot animation\
  \
  speed# (optional) - speed of animation. Defaults to 1.\
  sequence (optional) - specifies which sequence of animation frames to
  play. Defaults to 0.\
  transition# (optional) - used to tween between an entities current
  position rotation and the first frame of animation. Defaults to 0.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Animates an entity.\
  \
  More info about the optional parameters:\
  \
  speed# - a negative speed will play the animation backwards.\
  \
  sequence - Initially, an entity loaded with LoadAnimMesh will have a
  single animation sequence. More sequences can be added using either
  LoadAnimSeq or AddAnimSeq. Animation sequences are numbered
  0,1,2\...etc.\
  \
  transition# - A value of 0 will cause an instant \'leap\' to the first
  frame, while values greater than 0 will cause a smooth transition.

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Animate&ref=comments){target="_blank"}
to view the latest version of this page online
