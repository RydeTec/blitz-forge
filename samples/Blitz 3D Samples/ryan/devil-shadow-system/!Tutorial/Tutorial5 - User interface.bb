Include "..\Includes\DevilShadowSystem.bb"
Include "..\Includes\ShadowVolumes.bb"
Include "..\Includes\UserInterface.bb"

Graphics3D 1024, 768, 32, 2
SetBuffer BackBuffer()

;Camera
Cam = CreateCamera()
PositionEntity Cam, 0, 4, -8

;Shadows
InitShadows(Cam)
SetENBMHeight(1) ;<=== The heigth of ENBM. Change this parameter and see!

;Light
Light = CreateShadowLight() ;<==
PositionEntity Light, 10, 10, 0
PointEntity Light, CreatePivot()

;{
	;c1 = CreateSphere()
	;PositionEntity c1, -2, 2.5, 0
	;SetShadowMesh(c1)
	;c2 = CreateCylinder(12)
	;PositionEntity c2, 2, 2.5, 0
	;SetShadowMesh(c2)
	
	;yet, this above mess would be the traditional way to make objects casting shadows.
;}

;But there is an other solution:
c1 = CreateShadowSphere() ;<==
PositionEntity c1, -2, 2.5, 0
c2 = CreateShadowCylinder(12) ;<==
PositionEntity c2, 2, 2.5, 0

;CreateShadowCube() for example creates a cube, declares it as a shadow
;caster(or receiver or whatever you want...), and returns its entity's handle.
;there are a few more like LoadShadowMesh() or CreateShadowLight(), but see into
;the 'UserInterface.bb' file!

;Floor
c = CreateShadowCube() ;<== see?
ScaleEntity c, 5, 1, 5
EntityColor c, 0, 0, 255
SetShadowMesh(c, False)


;here is a sample function:
			;Function CreateShadowCube(casting = True, path$ = "***")
			;c = CreateCube()
			;SetShadowMesh(c, casting, path$)
			;Return c
			;End Function
;path$ means the shadow cache file path!

;Main loop
While Not KeyHit(1)
	TurnEntity c1, 1, .5, 1.5
	TurnEntity c2, 1, .5, 1.5
	Render(1)
	Flip
Wend
FreeShadows()
End