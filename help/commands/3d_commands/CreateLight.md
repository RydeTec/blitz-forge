# CreateLight ( \[type\]\[,parent\] )

# Parameters

  -----------------------------------------------------------------------
  type (optional) - type of light\
  1: directional (default)\
  2: point\
  3: spot\
  \
  parent (optional) - parent entity of light

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a light.\
  \
  Lights work by affecting the colour of all vertices within the light\'s
  range. You need at to create at least one light if you wish to use 3D
  graphics otherwise everything will appear flat.\
  \
  The optional type parameter allows you to specify the type of light you
  wish to create. A value of 1 creates a directional light. This works
  similar to a sun shining on a house. All walls facing a certain
  direction are lit the same. How much they are lit by depends on the
  angle of the light reaching them. Directional lights have infinite
  \'position\' and infinite range.\
  \
  A value of 2 creates a point (or omni) light. This works a little bit
  like a light bulb in a house, starting from a central point and
  gradually fading outwards.\
  \
  A value of 3 creates a spot light. This is a cone of light. This works
  similar to shining a torch in a house. It starts with an inner angle of
  light, and then extends towards an outer angle of light. You can adjust
  the angles of a \'spot\' light with the LightConeAngles command.\
  \
  The optional parent parameter allow you to specify a parent entity for
  the light so that when the parent is moved the child light will move
  with it. However, this relationship is one way; applying movement
  commands to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the light being created
  at position 0,0,0 rather than at the parent entity\'s position.\
  \
  \
  Other notes:\
  There is a DirectX limit on the number of lights available per scene -
  this is either 8 or 16 depending on your video card, but you should
  always assume 8.\
  \
  Also, you should remember that each light added effects the rendering
  speed.\
  \
  Lights do not cast shadows, like they do in real life.\
  \
  Most games get around these issues by the use of a pre-calculated
  \'baked\' lightmap texture for the static geometry in the scene.\
  \
  Other lighting techniques include: adjusting vertex colors, dynamic
  shadows, and/or dynamic lights (ie. moving the lights around in the
  scene as they are needed).\
  \
  See also: [LightRange](LightRange.htm){.small},
  [LightColor](LightColor.htm){.small},
  [LightConeAngles](LightConeAngles.htm){.small},
  [AmbientLight](AmbientLight.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CreateLight.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480\
  \
  camera = CreateCamera()\
  MoveEntity camera,0,0,-3\
  \
  ball = CreateSphere()\
  \
  lite = CreateLight(1) ; change this to 2 or 3 to see different lights\
  MoveEntity lite,5,0,0\
  PointEntity lite,ball ; make sure light is pointing at ball\
  \
  While Not KeyDown(1)\
  RenderWorld:Flip\
  Wend\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateLight&ref=comments){target="_blank"}
to view the latest version of this page online
