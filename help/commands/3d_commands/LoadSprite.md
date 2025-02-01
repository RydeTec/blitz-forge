# LoadSprite ( tex_file\$\[,tex_flag\]\[,parent\] )

# Parameters

  -----------------------------------------------------------------------
  text_file\$ - filename of image file to be used as sprite\
  \
  tex_flag (optional) - texture flag:\
  1: Color\
  2: Alpha\
  4: Masked\
  8: Mipmapped\
  16: Clamp U\
  32: Clamp V\
  64: Spherical reflection map\
  \
  parent - parent of entity

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a sprite entity, and assigns a texture to it.\
  \
  See also: [LoadSprite](LoadSprite.htm){.small},
  [RotateSprite](RotateSprite.htm){.small},
  [ScaleSprite](ScaleSprite.htm){.small},
  [HandleSprite](HandleSprite.htm){.small},
  [SpriteViewMode](SpriteViewMode.htm){.small},
  [PositionEntity](PositionEntity.htm){.small},
  [MoveEntity](MoveEntity.htm){.small},
  [TranslateEntity](TranslateEntity.htm){.small},
  [EntityAlpha](EntityAlpha.htm){.small},
  [FreeEntity](FreeEntity.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/LoadSprite.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480\
  \
  campivot = CreatePivot()\
  cam = CreateCamera(campivot)\
  MoveEntity cam,0,0,-5\
  \
  sp = LoadSprite(\"grass.bmp\")\
  SpriteViewMode sp,4\
  \
  While Not KeyDown(1)\
  RenderWorld:Flip\
  TurnEntity campivot,1,1,3\
  Wend\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadSprite&ref=comments){target="_blank"}
to view the latest version of this page online
