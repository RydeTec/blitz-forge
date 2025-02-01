# TerrainShading terrain,enable

# Parameters

  -----------------------------------------------------------------------
  terrain - terrain handle\
  enable - True to enable terrain shading, False to to disable it. The
  default mode is False.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Enables or disables terrain shading.\
  \
  Shaded terrains are a little slower than non-shaded terrains, and in
  some instances can increase the visibility of LOD \'pop-in\'. However,
  the option is there to have shaded terrains if you wish to do so.

  -----------------------------------------------------------------------

# [Example](../3d_examples/TerrainShading.bb)

  -----------------------------------------------------------------------
  ; TerrainShading Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  PositionEntity camera,1,1,1\
  \
  light=CreateLight()\
  RotateEntity light,45,0,0\
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
  ; Toggle terrain shading value between 0 and 1 when spacebar is
  pressed\
  If KeyHit(57)=True Then terra_shade=1-terra_shade\
  \
  ; Enable/disable terrain shading\
  TerrainShading terrain,terra_shade\
  \
  If KeyDown( 203 )=True Then x#=x#-0.1\
  If KeyDown( 205 )=True Then x#=x#+0.1\
  If KeyDown( 208 )=True Then y#=y#-0.1\
  If KeyDown( 200 )=True Then y#=y#+0.1\
  If KeyDown( 44 )=True Then z#=z#-0.1\
  If KeyDown( 30 )=True Then z#=z#+0.1\
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
  Text 0,20,\"Press spacebar to toggle between TerrainShading
  True/False\"\
  If terra_shade=True Then Text 0,40,\"TerrainShading: True\" Else Text
  0,40,\"TerrainShading: False\"\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TerrainShading&ref=comments){target="_blank"}
to view the latest version of this page online
