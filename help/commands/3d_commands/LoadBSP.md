# LoadBSP( file\$\[,gamma_adjust#\]\[,parent\] )

# Parameters

  -----------------------------------------------------------------------
  file\$ - filename of BSP model\
  gamma_adjust# (optional) - intensity of BSP lightmaps. Values should be
  in the range 0-1. Defaults to 0.\
  parent (optional) - parent entity of BSP

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Loads a BSP model and returns its handle.\
  \
  Some points about Blitz3D\'s BSP support:\
  \
  \* Shaders are \*not\* supported!\
  \
  \* BSP\'s cannot be painted, textured, colored, faded etc. in Blitz.\
  \
  \* A BSP model is just an entity. Use the standard entity commands to
  scale, rotate and position the BSP, and the standard collision commands
  to setup collisions with the BSP.\
  \
  \* BSP models are not lit by either \'normal\' AmbientLight, or by any
  directional lighting. This allows you to setup lighting for in-game
  models without affecting the BSP lighting. However, BSP models \*will\*
  be lit by point or spot lights.\
  \
  \* Textures for the BSP model must be in the same directory as the BSP
  file itself.\
  \
  The optional parent parameter allows you to specify a parent entity for
  the BSP so that when the parent is moved the child BSP will move with
  it. However, this relationship is one way; applying movement commands
  to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the BSP being created
  at position 0,0,0 rather than at the parent entity\'s position.\
  \
  See also: [BSPAmbientLight](BSPAmbientLight.htm){.small},
  [BSPLighting](BSPLighting.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/LoadBSP.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadBSP&ref=comments){target="_blank"}
to view the latest version of this page online
