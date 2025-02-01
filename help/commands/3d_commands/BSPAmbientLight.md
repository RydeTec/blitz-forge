# BSPAmbientLight bsp, red#, green#, blue#

# Parameters

  -----------------------------------------------------------------------
  bsp - BSP handle\
  red# - red BSP ambient light value\
  green# - green BSP ambient light value\
  blue# - blue BSP ambient light value\
  \
  The red, green and blue values should be in the range 0-255. The
  default BSP ambient light colour is 0,0,0.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the ambient lighting colour for a BSP model.\
  \
  Note that BSP models do not use the normal AmbientLight setting. This
  can also be used to increase the brightness of a BSP model, but the
  effect is not as \'nice\' as using the gamma_adjust parameter of
  LoadBSP.\
  \
  See also: [LoadBSP](LoadBSP.htm){.small},
  [BSPLighting](BSPLighting.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/BSPAmbientLight.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480\
  \
  campiv = CreatePivot()\
  cam = CreateCamera(campiv)\
  CameraRange cam, 0.1,2000\
  \
  level=LoadBSP( \"nyk3dm1\\nyk3dm1.bsp\",.8 ) ; load a \'legal\' quake3
  bsp map\
  BSPAmbientLight level, 0,255,0 ; make the ambient light green\
  ;BSPLighting level, False ; uncomment this line to turn lightmap off\
  \
  While Not KeyDown(1) ; if ESCAPE pressed then exit\
  RenderWorld:Flip\
  \
  mys = MouseYSpeed()\
  If Abs(EntityPitch(cam)+mys) \< 75 ; restrict pitch of camera\
  TurnEntity cam, mys,0,0\
  EndIf\
  TurnEntity campiv,0,-MouseXSpeed(),0\
  \
  If MouseDown(1) Then ; press mousebutton to move forward\
  TFormVector 0,0,3,cam,campiv\
  MoveEntity campiv,TFormedX(),TFormedY(),TFormedZ()\
  EndIf\
  \
  MoveMouse 320,240 ; centre mouse cursor\
  Wend\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=BSPAmbientLight&ref=comments){target="_blank"}
to view the latest version of this page online
