Include "SampleFunctions.bb"

InitGraphics(800, 600)
AmbientLight 20, 20, 20

;Camera
PositionEntity Cam, 3, 0, 3
PointEntity Cam, CamPiv
EntityType Cam, BODY
EntityRadius Cam, .2

;Collisions
Const BODY = 1
Const SCENE = 2
Collisions BODY, SCENE, 2, 3

;Scene
LoadScene()

;Light
Global Light = CreateShadowLight(3)
LightColor Light, 165, 210, 255
LightRange Light, 40
c = CreateShadowCone(8, False)
ScaleEntity c, .1, .1, .1
RotateEntity c, 90, 0, 0
EntityFX c, 1
EntityParent c, Light

StartProgram(True)
While Not KeyHit(1)
	UpdateProgram(Cam, .07)
Wend
FreeShadows()
End

Function UpdateSample()
frame = frame - 1
PositionEntity Light, -Cos(frame * .75) * 8.5, 1.5, -Sin(frame * .75) * 8.5
PointEntity Light, CamPiv
End Function

Function LoadScene()
;Room
c = LoadShadowMesh("Media\Fountain\Room.b3d", False)
RotateEntity c, -90, 0, 0
EntityShininess c, .1
EntityType c, SCENE
;Fountain
c = LoadShadowMesh("Media\Fountain\Fountain.b3d", True, "Media\Fountain\Fountain.shw")
RotateEntity c, -90, 0, 0
EntityShininess c, .5
EntityType c, SCENE
;Railing
c = LoadShadowMesh("Media\Fountain\Railing.b3d", True, "Media\Fountain\Railing.shw")
RotateEntity c, -90, 0, 0
EntityShininess c, .5
EntityType c, SCENE
;Corner lights
For x = -1 To 1 Step 2
	For z = -1 To 1 Step 2
		l = CreateLight(2)
		LightRange l, 2
		LightColor l, 255, 200, 155
		PositionEntity l, x * 8, -.5, z * 8 - (z < 0)
	Next
Next
End Function