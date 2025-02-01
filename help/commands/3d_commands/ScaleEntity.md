# ScaleEntity entity,x_scale#,y_scale#,z_scalel#,\[,global\]

# Parameters

  -----------------------------------------------------------------------
  entity - name of the entity to be scaled\
  x_scale# - x size of entity\
  y_scale# - y size of entity\
  z_scale# - z size of entity\
  global (optional) -

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Scales an entity so that it is of an absolute size.\
  \
  Scale values of 1,1,1 are the default size when creating/loading
  entities.\
  \
  Scale values of 2,2,2 will double the size of an entity.\
  \
  Scale values of 0,0,0 will make an entity disappear.\
  \
  Scale values of less than 0,0,0 will invert an entity and make it
  bigger.\
  \
  See also: [ScaleMesh](ScaleMesh.htm){.small},
  [FitMesh](FitMesh.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/ScaleEntity.bb)

  -----------------------------------------------------------------------
  ; ScaleEntity Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  light=CreateLight()\
  \
  cone=CreateCone( 32 )\
  PositionEntity cone,0,0,5\
  \
  ; Set scale values so that cone is default size to begin with\
  x_scale#=1\
  y_scale#=1\
  z_scale#=1\
  \
  While Not KeyDown( 1 )\
  \
  ; Change scale values depending on the key pressed\
  If KeyDown( 203 )=True Then x_scale#=x_scale#-0.1\
  If KeyDown( 205 )=True Then x_scale#=x_scale#+0.1\
  If KeyDown( 208 )=True Then y_scale#=y_scale#-0.1\
  If KeyDown( 200 )=True Then y_scale#=y_scale#+0.1\
  If KeyDown( 44 )=True Then z_scale#=z_scale#-0.1\
  If KeyDown( 30 )=True Then z_scale#=z_scale#+0.1\
  \
  ; Scale cone using scale values\
  ScaleEntity cone,x_scale#,y_scale#,z_scale#\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor/A/Z keys to scale cone\"\
  Text 0,20,\"X Scale: \"+x_scale#\
  Text 0,40,\"Y Scale: \"+y_scale#\
  Text 0,60,\"Z Scale: \"+z_scale#\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=ScaleEntity&ref=comments){target="_blank"}
to view the latest version of this page online
