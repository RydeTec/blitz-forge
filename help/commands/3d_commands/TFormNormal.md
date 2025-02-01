# TFormNormal x#, y#, z#, source_entity, dest_entity

# Parameters

  -----------------------------------------------------------------------
  x#, y#, z# = components of a vector in 3d space\
  \
  source_entity = handle of source entity, or 0 for 3d world\
  dest_entity = handle of destination entity, or 0 for 3d world

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Transforms between coordinate systems. After using TFormNormal the new\
  components can be read with TFormedX(), TFormedY() and TFormedZ().\
  \
  This is exactly the same as TFormVector but with one added feature.\
  After the transformation the new vector is \'normalized\', meaning it\
  is scaled to have length 1.\
  \
  For example, suppose the result of TFormVector is (1,2,2).\
  This vector has length Sqr( 1\*1 + 2\*2 + 2\*2 ) = Sqr( 9 ) = 3.\
  \
  This means TFormNormal would produce ( 1/3, 2/3, 2/3 ).

  -----------------------------------------------------------------------

# [Example](../3d_examples/TFormNormal.bb)

  -----------------------------------------------------------------------
  ; TFormNormal example\
  \
  Graphics3D 640, 480\
  \
  ; Just a quick demonstration of the \'normalization\' feature.\
  \
  TFormNormal 1,2,2, 0,0 ; transform from world to world\
  \
  ; The transformation from world to world does nothing.\
  ; But afterward the vector (1,2,2) is divided by the length 3.\
  \
  message\$ = \"The normalized vector is ( \"\
  message = message + TFormedX() + \", \" + TFormedY() + \", \" +
  TFormedZ() + \" )\"\
  \
  Text 70, 180, message\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=TFormNormal&ref=comments){target="_blank"}
to view the latest version of this page online
