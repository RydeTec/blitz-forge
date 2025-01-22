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

;Caster
Caster = LoadMesh("..\Media\Teapot.b3d")
PositionEntity Caster, 0, 2, 0
SetShadowMesh(Caster, True, "Teapot.shw")

;What is 'shadow file caching'?
;Well, before a mesh can cast a shadow, the shadow system needs to initialize it.
;This takes long, because we need to know all the 3 neighbour triangles of each
;triangle in the mesh. So he has to scan each triangle for each triangle! However,
;After he is ready with this

;The first time you run this sample, it takes a few secounds, while he is
;automaticly creating the *.shw file. Then, he just initializes the shadow
;caster by reading out the informations from the file. If you run it again, he
;won't take that long.

;Main loop
While Not KeyHit(1)
	TurnEntity Caster, 0, 1.5, 0
	Render(1)
	Flip
Wend
FreeShadows()
End