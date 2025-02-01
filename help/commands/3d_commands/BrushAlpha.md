# BrushAlpha brush,alpha#

# Parameters

  -----------------------------------------------------------------------
  brush - brush handle\
  alpha# - alpha level of brush

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the alpha level of a brush.\
  \
  The alpha# value should be in the range 0-1. The default brush alpha
  setting is 1.\
  \
  The alpha level is how transparent an entity is. A value of 1 will mean
  the entity is non-transparent, i.e. opaque. A value of 0 will mean the
  entity is completely transparent, i.e. invisible. Values between 0 and
  1 will cause varying amount of transparency accordingly, useful for
  imitating the look of objects such as glass and ice.\
  \
  An BrushAlpha value of 0 is especially useful as Blitz3D will not
  render entities with such a value, but will still involve the entities
  in collision tests. This is unlike HideEntity, which doesn\'t involve
  entities in collisions.

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=BrushAlpha&ref=comments){target="_blank"}
to view the latest version of this page online
