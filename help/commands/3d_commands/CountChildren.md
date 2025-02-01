# CountChildren ( entity )

# Parameters

  ------------------------
  entity - entity handle
  ------------------------

# Description

  ----------------------------------------------
  Returns the number of children of an entity.
  ----------------------------------------------

# [Example](../3d_examples/CountChildren.bb)

  -----------------------------------------------------------------------
  If CountChildren(entity) \> 0\
  For childcount = 1 to CountChildren(entity)\
  child = GetChild(entity,childcount)\
  Next\
  Endif

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CountChildren&ref=comments){target="_blank"}
to view the latest version of this page online
