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
SetENBMHeight(5) ;<=== The heigth of ENBM. Change this parameter and see!

;Light
Light = CreateLight()
PositionEntity Light, 10, 10, 0
PointEntity Light, CreatePivot()
SetShadowLight(Light)

;Floor
c = CreateCube()
ScaleEntity c, 5, 1, 5
EntityColor c, 0, 0, 255
SetShadowMesh(c, False)

;ENBM Object
Caster = CreateSphere(16)
ScaleEntity Caster, 2, 2, 2
PositionEntity Caster, 0, 3, 0
SetShadowMesh(Caster)

cubemap = LoadTexture("..\Media\Skybox.jpg", 385) ;just a cubemap
bumpmap = LoadTexture("..\Media\Rock_Bump.jpg") ;the bump map.
ScaleTexture bumpmap, .25, .25
SetENBMMesh(Caster, cubemap, bumpmap) ;applying it to the shadow caster!

;ENBM(Environmental bump mapping) is similar to dot3 bump mapping.
;but you are able to diffuse refleaction maps like cubemaps or
;spheremaps.


;Main loop
While Not KeyHit(1)
	TurnEntity Caster, .5, .25, .75
	Render(1)
	Flip
Wend
FreeShadows()
End