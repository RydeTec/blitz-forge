;Include all the includes for the shadow system
Include "..\Includes\DevilShadowSystem.bb"
Include "..\Includes\ShadowVolumes.bb"
Include "..\Includes\UserInterface.bb"

;Setup graphics
Graphics3D 1024, 768, 32, 2
SetBuffer BackBuffer()

;Camera
Cam = CreateCamera()
PositionEntity Cam, 0, 3, -8

;Init shadows with the player camera.
;Note: Before rendering all other cameras, like cubemapping cameras must be hidden!
InitShadows(Cam)

;Light
Light = CreateLight()
PositionEntity Light, 10, 10, 0
PointEntity Light, CreatePivot()
SetShadowLight(Light) ;a shadow light
;the second parameter(optional) decides about if this light is parallel

;Floor
c = CreateCube()
ScaleEntity c, 5, 1, 5
EntityColor c, 0, 0, 255
SetShadowMesh(c, False) ;shadow receiver(false = receiver)

;Caster
Caster = CreateSphere()
PositionEntity Caster, 3, 3, 0
EntityColor Caster, 255, 0, 0
SetShadowMesh(Caster) ;shadow caster(false = caster)

;Main loop
While Not KeyHit(1)
	TurnEntity Caster, 1, .5, 1.5
	
	;Use Render() insted of RenderWorld()
	;Don't ever use RenderWorld. Not even in the cubemap rendering proccess!
	;Render(mode = 0, anim_tween#
	;mode 0 = shadows and all other stuff desabled.
	;mode 1 = shadows and all other stuff enabled.
	;mode 2 = debug mode; volumes visible only. (For advanced users only!)
	;anim_tween# is the animation step for UpdateWorld
	;=> UpodateWorld(anim_tween#)
	Render(1)
	
	;Replace the parameter at Render() to see the effect. (possible parameters : 0;1;2)
	
	Flip
Wend
FreeShadows()
End