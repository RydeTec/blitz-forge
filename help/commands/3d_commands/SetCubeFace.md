# SetCubeFace texture,face

# Parameters

  -----------------------------------------------------------------------
  texture - texture\
  face - face of cube to select. This should be one of the following
  values:\
  0: left (negative X) face\
  1: forward (positive Z) face - this is the default.\
  2: right (positive X) face\
  3: backward (negative Z) face\
  4: up (positive Y) face\
  5: down (negative Y) face

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Selects a cube face for direct rendering to a texture.\
  \
  This command should only be used when you wish to draw directly to a
  cubemap texture in real-time. Otherwise, just loading a pre-rendered
  cubemap with a flag of 128 will suffice.\
  \
  To understand how this command works exactly it is important to
  recognise that Blitz treats cubemap textures slightly differently to
  how it treats other textures. Here\'s how it works\...\
  \
  A cubemap texture in Blitz actually consists of six images, each of
  which must be square \'power\' of two size - e.g. 32, 64, 128 etc. Each
  corresponds to a particular cube face. These images are stored
  internally by Blitz, and the texture handle that is returned by
  LoadTexture/CreateTexture when specifying the cubemap flag, only
  provides access to one of these six images at once (by default the
  first one, or \'0\' face).\
  \
  This is why, when loading a cubemap texture into Blitz using
  LoadTexture, all the six cubemap images must be laid out in a specific
  order (0-5, as described above), in a horizontal strip. Then Blitz
  takes this texture and internally converts it into six separate
  images.\
  \
  So seeing as the texture handle returned by
  [CreateTexture](../3d_commands/CreateTexture.htm){.small} /
  [LoadTexture](../3d_commands/LoadTexture.htm){.small} only provides
  access to one of these images at once (no. 1 by default), how do we get
  access to the other five images? This is where SetCubeFace comes in. It
  will tell Blitz that whenever you next draw to a cubemap texture, to
  draw to the particular image representing the face you have specified
  with the face parameter.\
  \
  Now you have the ability to draw to a cubemap in real-time.\
  \
  To give you some idea of how this works in code, here\'s a function
  that updates a cubemap in real-time. It works by rendering six
  different views and copying them to the cubemap texture buffer, using
  SetCubeFace to specify which particular cubemap image should be drawn
  to.\
  \
  ; Start of code\
  \
  Function UpdateCubeMap( tex,camera )\
  \
  tex_sz=TextureWidth(tex)\
  \
  ; do left view\
  SetCubeFace tex,0\
  RotateEntity camera,0,90,0\
  RenderWorld\
  \
  ; copy contents of backbuffer to cubemap\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  ; do forward view\
  SetCubeFace tex,1\
  RotateEntity camera,0,0,0\
  RenderWorld\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  ; do right view\
  SetCubeFace tex,2\
  RotateEntity camera,0,-90,0\
  RenderWorld\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  ; do backward view\
  SetCubeFace tex,3\
  RotateEntity camera,0,180,0\
  RenderWorld\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  ; do up view\
  SetCubeFace tex,4\
  RotateEntity camera,-90,0,0\
  RenderWorld\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  ; do down view\
  SetCubeFace tex,5\
  RotateEntity camera,90,0,0\
  RenderWorld\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  EndFunction\
  \
  ; End of code\
  \
  All rendering to a texture buffer affects the currently selected face.
  Do not change the selected cube face while a buffer is locked.\
  \
  Finally, you may wish to combine the vram 256 flag with the cubic
  mapping flag when drawing to cubemap textures for faster access.\
  \
  See also: [CreateTexture](CreateTexture.htm){.small},
  [LoadTexture](LoadTexture.htm){.small},
  [SetCubeMode](SetCubeMode.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/SetCubeFace.bb)

  -----------------------------------------------------------------------
  ; SetCubeFace Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  width=640\
  height=480\
  depth=0\
  mode=0\
  \
  Graphics3D width,height,depth,mode\
  SetBuffer BackBuffer()\
  \
  ; If user\'s graphics card does not support cubic mapping then quit
  example\
  If GfxDriverCaps3D()\<110 Then RuntimeError \"Sorry, your graphics card
  does not support cubic environemnt maps.\"\
  \
  cam=CreateCamera()\
  PositionEntity cam,0,10,-10\
  \
  ; Create separate camera for updating cube map - this allows us to
  manipulate main camera and cube camera which avoids any confusion\
  cube_cam=CreateCamera()\
  HideEntity cube_cam\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  ; Load object we will apply cubemap to - the classic teapot\
  teapot=LoadMesh(\"media/teapot.x\")\
  ScaleEntity teapot,3,3,3\
  PositionEntity teapot,0,10,0\
  \
  ; Create some scenery\
  \
  ; ground\
  ground=CreatePlane()\
  EntityColor ground,168,133,55\
  ground_tex=LoadTexture(\"media/sand.bmp\")\
  ScaleTexture ground_tex,10,10\
  EntityTexture ground,ground_tex\
  \
  ; sky\
  sky=CreateSphere(24)\
  ScaleEntity sky,500,500,500\
  FlipMesh sky\
  EntityFX sky,1\
  sky_tex=LoadTexture(\"media/sky.bmp\")\
  EntityTexture sky,sky_tex\
  \
  ; cactus\
  cactus=LoadMesh(\"media/cactus2.x\")\
  FitMesh cactus,-5,0,-5,2,6,.5\
  \
  ; camel\
  camel=LoadMesh(\"media/camel.x\")\
  FitMesh camel,5,0,-5,6,5,4\
  \
  ; Load ufo to give us a dynamic moving object that the cubemap will be
  able to reflect\
  ufo_piv=CreatePivot()\
  PositionEntity ufo_piv,0,15,0\
  ufo=LoadMesh(\"media/green_ufo.x\",ufo_piv)\
  PositionEntity ufo,0,0,10\
  \
  ; Create texture with color + cubic environment map + store in vram
  flags\
  tex=CreateTexture(256,256,1+128+256)\
  \
  ; Apply cubic environment map to teapot\
  EntityTexture teapot,tex\
  \
  While Not KeyDown(1)\
  \
  ; Control camera\
  \
  ; mouse look\
  \
  mxs#=mxs#+(MouseXSpeed()/5.0)\
  mys#=mys#+(MouseYSpeed()/5.0)\
  \
  RotateEntity cam,mys#,-mxs#,0\
  \
  MoveMouse width/2,height/2\
  \
  ; move camera forwards/backwards/left/right with cursor keys\
  \
  If KeyDown(200)=True Then MoveEntity cam,0,0,.2 ; move camera forward\
  If KeyDown(208)=True Then MoveEntity cam,0,0,-.2 ; move camera back\
  \
  If KeyDown(205)=True Then MoveEntity cam,.2,0,0 ; move camera left\
  If KeyDown(203)=True Then MoveEntity cam,-.2,0,0 ; move camera right\
  \
  ; Turn ufo pivot, causing child ufo mesh to spin around it (and
  teapot)\
  TurnEntity ufo_piv,0,2,0\
  \
  ; Hide our main camera before updating cube map - we don\'t need it to
  be rendererd every time cube_cam is rendered\
  HideEntity cam\
  \
  ; Update cubemap\
  UpdateCubemap(tex,cube_cam,teapot)\
  \
  ; Show main camera again\
  ShowEntity cam\
  \
  RenderWorld\
  \
  Text 0,0,\"Use mouse to look around\"\
  Text 0,20,\"Use cursor keys to change camera position\"\
  \
  Flip\
  \
  Wend\
  \
  \
  Function UpdateCubemap(tex,camera,entity)\
  \
  tex_sz=TextureWidth(tex)\
  \
  ; Show the camera we have specifically created for updating the
  cubemap\
  ShowEntity camera\
  \
  ; Hide entity that will have cubemap applied to it. This is so we can
  get cubemap from its position, without it blocking the view\
  HideEntity entity\
  \
  ; Position camera where the entity is - this is where we will be
  rendering views from for cubemap\
  PositionEntity
  camera,EntityX#(entity),EntityY#(entity),EntityZ#(entity)\
  \
  CameraClsMode camera,False,True\
  \
  ; Set the camera\'s viewport so it is the same size as our texture - so
  we can fit entire screen contents into texture\
  CameraViewport camera,0,0,tex_sz,tex_sz\
  \
  ; Update cubemap\
  \
  ; do left view\
  SetCubeFace tex,0\
  RotateEntity camera,0,90,0\
  RenderWorld\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  ; do forward view\
  SetCubeFace tex,1\
  RotateEntity camera,0,0,0\
  RenderWorld\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  ; do right view\
  SetCubeFace tex,2\
  RotateEntity camera,0,-90,0\
  RenderWorld\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  ; do backward view\
  SetCubeFace tex,3\
  RotateEntity camera,0,180,0\
  RenderWorld\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  ; do up view\
  SetCubeFace tex,4\
  RotateEntity camera,-90,0,0\
  RenderWorld\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  ; do down view\
  SetCubeFace tex,5\
  RotateEntity camera,90,0,0\
  RenderWorld\
  CopyRect 0,0,tex_sz,tex_sz,0,0,BackBuffer(),TextureBuffer(tex)\
  \
  ; Show entity again\
  ShowEntity entity\
  \
  ; Hide the cubemap camera\
  HideEntity camera\
  \
  End Function

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SetCubeFace&ref=comments){target="_blank"}
to view the latest version of this page online
