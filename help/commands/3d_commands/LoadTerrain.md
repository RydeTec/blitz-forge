# LoadTerrain ( file\$\[,parent\] )

# Parameters

  -----------------------------------------------------------------------
  file\$ - filename of image file to be used as height map\
  parent (optional) - parent entity of terrain

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Loads a terrain from an image file and returns the terrain\'s handle.\
  \
  The image\'s red channel is used to determine heights. Terrain is
  initially the same width and depth as the image, and 1 unit high.\
  \
  Tips on generating nice terrain:\
  \
  \* Smooth or blur the height map\
  \* Reduce the y scale of the terrain\
  \* Increase the x/z scale of the terrain\
  \* Reduce the camera range\
  \
  When texturing an entity, a texture with a scale of 1,1,1 (default)
  will be the same size as one of the terrain\'s grid squares. A texture
  that is scaled to the same size as the size of the bitmap used to load
  it or the no. of grid square used to create it, will be the same size
  as the terrain.\
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
  A heightmaps dimensions (width and height) must be the same and must be
  a power of 2, e.g. 32, 64, 128, 256, 512, 1024.\
  \
  See also: CreateTerrain.

  -----------------------------------------------------------------------

# [Example](../3d_examples/LoadTerrain.bb)

  -----------------------------------------------------------------------
  LoadTerrain Example\
  \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  PositionEntity camera,1,1,1\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  ; Load terrain\
  terrain=LoadTerrain( \"media/height_map.bmp\" )\
  \
  ; Set terrain detail, enable vertex morphing\
  TerrainDetail terrain,4000,True\
  \
  ; Scale terrain\
  ScaleEntity terrain,1,50,1\
  \
  ; Texture terrain\
  grass_tex=LoadTexture( \"media/mossyground.bmp\" )\
  EntityTexture terrain,grass_tex,0,1\
  \
  While Not KeyDown( 1 )\
  \
  If KeyDown( 205 )=True Then TurnEntity camera,0,-1,0\
  If KeyDown( 203 )=True Then TurnEntity camera,0,1,0\
  If KeyDown( 208 )=True Then MoveEntity camera,0,0,-0.1\
  If KeyDown( 200 )=True Then MoveEntity camera,0,0,0.1\
  \
  x#=EntityX(camera)\
  y#=EntityY(camera)\
  z#=EntityZ(camera)\
  \
  terra_y#=TerrainY(terrain,x#,y#,z#)+5\
  \
  PositionEntity camera,x#,terra_y#,z#\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadTerrain&ref=comments){target="_blank"}
to view the latest version of this page online
