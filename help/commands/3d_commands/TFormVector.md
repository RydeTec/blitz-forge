# TFormVector x#, y#, z#, source_entity, dest_entity

# Parameters

  -----------------------------------------------------------------------
  x#, y#, z# = components of a vector in 3d space\
  \
  source_entity = handle of source entity, or 0 for 3d world\
  dest_entity = handle of destination entity, or 0 for 3d world

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Transforms between coordinate systems. After using TFormVector the new\
  components can be read with TFormedX(), TFormedY() and TFormedZ().\
  \
  \
  See EntityX() for details about local coordinates.\
  \
  \
  Similar to TFormPoint, but operates on a vector. A vector can be
  thought of\
  as \'displacement relative to current location\'.\
  \
  For example, vector (1,2,3) means one step to the right, two steps up\
  and three steps forward.\
  \
  This is analogous to PositionEntity and MoveEntity:\
  \
  PositionEntity entity, x,y,z ; put entity at point (x,y,z)\
  \
  MoveEntity entity, x,y,z ; add vector (x,y,z) to current position

  -----------------------------------------------------------------------

# [Example](../3d_examples/TFormVector.bb)

  -----------------------------------------------------------------------
  ; TFormVector example\
  \
  Graphics3D 640, 480\
  \
  p = CreatePivot()\
  \
  PositionEntity p, 10, 20, 30 ; easy to visualize\
  TurnEntity p, -5, -15, 25 ; hard to visualize\
  \
  ; Question: what would happen if we took one step \'forward\'?\
  ; The local vector corresponding to one step forward is (0,0,1)\
  ; in the pivot\'s local space. We need the global version.\
  \
  TFormVector 0,0,1, p,0 ; transform from pivot to world\
  \
  message\$ = \"\'One step forward\' vector is ( \"\
  message = message + TFormedX() + \", \" + TFormedY() + \", \" +
  TFormedZ() + \" )\"\
  \
  Text 70, 180, message\
  \
  ; Now actually take the step. The new location should be\
  ; (10,20,30) plus the vector we just computed.\
  \
  MoveEntity p, 0,0,1\
  \
  message\$ = \"New location of pivot is ( \"\
  message = message + EntityX(p) + \", \"\
  message = message + EntityY(p) + \", \" + EntityZ(p) + \" )\"\
  \
  Text 100, 210, message\
  \
  Flip\
  \
  WaitKey()\
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TFormVector&ref=comments){target="_blank"}
to view the latest version of this page online
