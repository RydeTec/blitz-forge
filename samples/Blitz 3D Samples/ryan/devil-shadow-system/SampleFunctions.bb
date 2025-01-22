Include "Includes\DevilShadowSystem.bb"
Include "Includes\ShadowVolumes.bb"
Include "Includes\UserInterface.bb"

Global font_height = FontHeight() + 3
Global render_mode = 1
Global Cam, CamPiv, frame
Global Img_Logo

Function InitGraphics(w = 1024, h = 768)
Graphics3D w, h, 32, 2
SetBuffer BackBuffer()
SeedRnd MilliSecs()
Img_Logo = LoadImage("Media\DSS_LOGO.png")
MidHandle Img_Logo
Cam = CreateCamera()
CameraRange Cam, .1, 10000
CamPiv = CreatePivot()
InitShadows(Cam)
End Function

Function StartProgram(center_mouse = False)
If center_mouse Then MoveMouse GraphicsWidth() / 2, GraphicsHeight() / 2
FlushMouse()
FlushKeys()
End Function

Const framePeriod = 13
Global frameTime = MilliSecs() - framePeriod
Function UpdateProgram(Cam, freelook_speed# = 0)
Repeat
	frameElapsed = MilliSecs() - frameTime
Until frameElapsed
frameTicks = frameElapsed / framePeriod
;Render
gw = GraphicsWidth()
gh = GraphicsHeight()
h = 90
ho = gh - h
CameraViewport Cam, 0, h, gw, gh - h * 2
If KeyHit(2) Then render_mode = 0 ElseIf KeyHit(3) Then render_mode = 1 ElseIf KeyHit(4) Then render_mode = 2
Cls
Render(render_mode, frameTicks)
DrawImage Img_Logo, gw / 2, h / 2
Text 10, ho + 10, "FPS: " + GetFPS()
Text 10, ho + 25, "Render time: " + Debug_TimeRender
Text 10, ho + 40, "InitVolume time: " + Debug_TimeInitVolumes
Text 10, ho + 55, "Volume time: " + Debug_TimeVolumesBuilt
Text 10, ho + 70, "Shadow Polys: " + Debug_ShadowPolys + " (" + Debug_ShadowCastersPolys + ")"
txt1$ = "Press keys 1-3 to toggle the render modes."
If freelook_speed# > 0 Then txt2$ = "Use A,S,W,D and the mouse to move arround."
sw = StringWidth(txt1$)
If StringWidth(txt2$) > sw Then sw = StringWidth(txt2$)
Text gw - sw - 10, ho + 10, txt1$
Text gw - sw - 10, ho + 25, txt2$
Flip 0
;Update
For frameLimit = 1 To frameTicks
	If frameLimit = frameTicks Then CaptureWorld
	frameTime = frameTime + framePeriod
	UpdateSample()
	;Freelook
	If freelook_speed# <> 0 Then
		FreeLook(freelook_speed# * .5)
		HidePointer
	Else
		ShowPointer
	EndIf
Next
End Function

Global FPS, FPS_temp, FPS_time
Function GetFPS()
ctime = MilliSecs()
FPS_temp = FPS_temp + 1
If ctime - FPS_time > 500 Then
	FPS = FPS_temp * 2
	FPS_temp = 0
	FPS_time = ctime
EndIf
Return FPS
End Function

Global CamXS#, CamZS#, CamRotXS#, CamRotYS#
Function FreeLook(sp# = .1)
If sp# > 0 Then
	CamXS# = (CamXS# + ((KeyDown(32) Or KeyDown(205)) - (KeyDown(30) Or KeyDown(203))) * sp#) * .75
	CamZS# = (CamZS# + ((KeyDown(17) Or KeyDown(200)) - (KeyDown(31) Or KeyDown(208))) * sp#) * .75
	MoveEntity Cam, CamXS#, 0, CamZS#
EndIf
CamRotXS# = ((MouseXSpeed() - CamRotXS#) * .35 + CamRotXS#) * .75
CamRotYS# = ((MouseYSpeed() - CamRotYS#) * .35 + CamRotYS#) * .75
If EntityPitch(Cam) + CamRotYS# < -85 pitch# = -85 ElseIf EntityPitch(Cam) + CamRotYS# > 85 pitch# = 85 Else pitch# = EntityPitch(Cam) + CamRotYS#
yaw# = -CamRotXS# + EntityYaw(Cam)
RotateEntity Cam, pitch#, yaw#, 0
MoveMouse GraphicsWidth() / 2, GraphicsHeight() / 2
End Function