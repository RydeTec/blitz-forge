# SpriteViewMode sprite,view_mode

# Parameters

  -----------------------------------------------------------------------
  sprite - sprite handle\
  \
  view_mode - view_mode of sprite\
  1: fixed (sprite always faces camera - default)\
  2: free (sprite is independent of camera)\
  3: upright1 (sprite always faces camera, but rolls with camera as well,
  unlike mode no.1)\
  4: upright2 (sprite always remains upright. Gives a \'billboard\'
  effect. Good for trees, spectators etc.)

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the view mode of a sprite.\
  \
  The view mode determines how a sprite alters its orientation in respect
  to the camera. This allows the sprite to in some instances give the
  impression that it is more than two dimensional.\
  \
  In technical terms, the four sprite modes perform the following
  changes:\
  \
  1: Sprite changes its pitch and yaw values to face camera, but doesn\'t
  roll.\
  2: Sprite does not change either its pitch, yaw or roll values.\
  3: Sprite changes its yaw and pitch to face camera, and changes its
  roll value to match cameras.\
  4: Sprite changes its yaw value to face camera, but not its pitch
  value, and changes its roll value to match cameras.\
  \
  Note that if you use sprite view mode 2, then because it is independent
  from the camera, you will only be able to see it from one side unless
  you use EntityFx flag 16 with it to disable backface culling.\
  \
  See also: [CreateSprite](CreateSprite.htm){.small},
  [LoadSprite](LoadSprite.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/SpriteViewMode.bb)

  -----------------------------------------------------------------------
  ; SpriteViewMode Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  pivot=CreatePivot()\
  PositionEntity pivot,0,1,0\
  \
  camera=CreateCamera(pivot)\
  PositionEntity camera,0,0,10\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  plane=CreatePlane()\
  ground_tex=LoadTexture(\"media/Chorme-2.bmp\")\
  EntityTexture plane,ground_tex\
  \
  sprite=LoadSprite(\"media/b3dlogo.jpg\")\
  PositionEntity sprite,0,1,0\
  \
  pitch=-15\
  yaw=180\
  roll=0\
  view_mode=1\
  view_mode_info\$=\" (fixed)\"\
  \
  While Not KeyDown(1)\
  \
  If KeyDown(208)=True And pitch\<0 Then pitch=pitch+1\
  If KeyDown(200)=True And pitch\>-89 Then pitch=pitch-1\
  If KeyDown(205)=True Then yaw=yaw+1\
  If KeyDown(203)=True Then yaw=yaw-1\
  If KeyDown(30)=True Then roll=roll+1\
  If KeyDown(31)=True Then roll=roll-1\
  \
  ; Change sprite view mode depending on key pressed\
  If KeyHit(2)=True Then view_mode=1 : view_mode_info\$=\" (fixed)\"\
  If KeyHit(3)=True Then view_mode=2 : view_mode_info\$=\" (free)\"\
  If KeyHit(4)=True Then view_mode=3 : view_mode_info\$=\" (upright1)\"\
  If KeyHit(5)=True Then view_mode=4 : view_mode_info\$=\" (upright2)\"\
  \
  ; Set sprite view mode\
  SpriteViewMode sprite,view_mode\
  \
  RotateEntity pivot,pitch,yaw,0\
  PointEntity camera,sprite,roll\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor keys to orbit camera around sprite\"\
  Text 0,20,\"Press A and S keys to roll camera\"\
  Text 0,40,\"Press keys 1-4 to change sprite view mode\"\
  Text 0,60,\"SpriteViewMode: \"+view_mode+view_mode_info\$\
  \
  Flip\
  \
  Wend\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SpriteViewMode&ref=comments){target="_blank"}
to view the latest version of this page online
