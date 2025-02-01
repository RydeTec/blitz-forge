# TextureBlend Texture, Blend

# Parameters

  -----------------------------------------------------------------------
  Texture - Texture handle.\
  Blend - Blend mode of texture.\
  \
  0: Do not blend\
  1: No blend, or Alpha (alpha when texture loaded with alpha flag - not
  recommended for multitexturing - see below)\
  2: Multiply (default)\
  3: Add\
  4: Dot3\
  5: Multiply 2

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the blending mode for a texture.\
  \
  The texture blend mode determines how the texture will blend with the
  texture or polygon which is \'below\' it. Texture 0 will blend with the
  polygons of the entity it is applied to. Texture 1 will blend with
  texture 0. Texture 2 will blend with texture 1. And so on.\
  \
  Texture blending in Blitz effectively takes the highest order texture
  (the one with the highest index) and it blends with the texture below
  it, then that result to the texture directly below again, and so on
  until texture 0 which is blended with the polygons of the entity it is
  applied to and thus the world, depending on the
  [EntityBlend](../3d_commands/EntityBlend.htm){.small} of the object.\
  \
  Each of the blend modes are identical to their
  [EntityBlend](../3d_commands/EntityBlend.htm){.small} counterparts.\
  \
  In the case of multitexturing (more than one texture applied to an
  entity), it is not recommended you blend textures that have been loaded
  with the alpha flag, as this can cause unpredictable results on a
  variety of different graphics cards.\
  \
  Use [EntityTexture](../3d_commands/EntityTexture.htm){.small} to set
  the index number of a texture.\
  \
  See also: [EntityBlend](EntityBlend.htm){.small},
  [EntityTexture](EntityTexture.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/TextureBlend.bb)

  -----------------------------------------------------------------------
  ; TextureBlend Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  ; Choose a background colour which isn\'t the same colour as anything
  else, to avoid confusion\
  CameraClsColor camera,255,0,0\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  cube=CreateCube()\
  PositionEntity cube,0,0,5\
  \
  ; Load textures\
  tex0=LoadTexture( \"media/b3dlogo.jpg\" )\
  tex1=LoadTexture( \"media/chorme-2.bmp\" )\
  \
  ; Texture cube with textures\
  EntityTexture cube,tex0,0,0\
  EntityTexture cube,tex1,0,1\
  \
  tex0_blend_info\$=\"no texture\"\
  tex1_blend_info\$=\"no texture\"\
  \
  While Not KeyDown( 1 )\
  \
  ; Change texture 0 blending mode\
  If KeyHit( 11 )=True\
  tex0_blend=tex0_blend+1\
  If tex0_blend=4 Then tex0_blend=0\
  If tex0_blend=0 Then tex0_blend_info\$=\"no texture\"\
  If tex0_blend=1 Then tex0_blend_info\$=\"no blend\"\
  If tex0_blend=2 Then tex0_blend_info\$=\"multiply\"\
  If tex0_blend=3 Then tex0_blend_info\$=\"add\"\
  EndIf\
  \
  ; Change texture 1 blending mode\
  If KeyHit( 2 )=True\
  tex1_blend=tex1_blend+1\
  If tex1_blend=4 Then tex1_blend=0\
  If tex1_blend=0 Then tex1_blend_info\$=\"no texture\"\
  If tex1_blend=1 Then tex1_blend_info\$=\"no blend\"\
  If tex1_blend=2 Then tex1_blend_info\$=\"multiply\"\
  If tex1_blend=3 Then tex1_blend_info\$=\"add\"\
  EndIf\
  \
  ; Set texture blend modes\
  TextureBlend tex0,tex0_blend\
  TextureBlend tex1,tex1_blend\
  \
  TurnEntity cube,0.1,0.1,0.1\
  \
  RenderWorld\
  \
  Text 0,0,\"Press 0 to change texture 0\'s blending mode\"\
  Text 0,20,\"Press 1 to change texture 1\'s blending mode\"\
  Text 0,40,\"TextureBlend tex0,\"+tex0_blend+\"
  (\"+tex0_blend_info\$+\")\"\
  Text 0,60,\"TextureBlend tex1,\"+tex1_blend+\"
  (\"+tex1_blend_info\$+\")\"\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TextureBlend&ref=comments){target="_blank"}
to view the latest version of this page online
