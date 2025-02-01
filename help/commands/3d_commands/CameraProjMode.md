# CameraProjMode camera,mode

# Parameters

  -----------------------------------------------------------------------
  camera - camera handle\
  mode - projection mode:\
  0: no projection - disables camera (faster than HideEntity)\
  1: perspective projection (default)\
  2: orthographic projection

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the camera projection mode.\
  \
  The projection mode is the the technique used by Blitz to display 3D
  graphics on the screen. Using projection mode 0, nothing is displayed
  on the screen, and this is the fastest method of hiding a camera. Using
  camera projection mode 1, the graphics are displayed in their
  \'correct\' form - and this is the default mode for a camera. Camera
  projection mode 2 is a special type of projection, used for displaying
  3D graphics on screen, but in a 2D form - that is, no sense of
  perspective will be given to the graphics. Two identical objects at
  varying distances from the camera will both appear to be the same size.
  Orthographic projection is useful for 3D editors, where a sense of
  perspective is unimportant, and also certain games.\
  \
  Use \'CameraZoom\' to control the scale of graphics rendered with
  orthographic projection. As a general rule, using orthographic
  projection with the default camera zoom setting of 1 will result in
  graphics that are too \'zoomed-in\' - changing the camera zoom to 0.1
  should fix this.\
  \
  One thing to note with using camera project mode 2, is that terrains
  will not be displayed correctly - this is because the level of detail
  algorithm used by terrains relies on perspective in order to work
  properly.

  -----------------------------------------------------------------------

# [Example](../3d_examples/CameraProjMode.bb)

  -----------------------------------------------------------------------
  ; CameraProjMode Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  PositionEntity camera,0,0,-10\
  \
  light=CreateLight()\
  RotateEntity light,0,0,0\
  \
  ; Create cube 1, near to camera\
  cube1=CreateCube()\
  EntityColor cube1,255,0,0\
  PositionEntity cube1,0,0,0\
  \
  ; Create cube 2, same size as cube 1 but further away\
  cube2=CreateCube()\
  EntityColor cube2,0,255,0\
  PositionEntity cube2,5,5,5\
  \
  While Not KeyDown( 1 )\
  \
  ; If spacebar pressed then change mode value\
  If KeyHit(57)=True Then mode=mode+1 : If mode=3 Then mode=0\
  \
  ; If mode value = 2 (orthagraphic), then reduce zoom value to 0.1\
  If mode=2 Then zoom#=0.1 Else zoom#=1\
  \
  ; Set camera projection mode using mode value\
  CameraProjMode camera,mode\
  \
  ; Set camera zoom using zoom value\
  CameraZoom camera,zoom#\
  \
  RenderWorld\
  \
  Text 0,0,\"Press spacebar to change the camera project mode\"\
  Text 0,20,\"CameraProjMode camera,\"+mode\
  Text 0,40,\"CameraZoom camera,\"+zoom#\
  \
  Flip\
  \
  Cls\
  \
  Wend\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CameraProjMode&ref=comments){target="_blank"}
to view the latest version of this page online
