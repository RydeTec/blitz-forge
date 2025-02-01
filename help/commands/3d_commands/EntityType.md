# EntityType entity,collision_type\[,recursive\]

# Parameters

  -----------------------------------------------------------------------
  entity - entity handle\
  collision_type - collision type of entity. Must be in the range 0-999.\
  recursive (optional) - true to apply collision type to entity\'s
  children. Defaults to false.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the collision type for an entity.\
  \
  A collision_type value of 0 indicates that no collision checking will
  occur with that entity. A collision value of 1-999 will mean collision
  checking will occur.\
  \
  See also: [Collisions](Collisions.htm){.small},
  [GetEntityType](GetEntityType.htm){.small},
  [EntityBox](EntityBox.htm){.small},
  [EntityRadius](EntityRadius.htm){.small}.

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=EntityType&ref=comments){target="_blank"}
to view the latest version of this page online
