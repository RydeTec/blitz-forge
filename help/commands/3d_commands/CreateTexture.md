# CreateTexture ( width,height\[,flags\]\[,frames\] )

# Parameters

  -----------------------------------------------------------------------
  width - width of texture\
  height - height of texture\
  \
  flags (optional) - texture flag:\
  1: Color (default)\
  2: Alpha\
  4: Masked\
  8: Mipmapped\
  16: Clamp U\
  32: Clamp V\
  64: Spherical environment map\
  128: Cubic environment map\
  256: Store texture in vram\
  512: Force the use of high color textures\
  \
  frames (optional) - no of frames texture will have. Defaults to 1.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a texture and returns its handle.\
  \
  Width and height are the size of the texture. Note that the actual
  texture size may be different from the width and height requested, as
  different types of 3D hardware support different sizes of texture.\
  \
  The optional flags parameter allows you to apply certain effects to the
  texture. Flags can be added to combine two or more effects, e.g. 3
  (1+2) = texture with color and alpha maps.\
  \
  Here some more detailed descriptions of the flags:\
  \
  1: Color - colour map, what you see is what you get.\
  \
  2: Alpha - alpha map. If an image contains an alpha map, this will be
  used to make certain areas of the texture transparent. Otherwise, the
  colour map will be used as an alpha map. With alpha maps, the dark
  areas always equal high-transparency, light areas equal
  low-transparency.\
  \
  4: Masked - all areas of a texture coloured 0,0,0 will not be drawn to
  the screen.\
  \
  8: Mipmapped - low detail versions of the texture will be used at high
  distance. Results in a smooth, blurred look.\
  \
  16: Clamp u - Any part of a texture that lies outsides the U
  coordinates of 0-1 will not be drawn. Prevents texture-wrapping.\
  \
  32: Clamp v - Any part of a texture that lies outsides the v
  coordinates of 0-1 will not be drawn. Prevents texture-wrapping.\
  \
  64: Spherical environment map - a form of environment mapping. This
  works by taking a single image, and then applying it to a 3D mesh in
  such a way that the image appears to be reflected. When used with a
  texture that contains light sources, it can give some meshes such as a
  teapot a shiny appearance.\
  \
  128: Cubic environment map - a form of environment mapping. Cube
  mapping is similar to spherical mapping, except it uses six images each
  representing a particular \'face\' of an imaginary cube, to give the
  appearance of an image that perfectly reflects its surroundings.\
  \
  When creating cubic environment maps with the CreateTexture command,
  cubemap textures \*must\* be square \'power of 2\' sizes. See the
  [SetCubeFace](../3d_commands/SetCubeFace.htm){.small} command for
  information on how to then draw to the cubemap.\
  \
  When loading cubic environments maps into Blitz using LoadTexture, all
  six images relating to the six faces of the cube must be contained
  within the one texture, and be laid out in a horizontal strip in the
  following order - left, forward, right, backward, up, down. The images
  comprising the cubemap must all be power of two sizes.\
  \
  Please note that not some older graphics cards do not support cubic
  mapping. In order to find out if a user\'s graphics card can support
  it, use [GfxDriverCaps3D](../3d_commands/GfxDriverCaps3D.htm){.small}
  .\
  \
  256: Store texture in vram. In some circumstances, this makes for much
  faster dynamic textures - ie. when using CopyRect between two textures.
  When drawing to cube maps in real-time, it is preferable to use this
  flag.\
  \
  512: Force the use of high color textures in low bit depth graphics
  modes. This is useful for when you are in 16-bit color mode, and wish
  to create/load textures with the alpha flag - it should give better
  results.\
  \
  Once you have created a texture, use SetBuffer TextureBuffer to draw to
  it. However, to display 2D graphics on a texture, it is usually quicker
  to draw to an image and then copy it to the texturebuffer, and to
  display 3D graphics on a texture, your only option is to copy from the
  backbuffer to the texturebuffer.\
  \
  See also: [LoadTexture](LoadTexture.htm){.small},
  [LoadAnimTexture](LoadAnimTexture.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CreateTexture.bb)

  -----------------------------------------------------------------------
  ; CreateTexture Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
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
  ; Create texture of size 256x256\
  tex=CreateTexture( 256,256 )\
  \
  ; Set buffer - texture buffer\
  SetBuffer TextureBuffer( tex )\
  \
  ; Clear texture buffer with background white color\
  ClsColor 255,255,255\
  Cls\
  \
  ; Draw text on texture\
  font=LoadFont( \"arial\",24 )\
  SetFont font\
  Color 0,0,0\
  Text 0,0,\"This texture\"\
  Text 0,40,\"was created using\" : Color 0,0,255\
  Text 0,80,\"CreateTexture()\" : Color 0,0,0\
  Text 0,120,\"and drawn to using\" : Color 0,0,255\
  Text 0,160,\"SetBuffer TextureBuffer()\"\
  \
  ; Texture cube with texture\
  EntityTexture cube,tex\
  \
  ; Set buffer - backbuffer\
  SetBuffer BackBuffer()\
  \
  While Not KeyDown( 1 )\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateTexture&ref=comments){target="_blank"}
to view the latest version of this page online
