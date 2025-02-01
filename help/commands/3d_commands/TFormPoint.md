# TFormPoint x#, y#, z#, source_entity, dest_entity

# Parameters

  -----------------------------------------------------------------------
  x#, y#, z# = coordinates of a point in 3d space\
  \
  source_entity = handle of source entity, or 0 for 3d world\
  dest_entity = handle of destination entity, or 0 for 3d world

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Transforms between coordinate systems. After using TFormPoint the new\
  coordinates can be read with TFormedX(), TFormedY() and TFormedZ().\
  \
  \
  See EntityX() for details about local coordinates.\
  \
  Consider a sphere built with CreateSphere(). The \'north pole\' is at
  (0,1,0).\
  At first, local and global coordinates are the same. As the sphere is
  moved,\
  turned and scaled the global coordinates of the point change.\
  \
  But it is always at (0,1,0) in the sphere\'s local space.

  -----------------------------------------------------------------------

# [Example](../3d_examples/TFormPoint.bb)

  -----------------------------------------------------------------------
  ; TFormPoint example\
  \
  Graphics3D 640, 480\
  \
  s = CreateSphere() ; center at (0,0,0) north pole at (0,1,0)\
  \
  MoveEntity s, 1,2,3 ; center at (1,2,3) north pole at (1,2+1,3)\
  ScaleEntity s, 10,10,10 ; center at (1,2,3) north pole at (1,2+10,3)\
  \
  ; Now verify that the north pole is at (1,12,3) in the 3d world\
  \
  TFormPoint 0,1,0, s,0 ; north pole transformed from sphere to world\
  \
  message\$ = \"North pole is at ( \"\
  message = message + TFormedX() + \", \" + TFormedY() + \", \" +
  TFormedZ() + \" )\"\
  \
  Text 180, 200, message\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TFormPoint&ref=comments){target="_blank"}
to view the latest version of this page online
