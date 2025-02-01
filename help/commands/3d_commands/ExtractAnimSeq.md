# ExtractAnimSeq( entity,first_frame,last_frame\[,anim_seq\] )

# Parameters

  -----------------------------------------------------------------------
  entity - entity handle\
  first_frame - first frame of anim sequence to extract\
  last_frame - last frame of anim sequence to extract\
  anim_seq (optional) - anim sequence to extract from. This is usually 0,
  and as such defaults to 0.

  -----------------------------------------------------------------------

# Description

  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This command allows you to convert an animation with an MD2-style series of anim sequences into a pure Blitz anim sequence, and play it back as such using Animate.
  ---------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../3d_examples/ExtractAnimSeq.bb)

  -----------------------------------------------------------------------
  mesh=LoadAnimMesh( base_mesh\$ ) ;anim sequence 0.\
  ExtractAnimSeq( mesh,0,30 ) ;anim sequence 1: frames 0\...30 are
  \'run\'\
  ExtractAnimSeq( mesh,31,40 ) ;anim sequence 2: frames 31\...40 are
  \'jump\' etc etc\...\
  \
  Animate mesh,3,1,2 ;play one-shot jump anim

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ExtractAnimSeq&ref=comments){target="_blank"}
to view the latest version of this page online
