# Load3DSound ( file\$ )

# Parameters

  -------------------------------------------------------------------
  file\$ - filename of sound file to be loaded and used as 3D sound
  -------------------------------------------------------------------

# Description

  --------------------------------------------------------------
  Loads a sound and returns its handle for use with EmitSound.
  --------------------------------------------------------------

# [Example](../3d_examples/Load3DSound.bb)

  -----------------------------------------------------------------------
  ; Load3DSound Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  PositionEntity camera,0,1,-10\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  plane=CreatePlane()\
  ground_tex=LoadTexture(\"media/Chorme-2.bmp\")\
  EntityTexture plane,ground_tex\
  \
  cube=CreateCube()\
  cube_tex=LoadTexture(\"media/b3dlogo.jpg\")\
  EntityTexture cube,cube_tex\
  PositionEntity cube,0,1,0\
  \
  microphone=CreateListener(camera) ; Create listener, make it child of
  camera\
  sound=Load3DSound(\"media/ufo.wav\") ; Load 3D sound\
  \
  While Not KeyDown(1)\
  \
  If KeyDown(205)=True Then TurnEntity camera,0,-1,0\
  If KeyDown(203)=True Then TurnEntity camera,0,1,0\
  If KeyDown(208)=True Then MoveEntity camera,0,0,-0.05\
  If KeyDown(200)=True Then MoveEntity camera,0,0,0.05\
  \
  ; If left mouse button hit then emit sound from cube\
  If MouseHit(1) = True Then EmitSound(sound,cube)\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor keys to move about\"\
  Text 0,20,\"Press left mouse button to make a sound be emitted from the
  cube\"\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Load3DSound&ref=comments){target="_blank"}
to view the latest version of this page online
