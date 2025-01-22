Type ETE_Mesh
	Field ent
	Field cnt_tris, tri.ETE_Triangle[ETE_MaxTriang]
End Type
Type ETE_Triangle
	Field v1x#, v1y#, v1z#
	Field v2x#, v2y#, v2z#
	Field v3x#, v3y#, v3z#
	Field tf_v1x#, tf_v1y#, tf_v1z#
	Field tf_v2x#, tf_v2y#, tf_v2z#
	Field tf_v3x#, tf_v3y#, tf_v3z#
	Field nx#, ny#, nz#, cull
	Field ta, tb, tc, id_ta.ETE_Triangle, id_tb.ETE_Triangle, id_tc.ETE_Triangle
End Type
Const ETE_MaxTriang = 65536, SHWCacheVersion$ = "shw_1.57u", VolumeLenght = 65536
Global VolumeMesh, VolumeSurf, VolumeCap, VolumeCapSurf

Function InitVolumes()
VolumeMesh = CreateMesh()
VolumeSurf = CreateSurface(VolumeMesh)
EntityColor VolumeMesh, 255, 0, 0
EntityAlpha VolumeMesh, .5
VolumeCap = CreateMesh()
VolumeCapSurf = CreateSurface(VolumeCap)
EntityAlpha VolumeCap, .5
End Function

Function FreeVolumes()
Delete Each ETE_Mesh
Delete Each ETE_Triangle
If VolumeMesh Then FreeEntity VolumeMesh
If VolumeCap Then FreeEntity VolumeCap
End Function

Function InitVolumeCaster.ETE_Mesh(ent)
etem.ETE_Mesh = New ETE_Mesh
etem\ent = ent
cnt = -1
etem\cnt_tris = -1
cnt_surf = CountSurfaces(etem\ent)
For s = 1 To cnt_surf
	surf = GetSurface(ent, s)
	cnt_tris = CountTriangles(surf) - 1
	etem\cnt_tris = etem\cnt_tris + cnt_tris + 1
	For v = 0 To cnt_tris
		cnt = cnt + 1
		etem\tri[cnt] = New ETE_Triangle
		etet.ETE_Triangle = etem\tri[cnt]
		vert0 = TriangleVertex(surf, v, 0)
		vert1 = TriangleVertex(surf, v, 1)
		vert2 = TriangleVertex(surf, v, 2)
		etet\v1x# = VertexX(surf, vert0)
		etet\v1y# = VertexY(surf, vert0)
		etet\v1z# = VertexZ(surf, vert0)
		etet\v2x# = VertexX(surf, vert1)
		etet\v2y# = VertexY(surf, vert1)
		etet\v2z# = VertexZ(surf, vert1)
		etet\v3x# = VertexX(surf, vert2)
		etet\v3y# = VertexY(surf, vert2)
		etet\v3z# = VertexZ(surf, vert2)
		etet\ta = -1
		etet\tb = -1
		etet\tc = -1
	Next
