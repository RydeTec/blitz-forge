# CreateSprite ( \[parent\] )

# Parameters

  ---------------------------------------------
  parent (optional) - parent entity of sprite
  ---------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a sprite entity and returns its handle. Sprites are simple flat
  (usually textured) rectangles made from two triangles. Unlike other
  entity objects they don\'t actually have a mesh that can be
  manipulated.\
  \
  The sprite will be positioned at 0,0,0 and extend from 1,-1 to +1,+1.\
  \
  Sprites have two real strengths. The first is that they consist of only
  two polygons; meaning you can use many of them at once. This makes them
  ideal for particle effects and 2D-using-3D games where you want lots of
  sprites on-screen at once.\
  \
  Secondly, sprites can be assigned a view mode using SpriteViewMode. By
  default this view mode is set to 1, which means the sprite will always
  face the camera. So no matter what the orientation of the camera is
  relative to the sprite, you will never actually notice that they are
  flat; by giving them a spherical texture, you can make them appear to
  look no different than a normal sphere.\
  \
  The optional parent parameter allow you to specify a parent entity for
  the sprite so that when the parent is moved the child sprite will move
  with it. However, this relationship is one way; applying movement
  commands to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the sprite being
  created at position 0,0,0 rather than at the parent entity\'s
  position.\
  \
  Note: Sprites have their own commands for rotation and scaling.\
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

# [Example](../3d_examples/CreateSprite.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480\
  \
  cam = CreateCamera()\
  MoveEntity cam,0,0,-5\
  \
  sp = CreateSprite()\
  RotateSprite sp,20\
  \
  RenderWorld:Flip\
  WaitKey\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateSprite&ref=comments){target="_blank"}
to view the latest version of this page online
