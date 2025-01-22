Include "SampleFunctions.bb"

InitGraphics()
AmbientLight 80, 80, 130

;Lights
Global LightPiv = CreatePivot()
PositionEntity LightPiv, 0, 16, 0
l = CreateShadowLight(2)
EntityParent l, LightPiv
PositionEntity l, 8, 0, 0
LightColor l, 255, 255, 240
LightRange l, 20

;Scene
LoadShadowMesh("Media\Swift.3ds", True, "Media\Swift.shw")

;Beethoven
Global Beethoven = LoadShadowMesh("Media\Beethoven.b3d", True, "Media\Beethoven.shw")
ScaleEntity Beethoven, .25, .25, .25
PositionEntity Beethoven, 2, 4, -2

StartProgram()
While Not KeyHit(1)
	UpdateProgram(Cam)
Wend
FreeShadows()
End

Function UpdateSample()
frame = frame + 6
PositionEntity Cam, Cos(frame * .1) * 17, 4.75, Sin(frame * .1) * 17
PointEntity Cam, CamPiv
TurnEntity LightPiv, .25, .5, 1
RotateEntity Beethoven, -frame * .3, frame * .3, frame * .3
End Function