Next
cnt_tris = etem\cnt_tris
For a = 0 To cnt_tris
	at.ETE_Triangle = etem\tri[a]
	v1a_x# = at\v1x#
	v1a_y# = at\v1y#
	v1a_z# = at\v1z#
	v1b_x# = at\v2x#
	v1b_y# = at\v2y#
	v1b_z# = at\v2z#
	v1c_x# = at\v3x#
	v1c_y# = at\v3y#
	v1c_z# = at\v3z#
	For b = a + 1 To cnt_tris
		bt.ETE_Triangle = etem\tri[b]
		v1a_v2a = (v1a_x# = bt\v1x# And v1a_y# = bt\v1y# And v1a_z# = bt\v1z#)
		v1a_v2b = (v1a_x# = bt\v2x# And v1a_y# = bt\v2y# And v1a_z# = bt\v2z#)
		v1a_v2c = (v1a_x# = bt\v3x# And v1a_y# = bt\v3y# And v1a_z# = bt\v3z#)
		v1b_v2a = (v1b_x# = bt\v1x# And v1b_y# = bt\v1y# And v1b_z# = bt\v1z#)
		v1b_v2b = (v1b_x# = bt\v2x# And v1b_y# = bt\v2y# And v1b_z# = bt\v2z#)
		v1b_v2c = (v1b_x# = bt\v3x# And v1b_y# = bt\v3y# And v1b_z# = bt\v3z#)
		v1c_v2a = (v1c_x# = bt\v1x# And v1c_y# = bt\v1y# And v1c_z# = bt\v1z#)
		v1c_v2b = (v1c_x# = bt\v2x# And v1c_y# = bt\v2y# And v1c_z# = bt\v2z#)
		v1c_v2c = (v1c_x# = bt\v3x# And v1c_y# = bt\v3y# And v1c_z# = bt\v3z#)
		If v1a_v2b And v1b_v2a Then
			at\ta = b
			bt\ta = a
		ElseIf v1b_v2b And v1c_v2a Then
			at\tb = b
			bt\ta = a
		ElseIf v1c_v2b And v1a_v2a Then
			at\tc = b
			bt\ta = a
		ElseIf v1a_v2c And v1b_v2b Then
			at\ta = b
			bt\tb = a
		ElseIf v1b_v2c And v1c_v2b Then
			at\tb = b
			bt\tb = a
		ElseIf v1c_v2c And v1a_v2b Then
			at\tc = b
			bt\tb = a
		ElseIf v1a_v2a And v1b_v2c Then
			at\ta = b
			bt\tc = a
		ElseIf v1b_v2a And v1c_v2c Then
			at\tb = b
			bt\tc = a
		ElseIf v1c_v2a And v1a_v2c Then
			at\tc = b
			bt\tc = a
		EndIf
	Next
Next
InitETEM(etem)
Return etem
End Function

Function LoadVolumeCaster.ShadowMesh(ent, path$, file = -1)
If file = -1 Then
	file = ReadFile(path$)
	If ReadString(file) <> SHWCacheVersion$ Then
		CloseFile file
		DeleteFile path$
		RecursiveShadowCaster(ent, path$)
		Return
	EndIf
EndIf
sm.ShadowMesh = New ShadowMesh
sm\ent = ent
sm\casting = True
etem.ETE_Mesh = New ETE_Mesh
etem\ent = sm\ent
etem\cnt_tris = ReadInt(file)
For v = 0 To etem\cnt_tris
	etem\tri[v] = New ETE_Triangle
	etet.ETE_Triangle = etem\tri[v]
	etet\v1x# = ReadFloat(file)
	etet\v1y# = ReadFloat(file)
	etet\v1z# = ReadFloat(file)
	etet\v2x# = ReadFloat(file)
	etet\v2y# = ReadFloat(file)
	etet\v2z# = ReadFloat(file)
	etet\v3x# = ReadFloat(file)
	etet\v3y# = ReadFloat(file)
	etet\v3z# = ReadFloat(file)
	etet\ta = ReadShort(file) - 1
	etet\tb = ReadShort(file) - 1
	etet\tc = ReadShort(file) - 1
Next
InitETEM(etem)
cnt_children = CountChildren(ent)
For i = 1 To cnt_children
	LoadVolumeCaster(GetChild(ent, i), "", file)
Next
End Function

Function InitETEM(etem.ETE_Mesh)
cnt_tris = etem\cnt_tris
For v = 0 To cnt_tris
	etet.ETE_Triangle = etem\tri[v]
	If etet\ta > -1 Then etet\id_ta = etem\tri[etet\ta]
	If etet\tb > -1 Then etet\id_tb = etem\tri[etet\tb]
	If etet\tc > -1 Then etet\id_tc = etem\tri[etet\tc]
Next
End Function

Function UpdateVolumes(sl.ShadowLight, top_caps = True)
light_x# = EntityX(sl\ent, True) * (1 + sl\parallel * 1000000)
light_y# = EntityY(sl\ent, True) * (1 + sl\parallel * 1000000)
light_z# = EntityZ(sl\ent, True) * (1 + sl\parallel * 1000000)
For etem.ETE_Mesh = Each ETE_Mesh
	If sl\range# = -1 Or EntityDistance(sl\ent, etem\ent) < sl\range# Then
		cnt_tris = etem\cnt_tris
		For v = 0 To cnt_tris
			etet.ETE_Triangle = etem\tri[v]
			TFormPoint etet\v1x#, etet\v1y#, etet\v1z#, etem\ent, 0
			etet\tf_v1x# = TFormedX()
			etet\tf_v1y# = TFormedY()
			etet\tf_v1z# = TFormedZ()
			TFormPoint etet\v2x#, etet\v2y#, etet\v2z#, etem\ent, 0
			etet\tf_v2x# = TFormedX()
			etet\tf_v2y# = TFormedY()
			etet\tf_v2z# = TFormedZ()
			TFormPoint etet\v3x#, etet\v3y#, etet\v3z#, etem\ent, 0
			etet\tf_v3x# = TFormedX()
			etet\tf_v3y# = TFormedY()
			etet\tf_v3z# = TFormedZ()
			e0x# = etet\tf_v3x# - etet\tf_v2x#
			e0y# = etet\tf_v3y# - etet\tf_v2y#
			e0z# = etet\tf_v3z# - etet\tf_v2z#
			e1x# = etet\tf_v2x# - etet\tf_v1x#
			e1y# = etet\tf_v2y# - etet\tf_v1y#
			e1z# = etet\tf_v2z# - etet\tf_v1z#
			normlight_x# = (etet\tf_v1x# - light_x#) * (e0y# * e1z# - e0z# * e1y#)
			normlight_y# = (etet\tf_v1y# - light_y#) * (e0z# * e1x# - e0x# * e1z#)
			normlight_z# = (etet\tf_v1z# - light_z#) * (e0x# * e1y# - e0y# * e1x#)
			etet\cull = (normlight_x# + normlight_y# + normlight_z# > 0)
		Next
		For v = 0 To cnt_tris
			etet = etem\tri[v]
			r1x# = etet\tf_v1x# + (etet\tf_v1x# - light_x#) * VolumeLenght
			r1y# = etet\tf_v1y# + (etet\tf_v1y# - light_y#) * VolumeLenght
			r1z# = etet\tf_v1z# + (etet\tf_v1z# - light_z#) * VolumeLenght
			r2x# = etet\tf_v2x# + (etet\tf_v2x# - light_x#) * VolumeLenght
			r2y# = etet\tf_v2y# + (etet\tf_v2y# - light_y#) * VolumeLenght
			r2z# = etet\tf_v2z# + (etet\tf_v2z# - light_z#) * VolumeLenght
			r3x# = etet\tf_v3x# + (etet\tf_v3x# - light_x#) * VolumeLenght
			r3y# = etet\tf_v3y# + (etet\tf_v3y# - light_y#) * VolumeLenght
			r3z# = etet\tf_v3z# + (etet\tf_v3z# - light_z#) * VolumeLenght
			If etet\cull = False Then
				If etet\ta > -1 Then check1 = etet\id_ta\cull Else check1 = True
				If etet\tb > -1 Then check2 = etet\id_tb\cull Else check2 = True
				If etet\tc > -1 Then check3 = etet\id_tc\cull Else check3 = True
				If check1 Then
					vert1 = AddVertex(VolumeSurf, etet\tf_v1x#, etet\tf_v1y#, etet\tf_v1z#)
					vert2 = AddVertex(VolumeSurf, etet\tf_v2x#, etet\tf_v2y#, etet\tf_v2z#)
					vert3 = AddVertex(VolumeSurf, r1x#, r1y#, r1z#)
					vert4 = AddVertex(VolumeSurf, r2x#, r2y#, r2z#)
					AddTriangle VolumeSurf, vert1, vert3, vert4
					AddTriangle VolumeSurf, vert1, vert4, vert2
				EndIf
				If check2 Then
					vert1 = AddVertex(VolumeSurf, etet\tf_v2x#, etet\tf_v2y#, etet\tf_v2z#)
					vert2 = AddVertex(VolumeSurf, etet\tf_v3x#, etet\tf_v3y#, etet\tf_v3z#)
					vert3 = AddVertex(VolumeSurf, r2x#, r2y#, r2z#)
					vert4 = AddVertex(VolumeSurf, r3x#, r3y#, r3z#)
					AddTriangle VolumeSurf, vert1, vert3, vert4
					AddTriangle VolumeSurf, vert1, vert4, vert2
				EndIf
				If check3 Then
					vert1 = AddVertex(VolumeSurf, etet\tf_v1x#, etet\tf_v1y#, etet\tf_v1z#)
					vert2 = AddVertex(VolumeSurf, etet\tf_v3x#, etet\tf_v3y#, etet\tf_v3z#)
					vert3 = AddVertex(VolumeSurf, r1x#, r1y#, r1z#)
					vert4 = AddVertex(VolumeSurf, r3x#, r3y#, r3z#)
					AddTriangle VolumeSurf, vert1, vert4, vert3
					AddTriangle VolumeSurf, vert1, vert2, vert4
				EndIf
				If top_caps Then
					vert1 = AddVertex(VolumeCapSurf, etet\tf_v1x#, etet\tf_v1y#, etet\tf_v1z#)
					vert2 = AddVertex(VolumeCapSurf, etet\tf_v2x#, etet\tf_v2y#, etet\tf_v2z#)
					vert3 = AddVertex(VolumeCapSurf, etet\tf_v3x#, etet\tf_v3y#, etet\tf_v3z#)
					AddTriangle VolumeCapSurf, vert1, vert2, vert3
				EndIf
			ElseIf sl\parallel = False Then
				vert1 = AddVertex(VolumeSurf, r1x#, r1y#, r1z#)
				vert2 = AddVertex(VolumeSurf, r2x#, r2y#, r2z#)
				vert3 = AddVertex(VolumeSurf, r3x#, r3y#, r3z#)
				AddTriangle VolumeSurf, vert1, vert2, vert3
			EndIf
		Next
	EndIf
Next
End Function