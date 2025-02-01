# TextureFilter match_text\$,flags

# Parameters

  -----------------------------------------------------------------------
  match_text\$ - text that, if found in texture filename, will activate
  certain filters\
  \
  flags - filter texture flags:\
  1: Color\
  2: Alpha\
  4: Masked\
  8: Mipmapped\
  16: Clamp U\
  32: Clamp V\
  64: Spherical reflection map\
  128:\
  256: Store texture in vram\
  512: Force the use of high color textures

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Adds a texture filter. Any textures loaded that contain the text
  specified by match_text\$ will have the provided flags added.\
  \
  This is mostly of use when loading a mesh.\
  \
  By default, the following texture filter is used:\
  \
  TextureFilter \"\",1+8\
  \
  This means that all loaded textures will have color and be mipmapped by
  default.

  -----------------------------------------------------------------------

# [Example](../3d_examples/TextureFilter.bb)

  ----------------------------------------------------------------------------------------------
  ; ClearTextureFilters and TextureFilter Example.\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Const tex_color = 1 ; Color texture\
  Const tex_alpha = 2 ; Alpha texture (Include alpha channel data)\
  Const tex_mask = 4 ; Masked texture (black is transparent)\
  Const tex_mipmap = 8 ; Create texture mipmaps\
  Const tex_clampu = 16 ; Restrict U texture coords from \"bleeding over\"\
  Const tex_clampv = 32 ; Restrict V texture coords from \"bleeding over\"\
  Const tex_envshpere = 64 ; Load texture as a spherical environment map\
  Const tex_vram = 256 ; Force texture graphics to vram\
  Const tex_highcolor = 512 ; Forces texture graphics to be 32-bits per pixel\
  \
  Graphics3D 640,480\
  \
  ; Removes any texture filters that might apply.\
  \
  ClearTextureFilters\
  \
  ; Add an alpha texture to the list of\
  ; texture filters to apply to files\
  ; that have \"\_alpha\" in their filenames.\
  \
  TextureFilter \"\_alpha\",tex_color + tex_alpha + tex_mipmap\
  \
  ; Set appropriate texture flags for loading\
  ; suitable skybox textures from files named\
  ; something with \"\_skybox\".\
  \
  TextureFilter \"\_skybox\", tex_color + tex_mipmap + tex_clampu + tex_clampv\
  \
  ; Set the flags for loading a spherical refletction\
  ; map to apply to all \"\_refmap\" files.\
  \
  TextureFilter \"\_refmap\", tex_color + tex_mipmap + tex_envshpere\
  \
  ; Setup a texture filter to allow faster\
  ; (and easier) pixel manipulation on all\
  ; loaded \"\_fastblit\" files.\
  \
  TextureFilter \"\_fastblit\", tex_color + tex_vram + tex_highcolor\
  \
  ; This is where you would normally load your special\
  ; textures.\
  \
  ; The next bit resets the texture filters to their\
  ; standard settings.\
  \
  ClearTextureFilters\
  TextureFilter \"\", tex_color + tex_mipmap\
  \
  End

  ----------------------------------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TextureFilter&ref=comments){target="_blank"}
to view the latest version of this page online
