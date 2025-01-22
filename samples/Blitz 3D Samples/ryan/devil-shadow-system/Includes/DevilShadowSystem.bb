Type ShadowMesh
	Field ent, casting
End Type
Type ShadowLight
	Field ent, parallel, range#
End Type
Type ENBMMesh
	Field ent, tex, bump
End Type
Type DX7_Matrix
	Field m1#, m2#, m3#, m4#
	Field m5#, m6#, m7#, m8#
	Field m9#, m10#, m11#, m12#
	Field m13#, m14#, m15#, m16#
End Type
Global ShadowCam, ShadowPlane, BumpColorTex
Global Debug_TimeRender, Debug_TimeInitVolumes, Debug_TimeVolumesBuilt, Debug_ShadowCastersPolys, Debug_ShadowPolys
Global ShadowTex, ShadowTexWidth, ShadowTexHeight

Function InitShadows(Cam)
;Shadow Plane
ShadowCam = Cam
;ShadowPlane
ShadowPlane = CreateSprite()
EntityParent ShadowPlane, ShadowCam, 0
EntityFX ShadowPlane, 1
gw = GraphicsWidth()
gh = GraphicsHeight()
ShadowTex = CreateTexture(gw, gh, 257)
w = TextureWidth(ShadowTex) - gw
h = TextureHeight(ShadowTex) - gh
ratio# = gh * 4 / gw
ScaleSprite ShadowPlane, 4 + w * 4.0 / gw, ratio# + h * ratio# / gh
ShadowTexWidth = w * .5
ShadowTexHeight = h * .5
EntityTexture ShadowPlane, ShadowTex
;Volumes
InitVolumes()
;Init DirectX7
If DX7_SetSystemProperties(SystemProperty("Direct3D7"), SystemProperty("Direct3DDevice7"), SystemProperty("DirectDraw7"), SystemProperty("AppHWND"), SystemProperty("AppHINSTANCE")) Then RuntimeError "Error initializing dx7."
If DX7_GetStencilBitDepth() < 8 Then
	DX7_CreateStencilBuffer()
	If DX7_GetStencilBitDepth() < 8 Then RuntimeError "Graphic card does not support stencil buffers."
End If
;ENBM
If DX7_SupportsBumpMapping() = False Or DX7_SupportsLumiBumpMapping() = False Then RuntimeError("Graphic card does not support bump mapping.")
BumpColorTex = CreateTexture(1, 1)
;Misc
Debug_TimeInitVolumes = 0
SetENBMHeight(.1)
InitWater()
End Function

Function FreeShadows()
DX7_RemoveSystemProperties()
FreeVolumes()
FreeWater()
Delete Each ShadowMesh
Delete Each ShadowLight
Delete Each ENBMMesh
End Function

