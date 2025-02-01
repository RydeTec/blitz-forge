# Collisions src_type,dest_type,method,response

# Parameters

  -----------------------------------------------------------------------
  src_type - entity type to be checked for collisions.\
  dest_type - entity type to be collided with.\
  \
  method - collision detection method.\
  1: ellipsoid-to-ellipsoid collisions\
  2: ellipsoid-to-polygon collisions\
  3: ellipsoid-to-box collisions\
  \
  response - what the source entity does when a collision occurs.\
  1: stop\
  2: slide1 - full sliding collision\
  3: slide2 - prevent entities from sliding down slopes

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Enables collisions between two different entity types.\
  \
  Entity types are just numbers you assign to an entity using EntityType.
  Blitz then uses the entity types to check for collisions between all
  the entities that have those entity types.\
  \
  Blitz has many ways of checking for collisions, as denoted by the
  method parameter. However, collision checking is always ellipsoid to
  something. In order for Blitz to know what size a source entity is, you
  must first assign an entity radius to all source entities using
  EntityRadius.\
  \
  In the case of collision detection method 1 being selected
  (ellipsoid-to-ellipsoid), then the destination entities concerned will
  need to have an EntityRadius assigned to them too. In the case of
  method 3 being selected (ellipsoid-to-box), then the destination
  entities will need to have an EntityBox assigned to them. Method 2
  (ellipsoid-to-polygon) requires nothing to be assigned to the
  destination entities.\
  \
  Not only does Blitz check for collisions, but it acts upon them when it
  detects them too, as denoted by the response parameter. You have three
  options in this situation. You can either choose to make the source
  entity stop, slide or only slide upwards.\
  \
  All collision checking occurs, and collision responses are acted out,
  when UpdateWorld is called.\
  \
  Finally, every time the Collision command is used, collision
  information is added to the collision information list. This can be
  cleared at any time using the ClearCollisions command.\
  \
  See also: [EntityBox](EntityBox.htm){.small},
  [EntityRadius](EntityRadius.htm){.small},
  [Collisions](Collisions.htm){.small},
  [EntityType](EntityType.htm){.small},
  [ResetEntity](ResetEntity.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/Collisions.bb)

  -----------------------------------------------------------------------
  ; Collisions Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  ; Set collision type values\
  type_ground=1\
  type_character=2\
  type_scenery=3\
  \
  camera=CreateCamera()\
  RotateEntity camera,45,0,0\
  PositionEntity camera,0,15,-10\
  \
  light=CreateLight()\
  RotateEntity light,45,0,0\
  \
  ; Create cube \'ground\'\
  cube=CreateCube()\
  ScaleEntity cube,10,10,10\
  EntityColor cube,0,127,0\
  EntityType cube,type_ground\
  PositionEntity cube,0,-5,0\
  \
  ; Create sphere \'character\'\
  sphere=CreateSphere( 32 )\
  EntityColor sphere,127,0,0\
  EntityRadius sphere,1\
  EntityType sphere,type_character\
  PositionEntity sphere,0,7,0\
  \
  ; Enable collisions between type_character and type_ground\
  Collisions type_character,type_ground,2,2\
  \
  ; Create cylinder \'scenery\'\
  cylinder=CreateCylinder( 32 )\
  ScaleEntity cylinder,2,2,2\
  EntityColor cylinder,0,0,255\
  EntityRadius cylinder,2\
  EntityBox cylinder,-2,-2,-2,4,4,4\
  EntityType cylinder,type_scenery\
  PositionEntity cylinder,-4,7,-4\
  \
  ; Create cone \'scenery\'\
  cone=CreateCone( 32 )\
  ScaleEntity cone,2,2,2\
  EntityColor cone,0,0,255\
  EntityRadius cone,2\
  EntityBox cone,-2,-2,-2,4,4,4\
  EntityType cone,type_scenery\
  PositionEntity cone,4,7,-4\
  \
  ; Create prism \'scenery\'\
  prism=CreateCylinder( 3 )\
  ScaleEntity prism,2,2,2\
  EntityColor prism,0,0,255\
  EntityRadius prism,2\
  EntityBox prism,-2,-2,-2,4,4,4\
  EntityType prism,type_scenery\
  PositionEntity prism,-4,7,4\
  RotateEntity prism,0,180,0\
  \
  ; Create pyramid \'scenery\'\
  pyramid=CreateCone( 4 )\
  ScaleEntity pyramid,2,2,2\
  EntityColor pyramid,0,0,255\
  EntityRadius pyramid,2\
  EntityBox pyramid,-2,-2,-2,4,4,4\
  EntityType pyramid,type_scenery\
  RotateEntity pyramid,0,45,0\
  PositionEntity pyramid,4,7,4\
  \
  ; Set collision method and response values\
  method=2\
  response=2\
  \
  method_info\$=\"ellipsoid-to-polygon\"\
  response_info\$=\"slide1\"\
  \
  While Not KeyDown( 1 )\
  \
  x#=0\
  y#=0\
  z#=0\
  \
  If KeyDown( 203 )=True Then x#=-0.1\
  If KeyDown( 205 )=True Then x#=0.1\
  If KeyDown( 208 )=True Then z#=-0.1\
  If KeyDown( 200 )=True Then z#=0.1\
  \
  MoveEntity sphere,x#,y#,z#\
  MoveEntity sphere,0,-0.02,0 ; gravity\
  \
  ; Change collision method\
  If KeyHit( 50 )=True\
  method=method+1\
  If method=4 Then method=1\
  If method=1 Then method_info\$=\"ellipsoid-to-sphere\"\
  If method=2 Then method_info\$=\"ellipsoid-to-polygon\"\
  If method=3 Then method_info\$=\"ellipsoid-to-box\"\
  EndIf\
  \
  ; Change collision response\
  If KeyHit( 19 )=True\
  response=response+1\
  If response=4 Then response=1\
  If response=1 Then response_info\$=\"stop\"\
  If response=2 Then response_info\$=\"slide1\"\
  If response=3 Then response_info\$=\"slide2\"\
  EndIf\
  \
  ; Enable collisions between type_character and type_scenery\
  Collisions type_character,type_scenery,method,response\
  \
  ; Perform collision checking\
  UpdateWorld\
  \
  RenderWorld\
  \
  Text 0,0,\"Use cursor keys to move sphere\"\
  Text 0,20,\"Press M to change collision Method (currently:
  \"+method_info\$+\")\"\
  Text 0,40,\"Press R to change collision Response (currently:
  \"+response_info\$+\")\"\
  Text 0,60,\"Collisions
  type_character,type_scenery,\"+method+\",\"+response\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Collisions&ref=comments){target="_blank"}
to view the latest version of this page online
