; -------------------------------------------------------------------------------------------------------------------
; Swift Shadow System Demo:  Copyright 2002 - Shawn C. Swift
; -------------------------------------------------------------------------------------------------------------------

Include "Swift Shadow System - 037.bb"

Global info1$="Swift Shadow System Demo"
Global info2$="Please note that this is a STRESS TEST!"
Global info3$="There are 48 pairs of casting/receiving objects in this scene."
Global info4$="And double that number when you add a second light!"
Include "start.bb"


; Stuff for the normal calculation function:
Dim Face_NX#(32768)
Dim Face_NY#(32768)
Dim Face_NZ#(32768)
		
Dim Vertex_ConnectedTris(32768)
Dim Vertex_TriList(32768, 16)

; Stuff for the HSV to RGB function.
Global HSV_R
Global HSV_G
Global HSV_B


.Main

	;PlayMusic("sonata-14.it")

	; Removes all the default flags when you load a texture.
	; Required because shadow maps cannot be mipmapped or the UV clamping will cause artifacts, 
	; and blitz by default has mipmapping enabled for all textures loaded.
	ClearTextureFilters  

	
	; Load a texture for our fake shadows.
	; Note the flags:
	; Texture must have UV clamping enabled, and no mipmapping!
	TEX_Fake_Shadow = LoadTexture("TEX-003.jpg", 16+32)

	
	; Create a camera and move it back from 0,0,0
	Camera = CreateCamera()
	CameraRange Camera, 0.1, 65536
	PositionEntity Camera, 0, 200, -800

	Camera_Pivot = CreatePivot()
	
	EntityParent Camera, Camera_Pivot, True

	; Set up the lights.
	Gosub Setup_Lights

	Gosub Create_Objects

	;PointEntity Camera, Caster_1
	TurnEntity Camera, 15, 0, 0

	Shadow_Resolution = 256

	; Set the amount to nudge the shadows by to combat z fighting.
	Shadow_Nudge# = 0.75

	; Specify what objects should cast shadows.
	Cast_Shadow(Caster_1, Shadow_Resolution, 1)	; Cube
	Cast_Shadow(Caster_2, Shadow_Resolution)	; Fighter
	Cast_Shadow(Receiver_2, Shadow_Resolution)	; Sphere

	Cast_Shadow(Receiver_3, Shadow_Resolution)
	Cast_Shadow(Receiver_4, Shadow_Resolution)
	Cast_Shadow(Receiver_5, Shadow_Resolution)	
	Cast_Shadow(Receiver_6, Shadow_Resolution)


	; Remember that we're drawing rendered shadows by default.
	Cast_Rendered_Shadows = True

	
	; Specify what objects should receive shadows.
	; It's not a good idea to have too many objects receiving shadows.
	Receive_Shadow(Receiver_1)
	Receive_Shadow(Receiver_2)
	;Receive_Shadow(Receiver_3)
	;Receive_Shadow(Receiver_4)
	;Receive_Shadow(Receiver_5)	
	;Receive_Shadow(Receiver_6)
	
	
	; Specify the lights which cast shadows, and set their range.
	Cast_Light(Light_1, 2000)

	Time_Old = MilliSecs()
	Time_Of_Next_Event = Time_Old + 2000

	While Not KeyDown(1)

		Time_New		= MilliSecs()
		Time_Delta      = Time_New - Time_Old				
		Time_Delta_Sec# = Float(Time_Delta)/1000.0
		Time_Old		= Time_New


		If KeyHit(88) Then SaveScreenshot()
	
	
		If KeyHit(2)

			; Stop the objects from casting shadows.			
			; Note:
			; You only need to call this once per object casting shadows, 
			; even if it casts shadows onto more than one reciever.
			Delete_Shadow_Caster(Caster_1)		; Cube
			Delete_Shadow_Caster(Caster_2)		; Fighter
			Delete_Shadow_Caster(Receiver_2)	; Sphere
			Delete_Shadow_Caster(Receiver_3)	
			Delete_Shadow_Caster(Receiver_4)	
			Delete_Shadow_Caster(Receiver_5)	
			Delete_Shadow_Caster(Receiver_6)	

			; If we're currenty casting rendered shadows...
			If Cast_Rendered_Shadows = True

				; Make the objects cast fake shadows.
				; The value of the resolution parameter doesn't matter because you created the texture.
				Cast_Textured_Shadow(Caster_1, TEX_Fake_Shadow) 	; Cube 
				Cast_Textured_Shadow(Caster_2, TEX_Fake_Shadow) 	; Fighter
				Cast_Textured_Shadow(Receiver_2, TEX_Fake_Shadow)	; Sphere	
				Cast_Rendered_Shadows = False
							
			Else
			
				; Make the objects cast rendered shadows.
				; We don't need a texture parameter because the function will create a unique texture
				; for each shadow itself.
				Cast_Shadow(Caster_1,   Shadow_Resolution, 1)   ; Cube
				Cast_Shadow(Caster_2,   Shadow_Resolution)		; Fighter
				Cast_Shadow(Receiver_2, Shadow_Resolution)		; Sphere
				Cast_Shadow(Receiver_3, Shadow_Resolution)
				Cast_Shadow(Receiver_4, Shadow_Resolution)
				Cast_Shadow(Receiver_5, Shadow_Resolution)	
				Cast_Shadow(Receiver_6, Shadow_Resolution)
				Cast_Rendered_Shadows = True
				
			EndIf
		
		EndIf		
	
		
		If KeyHit(3)
		
			If Shadow_Resolution = 128
				Shadow_Resolution = 256
			Else
				If Shadow_Resolution = 256
					Shadow_Resolution = 512
				Else
					If Shadow_Resolution = 512
						Shadow_Resolution = 128	
					EndIf
				EndIf
			EndIf


			; If we're currenty casting rendered shadows...
			If Cast_Rendered_Shadows = True
	
				; Stop the objects from casting shadows.			
				; Note:
				; You only need to call this once per object casting shadows, 
				; even if it casts shadows onto more than one reciever.
				Delete_Shadow_Caster(Caster_1)		; Cube
				Delete_Shadow_Caster(Caster_2)		; Fighter
				Delete_Shadow_Caster(Receiver_2)	; Sphere
				Delete_Shadow_Caster(Receiver_3)	
				Delete_Shadow_Caster(Receiver_4)	
				Delete_Shadow_Caster(Receiver_5)	
				Delete_Shadow_Caster(Receiver_6)	
			
				; Make the objects cast rendered shadows.
				; We don't need a texture parameter because the function will create a unique texture
				; for each shadow itself.
				Cast_Shadow(Caster_1, Shadow_Resolution, 1)		; Cube
				Cast_Shadow(Caster_2, Shadow_Resolution)		; Fighter
				Cast_Shadow(Receiver_2, Shadow_Resolution)		; Sphere
				Cast_Shadow(Receiver_3, Shadow_Resolution)
				Cast_Shadow(Receiver_4, Shadow_Resolution)
				Cast_Shadow(Receiver_5, Shadow_Resolution)	
				Cast_Shadow(Receiver_6, Shadow_Resolution)
				
			EndIf


		EndIf
	
			
		If KeyHit(4)
	
			Delay 5000
											
		EndIf
	
		
		If KeyHit(5)
	
			FormatC = Not FormatC
												
		EndIf
			

		If KeyHit(6)
	
			Second_Light = Not Second_Light
			
			If Second_Light = True

				Cast_Light(Light_2, 2000)
				ShowEntity Light_2
				
			Else

				Delete_Light_Caster(Light_2)					
				HideEntity Light_2
				
			EndIf									
												
		EndIf

		
		If KeyHit(57)
			Show_Shadow_Map = Not Show_Shadow_Map
		EndIf
				
		TurnEntity Caster_1, 1, 1, 1
		TurnEntity Caster_2, 1, 0.5, 0
		;TurnEntity Caster_3, 0, 1, 0.5

		TurnEntity Light_Pivot, 0.25, 0.5, 1		
		
		TurnEntity Camera_Pivot, 0, -0.5, 0
		
		; Create a fully saturated, full bright color.
		CubeHue = (CubeHue+1) Mod 360
		HSV_to_RGB(CubeHue, 0.9, 0.25) 
		
		; Set the cube's color to our new color.
		EntityColor Caster_1, HSV_R, HSV_G, HSV_B
	
		Update_Shadows(camera) 

		UpdateWorld
		If Show_Shadow_Map = False Then RenderWorld

		
		If Time_Of_Next_Event < Time_Old
		
			Time_Of_Next_Event = Time_Old + 2000
			Event = (Event + 1) Mod 6
			
		EndIf	
			
		Select Event 
		
			Case 0 
		
				Info$ = "-- Swift Shadow System - Created by Shawm C. Swift --"
		
			Case 1
						
				Info$ = "-- Press 1 to toggle rendered/fake shadows! --"
				
			Case 2

				Info$ = "-- Press 2 to toggle shadow resolution between 128, 256, and 512! --"
				
			Case 3
			
				Info$ = "-- Press 3 to crash your pc! --"
				
			Case 4
			
				Info$ = "-- Press 4 to FORMAT C: drive! ---"
		
			Case 5
			
				Info$ = "-- Press 5 to kill your framerate... I mean add a second light to the scene. --"
				
		End Select 
				
		Text GraphicsWidth()/2, 16*1, Info$, True, False

		If (Time_Delta > 0)

			Frames_Per_Second = Int(Floor(1000.0/Float(Time_Delta)))
			Text GraphicsWidth()/2, 16*2, "FPS: " + Str$(Frames_Per_Second), True, False

		EndIf	


		Select (Event Mod 3)
		
			Case 0
		
				Text GraphicsWidth()/2, 16*3, "Shadow Resolution: " + Str$(Shadow_Resolution), True, False
				
			Case 1
					
				Text GraphicsWidth()/2, 16*3, "Shadow Polys: " + Str$(Shadow_Poly_Count), True, False
				
			Case 2
					
				Text GraphicsWidth()/2, 16*3, "Scene Polys: " + Str$(TrisRendered()), True, False

		End Select
		
		
		If FormatC = True
			Text GraphicsWidth()/2, GraphicsHeight()/2, "Formatting C:\ ... Please wait.", True, True		
		EndIf

		Flip True

	Wend


