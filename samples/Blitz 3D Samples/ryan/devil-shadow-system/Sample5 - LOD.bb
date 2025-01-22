Include "SampleFunctions.bb"

InitGraphics()
AmbientLight 50, 50, 50

;Camera
PositionEntity Cam, 7, 6, -2
PointEntity Cam, CreatePivot()
EntityType Cam, BODY
EntityRadius Cam, .15

;Collisions
Const BODY = 1
Const SCENE = 2
Collisions BODY, SCENE, 2, 3

;Lights
Global Light = CreateShadowLight(2)
PositionEntity Light, 0, 10, 0
LightRange Light, 7
LightColor Light, 255, 240, 200
LightConeAngles Light, 45, 50

;Scene
c = LoadShadowMesh("Media\Scene_Optimized.b3d", True, "Media\Scene_Optimized.shw")
EntityAlpha c, 0
ScaleEntity c, .05, .05, .05
c = LoadShadowMesh("Media\Scene.b3d", False)
ScaleEntity c, .05, .05, .05
EntityType c, SCENE

;Some lights
l = CreateLight(3)
LightColor l, 150, 255, 150
PositionEntity l, 0, 2.5, 4
LightRange l, 2
RotateEntity l, 90, 0, 0

StartProgram(True)
While Not KeyHit(1)
	UpdateProgram(Cam, .1)
Wend
FreeShadows()
End

Function UpdateSample()
frame = frame + 1
PositionEntity Light, Cos(frame) * 6, 7, Sin(frame) * 6 + 1
End Function