# LoadAnimTexture ( file\$,flags,frame_width,frame_height,first_frame,frame_count )

# Parameters

  -----------------------------------------------------------------------
  file\$ - name of image file with animation frames laid out in
  left-right, top-to-bottom order\
  \
  flags (optional) - texture flag:\
  1: Color (default)\
  2: Alpha\
  4: Masked\
  8: Mipmapped\
  16: Clamp U\
  32: Clamp V\
  64: Spherical reflection map\
  128: Cubic environment map\
  256: Store texture in vram\
  512: Force the use of high color textures\
  \
  frame_width - width of each animation frame\
  frame_height - height of each animation frame\
  first_frame - the first frame to be used as an animation frame\
  frame_count - the amount of frames to be used

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Loads an animated texture from an image file and returns the texture\'s
  handle.\
  \
  The flags parameter allows you to apply certain effects to the texture.
  Flags can be added to combine two or more effects, e.g. 3 (1+2) =
  texture with colour and alpha maps.\
  \
  See [CreateTexture](../3d_commands/CreateTexture.htm){.small} for more
  detailed descriptions of the texture flags.\
  \
  The frame_width, frame_height, first_frame and frame_count parameters
  determine how Blitz will separate the image file into individual
  animation frames.\
  \
  See also: [CreateTexture](CreateTexture.htm){.small},
  [LoadTexture](LoadTexture.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/LoadAnimTexture.bb)

  -----------------------------------------------------------------------
  ; LoadAnimTexture Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  cube=CreateCube()\
  PositionEntity cube,0,0,5\
  \
  ; Load anim texture\
  anim_tex=LoadAnimTexture( \"media/boomstrip.bmp\",49,64,64,0,39 )\
  \
  While Not KeyDown( 1 )\
  \
  ; Cycle through anim frame values. 100 represents delay, 39 represents
  no. of anim frames\
  frame=MilliSecs()/100 Mod 39\
  \
  ; Texture cube with anim texture frame\
  EntityTexture cube,anim_tex,frame\
  \
  pitch#=0\
  yaw#=0\
  roll#=0\
  \
  If KeyDown( 208 )=True Then pitch#=-1\
  If KeyDown( 200 )=True Then pitch#=1\
  If KeyDown( 203 )=True Then yaw#=-1\
  If KeyDown( 205 )=True Then yaw#=1\
  If KeyDown( 45 )=True Then roll#=-1\
  If KeyDown( 44 )=True Then roll#=1\
  \
  TurnEntity cube,pitch#,yaw#,roll#\
  \
  RenderWorld\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadAnimTexture&ref=comments){target="_blank"}
to view the latest version of this page online