End


; -------------------------------------------------------------------------------------------------------------------
.Setup_Lights

	; Tell the shadow system what the ambient light level is.  (Blitz doesn't have a command to retreive that info.)
	Shadow_Ambient_Light_R = 80
	Shadow_Ambient_Light_G = 80
	Shadow_Ambient_Light_B = 128

	AmbientLight Shadow_Ambient_Light_R, Shadow_Ambient_Light_G, Shadow_Ambient_Light_B

	; Create three point light sources.
	Light_1 = CreateLight(2)
	Light_2 = CreateLight(2)
	
	; Set the color of the lights.
	LightColor Light_1, 255, 255, 240
	LightColor Light_2, 255, 255, 240
	
	; Set the radius of the lights.
	LightRange Light_1, 2000
	LightRange Light_2, 2000

	; Create a pivot to rotate the light sources around.
	Light_Pivot = CreatePivot()
	PositionEntity Light_Pivot, 0, 800, 0
	
	; Attach the light sources to the pivot.
	EntityParent Light_1, Light_Pivot
	EntityParent Light_2, Light_Pivot
	
	; Position the light sources around the pivot.
	PositionEntity Light_1, 400, 0, 0
	PositionEntity Light_2, 0, 0, 400
	
	; Create entities to show the positions of the light sources in the world.
	TEX_04 = LoadTexture("TEX-004.bmp", 8)
	Light_Sprite_1 = CreateSprite(Light_1) 
	ScaleSprite Light_Sprite_1, 100, 100
	EntityFX Light_Sprite_1, 1
	EntityBlend Light_Sprite_1, 3
	EntityTexture Light_Sprite_1, TEX_04

	Light_Sprite_2 = CopyEntity(Light_1, Light_2) 
	HideEntity Light_2
 

Return


; -------------------------------------------------------------------------------------------------------------------
; This subroutine creates and textures the meshes we will use to cast and recieve the shadows.
; -------------------------------------------------------------------------------------------------------------------
.Create_Objects


	; We have to set the mipmap flag manually because we turned that off so we could create 
	; shadow textures without mipmapping.	
	TEX_01 = LoadTexture("TEX-001.jpg", 8) 
	TEX_02 = LoadTexture("TEX-002.jpg", 8)
	TEX_05 = LoadTexture("TEX-005.jpg", 8)
		
	Receiver_1 = CreateCube()
	ScaleMesh Receiver_1, 500, 50, 500
	PositionEntity Receiver_1, 0, -100, 0, True
	Calculate_Normals(Receiver_1)
	EntityTexture Receiver_1, TEX_01

	Receiver_2 = CreateSphere(8, Receiver_1)
	ScaleMesh Receiver_2, 100, 50, 100
	PositionEntity Receiver_2, 0, 0, 0, True
	UpdateNormals(Receiver_2)	
	EntityTexture Receiver_2, TEX_02


	; Make pillars	
	Temp1 = CreateCylinder(12, False)
	ScaleMesh Temp1, 40, 100, 40
	UpdateNormals(Temp1)

	Temp2 = CreateCube()
	ScaleMesh Temp2, 50, 10, 50
	Calculate_Normals(Temp2)
	PositionMesh Temp2, 0, 100, 0

	Temp3 = CreateSphere(8)
	ScaleMesh Temp3, 40, 40, 40
	UpdateNormals(Temp3)	
	PositionMesh Temp3, 0, 150, 0
	
	Receiver_3 = CreateMesh(Receiver_1)
	AddMesh Temp1, Receiver_3
	AddMesh Temp2, Receiver_3
	AddMesh Temp3, Receiver_3
	
	EntityTexture Receiver_3, TEX_02
	
	FreeEntity Temp1
	FreeEntity Temp2
	FreeEntity Temp3
	
	Receiver_4  = CopyEntity(Receiver_3, Receiver_1)
	Receiver_5  = CopyEntity(Receiver_3, Receiver_1)
	Receiver_6  = CopyEntity(Receiver_3, Receiver_1)

	PositionEntity Receiver_3,  300, 50,  300, True
	PositionEntity Receiver_4, -300, 50, -300, True
	PositionEntity Receiver_5, -300, 50,  300, True
	PositionEntity Receiver_6,  300, 50, -300, True


	; Create entities to cast shadows.
	Caster_1 = CreateCube()
	ScaleMesh Caster_1, 25, 25, 25
	PositionEntity Caster_1, 0, 150, 0, True
	EntityAlpha Caster_1, 0.5
	EntityFX Caster_1, 16
	
	Caster_2 = LoadMesh("beethoven.3ds")
	ScaleMesh Caster_2, 10, 10, 10
	PositionEntity Caster_2, 100, 200, -100, True
	EntityColor Caster_2, 255, 255, 255
	EntityTexture Caster_2, TEX_05

	;Caster_3 = CopyEntity(Caster_2)
	;PositionEntity Caster_3, -50, 250, -150, True

	
Return



; -------------------------------------------------------------------------------------------------------------------
; This function adds a bit of noise to a mesh so that it's more interesting.
; -------------------------------------------------------------------------------------------------------------------
Function Randomize_Mesh(ThisMesh)

	Offset = 25
	
	; Loop through all surfaces of the mesh.
	Surfaces = CountSurfaces(ThisMesh)
	For LOOP_Surface = 1 To Surfaces

		Surface_Handle = GetSurface(ThisMesh, LOOP_Surface)
	
		; Loop through all triangles in this surface of the mesh.
		Verts = CountVertices(Surface_Handle)
		For LOOP_Verts = 0 To Verts-1
	
			Vx# = VertexX#(Surface_Handle, LOOP_Verts)
			Vy# = VertexY#(Surface_Handle, LOOP_Verts)
			Vz# = VertexZ#(Surface_Handle, LOOP_Verts)
						
			VertexCoords Surface_Handle, LOOP_Verts, Vx#, Vy#+Rnd(-Offset,Offset), Vz#

		Next

	Next

End Function


; -------------------------------------------------------------------------------------------------------------------
; This function takes a screenshot of whatever is on the screen and saves it in the current directory.
; -------------------------------------------------------------------------------------------------------------------
Function SaveScreenshot()

    iFileNumber% = 0
    Repeat
        iFileNumber = iFileNumber + 1
        sFileName$ = "Screenshot" + String$("0", 3 - Len(Str$(iFileNumber))) + iFileNumber + ".bmp"
    Until Not(FileType(sFileName$))
    
    SaveBuffer FrontBuffer(), sFileName$

End Function


; -------------------------------------------------------------------------------------------------------------------
; This function calculates and sets the normals for a mesh.
;
; This is diffrent from Blitz's function in that it does not smooth across edges which do not share vertices.  
; In my opinion, that is the best way to calculate the normals for a mesh.  Blitz on the other hand assumes that
; vertices which lie at the same point in space should be smoothed across.  This works well for 
;
; Should probably update this so that it can recursively loop through all of an entities children as well.
; -------------------------------------------------------------------------------------------------------------------
Function Calculate_Normals(ThisMesh)
	
	; Loop through all surfaces of the mesh.
	Surfaces = CountSurfaces(ThisMesh)
	For LOOP_Surface = 1 To Surfaces

		Surface_Handle = GetSurface(ThisMesh, LOOP_Surface)

		; Reset the number of connected polygons for each vertex.
		For LoopV = 0 To 32767	
			Vertex_ConnectedTris(LoopV) = 0
		Next	
	
		; Loop through all triangles in this surface of the mesh.
		Tris = CountTriangles(Surface_Handle)
		For LOOP_Tris = 0 To Tris-1

			; Get the vertices that make up this triangle.
				Vertex_0 = TriangleVertex(Surface_Handle, LOOP_Tris, 0)
				Vertex_1 = TriangleVertex(Surface_Handle, LOOP_Tris, 1)
				Vertex_2 = TriangleVertex(Surface_Handle, LOOP_Tris, 2)
	
			; Adjust the number of triangles each vertex is connected to and
			; store this triangle in each vertex's list of triangles it is connected to.
				ConnectedTris = Vertex_ConnectedTris(Vertex_0)
				Vertex_TriList(Vertex_0, ConnectedTris) = LOOP_Tris
				Vertex_ConnectedTris(Vertex_0) = ConnectedTris + 1

				ConnectedTris = Vertex_ConnectedTris(Vertex_1)
				Vertex_TriList(Vertex_1, ConnectedTris) = LOOP_Tris
				Vertex_ConnectedTris(Vertex_1) = ConnectedTris + 1

				ConnectedTris = Vertex_ConnectedTris(Vertex_2)
				Vertex_TriList(Vertex_2, ConnectedTris) = LOOP_Tris
				Vertex_ConnectedTris(Vertex_2) = ConnectedTris + 1

			; Calculate the normal for this face.

				; Get the corners of this face:
				Ax# = VertexX#(Surface_Handle, Vertex_0)
				Ay# = VertexY#(Surface_Handle, Vertex_0)
				Az# = VertexZ#(Surface_Handle, Vertex_0)

				Bx# = VertexX#(Surface_Handle, Vertex_1)
				By# = VertexY#(Surface_Handle, Vertex_1)
				Bz# = VertexZ#(Surface_Handle, Vertex_1)

				Cx# = VertexX#(Surface_Handle, Vertex_2)
				Cy# = VertexY#(Surface_Handle, Vertex_2)
				Cz# = VertexZ#(Surface_Handle, Vertex_2)

				; Triangle 1
				; Get the vectors for two edges of the triangle.
				Px# = Ax#-Bx#
				Py# = Ay#-By#
				Pz# = Az#-Bz#

				Qx# = Bx#-Cx#
				Qy# = By#-Cy#
				Qz# = Bz#-Cz#

				; Compute their cross product.
				Nx# = Py#*Qz# - Pz#*Qy#
				Ny# = Pz#*Qx# - Px#*Qz#
				Nz# = Px#*Qy# - Py#*Qx#

				; Store the face normal.
				Face_NX#(LOOP_Tris) = Nx#
				Face_NY#(LOOP_Tris) = Ny#
				Face_NZ#(LOOP_Tris) = Nz#

		Next

		; Now that all the face normals for this surface have been calculated, calculate the vertex normals.
		Vertices = CountVertices(Surface_Handle)
		For LOOP_Vertices = 0 To Vertices-1

			; Reset this normal.
			Nx# = 0
			Ny# = 0
			Nz# = 0

			; Add the normals of all polygons which are connected to this vertex.
			Polys = Vertex_ConnectedTris(LOOP_Vertices)
				
			For LOOP_Polys = 0 To Polys-1

				ThisPoly = Vertex_TriList(LOOP_Vertices, LOOP_Polys)

				Nx# = Nx# + Face_NX#(ThisPoly)
				Ny# = Ny# + Face_NY#(ThisPoly)
				Nz# = Nz# + Face_NZ#(ThisPoly)			
				
			Next	
				
			; Normalize the new vertex normal.
			; (Normalizing is scaling the vertex normal down so that it's length = 1)

				Nl# = Sqr(Nx#^2 + Ny#^2 + Nz#^2)

				; Avoid a divide by zero error if by some freak accident, the vectors add up to 0.
				; If Nl# = 0 Then Nl# = 0.1

				Nx# = Nx# / Nl#
				Ny# = Ny# / Nl#
				Nz# = Nz# / Nl#

			; Set the vertex normal.
			
				VertexNormal Surface_Handle, LOOP_Vertices, Nx#, Ny#, Nz#
				;VertexColor Surface_Handle, LOOP_Vertices, polys*127, polys*127, polys*127
		
		Next

	Next

End Function


; -------------------------------------------------------------------------------------------------------------------
; This function takes a color in HSV format and returns a color in RGB format.
; -------------------------------------------------------------------------------------------------------------------
Function HSV_to_RGB(h#, s#, v#)

	If s = 0 Then

		r# = g# = b# = v		;grey

	Else

		h = h / 60
		i = Floor(h)
		f# = h - i
		p# = v * (1 - s)
		q# = v * (1 - s * f)
		t# = v * (1 - s * (1 - f))

		Select i

			Case 0
				r# = v
				g# = t
				b# = p

			Case 1
				r# = q
				g# = v
				b# = p
	
			Case 2
				r# = p
				g# = v
				b# = t

			Case 3
				r# = p
				g# = q
				b# = v
	
			Case 4
				r# = t
				g# = p
				b# = v

			Default
				r# = v
				g# = p
				b# = q

		End Select		

	EndIf

	HSV_R = r# * 255
	HSV_G = g# * 255
	HSV_B = b# * 255

End Function 