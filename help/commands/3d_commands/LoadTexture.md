# LoadTexture ( file\$\[,flags\] )

# Parameters

  -----------------------------------------------------------------------
  file\$ - filename of image file to be used as texture\
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
  512: Force the use of high color textures

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Load a texture from an image file and returns the texture\'s handle.
  Supported file formats include: BMP, PNG, TGA and JPG. Only PNG and TGA
  support alpha.\
  \
  \
  The optional flags parameter allows you to apply certain effects to the
  texture. Flags can be added to combine two or more effects, e.g. 3
  (1+2) = texture with colour and alpha maps.\
  \
  \
  See [CreateTexture](../3d_commands/CreateTexture.htm){.small} for more
  detailed descriptions of the texture flags.\
  \
  \
  Something to consider when applying texture flags to loaded textures is
  that the texture may have already had certain flags applied to it via
  the [TextureFilter](../3d_commands/TextureFilter.htm){.small} command.
  The default for the
  [TextureFilter](../3d_commands/TextureFilter.htm){.small} command is 9
  (1+8), which is a coloured, mipmapped texture. This cannot be
  overridden via the flags parameter of the LoadTexture command - if you
  wish for the filters to be removed you will need to use the
  [ClearTextureFilters](../3d_commands/ClearTextureFilters.htm){.small}
  command, which must be done after setting the graphics mode (setting
  the graphics mode restores the default texture filters).\
  \
  See also: [CreateTexture](CreateTexture.htm){.small},
  [LoadAnimTexture](LoadAnimTexture.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/LoadTexture.bb)

  -----------------------------------------------------------------------
  ; LoadTexture Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
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
  ; Load texture\
  tex=LoadTexture( \"media/b3dlogo.jpg\" )\
  \
  ; Texture cube with texture\
  EntityTexture cube,tex\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadTexture&ref=comments){target="_blank"}
to view the latest version of this page online
