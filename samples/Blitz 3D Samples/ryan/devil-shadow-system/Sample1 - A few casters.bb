Include "SampleFunctions.bb"

InitGraphics()
AmbientLight 70, 80, 100

;Camera
PositionEntity Cam, 3, 5, 3
RotateEntity Cam, 20, 145, 0
CameraClsColor Cam, 60, 120, 255

;Floor
c = CreateShadowCube(False)
ScaleEntity c, 10, 1, 10
floor_tex = LoadTexture("Media\Brick.jpg")
ScaleTexture floor_tex, .2, .2
EntityTexture c, floor_tex

;Casters
Dim Ent(4)
tex = LoadTexture("Media\Wood.jpg")
For i = 0 To 4
	Select i
		Case 0: Ent(i) = CreateShadowCube()
		Case 1: Ent(i) = CreateShadowSphere()
		Case 2: Ent(i) = CreateShadowCylinder(12)
		Case 3: Ent(i) = CreateShadowCone(12)
		Case 4: Ent(i) = LoadShadowMesh("Media\Teapot.b3d", True, "Media\Teapot.shw")
	End Select
	PositionEntity Ent(i), (i - 2) * 3, 3, -5
	EntityTexture Ent(i), tex
Next
;Teapot
Global Tea = LoadShadowMesh("Media\Teapot.b3d", True, "Media\Teapot.shw")
EntityTexture Tea, tex
ScaleEntity Tea, 1.6, 1.6, 1.6
PositionEntity Tea, -1, 2.4, 1.5
EntityShininess Tea, 1
;Mak
Global Mak = LoadAnimShadowMesh("Media\Mak.3ds", True, "Media\Mak.shw")
ScaleEntity Mak, .1, .1, .1
PositionEntity Mak, 3, 1.2, 0
Animate Mak, 1, .3

;Light
Light = CreateShadowLight(1, True)
PositionEntity Light, 10, 10, 0
PointEntity Light, CreatePivot()
LightColor Light, 255, 255, 195

StartProgram(True)
While Not KeyHit(1)
	UpdateProgram(Cam, .06)
Wend
FreeShadows()
End

Function UpdateSample()
TurnEntity Mak, 0, .35, 0
TurnEntity Tea, 0, -1, 0
For i = 0 To 4
	TurnEntity Ent(i), 1, .5, 1.5
Next
End Function