;Note: the global commands like Render() has already
;been explained in the previous tutorial. No big
;explinations here anymore.

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

;Animated caster
Caster = LoadAnimMesh("..\Media\Mak.3ds")
ScaleEntity Caster, .1, .1, .1
PositionEntity Caster, 2, 1.2, 0
Animate Caster, 1, .5
SetShadowMesh(Caster)

;Note: This shadow system doesn't matter about, whether it is animated or not.
;Just use SetShadowMesh() for animated and not animated objects.
;The other issue is, that b3d animations are not working. Only 3ds/x anims!

;Main loop
While Not KeyHit(1)
	TurnEntity Caster, 0, .5, 0
	Render(1, 1) ;the secont parameter here is the animation step. Replace it with 2 and see what happens :)
	Flip
Wend
FreeShadows()
End