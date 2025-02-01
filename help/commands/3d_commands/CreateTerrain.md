# CreateTerrain ( grid_size\[,parent\] )

# Parameters

  -----------------------------------------------------------------------
  grid_size - no of grid squares along each side of terrain, and must be
  a power of 2 value, e.g. 32, 64, 128, 256, 512, 1024.\
  parent (optional) - parent entity of terrain

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a terrain entity and returns its handle.\
  \
  The terrain extends from 0,0,0 to grid_size,1,grid_size.\
  \
  A terrain is a special type of polygon object that uses real-time level
  of detail (LOD) to display landscapes which should theoretically
  consist of over a million polygons with only a few thousand. The way it
  does this is by constantly rearranging a certain amount of polygons to
  display high levels of detail close to the viewer and low levels
  further away.\
  \
  This constant rearrangement of polygons is occasionally noticeable
  however, and is a well-known side-effect of all LOD landscapes. This
  \'pop-in\' effect can be reduced in lots of ways though, as the other
  terrain help files will go on to explain.\
  \
  The optional parent parameter allows you to specify a parent entity for
  the terrain so that when the parent is moved the child terrain will
  move with it. However, this relationship is one way; applying movement
  commands to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the terrain being
  created at position 0,0,0 rather than at the parent entity\'s
  position.\
  \
  See also: LoadTerrain.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CreateTerrain.bb)

  -----------------------------------------------------------------------
  ; CreateTerrain Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  PositionEntity camera,0,1,0\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  ; Create terrain\
  terrain=CreateTerrain(128)\
  \
  ; Texture terrain\
  grass_tex=LoadTexture( \"media/mossyground.bmp\" )\
  EntityTexture terrain,grass_tex\
  \
  While Not KeyDown( 1 )\
  \
  If KeyDown( 205 )=True Then TurnEntity camera,0,-1,0\
  If KeyDown( 203 )=True Then TurnEntity camera,0,1,0\
  If KeyDown( 208 )=True Then MoveEntity camera,0,0,-0.05\
  If KeyDown( 200 )=True Then MoveEntity camera,0,0,0.05\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor keys to move about the terrain\"\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateTerrain&ref=comments){target="_blank"}
to view the latest version of this page online
