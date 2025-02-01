# EntityPickMode entity,pick_geometry\[,obscurer\]

# Parameters

  -----------------------------------------------------------------------
  entity - entity handle\
  \
  pick_geometry - type of geometry used for picking:\
  0: Unpickable (default)\
  1: Sphere (EntityRadius is used)\
  2: Polygon\
  3: Box (EntityBox is used)\
  \
  obscurer (optional) - True to determine that the entity \'obscures\'
  other entities during an EntityVisible call. Defaults to True.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the pick mode for an entity.\
  \
  The optional obscurer parameter is used with EntityVisible to determine
  just what can get in the way of the line-of-sight between 2 entities.
  This allows some entities to be pickable using the other pick commands,
  but to be ignored (i.e. \'transparent\') when using EntityVisible. So,
  its very much EntityVisible specific.\
  \
  Please note that only Sphere and Box picking will work with Blitz3D
  sprites. For polygon picking, you will need a valid mesh.\
  \
  See also: [EntityPick](EntityPick.htm){.small},
  [LinePick](LinePick.htm){.small}, [CameraPick](CameraPick.htm){.small},
  [EntityPickMode](EntityPickMode.htm){.small}.

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=EntityPickMode&ref=comments){target="_blank"}
to view the latest version of this page online
