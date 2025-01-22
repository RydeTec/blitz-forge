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
Light = CreateShadowLight(1, True)
PositionEntity Light, 5, 5, 3
PointEntity Light, CreatePivot()

;Floor
floor_bump = LoadTexture("Media\Rock_Bump.jpg")
ScaleTexture floor_bump, 20, 20
c = CreateWater(floor_bump, 100)
ScaleMesh c, .2, 1, .2
floor_tex = LoadTexture("Media\Rock.jpg")
ScaleTexture floor_tex, 20, 20
EntityTexture c, floor_tex, 0, 3

;Floor overlay
c = CopyMesh(c)
PositionEntity c, 0, .01, 0
EntityAlpha c, .5
EntityBlend c, 2
SetShadowMesh(c, False)

;Columns
tex = LoadTexture("Media\Wood.jpg")
ScaleTexture tex, 1.5, .5
s = 2
For x = -s To s Step 2
	For z = -s To s Step 2
		If Abs(x) = s Or Abs(z) = s Then
			c = CreateShadowCylinder(16)
			ScaleEntity c, .5, 1.5, .5
			PositionEntity c, x * 3, 1.5, z * 3
			EntityTexture c, tex
		EndIf
	Next
Next

StartProgram(True)
While Not KeyHit(1)
	UpdateWater()
	UpdateProgram(Cam, .1)
Wend
FreeShadows()
End

Function UpdateSample()
frame = frame + 1
End Function