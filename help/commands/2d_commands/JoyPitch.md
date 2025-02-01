# JoyPitch#(\[port\])

# Parameters

  ------------------------------------------------------------------------------------------
  port (optional) - an integer value representing the port to be checked for joystick data
  ------------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Returns the pitch value of a joystick.\
  \
  The optional port parameter allows you to select which joystick to get
  output from should you have multiple controllers connected to your PC.

  -----------------------------------------------------------------------

# [Example](../2d_examples/JoyPitch.bb)

  -----------------------------------------------------------------------
  ; JoyPitch Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  While Not KeyDown(1)\
  \
  ; Get various joystick values\
  ju#=JoyU()\
  judir=JoyUDir()\
  jv#=JoyV()\
  jvdir=JoyVDir()\
  jyaw#=JoyYaw()\
  jpitch#=JoyPitch()\
  jroll#=JoyRoll()\
  \
  ; Output joystick values\
  Text 0,0,\"Move joystick to output values onto screen\"\
  Text 0,20,\"JoyU(): \"+ju#\
  Text 0,40,\"JoyUDir(): \"+judir\
  Text 0,60,\"JoyV(): \"+jv#\
  Text 0,80,\"JoyVDir(): \"+jvdir\
  Text 0,100,\"JoyYaw(): \"+jyaw#\
  Text 0,120,\"JoyPitch(): \"+jpitch#\
  Text 0,140,\"JoyRoll(): \"+jroll#\
  \
  Flip\
  Cls\
  \
  Wend

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=JoyPitch&ref=comments){target="_blank"}
to view the latest version of this page online
