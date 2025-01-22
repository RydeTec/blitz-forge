Function CreateShadowCube(casting = True, path$ = "***")
c = CreateCube()
SetShadowMesh(c, casting, path$)
Return c
End Function

Function CreateShadowSphere(seg = 8, casting = True, path$ = "***")
c = CreateSphere(seg)
SetShadowMesh(c, casting, path$)
Return c
End Function

Function CreateShadowCylinder(seg = 8, casting = True, path$ = "***")
c = CreateCylinder(seg)
SetShadowMesh(c, casting, path$)
Return c
End Function

Function CreateShadowCone(seg = 8, casting = True, path$ = "***")
c = CreateCone(seg)
SetShadowMesh(c, casting, path$)
Return c
End Function

Function CreateShadowPlane(seg = 1)
c = CreatePlane(seg)
SetShadowMesh(c, False)
Return c
End Function

Function LoadShadowMesh(mesh_path$, casting = True, path$ = "***")
c = LoadMesh(mesh_path$)
SetShadowMesh(c, casting, path$)
Return c
End Function

Function LoadAnimShadowMesh(mesh_path$, casting = True, path$ = "***")
c = LoadAnimMesh(mesh_path$)
SetShadowMesh(c, casting, path$)
Return c
End Function

Function CreateShadowLight(typ = 1, parallel = False, range# = -1)
c = CreateLight(typ)
SetShadowLight(c, parallel, range#)
Return c
End Function




Type Water
	Field ent, bump
End Type
Dim WaterVertex(100, 100)
Global WaterCam, WaterTex

Function InitWater()
WaterCam = CreateCamera()
CameraRange WaterCam, .1, 1000000
HideEntity WaterCam
WaterTex = CreateTexture(512, 512, 385)
End Function

Function FreeWater()
If WaterCam Then FreeEntity WaterCam
If WaterTex Then FreeTexture WaterTex
For w.Water = Each Water
	If w\ent Then FreeEntity w\ent
	If w\bump Then FreeTexture w\bump
	Delete w
Next
End Function

Function CreateWater(bump, size = 100, round = False)
w.Water = New Water
w\ent = CreateMesh()
surf = CreateSurface(w\ent)
For x = 0 To size
	For z = 0 To size
		xx = x - size / 2
		zz = z - size / 2
		If Sqr(xx * xx + zz * zz) < size * .5 + 2 Or round = False Then WaterVertex(x, z) = AddVertex(surf, x, 0, z, x, z)
	Next
Next
For x = 0 To size - 1
	For z = 0 To size - 1
		xx = x - size / 2
		zz = z - size / 2
		If Sqr(xx * xx + zz * zz) < size * .5 Or round = False Then
			AddTriangle surf, WaterVertex(x, z), WaterVertex(x, z + 1), WaterVertex(x + 1, z)
			AddTriangle surf, WaterVertex(x + 1, z + 1), WaterVertex(x + 1, z), WaterVertex(x, z + 1)
		EndIf
	Next
Next
PositionMesh w\ent, -size * .5, 0, -size * .5
EntityFX w\ent, 1
UpdateNormals w\ent
SetShadowMesh(w\ent, False)
w\bump = bump
SetENBMMesh(w\ent, WaterTex, w\bump)
Return w\ent
End Function

Function UpdateWater()
HideEntity ShadowCam
For w.Water = Each Water
	RenderCubemap_Flat(WaterTex, WaterCam, ShadowCam)
	Exit
Next
ShowEntity ShadowCam
End Function

Function RenderCubemap_Flat(tex, camera, entity)
tex_sz = TextureWidth(tex)
ShowEntity camera
HideEntity entity
PositionEntity camera, EntityX(entity, True), -EntityY(entity, True), EntityZ(entity, True), True
CameraViewport camera, 0, 0, tex_sz, tex_sz
SetCubeFace tex, 0: RotateEntity camera, 0, 90, 0:  Render(): CopyRect 0, 0, tex_sz, tex_sz, 0, 0, BackBuffer(), TextureBuffer(tex)
SetCubeFace tex, 1: RotateEntity camera, 0, 0, 0:   Render(): CopyRect 0, 0, tex_sz, tex_sz, 0, 0, BackBuffer(), TextureBuffer(tex)
SetCubeFace tex, 2: RotateEntity camera, 0, -90, 0: Render(): CopyRect 0, 0, tex_sz, tex_sz, 0, 0, BackBuffer(), TextureBuffer(tex)
SetCubeFace tex, 3: RotateEntity camera, 0, 180, 0: Render(): CopyRect 0, 0, tex_sz, tex_sz, 0, 0, BackBuffer(), TextureBuffer(tex)
SetCubeFace tex, 4: RotateEntity camera, -90, 0, 0: Render(): CopyRect 0, 0, tex_sz, tex_sz, 0, 0, BackBuffer(), TextureBuffer(tex)
ShowEntity entity
HideEntity camera
End Function