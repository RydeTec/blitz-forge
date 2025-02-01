# LoaderMatrix file_extension\$,xx#,xy#,xz#,yx#,yy#,yz#,zx#,zy#,zz#

# Parameters

  -----------------------------------------------------------------------
  file extension\$ - file extension of 3d file, e.g. \".x\",\".3ds\"\
  xx# - 1,1 element of 3x3 matrix\
  xy# - 2,1 element of 3x3 matrix\
  xz# - 3,1 element of 3x3 matrix\
  yx# - 1,2 element of 3x3 matrix\
  yy# - 2,2 element of 3x3 matrix\
  yz# - 3,2 element of 3x3 matrix\
  zx# - 1,3 element of 3x3 matrix\
  zy# - 2,3 element of 3x3 matrix\
  zz# - 3,3 element of 3x3 matrix

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets a matrix for 3d files loaded with the specified file extension.\
  \
  This can be used to change coordinate systems when loading.\
  \
  By default, the following loader matrices are used:\
  \
  LoaderMatrix \"x\",1,0,0,0,1,0,0,0,1 ; no change in coord system\
  LoaderMatrix \"3ds\",1,0,0,0,0,1,0,1,0 ; swap y/z axis\'\
  \
  You can use LoaderMatrix to flip meshes/animations if necessary, eg:\
  \
  LoaderMatrix \"x\",-1,0,0,0,1,0,0,0,1 ; flip x-cords for \".x\" files\
  LoaderMatrix \"3ds\",-1,0,0,0,0,-1,0,1,0 ; swap y/z, negate x/z for
  \".3ds\" files

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoaderMatrix&ref=comments){target="_blank"}
to view the latest version of this page online