Function SetENBMHeight(bump_height#)
DX7_SetBumpInfo bump_height#, -bump_height#, -bump_height#, bump_height#, 1, 0, 0
End Function

Function SetShadowLight(ent, parallel = False, range# = -1)
Delete Each ShadowLight
sl.ShadowLight = New ShadowLight
sl\ent = ent
sl\parallel = parallel
sl\range# = range#
End Function

Function SetShadowMesh(ent, casting = True, path$ = "***")
If casting Then
	ms = MilliSecs()
	RecursiveShadowCaster(ent, path$)
	Debug_TimeInitVolumes = Debug_TimeInitVolumes + MilliSecs() - ms
Else
	sm.ShadowMesh = New ShadowMesh
	sm\ent = ent
EndIf
End Function

Function DeleteShadowMesh(ent)
For sm.ShadowMesh = Each ShadowMesh
	If sm\ent = ent Then
		If sm\casting Then
			For etem.ETE_Mesh = Each ETE_Mesh
				If etem\ent = ent Then
					For v = 0 To etem\cnt_tris
						Delete etem\tri[v]
					Next
					Delete etem
				EndIf
			Next
			cnt_children = CountChildren(ent)
			If cnt_children > 0 Then
				For i = 1 To cnt_children
					DeleteShadowMesh(GetChild(ent, i))
				Next
			EndIf
		EndIf
		Delete sm
	EndIf
Next
End Function

Function SetENBMMesh(ent, tex, bump, bump_frame = 0)
For enbm.ENBMMesh = Each ENBMMesh
	If enbm\ent = ent Then
		Intern_SetENBMMesh(enbm, ent, tex, bump, bump_frame)
		Return
	EndIf
Next
Intern_SetENBMMesh(Null, ent, tex, bump, bump_frame)
End Function

Function Render(mode = 0, anim_tween# = 0)
Debug_ShadowCastersPolys = 0
For etem.ETE_Mesh = Each ETE_Mesh
	Debug_ShadowCastersPolys = Debug_ShadowCastersPolys + etem\cnt_tris + 1
Next
Select mode
	Case 0, 2
		EntityAlpha VolumeMesh, .5
		EntityAlpha VolumeCap, .5
		DX7_DisableRenderBumpTexture()
		ClearSurface VolumeSurf
		ClearSurface VolumeCapSurf
		For sm.ShadowMesh = Each ShadowMesh
			ShowEntity sm\ent
		Next
		HideEntity ShadowPlane
		CameraClsMode ShadowCam, True, True
		ms = MilliSecs()
		UpdateWorld anim_tween#
		If mode = 2 Then
			BuildVolumes(False)
			ShowEntity VolumeMesh
			EntityFX VolumeMesh, 17
			EntityAlpha VolumeMesh, .1
		Else
			Debug_TimeVolumesBuilt = 0
			Debug_ShadowPolys = 0
		EndIf
		RenderWorld
		Debug_TimeRender = MilliSecs() - ms
	Case 1
		EntityAlpha VolumeMesh, .0001
		EntityAlpha VolumeCap, .0001
		DX7_EnableRenderBumpTexture()
		EntityFX VolumeMesh, 0
		ms = MilliSecs()
		RenderShadows(anim_tween#)
		Debug_TimeRender = MilliSecs() - ms - Debug_TimeVolumesBuilt
	Default
		RuntimeError "Wrong render mode."
End Select
End Function

Const SOP_KEEP = 1, SOP_ZERO = 2, SOP_REPLACE = 3, SOP_INCRSAT = 4, SOP_DECRSAT = 5, SOP_INVERT = 6, SOP_INCR = 7, SOP_DECR = 8
Const CMP_NEVER = 1, CMP_LESS = 2, CMP_EQUAL = 3, CMP_LESSEQUAL = 4, CMP_GREATER = 5, CMP_NOTEQUAL = 6, CMP_GREATEREQUAL = 7, CMP_ALWAYS = 8
Function RenderShadows(anim_tween#)
PositionEntity ShadowPlane, 0, 0, 4 * BB_GetCamZoom(ShadowCam)
HideEntity ShadowPlane
HideEntity VolumeMesh
SetShadowMeshStates(True)
CameraClsMode ShadowCam, True, True
UpdateWorld anim_tween#
HideEntity ShadowPlane
SetShadowLightStates(False)
DX7_InfiniteFarClipPlane()
RenderWorld
SetShadowLightStates(True)
CopyRect 0, 0, GraphicsWidth(), GraphicsHeight(), ShadowTexWidth, ShadowTexHeight, 0, TextureBuffer(ShadowTex)
CameraClsMode ShadowCam, False, False
BuildVolumes()
DX7_DeviceClear 7, BB_GetCamClsRed(ShadowCam) * $10000 + BB_GetCamClsGreen(ShadowCam) * $100 + BB_GetCamClsBlue(ShadowCam), 1, 0
DX7_DisableStencil
HideEntity VolumeMesh
HideEntity VolumeCap
SetShadowMeshStates(True)
RenderWorld
SetShadowMeshStates(False)
DX7_EnableStencil
DX7_UnlockZWrites
DX7_UnlockZBuffer
DX7_DisableZWrites
DX7_EnableZBuffer
DX7_LockZWrites
DX7_LockZBuffer
DX7_EnableAlphaBlending
DX7_SetSrcBlendMode 1
DX7_SetDestBlendMode 2
ShowEntity VolumeMesh
ShowEntity VolumeCap
DX7_SetStencilFunc CMP_ALWAYS
DX7_SetStencilFail SOP_KEEP
DX7_SetStencilPass SOP_KEEP
DX7_SetStencilRef 0
DX7_SetStencilMask $FFFFFFFF
DX7_SetStencilWriteMask $FFFFFFFF
DX7_SetStencilZFail SOP_INCR
DX7_SetCullMode 2
RenderWorld
DX7_SetStencilZFail SOP_DECR
DX7_SetCullMode 3
RenderWorld
HideEntity VolumeMesh
DX7_SetZFunc CMP_GREATEREQUAL
DX7_SetStencilFunc CMP_ALWAYS
RenderWorld
HideEntity VolumeCap
DX7_UnlockAlphaBlending
DX7_EnableAlphaBlending
DX7_SetSrcBlendMode 5
DX7_SetDestBlendMode 6
SetShadowLightStates(False)
ShowEntity ShadowPlane
DX7_UnlockZBuffer
DX7_UnlockZWrites
DX7_DisableZBuffer
DX7_DisableZWrites
DX7_LockZBuffer
DX7_LockZWrites
DX7_SetStencilFunc CMP_LESSEQUAL
DX7_SetStencilRef 1
RenderWorld
SetShadowLightStates(True)
DX7_SetZFunc CMP_LESS
DX7_UnlockZBuffer
DX7_UnlockZWrites
DX7_EnableZBuffer
DX7_EnableZWrites
DX7_DisableStencil
DX7_UnlockZWrites
DX7_UnlockZBuffer
HideEntity ShadowPlane
End Function

Function RecursiveShadowCaster.ShadowMesh(ent, path$, file = -1)
If FileType(path$) = 1 Then
	LoadVolumeCaster(ent, path$)
Else
	If file = -1 And path$ <> "***" Then
		If Right(path$, 4) <> ".shw" Then path$ = path$ + ".shw"
		file = WriteFile(path$)
		WriteString file, SHWCacheVersion$
	EndIf
	sm.ShadowMesh = New ShadowMesh
	sm\ent = ent
	sm\casting = True
	etem.ETE_Mesh = InitVolumeCaster(sm\ent)
	If file <> - 1 Then
		cnt_tris = etem\cnt_tris
		WriteInt file, cnt_tris
		For v = 0 To cnt_tris
			etet.ETE_Triangle = etem\tri[v]
			WriteFloat file, etet\v1x#
			WriteFloat file, etet\v1y#
			WriteFloat file, etet\v1z#
			WriteFloat file, etet\v2x#
			WriteFloat file, etet\v2y#
			WriteFloat file, etet\v2z#
			WriteFloat file, etet\v3x#
			WriteFloat file, etet\v3y#
			WriteFloat file, etet\v3z#
			WriteShort file, etet\ta + 1
			WriteShort file, etet\tb + 1
			WriteShort file, etet\tc + 1
		Next
	EndIf
	cnt_children = CountChildren(ent)
	If cnt_children = 0 Then Return
	For i = 1 To cnt_children
		RecursiveShadowCaster(GetChild(ent, i), "***", file)
	Next
EndIf
End Function

Function BuildVolumes(top_caps = True)
ms = MilliSecs()
ClearSurface VolumeSurf
ClearSurface VolumeCapSurf
UpdateVolumes(First ShadowLight, top_caps)
Debug_ShadowPolys = CountTriangles(VolumeSurf) + CountTriangles(VolumeCapSurf)
Debug_TimeVolumesBuilt = MilliSecs() - ms
End Function

Function SetShadowMeshStates(show)
If show Then
	For sm.ShadowMesh = Each ShadowMesh
		ShowEntity sm\ent
	Next
Else
	For sm.ShadowMesh = Each ShadowMesh
		HideEntity sm\ent
	Next
EndIf
End Function

Function SetShadowLightStates(show)
sl.ShadowLight = First ShadowLight
If show Then ShowEntity sl\ent Else HideEntity sl\ent
End Function

Function Intern_SetENBMMesh(enbm.ENBMMesh, ent, tex, bump, bump_frame)
If enbm = Null Then enbm.ENBMMesh = New ENBMMesh
enbm\ent = ent
enbm\tex = tex
enbm\bump = bump
TextureBlend enbm\tex, 3
EntityTexture enbm\ent, BumpColorTex
EntityTexture enbm\ent, enbm\bump, bump_frame, 1
EntityTexture enbm\ent, enbm\tex, 0, 2
End Function

Function DX7_InfiniteFarClipPlane()
m.DX7_Matrix = New DX7_Matrix
DX7_GetTransform 3, m
m\m11# = 1.0
m\m15# = 1.0
;DX7_SetTransform 3, m
;Delete m
End Function