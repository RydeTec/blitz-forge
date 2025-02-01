# SetGamma red,green,blue,dest_red#,dest_green#,dest_blue#

# Parameters

  -----------------------------------------------------------------------
  red,green,blue - specifies the source red,green,blue components.\
  dest_red#,dest_green#,dest_blue - specifies the destination
  red,green,blue components.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  SetGamma allows you to modify the gamma tables.\
  \
  Gamma can ONLY be used in fullscreen mode.\
  \
  After performing one or more SetGamma commands, you must call
  [UpdateGamma](../2d_commands/UpdateGamma.htm){.small} in order for the
  changes to become effective.\
  \
  Note that the behaviour of this command in Blitz3D is different to
  BlitzPlus. Blitz3D will clamp out of range values, so that 300 is
  treated as 255. BlitzPlus allows values to \"roll-over\", so that 300
  is treated as 44.\
  \
  See also: [UpdateGamma](UpdateGamma.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/SetGamma.bb)

  -----------------------------------------------------------------------
  ;gamma demo - use left/right arrows to\
  ;control display intensity\
  ;\
  ;hold down left-control for a \'red-out\' effect!\
  \
  Graphics 640,480,16,1\
  SetBuffer BackBuffer()\
  \
  n=0\
  \
  While Not KeyHit(1)\
  \
  If KeyDown(203) And n\>0 Then n=n-1\
  If KeyDown(205) And n\<255 Then n=n+1\
  \
  If KeyDown(29)\
  SetGammaRed(n)\
  Else\
  SetGammaIntensity(n)\
  EndIf\
  Cls\
  SeedRnd 1234\
  For k=1 To 1000\
  Color Rnd(255),Rnd(255),Rnd(255)\
  Rect Rnd(640),Rnd(480),Rnd(64),Rnd(64)\
  Next\
  \
  Text 0,0,\"Intensity offset=\"+n\
  Flip\
  Wend\
  End ; bye!\
  \
  Function SetGammaRed( n )\
  For k=0 To 255\
  SetGamma k,k,k,k+n,0,0\
  Next\
  UpdateGamma\
  End Function\
  \
  Function SetGammaIntensity( n )\
  For k=0 To 255\
  SetGamma k,k,k,k+n,k+n,k+n\
  Next\
  UpdateGamma\
  End Function

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SetGamma&ref=comments){target="_blank"}
to view the latest version of this page online
