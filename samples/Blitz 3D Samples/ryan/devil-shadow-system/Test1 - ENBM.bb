Include "SampleFunctions.bb"

InitGraphics()

;Camera
PositionEntity Cam, 4, 5, 4
PointEntity Cam, CreatePivot()

;Shadows
SetENBMHeight(1)

;Sky
c = CreateShadowSphere(5, False)
ScaleEntity c, -1000, -1000, -1000
EntityTexture c, LoadTexture("Media\Island\Skybox.jpg", 385)
EntityFX c, 9

;Light
Light = CreateShadowLight()
PositionEntity Light, 5, 5, 0
PointEntity Light, CreatePivot()

;Floor
floor_bump = LoadTexture("Media\Rock_Bump.jpg")
ScaleTexture floor_bump, 20, 20
c = CreateWater(floor_bump, 100)
ScaleEntity c, .2, 1, .2
floor_tex = LoadTexture("Media\Rock.jpg")
ScaleTexture floor_tex, 20, 20
EntityTexture c, floor_tex, 0, 3

;Columns
tex = LoadTexture("Media\Wood.jpg")
ScaleTexture tex, 1.5, .5
s = 2
For x = -s To s Step 2
	For z = -s To s Step 2
		If Abs(x) = s Or Abs(z) = s Then
			c = CreateShadowCylinder(16, False)
			ScaleEntity c, .5, 1.5, .5
			PositionEntity c, x * 3, 1.5, z * 3
			EntityTexture c, tex
		EndIf
	Next
Next

;Beethovens
CubeTex = LoadTexture("Media\Cubemap.png", 128)
t1 = LoadTexture("Media\WoodPanel.jpg")
t2 = LoadTexture("Media\WoodPanel_Bump.jpg")
cnt = -1
Dim Beethoven(3)
For x = -1 To 1 Step 2
	For z = - 1 To 1 Step 2
		cnt = cnt + 1
		c = LoadShadowMesh("Media\Beethoven.b3d", False)
		ScaleEntity c, .25, .25, .25
		PositionEntity c, x * 3, 3, z * 3
		EntityTexture c, t1, 0, 3
		SetENBMMesh(c, CubeTex, t2)
		Beethoven(cnt) = c
	Next
Next

;Guy
Global Guy = LoadShadowMesh("Media\Hellknight.b3d", False)
ScaleEntity Guy, .03, .03, .03
PositionEntity Guy, 0, 3.2, 0
EntityTexture Guy, LoadTexture("Media\Hellknight.jpg"), 0, 3
SetENBMMesh(Guy, CubeTex, LoadTexture("Media\Hellknight_Bump.jpg"))

;Ball
c = CreateShadowSphere(16, False)
ScaleEntity c, 2.5, 1.25, 2.5
t1 = LoadTexture("Media\Metal.jpg")
ScaleTexture t1, .25, .25
t2 = LoadTexture("Media\Metal_Bump.jpg")
ScaleTexture t2, .25, .25
EntityTexture c, t1, 0, 3
SetENBMMesh(c, CubeTex, t2)

StartProgram(True)
While Not KeyHit(1)
	UpdateWater()
	UpdateProgram(Cam, .1)
Wend
FreeShadows()
End

Function UpdateSample()
frame = frame + 1
For i = 0 To 3
	RotateEntity Beethoven(i), frame * .1, frame * .1, frame * .1
Next
TurnEntity Guy, 0, .1, 0
End Function