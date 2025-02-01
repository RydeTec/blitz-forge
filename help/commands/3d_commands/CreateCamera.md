# CreateCamera ( \[parent\] )

# Parameters

  ---------------------------------------------
  parent (optional) - parent entity of camera
  ---------------------------------------------

# Description

  -----------------------------------------------------------------------
  Creates a camera entity and returns its handle.\
  \
  Without at least one camera, you won\'t be able to see anything in your
  3D world. With more than one camera, you will be to achieve effect such
  as split-screen modes and rear-view mirrors.\
  \
  A camera can only render to the backbuffer. If you wish to display 3D
  graphics on an image or a texture then copy the contents of the
  backbuffer to the appropriate buffer.\
  \
  The optional parent parameter allow you to specify a parent entity for
  the camera so that when the parent is moved the child camera will move
  with it. However, this relationship is one way; applying movement
  commands to the child will not affect the parent.\
  \
  Specifying a parent entity will still result in the camera being
  created at position 0,0,0 rather than at the parent entity\'s position.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CreateCamera.bb)

  -----------------------------------------------------------------------
  ; CreateCamera Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  ; Create camera\
  camera=CreateCamera()\
  \
  light=CreateLight()\
  \
  cone=CreateCone()\
  PositionEntity cone,0,0,5\
  \
  While Not KeyDown( 1 )\
  RenderWorld\
  Flip\
  Wend\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateCamera&ref=comments){target="_blank"}
to view the latest version of this page online
