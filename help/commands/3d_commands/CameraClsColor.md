# CameraClsColor camera,red#,green#,blue#

# Parameters

  -----------------------------------------------------------------------
  camera - camera handle\
  red# - red value of camera background color\
  green# - green value of camera background color\
  blue# - blue value of camera background color

  -----------------------------------------------------------------------

# Description

  --------------------------------------------------
  Sets camera background color. Defaults to 0,0,0.
  --------------------------------------------------

# [Example](../3d_examples/CameraClsColor.bb)

  -----------------------------------------------------------------------
  ; CameraClsColor Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  sphere=CreateSphere( 32 )\
  PositionEntity sphere,0,0,5\
  \
  While Not KeyDown(1)\
  \
  ; Change red, green, blue values depending on key pressed\
  If KeyDown(2)=True And red#\>0 Then red#=red#-1\
  If KeyDown(3)=True And red#\<255 Then red#=red#+1\
  If KeyDown(4)=True And green#\>0 Then green#=green#-1\
  If KeyDown(5)=True And green#\<255 Then green#=green#+1\
  If KeyDown(6)=True And blue#\>0 Then blue#=blue#-1\
  If KeyDown(7)=True And blue#\<255 Then blue#=blue#+1\
  \
  ; Set camera clear color using red, green, blue values\
  CameraClsColor camera,red#,green#,blue#\
  \
  RenderWorld\
  \
  Text 0,0,\"Press keys 1-6 to change CameraClsColor red#,green#,blue#
  values\
  Text 0,20,\"Red: \"+red#\
  Text 0,40,\"Green: \"+green#\
  Text 0,60,\"Blue: \"+blue#\
  \
  Flip\
  \
  Wend\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CameraClsColor&ref=comments){target="_blank"}
to view the latest version of this page online
