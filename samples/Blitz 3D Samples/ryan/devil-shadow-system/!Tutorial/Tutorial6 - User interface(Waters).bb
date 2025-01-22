Include "..\Includes\DevilShadowSystem.bb"
Include "..\Includes\ShadowVolumes.bb"
Include "..\Includes\UserInterface.bb"

Graphics3D 1024, 768, 32, 2
SetBuffer BackBuffer()

;Camera
Cam = CreateCamera()
PositionEntity Cam, 0, 4, -4

;Shadows
InitShadows(Cam)

;Light
Light = CreateShadowLight() ;<==
PositionEntity Light, 10, 10, 0
PointEntity Light, CreatePivot()

;Skybox(i mean skysphere!)
c = CreateShadowSphere(5, False)
ScaleEntity c, -1000, -1000, -1000
EntityTexture c, LoadTexture("..\Media\Island\Skybox.jpg", 385)
EntityFX c, 9

;The water
bumpmap = LoadTexture("..\Media\Island\WaterBump.png")
Water = CreateWater(bumpmap, 10)
PointEntity Cam, Water


;Main loop
While Not KeyHit(1)
	UpdateWater()
	;render the cubemaps for the water! If you have 2 waters, he
	;doesn't render the cubemaps twice!!
	
	;move the bumpmap texture
	frame = frame + 1
	PositionTexture bumpmap, frame * .005, frame * .005
	Render(1)
	Flip
Wend
FreeShadows()
End