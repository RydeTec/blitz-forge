Include "SampleFunctions.bb"

InitGraphics()

;Camera
PositionEntity Cam, 5, 3, -5
RotateEntity Cam, 20, 0, 0
EntityType Cam, BODY
EntityRadius Cam, .2

;Collisions
Const BODY = 1
Const SCENE = 2
Collisions BODY, SCENE, 2, 3

;Sky
c = CreateShadowSphere(5, False)
ScaleEntity c, -1000, -1000, -1000
EntityTexture c, LoadTexture("Media\Island\Skybox.jpg", 385)
EntityFX c, 9

;Water
Global WaterBump = LoadTexture("Media\Island\WaterBump.png")
ScaleTexture WaterBump, 15, 15
CreateWater(WaterBump, 100, True)

;Terrain
c = LoadMesh("Media\Island\Island.b3d")
Global Terrain = ClipMesh(c, -10)
FreeEntity c
ScaleMesh Terrain, .1, .1, .1
PositionEntity Terrain, -40, .5, -40
EntityType Terrain, SCENE
SetShadowMesh(Terrain, False)

;Light
Light = CreateShadowLight()
PositionEntity Light, 1, 1, 1
PointEntity Light, CreatePivot()

MoveMouse GraphicsWidth() / 2, GraphicsHeight() / 2
FlushMouse()
While Not KeyHit(1)
	UpdateWater()
	UpdateProgram(Cam, .1)
Wend
FreeShadows()
End

Function UpdateSample()
frame = frame + 7
PositionTexture WaterBump, frame * .0002, frame * .0002
If EntityY(Cam) < 1 Then PositionEntity Cam, EntityX(Cam), 1, EntityZ(Cam)
End Function

Function ClipMesh(mesh, planey# = 0)
Local nmesh = CreateMesh()
Local vertex[2], nvertex[2]
cnt_surf = CountSurfaces(mesh)
For s = 1 To cnt_surf
	surf = GetSurface(mesh, s)
	nsur = CreateSurface(nmesh, GetSurfaceBrush(surf))
	cnt_tris = CountTriangles(surf) - 1
	For t = 0 To cnt_tris
		ndel = 0
		For v = 0 To 2
			vertex[v] = TriangleVertex(surf, t, v)
			If VertexY(surf, vertex[v]) > planey# Then ndel = 1
		Next
		If ndel = 1 Then
			For v = 0 To 2
				nvertex[v] = AddVertex(nsur, VertexX(surf, vertex[v]), VertexY(surf, vertex[v]), VertexZ(surf, vertex[v]), VertexU(surf, vertex[v]), VertexV(surf, vertex[v]), VertexW(surf, vertex[v]))
				VertexNormal nsur, nvertex[v], VertexNX(surf, vertex[v]), VertexNY(surf, vertex[v]), VertexNZ(surf, vertex[v])
				VertexColor nsur, nvertex[v], VertexRed(surf, vertex[v]), VertexGreen(surf, vertex[v]), VertexBlue(surf, vertex[v]), VertexAlpha(surf, vertex[v])
			Next
			AddTriangle nsur, nvertex[0], nvertex[1], nvertex[2]
		EndIf
	Next
Next
Return nmesh
End Function