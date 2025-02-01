# BSPLighting bsp, use_lightmaps

# Parameters

  -----------------------------------------------------------------------
  bsp - BSP handle\
  use_lightmaps - True to use lightmaps, False for vertex lighting. The
  default mode is True.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Controls whether BSP models are illuminated using lightmaps, or by
  vertex lighting.\
  \
  Vertex lighting will be faster on some graphics cards, but doesn\'t
  look as good!\
  \
  See also: [LoadBSP](LoadBSP.htm){.small},
  [BSPAmbientLight](BSPAmbientLight.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/BSPLighting.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=BSPLighting&ref=comments){target="_blank"}
to view the latest version of this page online
