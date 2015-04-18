;Set Constants
Const MaxFogFar# = 2000.0

Global SkyEN, CloudEN, StarsEN
Global SkyTexID = 65535, CloudTexID = 65535, StormCloudTexID = 65535, StarsTexID = 65535
Global FogR, FogG, FogB, FogNear#, FogFar#
Global Outdoors
Global AmbientR = 100, AmbientG = 100, AmbientB = 100
Global DefaultLightPitch#, DefaultLightYaw#
Global LoadingTexID = 65535, LoadingMusicID = 65535
Global MapTexID
Global SlopeRestrict# = 0.6

Type Remove_Surf
	Field ID
End Type
Type Cluster
	Field XC#, YC#, ZC#
	Field Mesh, Surf[200]
End Type

Type Scenery
	Field SceneryID ; Set by user, used for scenery ownerships {##}
	Field EN, MeshID
	Field AnimationMode ; 0 = no animation, 1 = constant animation (loops), 2 = constant (ping-pongs), 3 = animate when selected
	Field ScaleX#, ScaleY#, ScaleZ#
	Field Lightmap$     ; Lightmap filename
	Field RCTE$         ; Used by toolbox editors only
	Field TextureID     ; To alter the texture loaded automatically with the model, if required (65535 to ignore)
	Field CatchRain
	Field Locked
	
	Field CastShadow ;[010]
	Field ReceiveShadow
	Field RenderRange ;[011]
	
	Field SceneryType$ ;Variable declaration for view distance Ramoida
	
	Field LightID ; Dynamic lighting
	Field ENName$ 
	Field ENLight$
	Field ENWF$
	Field ENFM$
	Field ENFL$
	Field Name$
End Type

;LOD Ramoida (Set view distances here)
Const BuildingMinViewDistance# = 230.0 ; If distance < this value will be Visible, else will autofade slowly
Const BuildingMaxViewDistance# = 250.0 ; If distance > this value will be 100% invisible
Const GrassMinViewDistance# = 75.0
Const GrassMaxViewDistance# = 90.0
Const RunicMinViewDistance# = 5.0
Const RunicMaxViewDistance# = 10.0

Const ParticleMaxViewDistance# = 10.0

Type Water
	Field EN, TexID, Opacity
	Field Red, Green, Blue
	Field ScaleX#, ScaleZ#
	Field TexHandle, TexScale#, U#, V#
	Field ServerWater ; Used by editor only
	; Water refraction terrier 
    Field WaterClipplane
End Type


Type ColBox
	Field EN
	Field ScaleX#, ScaleY#, ScaleZ#
End Type

Type Emitter
	Field Config, ConfigName$, EN, TexID
End Type

Type Terrain
	Field EN, DetailTex
	Field BaseTexID, DetailTexID
	Field DetailTexScale#
	Field ScaleX#, ScaleY#, ScaleZ#
	Field Detail, Morph, Shading
End Type

Type SoundZone
	Field EN, Radius#
	Field SoundID, MusicID ; Can be one or the other
	Field RepeatTime ; Number of seconds to wait before repeating the sound, or -1 to play it once only
	Field Volume ; Volume (1% - 100%)
	Field LoadedSound, MusicFilename$
	Field Is3D
	Field Channel, Timer, Fade# ; Variables for updating the sound zone in the client
End Type

; Generated from scenery objects to prevent rain/snow particles falling through
Type CatchPlane
	Field MinX#, MinZ#, MaxX#, MaxZ#, Y#
End Type

; Creates a subdivided plane (used for water)
Function CreateSubdividedPlane(XDivs, ZDivs, UScale# = 1.0, VScale# = 1.0, Parent = 0)

	EN = CreateMesh(Parent)
	Surf = CreateSurface(EN)

	For x = 0 To XDivs - 1
		For z = 0 To ZDivs - 1
			XPos# = Float#(x) / Float#(XDivs - 1)
			ZPos# = Float#(z) / Float#(ZDivs - 1)
			V = AddVertex(Surf, XPos#, 0.0, ZPos#, XPos# * UScale#, ZPos# * VScale#)
			VertexNormal(Surf, V, 0.0, 1.0, 0.0)
			If x > 0 And z > 0
				v1 = ((x - 1) * ZDivs) + (z - 1)
				v2 = ((x - 1)* ZDivs) + z
				v3 = (x * ZDivs) + (z - 1)
				v4 = (x * ZDivs) + z
				AddTriangle(Surf, v1, v2, v4)
				AddTriangle(Surf, v1, v4, v3)
			EndIf
		Next
	Next
	PositionMesh(EN, -0.5, 0.0, -0.5)
	Return EN


End Function


;&&&&&&&&&&&&&&& Refractive water terrier
Function RenderWater(Camera)
      For W.Water = Each Water

        ; CameraFogMode Camera,0   
         ;HideEntity CameraSprite
            
        ; ShowClipplane W\WaterClipplane => 
			AlignClipplane W\WaterClipplane, W\EN

			;EntityAlpha W\EN,0.25 => sinon impose un alpha au plan d'eau
			EntityTexture W\EN, FoamTexture
      Next
       
      SetBuffer TextureBuffer(RefractTexture)
     ; CameraViewport Camera, 0,0, TextureWidth(RefractTexture), TextureHeight(RefractTexture)
	 ; CameraViewport Camera, 0,0, 1, 1
	
	;CameraViewport Camera, 0, 0, TextureWidth(RefractTexture) , TextureHeight(RefractTexture)
  ;  ScaleEntity Camera,1,Float(GraphicsHeight())/Float(GraphicsWidth()),1  
;	
	;ShowEntity(Camera)    
      RenderWorld 
	
	HideEntity(Camera)
	


		; render world in backBuffer (set refractions texture to water plane)
     
      For W.Water = Each Water
      		;	If CameraUnderWater = True 
			;	;ShowEntity CameraSprite
			;	CameraFogMode Camera,1
			;	CameraFogRange Camera,0.5,35
			;	CameraFogColor Camera,20,20,40
			;Else
				;HideEntity CameraSprite
				;CameraFogMode Camera,1
				;CameraFogRange Camera,20,250
				;CameraFogColor Camera,190,200,220
			;EndIf
      
         
         HideClipplane W\WaterClipplane
         
			EntityTexture W\EN, BumpTexture, (BumpTextureFrame Mod 32), 0
			EntityTexture W\EN, RefractTexture, 0, 1
			;EntityAlpha W\EN,1 => sinon impose un alpha au plan d'eau
			EntityColor W\EN,25,30,35 ; => renforce le r�alisme mais bleu par d�faut
	
			SetBuffer BackBuffer()
			
			
			ShowEntity(Camera)
		;	CameraViewport Camera, 0,0, GraphicsWidth(), GraphicsHeight()
		;	ScaleEntity Camera,1,1,1		; ����������� ��������� ������
			;RenderWorld  ;=> fait gagner 10 FPS de +
            Next  
End Function


; Loads the client (3D) data for an area
Function LoadArea(Name$, CameraEN, DisplayItems = False, UpdateRottNet = False)
		
	; RottNet update
	Local RNUpdateTime% = MilliSecs()
	Local CLoadMusic%
	
	;Adding shadows to options menu Cysis145
	F = ReadFile("Data\Options.dat")
		Width = ReadShort(F)
		Height = ReadShort(F)
		Depth = ReadByte(F)
		AA = ReadByte(F)
		DefaultVolume# = ReadFloat#(F)
		GrassEnabled = ReadByte(F)
		AnisotropyLevel = ReadByte(F)
		FullScreen = ReadByte(F)
		VSync = ReadByte(F)
		Bloom = ReadByte(F)
		Rays = ReadByte(F)
		AWater = ReadByte(F)
		ShadowC = ReadByte(F)
		ShadowQ = ReadByte(F)
		ShadowR = ReadByte(F)
	CloseFile(F)
	Select ShadowQ
		Case 0
			CreateShadow 0
		Case 1
			CreateShadow 1
		Case 2
			CreateShadow 2
	End Select
	
	Select ShadowR
		Case 0
			ShadowRange 50
		Case 1
			ShadowRange 75
		Case 2
			ShadowRange 100
		Case 3
			ShadowRange 130
	End Select
	
	;Season = GetSeason()
	; Dusk
	;If TimeH = SeasonDuskH(Season)
	;	ShadowPower 0.0  ;Set Shadow Opacity
	; Dawn
	;ElseIf TimeH = SeasonDawnH(Season)
	;	ShadowPower 0.0  ;Set Shadow Opacity
	;EndIf

	ShadowPower 0.4  ;Set Shadow Opacity
	ShadowColor 255, 255, 255
	;ShadowLight DefaultLight\EN
	ShadowTexture = ShadowTexture() ; Gets shadow map
	
	;Shadow Fading
	FadeOutTexture = LoadTexture("Data\Textures\Shadows\fade.png", 59)	
	ShadowFade FadeOutTexture
		
	LockMeshes()
	LockTextures()
	
	; Open file
	F = ReadFile("Data\Areas\" + Name$ + ".dat")
	If F = 0 Then Return False

		; Loading screen
		LoadingTexID = ReadShort(F)
		LoadingMusicID = ReadShort(F)
		
		; Music
		If LoadingMusicID < 65535 Then CLoadMusic = PlayMusic("Data\Music\" + GetMusicName$(LoadingMusicID))		
		If DisplayItems = False
			; Progress bar
			LoadProgressBar = GY_CreateProgressBar(0, 0.3, 0.9, 0.4, 0.035, 0, 100, 255, 255, 255, -3012)
			; Preset image
			LoadScreen = CreateMesh(GY_Cam)
			Surf = CreateSurface(LoadScreen)
			v1 = AddVertex(Surf, 0.0, -1.0, 0.0, 0.0, 1.0)
			v2 = AddVertex(Surf, 1.0, -1.0, 0.0, 1.0, 1.0)
			v3 = AddVertex(Surf, 1.0, 0.0, 0.0, 1.0, 0.0)
			v4 = AddVertex(Surf, 0.0, 0.0, 0.0, 0.0, 0.0)
			AddTriangle Surf, v3, v2, v1
			AddTriangle Surf, v4, v3, v1
			
			
			;Widescreen Ramoida
			If ResolutionType = 1 ; 16:9 ratio
				ScaleMesh LoadScreen, 27.0, 15.05, 1.0 ;x,y,z
				PositionEntity LoadScreen, -13.5, 7.55, 10.0
			Else  ;;4:3 ratio
				ScaleMesh LoadScreen, 20.5, 15.5, 1.0
				PositionEntity LoadScreen, -10.07, 7.55, 10.0
			EndIf
			
			EntityOrder LoadScreen,-3011
			EntityFX LoadScreen, 1 + 8
						
			If LoadingTexID < 65535
				Tex = GetTexture(LoadingTexID)
				If Tex <> 0
					EntityTexture(LoadScreen, Tex)
					UnloadTexture(LoadingTexID)
				EndIf
			; Random image
			ElseIf RandomImages > 0
				D = ReadDir("Data\Textures\Random")
				If D = 0
					EntityColor(LoadScreen, 0, 0, 0)
				Else
					For i = 1 To Rand(1, RandomImages)
						Repeat
							File$ = NextFile$(D)
						Until FileType("Data\Textures\Random\" + File$) = 1 Or File$ = ""
						If File$ = "" Then Exit
					Next
					If FileType("Data\Textures\Random\" + File$) = 1
						Tex = LoadTexture("Data\Textures\Random\" + File$)
						If Tex = 0
							EntityColor(LoadScreen, 0, 0, 0)
						Else
							EntityTexture(LoadScreen, Tex)
							FreeTexture(Tex)
						EndIf
					Else
						EntityColor(LoadScreen, 0, 0, 0)
					EndIf
					CloseDir(D)
				EndIf
			; No image
			Else
				EntityColor(LoadScreen, 0, 0, 0)
			EndIf
		EndIf

		; Loading bar update
		If LoadScreen <> 0
			GY_UpdateProgressBar(LoadProgressBar, 0)
			RenderWorld()
			Flip()
		EndIf

		; Environment
		SkyTexID = ReadShort(F)
		CloudTexID = ReadShort(F)
		StormCloudTexID = ReadShort(F)
		StarsTexID = ReadShort(F)

		FogR = ReadByte(F)
		FogG = ReadByte(F)
		FogB = ReadByte(F)
		FogNear# = ReadFloat#(F)
		FogFar#  = ReadFloat#(F)
		If FogFar# > MaxFogFar# Then FogFar# = MaxFogFar#
		FogNearNow# = FogNear#
		FogFarNow# = FogFar#

		; Sky
		If SkyTexID > -1 And SkyTexID < 65535
			EntityTexture(SkyEN, GetTexture(SkyTexID))
			EntityAlpha(SkyEN, 1.0)
		Else
			EntityAlpha(SkyEN, 0.0)
		EndIf
		If CloudTexID > -1 And CloudTexID < 65535
			EntityTexture(CloudEN, GetTexture(CloudTexID))
			EntityAlpha(CloudEN, 0.4)
		ElseIf StormCloudTexID > -1 And StormCloudTexID < 65535
			EntityTexture(CloudEN, GetTexture(StormCloudTexID))
			EntityAlpha(CloudEN, 0.4)
		Else
			EntityAlpha(CloudEN, 0.0)
		EndIf
		If StarsTexID > -1 And StarsTexID < 65535
			EntityTexture(StarsEN, GetTexture(StarsTexID))
			EntityAlpha(StarsEN, 1.0)
		Else
			EntityAlpha(StarsEN, 0.0)
		EndIf

		; Camera
		If CameraEN <> 0
			SetViewDistance(CameraEN, FogNear#, FogFar#)
			CameraFogColor(CameraEN, FogR, FogG, FogB)
			CameraClsColor(CameraEN, FogR, FogG, FogB)
		EndIf

		MapTexID = ReadShort(F)
		Outdoors = ReadByte(F)
		AmbientR = ReadByte(F)
		AmbientG = ReadByte(F)
		AmbientB = ReadByte(F)
		DefaultLightPitch# = ReadFloat#(F)
		DefaultLightYaw# = ReadFloat#(F)
		SlopeRestrict# = ReadFloat#(F)
		AmbientLight(AmbientR, AmbientG, AmbientB)
		
	;&&&&& Camera refractive water terrier
	CameraSprite = CreateSprite(Camera)
	PositionEntity CameraSprite,0,0,0.11
	EntityColor CameraSprite,20,20,40
    EntityAlpha CameraSprite,0.35


		; RottNet update
		If UpdateRottNet = True And MilliSecs() - RNUpdateTime > 500 Then RCE_Update() : RCE_CreateMessages() : RNUpdateTime = MilliSecs()

		; Loading bar update
		If LoadScreen <> 0
			GY_UpdateProgressBar(LoadProgressBar, 5)
			RenderWorld() ;[998]
			Flip()
		EndIf

		; Scenery
		Sceneries = ReadShort(F)
		For i = 1 To Sceneries
			S.Scenery = New Scenery
			; Mesh (from media database ID)
			S\MeshID = ReadShort(F)
			
		; Nasty hack to disable decryption on RCTE terrains in a subfolder DISABLED terrains are no longer encrypted so no need to keep code.
			NoDecrypt = False
			Name$ = Upper$(GetMeshName$(S\MeshID))
			;If Instr(Name$, "RCTE\") = 1 Or Instr(Name$, "RCTE/") = 1
			;	If Instr(Name$, "\", 6) > 0 Or Instr(Name$, "/", 6) > 0 Then NoDecrypt = True
			;EndIf
			
			; Load the mesh
			S\EN = GetMesh(S\MeshID, False)

			; Read position/rotation/scale
			X# = ReadFloat#(F) : Y# = ReadFloat#(F) : Z# = ReadFloat#(F)
			Pitch# = ReadFloat#(F) : Yaw# = ReadFloat#(F) : Roll# = ReadFloat#(F)
			S\ScaleX# = ReadFloat#(F) : S\ScaleY# = ReadFloat#(F) : S\ScaleZ# = ReadFloat#(F)
		
			; is it a bump mesh? [BUMP]
         If Instr(Name$, "BUMPED\") Then
        	 meshName$ = GetMeshNameClean$(S\MeshID)
        	 bumpMap = LoadTexture("Data\Meshes\"+meshName$+"_NORMAL.jpg")
			 ;TextureCoords(colorMap, 1)
			 EntityTexture S\EN, bumpMap, 0, 1
        	 TextureBlend bumpMap,4
			 FreeTexture(bumpMap)
         EndIf

			; bump mesh end
			
			; Animation mode and ownership ID
			S\AnimationMode = ReadByte(F)
			S\SceneryID = ReadByte(F) ;{##}
			; Retexturing
			S\TextureID = ReadShort(F)
			; Collision/picking
			S\CatchRain = ReadByte(F)
			
			
			
			Collides = ReadByte(F)
			; Lightmap information and RCTE data
			S\Lightmap$ = ReadString$(F)
			S\RCTE$ = ReadString$(F)
			
			;v1.104 options cysis145 [010]
			S\CastShadow = ReadByte(F)
			S\ReceiveShadow = ReadByte(F)
			
			S\RenderRange = ReadByte(F) ;[011]

			If S\EN <> 0
				; Toolbox extras [~@~]
				;If Len(S\RCTE$) > 5
				;	Select Left$(S\RCTE$, 5)
				;		Case "_TREE"
				;			If DisplayItems = False
				;				swingsty = Int(Mid$(S\RCTE$, 6, 1))
				;				evergrn = Int(Mid$(S\RCTE$, 7, 1))
				;				S\EN = LoadTree("", evergrn, S\EN, swingsty)
				;			EndIf
				;		Case "_GRSS"
				;			swingsty = Int(Mid$(S\RCTE$, 6, 1))
				;			evergrn = Int(Mid$(S\RCTE$, 7, 1))
				;			S\EN = LoadGrass("", evergrn, S\EN, swingsty)
				;		Case "_RCDN"
				;			If DisplayItems = False
				;				RotateMesh(S\EN, Pitch#, Yaw#, Roll#)
				;				ScaleEntity(S\EN, S\ScaleX#, S\ScaleY#, S\ScaleZ#)
				;				ChunkTerrain(S\EN, 3, 3, 3, X#, Y#, Z#)
				;				Delete S
				;				Goto CancelScenery
				;			EndIf
				;	End Select
				;EndIf

				; Set position/rotation
				PositionEntity S\EN, X#, Y#, Z# : RotateEntity S\EN, Pitch#, Yaw#, Roll#
				ScaleEntity S\EN, S\ScaleX#, S\ScaleY#, S\ScaleZ#
				
				;Dynamic Lighting
				S\ENName=Left(Lower(GetFilename$(Name$)), 2)
				S\LightID=0
				S\ENLight=Lower(GetFilename$(Name$))
				S\ENWF=Lower(GetFilename$(Name$))
				S\ENFM=Lower(GetFilename$(Name$))
				S\ENFL=Lower(GetFilename$(Name$))
				
				
				;;LOD RAMOIDA
	;			S\SceneryType$ = "" ;initiate the variable just in case Ramoida
	;			If Instr(Name$, "RCTE\") Or  Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\CITYPATHS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFSCAFFOLD\") Or  Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\ICE CAVES\") Or  Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\CAVE\") Or Instr(Name$, "AAAAAAAM\OLD_MINE\OLD_MINE\")
	;				S\SceneryType$ = "T" ;If it is Terrain, mark it as "T" ;This will be used at Client.bb
	;			;;BUILDINGS
	;		    ElseIf Instr(Name$, "BUILDINGS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\ALKERZ\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\DONE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\DRAGON GATE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\MIDDEL EAST\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\NEWBUILDINGS2\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\RUINED_BRIDGE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\TREE BASE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\TROPICAL\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\WAGONS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\WARTORN\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\CASTLE2\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\FARM\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\MARKET STALLS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\NEW BUILDINGS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\RESPAWN POINT\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\SNOW PILES\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\DOCKS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\FIRT06\")  Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\FOUNTAIN\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\STATUES\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\VILLAGE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ALKERZARK CENTER CHURCH\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ANCIENT_RUINS03\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ARTERIA3D_TROPICALPACK\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ELVENCITY_2010_UPDATE_GENERIC\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\OLD PORTAL\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\RUINED_BRIDGE\") Or Instr(Name$, "AAAAAAAM\CATAPULTS-SRC\") Or Instr(Name$, "AAAAAAAM\STONE BRIDGE\") Or Instr(Name$, "AAAAAAAM\WAGONS\") Or Instr(Name$, "AAAAAAAM\WINDMILL\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\NICE RS\") Or Instr(Name$, "RCTREES\") Or Instr(Name$, "AAAAAAAM\SPIKES\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\")
	;				S\SceneryType$ = "B" 
	;				EntityAutoFade S\EN,BuildingMinViewDistance#,BuildingMaxViewDistance#;If it is an object set autofade
	;			;GRASS
	;			ElseIf Instr(Name$, "GRASS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\FOREST GRASSS\")  Or Instr(Name$, "AAAAAAAM\CRATES3D\CRATES\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\BARRELS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\HANGING ANIMALS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\BANNERS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\LAMP POST\") Or Instr(Name$, "ACTORS\HORSE AND CHART\") Or Instr(Name$, "ACTORS\NPC'S\ALKERZARK SOLDIERS\")
	;				S\SceneryType$ = "G"
	;				EntityAutoFade S\EN, GrassMinViewDistance#, GrassMaxViewDistance#
	;			;;RUNIC
	;			ElseIf Instr(Name$, "EFFECTS\RUNICPATH\")
	;				S\SceneryType$ = "Z"
	;				EntityAutoFade S\EN, RunicMinViewDistance#, RunicMaxViewDistance#
	;			EndIf
	;	
	;			;Shadows file trackers				
	;			;Recievers
	;			If Instr(Name$, "RCTE\") Or Instr(Name$, "Decles\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\ALKERZ\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\DRAGON GATE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\TRACK 1.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\TRACK 2.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\TRACK END.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\TRACK SLIGHT TURN.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\TRACK STRAIGHT END.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\TRACK STRAIGHT.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\TRACK TIGHT TURN.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\NEWBUILDINGS2\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\RUINED_BRIDGE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\TREE BASE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\TROPICAL\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\WAGONS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\FARM\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\MARKET STALLS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\NEW BUILDINGS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\RESPAWN POINT\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\VIKINGPACK2\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\CITYPATHS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\DOCKS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\FOUNTAIN\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\STATUES\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\VILLAGE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ALKERZARK CENTER CHURCH\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ANCIENT_RUINS03\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ARTERIA3D_TROPICALPACK\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ELVENCITY_2010_UPDATE_GENERIC\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\OLD PORTAL\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\RUINED_BRIDGE\")  Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\NICE RS\")  Or Instr(Name$, "AAAAAAAM\CATAPULTS-SRC\") Or Instr(Name$, "AAAAAAAM\PEASANT_HOUSE\") Or Instr(Name$, "AAAAAAAM\STONE BRIDGE\") Or Instr(Name$, "AAAAAAAM\WAGONS\") Or Instr(Name$, "AAAAAAAM\WINDMILL\") Or Instr(Name$, "AAAFROMDEMO\") Or Instr(Name$, "AAAM") Or Instr(Name$, "ATREEMAGIK\") Or Instr(Name$, "B3D ACTORS\") Or Instr(Name$, "BRIDGES\") Or Instr(Name$, "ITEMS\") Or Instr(Name$, "MYMODLES\") Or Instr(Name$, "MYWEAPONS\") Or Instr(Name$, "RCTREES\") Or Instr(Name$, "SHADRE\") Or Instr(Name$, "TREEMAGIK\") Or Instr(Name$, "AAAAAAAM\SPIKES\") Or Instr(Name$, "CHARACTER SET\")
	;			 	;Any models in the rcte folder becomes a receiver
	;				EntityTexture S\EN, ShadowTexture, 0, 2
	;				;AttachShadowReceiver% (S\EN) ; removes incorrect shadows (this was disabled as it was massively impacting on performance of around 30 fps)
	;			EndIf
	;			;Casters
	;			If Instr(Name$, "AAAAAAAAAAAAMMMMMM2\ALKERZ\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\BONES DINO\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\DONE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\CLIFF SUPPORT.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\CRANE.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\MINE CART.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\PROP SET1.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\PROP SET2.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\SHAFT SUPPORT.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\STATUE.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\STONE CARVING.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\FROZENREEFMINES\TRACK STOPPER.B3D") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\DRAGO RUINS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\DRAGON GATE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\MIDDEL EAST\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\NEWBUILDINGS2\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\RUINED_BRIDGE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\TROPICAL\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\WAGONS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\WARTORN\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\CASTLE2\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\LAMP POST\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\MARKET STALLS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\NEW BUILDINGS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\RESPAWN POINT\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\VIKINGPACK2\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\DOCKS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\FIRT06\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\FOUNTAIN\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\STATUES\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\VILLAGE\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ALKERZARK CENTER CHURCH\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ANCIENT_RUINS03\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ARTERIA3D_TROPICALPACK\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ELVENCITY_2010_UPDATE_GENERIC\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\ROOTS\") Or Instr(Name$, "AAAAAAAAAAAAMMMMMM2\AMMMMM222\AMMMMM1\AMMMM\RUINED_BRIDGE\") Or Instr(Name$, "AAAAAAAM\CATAPULTS-SRC\") Or Instr(Name$, "AAAAAAAM\CRATES3D\") Or Instr(Name$, "AAAAAAAM\LOGS\") Or Instr(Name$, "AAAAAAAM\PEASANT_HOUSE\") Or Instr(Name$, "AAAAAAAM\STONE BRIDGE\") Or Instr(Name$, "AAAAAAAM\WAGONS") Or Instr(Name$, "AAAAAAAM\WINDMILL") Or Instr(Name$, "AAAM\") Or Instr(Name$, "AFROM RANDOM\") Or Instr(Name$, "ANIMAL PACKS\") Or Instr(Name$, "ATREEMAGIK\") Or Instr(Name$, "B3D ACTORS\") Or Instr(Name$, "BRIDGES\") Or Instr(Name$, "DUNGEON PACK 1\") Or Instr(Name$, "ITEMS\") Or Instr(Name$, "MY MODLES\") Or Instr(Name$, "MYWEAPONS\") Or Instr(Name$, "RCTREES\")
	;				CreateShadowCaster% (S\EN)
	;			EndIf
				
				; NewLOD cysis145 [011]
				S\SceneryType$ = "" 
				If DisplayItems = False
					;Always Renders
					If S\RenderRange = 0
						S\SceneryType$ = "T"
					;Short Range
					ElseIf S\RenderRange = 1
						S\SceneryType$ = "G"
						EntityAutoFade S\EN, GrassMinViewDistance#, GrassMaxViewDistance#
					;Long Range
					ElseIf S\RenderRange =2
						S\SceneryType$ = "B" 
						EntityAutoFade S\EN, BuildingMinViewDistance#, BuildingMaxViewDistance#
					EndIf
				EndIf


	; Scenery Shadows cysis145 [010]
	If S\ReceiveShadow And DisplayItems = False 
		EntityTexture S\EN, ShadowTexture, 0, 2 
  	    AttachShadowReceiver S\EN ;helps remove incorrect shadowing 
	EndIf

	If S\CastShadow And DisplayItems = False 
		CreateShadowCaster S\EN 
	EndIf 
				
				

				; Chunking for dungeons etc. 
				If DisplayItems = False
					Name$ = Upper$(GetMeshName$(S\MeshID))
					If Instr(Name$, "RCDUNGEON\") Or Instr(Name$, "CUSTOMCHUNK\")
						RotateMesh(S\EN, Pitch#, Yaw#, Roll#)
						ScaleEntity(S\EN, S\ScaleX#, S\ScaleY#, S\ScaleZ#)
						ChunkTerrain(S\EN, 3, 3, 3, X#, Y#, Z#)
						Delete S
						Goto CancelScenery
					EndIf
				EndIf

				; Lightmap
				If S\Lightmap$ <> ""
					LMap = LoadTexture("Data\Textures\Lightmaps\" + S\Lightmap$)
					TextureCoords(LMap, 1)
					EntityTexture(S\EN, LMap, 0, 1)
					FreeTexture(LMap)
				EndIf

				; Retexturing
           ; If Instr(Name$, "BUMPED\") Then
        ;	    colorMap = LoadTexture ("Data\Meshes\"+meshName$+"_DIMMER.jpg")
		;		;TextureCoords(colorMap, 1)
		;		EntityTexture S\EN, colorMap, 0, 0
         ;	 	TextureBlend colorMap, 3
		;		FreeTexture(colorMap)
         ;   Else
        ;	    If S\TextureID < 65535 Then EntityTexture S\EN, GetTexture(S\TextureID)
         ;   EndIf

			If Instr(Name$, "BUMPED\") Then
        	    colorMap = LoadTexture ("Data\Meshes\"+meshName$+"_SPEC.jpg")
				;TextureCoords(colorMap, 1)
				EntityTexture S\EN, colorMap, 0, 0
         	 	TextureBlend colorMap, 3
				FreeTexture(colorMap)
            Else
        	    If S\TextureID < 65535 Then EntityTexture S\EN, GetTexture(S\TextureID)
            EndIf

			If Instr(Name$, "BUMPED\") Then
        	    colorMap = LoadTexture ("Data\Meshes\"+meshName$+"_COLOR.jpg")
				;TextureCoords(colorMap, 1)
				EntityTexture S\EN, colorMap, 0, 2
         	 	TextureBlend colorMap, 2
				FreeTexture(colorMap)
            Else
        	    If S\TextureID < 65535 Then EntityTexture S\EN, GetTexture(S\TextureID)
            EndIf


			;Glow models
	;		If Instr(Name$, "GLOWMODELS\") Then
    ;    	    colorMap = LoadTexture ("Data\Meshes\"+meshName$+"_GLOW.jpg")
	;			;TextureCoords(colorMap, 1)
	;			EntityTexture S\EN, colorMap, 0, 4
    ;     	 	TextureBlend colorMap, 3
	;			FreeTexture(colorMap)
    ;        Else
    ;    	    If S\TextureID < 65535 Then EntityTexture S\EN, GetTexture(S\TextureID)
    ;        EndIf


				; Type handle
				NameEntity S\EN, Handle(S)

				; Animation
				If DisplayItems = False
					If S\AnimationMode = 1
						Animate(S\EN, 1)
					ElseIf S\AnimationMode = 2
						Animate(S\EN, 2)
					EndIf
				EndIf

				; Set collision/picking
				EntityType(S\EN, Collides)
				If Collides = C_Sphere
					EntityPickMode(S\EN, 1)
					MaxLength# = MeshWidth#(S\EN) * S\ScaleX#
					If MeshDepth#(S\EN) * S\ScaleZ# > MaxLength# Then MaxLength# = MeshDepth#(S\EN) * S\ScaleZ#
					EntityRadius(S\EN, MaxLength# / 2.0, (MeshHeight#(S\EN) * S\ScaleY#) / 2.0)
				ElseIf Collides = C_Triangle
					EntityPickMode(S\EN, 2)
				ElseIf Collides = C_Box
					EntityPickMode(S\EN, 3)
					WidthT# = MeshWidth#(S\EN) * S\ScaleX#
					HeightT# = MeshHeight#(S\EN) * S\ScaleY#
					DepthT# = MeshDepth#(S\EN) * S\ScaleZ#
					EntityBox(S\EN, WidthT# / -2.0, HeightT# / -2.0, DepthT# / -2.0, WidthT#, HeightT#, DepthT#)
				EndIf
				ResetEntity(S\EN)

				; Create catch plane if required
				If S\CatchRain And DisplayItems = False
					MMV.MeshMinMaxVertices = MeshMinMaxVerticesTransformed(S\EN, Pitch#, Yaw#, Roll#, S\ScaleX#, S\ScaleY#, S\ScaleZ#)
					CP.CatchPlane = New CatchPlane
					CP\Y# = MMV\MaxY# + Y#
					CP\MinX# = MMV\MinX# + X#
					CP\MinZ# = MMV\MinZ# + Z#
					CP\MaxX# = MMV\MaxX# + X#
					CP\MaxZ# = MMV\MaxZ# + Z#
				EndIf
			; Mesh has been deleted or removed from the database!
			Else
				If DisplayItems = True
					Delete(S)
				Else
					RuntimeError("Could not find model with ID " + S\MeshID)
				EndIf
			EndIf
			.CancelScenery

			; RottNet update
			If UpdateRottNet = True And MilliSecs() - RNUpdateTime > 500 Then RCE_Update() : RCE_CreateMessages() : RNUpdateTime = MilliSecs()

			; Loading bar update every alternate object
			If LoadScreen <> 0 And (i Mod 2) = 0
				GY_UpdateProgressBar(LoadProgressBar, 5 + (40 * i) / Sceneries)
				RenderWorld()
				Flip()
			EndIf
		Next

		; Water
		Waters = ReadShort(F)
		For i = 1 To Waters
			W.Water = New Water
			; Entity/Texture
			W\TexID = ReadShort(F)
			W\TexHandle = GetTexture(W\TexID, True)
			W\TexScale# = ReadFloat#(F)
			; Position/size
			X# = ReadFloat#(F) : Y# = ReadFloat#(F) : Z# = ReadFloat#(F)
			W\ScaleX# = ReadFloat#(F) : W\ScaleZ# = ReadFloat#(F)
			XDivs = Ceil(W\ScaleX# / 15.0)
			ZDivs = Ceil(W\ScaleZ# / 15.0)
			If XDivs > 70 Then XDivs = 70
			If ZDivs > 70 Then ZDivs = 70
			W\EN = CreateSubdividedPlane(XDivs, ZDivs, W\ScaleX#, W\ScaleZ#)
			ScaleEntity W\EN, W\ScaleX#, 1.0, W\ScaleZ#
			PositionEntity W\EN, X#, Y#, Z#
			ScaleTexture W\TexHandle, W\TexScale#, W\TexScale#
			
			EntityTexture W\EN, W\TexHandle,0,2 ; 1 par d�faut mais invisible avec refract		
			
		;&&& Water edit terrier 
   		;BumpTexA = LoadAnimTexture("Data\Textures\Water\water_anim.jpg", 9, 64, 64, 0, 32) ;I'm using the FastExt demo bump. If you are using your own, you may need to change the parameters. 
  		;TextureBlend BumpTexA, FE_BUMPLUM 
		; bump
		
		BumpTexture = LoadAnimTexture ( "Data\Textures\Water\water_anim.jpg", 9, 64, 64, 0, 32 )
		TextureBlend BumpTexture, FE_BUMP
	 	;ScaleTexture BumpTexture,0.012,0.012	
									; <<<< 	����� ����� ��� ����� 
		FoamTexture = LoadTexture ( "Data\Textures\Water\foam.png", 1+2 )
		TextureBlend FoamTexture, 1
		ScaleTexture FoamTexture,30,30
			
        ScaleTexture BumpTexture,W\TexScale#, W\TexScale#
       	;ScaleTexture FoamTexture,W\TexScale#, W\TexScale#

       	 W\WaterClipplane = CreateClipplane (  WaterPlane  )               
		;ClipPivotUp = CreatePivot()  :  RotateEntity ClipPivotUp,0,0,180   :   PositionEntity ClipPivotUp, 0, 0.05, 0
         
		AlignClipplane W\WaterClipplane, W\EN
			
			; Colour
			W\Red = ReadByte(F)
			W\Green = ReadByte(F)
			W\Blue = ReadByte(F)
			; Opacity
			W\Opacity = ReadByte(F)
			If W\Opacity >= 100
				EntityFX(W\EN, 1 + 16)
			Else
				EntityFX(W\EN, 16)
			EndIf
			Alpha# = Float#(W\Opacity) / 100.0
			If Alpha# > 1.0 Then Alpha# = 1.0
			EntityAlpha(W\EN, Alpha#)
				
			; Picking
			EntityBox W\EN, W\ScaleX# / -2.0, -1.0, W\ScaleZ# / -2.0, W\ScaleX#, 2.0, W\ScaleZ#
			; Type handle
			NameEntity W\EN, Handle(W)
			; If I am a walking only character, create a collision box here
			If DisplayItems = False And Me.ActorInstance <> Null
				If Me\Actor\Environment = Environment_Walk
					C.ColBox = New ColBox
					C\EN = CreateCube()
					EntityAlpha C\EN, 0.0
					; Position/rotation/size
					Y# = Y# - 1000.0
					C\ScaleX# = Abs(W\ScaleX# / 2.0) : C\ScaleY# = 1000.0 : C\ScaleZ# = Abs(W\ScaleZ# / 2.0)
					PositionEntity C\EN, X#, Y#, Z#
					ScaleEntity C\EN, C\ScaleX#, C\ScaleY#, C\ScaleZ#
					; Collisions
					EntityBox C\EN, -C\ScaleX#, -C\ScaleY#, -C\ScaleZ#, C\ScaleX# * 2.0, C\ScaleY# * 2.0, C\ScaleZ# * 2.0
					EntityType C\EN, C_Box
					; Type handle
					NameEntity C\EN, Handle(C)
				EndIf
			EndIf
			; RottNet update
			If UpdateRottNet = True And MilliSecs() - RNUpdateTime > 500 Then RCE_Update() : RCE_CreateMessages() : RNUpdateTime = MilliSecs()
		Next

		; Loading bar update
		If LoadScreen <> 0
			GY_UpdateProgressBar(LoadProgressBar, 60)
			RenderWorld()
			Flip()
		EndIf

		; Collision zones
		ColBoxes = ReadShort(F)
		For i = 1 To ColBoxes
			C.ColBox = New ColBox
			C\EN = CreateCube()
			If DisplayItems = True Then EntityAlpha C\EN, 0.4 Else EntityAlpha C\EN, 0.0
			; Position/rotation/size
			X# = ReadFloat#(F) : Y# = ReadFloat#(F) : Z# = ReadFloat#(F)
			Pitch# = ReadFloat#(F) : Yaw# = ReadFloat#(F) : Roll# = ReadFloat#(F)
			C\ScaleX# = ReadFloat#(F) : C\ScaleY# = ReadFloat#(F) : C\ScaleZ# = ReadFloat#(F)
			PositionEntity C\EN, X#, Y#, Z#
			RotateEntity C\EN, Pitch#, Yaw#, Roll#
			ScaleEntity C\EN, C\ScaleX#, C\ScaleY#, C\ScaleZ#
			; Collisions
			EntityBox C\EN, -C\ScaleX#, -C\ScaleY#, -C\ScaleZ#, C\ScaleX# * 2.0, C\ScaleY# * 2.0, C\ScaleZ# * 2.0
			EntityType C\EN, C_Box
			; Type handle
			NameEntity C\EN, Handle(C)
		Next

		; Loading bar update
		If LoadScreen <> 0
			GY_UpdateProgressBar(LoadProgressBar, 65)
			RenderWorld()
			Flip()
		EndIf

		; Emitters
		Emitters = ReadShort(F)
		For i = 1 To Emitters
			; Create emitter parent entity
			E.Emitter = New Emitter
			If DisplayItems = True 
				E\EN = CreateCone() : ScaleMesh E\EN, 3, 3, 3 : EntityAlpha E\EN, 0.5
			Else 
				E\EN = CreatePivot()
			EndIf
								
			; Read in emitter data
			E\ConfigName$ = ReadString$(F)
			E\TexID = ReadShort(F)
			Texture = GetTexture(E\TexID)
			X# = ReadFloat#(F) : Y# = ReadFloat#(F) : Z# = ReadFloat#(F)
			Pitch# = ReadFloat#(F) : Yaw# = ReadFloat#(F) : Roll# = ReadFloat#(F)
			; Load config
			E\Config = RP_LoadEmitterConfig("Data\Emitter Configs\" + E\ConfigName$ + ".rpc", Texture, CameraEN)
			
			; Loaded successfully, create the emitter
			If E\Config <> 0 
				EmitterEN = RP_CreateEmitter(E\Config)
				EntityParent EmitterEN, E\EN, False
				EntityPickMode E\EN, 2
				NameEntity E\EN, Handle(E)
				; Position/rotation
				PositionEntity E\EN, X#, Y#, Z#
				RotateEntity E\EN, Pitch#, Yaw#, Roll# 
			; Failed To load config, remove the emitter And display an error message If running on client
			Else
				If DisplayItems = False Then RuntimeError("Could not load emitter: " + E\ConfigName$)
				HideEntity(E\EN)
				FreeEntity(E\EN)
				Delete(E)
			EndIf
			
		;	EntityAutoFade E\EN, 10, 20 
			
						
			; RottNet update
			If UpdateRottNet = True And MilliSecs() - RNUpdateTime > 500 Then RCE_Update() : RCE_CreateMessages() : RNUpdateTime = MilliSecs()
		Next

		; Loading bar update
		If LoadScreen <> 0
			GY_UpdateProgressBar(LoadProgressBar, 80)
			RenderWorld()
			Flip()
		EndIf

		; Blitz LOD terrains
		Terrains = ReadShort(F)
		For i = 1 To Terrains
			T.Terrain = New Terrain
			; Textures
			T\BaseTexID = ReadShort(F)
			T\DetailTexID = ReadShort(F)
			; Terrain heights
			GridSize = ReadInt(F)
			T\EN = CreateTerrain(GridSize)
			For X = 0 To TerrainSize(T\EN)
				For Z = 0 To TerrainSize(T\EN)
					ModifyTerrain(T\EN, X, Z, ReadFloat#(F), False)
				Next
			Next
			; Position/rotation/size
			X# = ReadFloat#(F) : Y# = ReadFloat#(F) : Z# = ReadFloat#(F)
			Pitch# = ReadFloat#(F) : Yaw# = ReadFloat#(F) : Roll# = ReadFloat#(F)
			T\ScaleX# = ReadFloat#(F)
			T\ScaleY# = ReadFloat#(F)
			T\ScaleZ# = ReadFloat#(F)
			PositionEntity T\EN, X#, Y#, Z# : RotateEntity T\EN, Pitch#, Yaw#, Roll#
			ScaleEntity T\EN, T\ScaleX#, T\ScaleY#, T\ScaleZ#
			; Texture scale
			T\DetailTexScale# = ReadFloat#(F)
			; Apply textures
			Tex = GetTexture(T\BaseTexID, True)
			If Tex <> 0
				ScaleTexture(Tex, TerrainSize(T\EN), TerrainSize(T\EN))
				EntityTexture(T\EN, Tex, 0, 0)
				FreeTexture(Tex)
			EndIf
			If T\DetailTexID > 0 And T\DetailTexID < 65535
				T\DetailTex = GetTexture(T\DetailTexID, True)
				ScaleTexture(T\DetailTex, T\DetailTexScale#, T\DetailTexScale#)
				EntityTexture(T\EN, T\DetailTex, 0, 1)
				If DisplayItems = False
					FreeTexture(T\DetailTex)
					T\DetailTex = 0
				EndIf
			EndIf
			; Detail etc.
			T\Detail = ReadInt(F)
			T\Morph = ReadByte(F)
			T\Shading = ReadByte(F)
			TerrainDetail(T\EN, T\Detail, T\Morph)
			TerrainShading(T\EN, T\Shading)
			; Collisions
			EntityType T\EN, C_Triangle
			EntityPickMode T\EN, 2
			; Type handle
			NameEntity T\EN, Handle(T)

			; RottNet update
			If UpdateRottNet = True And MilliSecs() - RNUpdateTime > 500 Then RCE_Update() : RCE_CreateMessages() : RNUpdateTime = MilliSecs()

			; Loading bar update
			If LoadScreen <> 0
				GY_UpdateProgressBar(LoadProgressBar, 80 + ((15 * i) / Terrains))
				RenderWorld()
				Flip()
			EndIf
		Next

		; Loading bar update
		If LoadScreen <> 0
			GY_UpdateProgressBar(LoadProgressBar, 98) ;95
			RenderWorld()
			Flip()
		EndIf

		; Sound zones
		Sounds = ReadShort(F)
		For i = 1 To Sounds
			SZ.SoundZone = New SoundZone
			If DisplayItems = True
				SZ\EN = CreateSphere()
				EntityAlpha SZ\EN, 0.5
				EntityColor SZ\EN, 255, 255, 0
			Else
				SZ\EN = CreatePivot()
			EndIf
			; Position/size
			X# = ReadFloat#(F) : Y# = ReadFloat#(F) : Z# = ReadFloat#(F)
			SZ\Radius# = ReadFloat#(F)
			ScaleEntity SZ\EN, SZ\Radius#, SZ\Radius#, SZ\Radius#
			PositionEntity SZ\EN, X#, Y#, Z#
			; Sound options
			SZ\SoundID = ReadShort(F)
			SZ\MusicID = ReadShort(F)
			SZ\RepeatTime = ReadInt(F)
			SZ\Volume = ReadByte(F)
			; Load sound
			If SZ\SoundID <> 65535
				SZ\LoadedSound = GetSound(SZ\SoundID)
				SZ\Is3D = Asc(Right$(GetSoundName$(SZ\SoundID), 1))
			Else
				SZ\MusicFilename$ = "Data\Music\" + GetMusicName$(SZ\MusicID)
			EndIf
			; Type handle
			NameEntity SZ\EN, Handle(SZ)
			; RottNet update
			If UpdateRottNet = True And MilliSecs() - RNUpdateTime > 500 Then RCE_Update() : RCE_CreateMessages() : RNUpdateTime = MilliSecs()
		Next

	CloseFile(F)

	UnlockMeshes()
	UnlockTextures()

	; End loading screen
	If LoadScreen <> 0
		FreeEntity(LoadScreen)
		;FreeEntity(LoadLabel)
		GY_FreeGadget(LoadProgressBar)
	EndIf
	If ChannelPlaying(CLoadMusic) = True Then StopChannel(CLoadMusic)
	
	Return True

End Function

; Saves the current area back to file
Function SaveArea(Name$)

	F = WriteFile("Data\Areas\" + Name$ + ".dat")
	If F = 0 Then Return False

		; Loading screen
		WriteShort F, LoadingTexID
		WriteShort F, LoadingMusicID

		; Environment
		WriteShort F, SkyTexID
		WriteShort F, CloudTexID
		WriteShort F, StormCloudTexID
		WriteShort F, StarsTexID

		WriteByte F, FogR
		WriteByte F, FogG
		WriteByte F, FogB
		WriteFloat F, FogNear#
		WriteFloat F, FogFar#

		WriteShort F, MapTexID
		WriteByte F, Outdoors
		WriteByte F, AmbientR
		WriteByte F, AmbientG
		WriteByte F, AmbientB
		WriteFloat F, DefaultLightPitch#
		WriteFloat F, DefaultLightYaw#
		WriteFloat F, SlopeRestrict#

		; Scenery
		Count = 0
		For S.Scenery = Each Scenery : Count = Count + 1 : Next
		WriteShort F, Count
		For S.Scenery = Each Scenery
			WriteShort F, S\MeshID
			WriteFloat F, EntityX#(S\EN, True)
			WriteFloat F, EntityY#(S\EN, True)
			WriteFloat F, EntityZ#(S\EN, True)
			WriteFloat F, EntityPitch#(S\EN, True)
			WriteFloat F, EntityYaw#(S\EN, True)
			WriteFloat F, EntityRoll#(S\EN, True)
			WriteFloat F, S\ScaleX#
			WriteFloat F, S\ScaleY#
			WriteFloat F, S\ScaleZ#
			WriteByte F, S\AnimationMode
			WriteByte F, S\SceneryID
			WriteShort F, S\TextureID
			WriteByte F, S\CatchRain
						
			WriteByte F, GetEntityType(S\EN)
			WriteString F, S\Lightmap$
			WriteString F, S\RCTE$ ; Extra data for RTCE
			
			WriteByte F, S\CastShadow ;[010]
			WriteByte F, S\ReceiveShadow
			WriteByte F, S\RenderRange ;[011]
			
		Next

		; Water
		Count = 0
		For W.Water = Each Water : Count = Count + 1 : Next
		WriteShort F, Count
		For W.Water = Each Water
			WriteShort F, W\TexID
			WriteFloat F, W\TexScale#
			WriteFloat F, EntityX#(W\EN, True)
			WriteFloat F, EntityY#(W\EN, True)
			WriteFloat F, EntityZ#(W\EN, True)
			WriteFloat F, W\ScaleX#
			WriteFloat F, W\ScaleZ#
			WriteByte F, W\Red
			WriteByte F, W\Green
			WriteByte F, W\Blue
			WriteByte F, W\Opacity
		Next

		; Collision boxes
		Count = 0
		For C.ColBox = Each ColBox : Count = Count + 1 : Next
		WriteShort F, Count
		For C.ColBox = Each ColBox
			WriteFloat F, EntityX#(C\EN, True)
			WriteFloat F, EntityY#(C\EN, True)
			WriteFloat F, EntityZ#(C\EN, True)
			WriteFloat F, EntityPitch#(C\EN, True)
			WriteFloat F, EntityYaw#(C\EN, True)
			WriteFloat F, EntityRoll#(C\EN, True)
			WriteFloat F, C\ScaleX#
			WriteFloat F, C\ScaleY#
			WriteFloat F, C\ScaleZ#
		Next

		; Emitters
		Count = 0
		For E.Emitter = Each Emitter : Count = Count + 1 : Next
		WriteShort F, Count
		For E.Emitter = Each Emitter
			WriteString F, E\ConfigName$
			WriteShort F, E\TexID
			WriteFloat F, EntityX#(E\EN, True)
			WriteFloat F, EntityY#(E\EN, True)
			WriteFloat F, EntityZ#(E\EN, True)
			WriteFloat F, EntityPitch#(E\EN, True)
			WriteFloat F, EntityYaw#(E\EN, True)
			WriteFloat F, EntityRoll#(E\EN, True)
		Next

		; Terrains
		Count = 0
		For T.Terrain = Each Terrain :  Count = Count + 1 : Next
		WriteShort F, Count
		For T.Terrain = Each Terrain
			WriteShort F, T\BaseTexID
			WriteShort F, T\DetailTexID
			WriteInt F, TerrainSize(T\EN)
			For X = 0 To TerrainSize(T\EN)
				For Z = 0 To TerrainSize(T\EN)
					WriteFloat F, TerrainHeight#(T\EN, X, Z)
				Next
			Next
			WriteFloat F, EntityX#(T\EN, True)
			WriteFloat F, EntityY#(T\EN, True)
			WriteFloat F, EntityZ#(T\EN, True)
			WriteFloat F, EntityPitch#(T\EN, True)
			WriteFloat F, EntityYaw#(T\EN, True)
			WriteFloat F, EntityRoll#(T\EN, True)
			WriteFloat F, T\ScaleX#
			WriteFloat F, T\ScaleY#
			WriteFloat F, T\ScaleZ#
			WriteFloat F, T\DetailTexScale#
			WriteInt   F, T\Detail
			WriteByte  F, T\Morph
			WriteByte  F, T\Shading
		Next

		; Sound zones
		Count = 0
		For SZ.SoundZone = Each SoundZone : Count = Count + 1 : Next
		WriteShort F, Count
		For SZ.SoundZone = Each SoundZone
			WriteFloat F, EntityX#(SZ\EN, True)
			WriteFloat F, EntityY#(SZ\EN, True)
			WriteFloat F, EntityZ#(SZ\EN, True)
			WriteFloat F, SZ\Radius#
			WriteShort F, SZ\SoundID
			WriteShort F, SZ\MusicID
			WriteInt F, SZ\RepeatTime
			WriteByte F, SZ\Volume
		Next

	CloseFile(F)
	Return True

End Function

; Unloads the current area from memory
Function UnloadArea()

	If SkyTexID > -1 And SkyTexID < 65535 Then UnloadTexture(SkyTexID)
	If CloudTexID > -1 And CloudTexID < 65535 Then UnloadTexture(CloudTexID)
	If StormCloudTexID > -1 And StormCloudTexID < 65535 Then UnloadTexture(StormCloudTexID)
	If StarsTexID > -1 And StarsTexID < 65535 Then UnloadTexture(StarsTexID)

;	UnloadTrees(False) [~@~]

	For S.Scenery = Each Scenery
		;Shadow
		FreeShadowCaster% (S\EN)
		;FreeShadowReceiver% (S\EN)
	
		If S\TextureID < 65535 Then UnloadTexture(S\TextureID)
		UnloadMesh(S\MeshID)
		FreeEntity(S\EN)
		Delete(S)
	Next

	For W.Water = Each Water
		UnloadTexture(W\TexID)
		FreeTexture(W\TexHandle)
		FreeEntity(W\EN)
		
		;FreeTexture(W\BumpTexA)
		
		Delete(W)
	Next

	For C.ColBox = Each ColBox
		FreeEntity(C\EN)
		Delete(C)
	Next

	For E.Emitter = Each Emitter
		RP_FreeEmitter(GetChild(E\EN, 1), True, False)
		UnloadTexture(E\TexID)
		FreeEntity(E\EN)
		Delete(E)
	Next

	For SZ.SoundZone = Each SoundZone
		If SZ\Channel <> 0 Then StopChannel(SZ\Channel)
		If SZ\SoundID > 0 And SZ\SoundID < 65535 Then UnloadSound(SZ\SoundID)
		FreeEntity(SZ\EN)
		Delete(SZ)
	Next

	For T.Terrain = Each Terrain
		FreeEntity T\EN
		UnloadTexture(T\BaseTexID)
		If T\DetailTex <> 0 Then FreeTexture(T\DetailTex)
		If T\DetailTexID < 65535 Then UnloadTexture(T\DetailTexID)
		Delete(T)
	Next
	
	;For AI.ActorInstance = Each ActorInstance
	;		FreeShadowCaster% (AI\EN)
	;Next

	Delete Each CatchPlane

	;Shadow
	FreeShadows
	FreeTexture ShadowTexture

End Function

; Sets the view distance
Function SetViewDistance(CameraEN, Near#, Far#)

	CameraRange CameraEN, 0.8, Far# + 10.0
	CameraFogRange CameraEN, Near#, Far#
	ScaleEntity SkyEN, Far# - 10.0, Far# - 10.0, Far# - 10.0
	ScaleEntity StarsEN, Far# - 10.0, Far# - 10.0, Far# - 10.0
	ScaleEntity CloudEN, Far# - 15.0, Far# - 15.0, Far# - 15.0

End Function

; Splits terrain into smaller segments
Function ChunkTerrain(Mesh, chx# = 10, chy# = 10, chz# = 10, XPos# = 0.0, YPos# = 0.0, ZPos# = 0.0)

	; Clear existing chunks
	Delete Each Cluster

	; First we'll need to get the original terrain scale for matching scale after the chunking
    vx# = GetMatElement#(Mesh, 0, 0)
	vy# = GetMatElement#(Mesh, 0, 1)
	vz# = GetMatElement#(Mesh, 0, 2)
	XScale# = Sqr#(vx# * vx# + vy# * vy# + vz# * vz#)
	vx# = GetMatElement#(Mesh, 1, 0)
	vy# = GetMatElement#(Mesh, 1, 1)
	vz# = GetMatElement#(Mesh, 1, 2)
	YScale# = Sqr#(vx# * vx# + vy# * vy# + vz# * vz#)
	vx# = GetMatElement#(Mesh, 2, 0)
	vy# = GetMatElement#(Mesh, 2, 1)
	vz# = GetMatElement#(Mesh, 2, 2)
	ZScale# = Sqr#(vx# * vx# + vy# * vy# + vz# * vz#)

	; The default values will give you about 23 chunks per terrain
	; Raising the divided by number will give you more chunks
	cx# = Int((MeshWidth#(Mesh)) / chx#)
	cy# = Int((MeshHeight#(Mesh)) / chy#)
	cz# = Int((MeshDepth#(Mesh)) / chz#)

	; Let the chunking begin
	sos = CountSurfaces(mesh)
	For s = 1 To sos
		surf = GetSurface(mesh, s)
		brush = GetSurfaceBrush(surf)

		For t = 0 To CountTriangles(surf) - 1
			x0#  = VertexX#(surf, TriangleVertex(surf, t, 0))
			y0#  = VertexY#(surf, TriangleVertex(surf, t, 0))
			z0#  = VertexZ#(surf, TriangleVertex(surf, t, 0))
			nx0# = VertexNX#(surf, TriangleVertex(surf, t, 0))
			ny0# = VertexNY#(surf, TriangleVertex(surf, t, 0))
			nz0# = VertexNZ#(surf, TriangleVertex(surf, t, 0))
			al0# = VertexAlpha#(surf, TriangleVertex(surf, t, 0))
			cr0# = VertexRed#(surf, TriangleVertex(surf, t, 0))
			cg0# = VertexGreen#(surf, TriangleVertex(surf, t, 0))
			cb0# = VertexBlue#(surf, TriangleVertex(surf, t, 0))
			x1#  = VertexX#(surf, TriangleVertex(surf, t, 1))
			y1#  = VertexY#(surf, TriangleVertex(surf, t, 1))
			z1#  = VertexZ#(surf, TriangleVertex(surf, t, 1))
			nx1# = VertexNX#(surf, TriangleVertex(surf, t, 1))
			ny1# = VertexNY#(surf, TriangleVertex(surf, t, 1))
			nz1# = VertexNZ#(surf, TriangleVertex(surf, t, 1))
			al1# = VertexAlpha#(surf, TriangleVertex(surf, t, 1))
			cr1# = VertexRed#(surf, TriangleVertex(surf, t, 1))
			cg1# = VertexGreen#(surf, TriangleVertex(surf, t, 1))
			cb1# = VertexBlue#(surf, TriangleVertex(surf, t, 1))
			x2#  = VertexX#(surf, TriangleVertex(surf, t, 2))
			y2#  = VertexY#(surf, TriangleVertex(surf, t, 2))
			z2#  = VertexZ#(surf, TriangleVertex(surf, t, 2))
			nx2# = VertexNX#(surf, TriangleVertex(surf, t, 2))
			ny2# = VertexNY#(surf, TriangleVertex(surf, t, 2))
			nz2# = VertexNZ#(surf, TriangleVertex(surf, t, 2))
			al2# = VertexAlpha#(surf, TriangleVertex(surf, t, 2))
			cr2# = VertexRed#(surf, TriangleVertex(surf, t, 2))
			cg2# = VertexGreen#(surf, TriangleVertex(surf, t, 2))
			cb2# = VertexBlue#(surf, TriangleVertex(surf, t, 2))
			
			u0a# = VertexU#(surf, TriangleVertex(surf, t, 0), 0)
			v0a# = VertexV#(surf, TriangleVertex(surf, t, 0), 0)
			u1a# = VertexU#(surf, TriangleVertex(surf, t, 1), 0)
			v1a# = VertexV#(surf, TriangleVertex(surf, t, 1), 0)
			u2a# = VertexU#(surf, TriangleVertex(surf, t, 2), 0)
			v2a# = VertexV#(surf, TriangleVertex(surf, t, 2), 0)
			
			
			u0b# = VertexU#(surf, TriangleVertex(surf, t, 0), 1)
			v0b# = VertexV#(surf, TriangleVertex(surf, t, 0), 1)
			u1b# = VertexU#(surf, TriangleVertex(surf, t, 1), 1)
			v1b# = VertexV#(surf, TriangleVertex(surf, t, 1), 1)
			u2b# = VertexU#(surf, TriangleVertex(surf, t, 2), 1)
			v2b# = VertexV#(surf, TriangleVertex(surf, t, 2), 1)

			; Let's see which chunk we'll assign this vert to
			x_c# = NearestPower(VertexX#(surf, TriangleVertex(surf, t, 0)), cx#)
			y_c# = NearestPower(VertexY#(surf, TriangleVertex(surf, t, 0)), cy#)
			z_c# = NearestPower(VertexZ#(surf, TriangleVertex(surf, t, 0)), cz#)
			Found = False
			For Cl.Cluster = Each Cluster
				If x_c = Cl\xc And y_c = Cl\yc And z_c = Cl\zc
					If Cl\Surf[s] <> 0
						Found = True
						v0 = AddVertex(Cl\Surf[s], x0, y0, z0)
						VertexTexCoords Cl\Surf[s], v0, u0a, v0a, 1, 0
						VertexTexCoords Cl\Surf[s], v0, u0b, v0b, 1, 1
						VertexColor Cl\Surf[s], v0, cr0, cg0, cb0, al0
						VertexNormal Cl\Surf[s], v0, nx0, ny0, nz0
						v1 = AddVertex(Cl\Surf[s], x1, y1, z1)
						VertexTexCoords Cl\Surf[s], v1, u1a, v1a, 1, 0
						VertexTexCoords Cl\Surf[s], v1, u1b, v1b, 1, 1
						VertexColor Cl\Surf[s], v1, cr1, cg1, cb1, al1
						VertexNormal Cl\Surf[s], v1, nx1, ny1, nz1
						v2 = AddVertex(Cl\Surf[s], x2, y2, z2)
						VertexTexCoords Cl\Surf[s], v2, u2a, v2a, 1, 0
						VertexTexCoords Cl\Surf[s], v2, u2b, v2b, 1, 1
						VertexColor Cl\Surf[s], v2, cr2, cg2, cb2, al2
						VertexNormal Cl\Surf[s], v2, nx2, ny2, nz2
						nope = AddTriangle(Cl\Surf[s], v0, v1, v2)
						Exit
					EndIf
				EndIf
			Next

			; If there was no chunk for that area, we'll make it here
			If Found = False
				Cl.Cluster = New Cluster
				nsegs = nsegs + 1
				Cl\xc# = x_c
				Cl\yc# = y_c
				Cl\zc# = z_c
				Cl\Mesh = CreateMesh()
				For ss = 1 To sos
					Cl\Surf[ss] = CreateSurface(Cl\Mesh)
					surf2 = GetSurface(Mesh, ss) 
					brush = GetSurfaceBrush(surf2)
                    PaintSurface Cl\Surf[ss], brush
				Next
				v0 = AddVertex(Cl\Surf[s], x0, y0, z0)
				VertexTexCoords Cl\Surf[s], v0, u0a, v0a, 1, 0
				VertexTexCoords Cl\Surf[s], v0, u0b, v0b, 1, 1
				VertexColor Cl\Surf[s], v0, cr0, cg0, cb0, al0
				VertexNormal Cl\Surf[s], v0, nx0, ny0, nz0
				v1 = AddVertex(Cl\Surf[s], x1, y1, z1)
				VertexTexCoords Cl\Surf[s], v1, u1a, v1a, 1, 0
				VertexTexCoords Cl\Surf[s], v1, u1b, v1b, 1, 1
				VertexColor Cl\Surf[s], v1, cr1, cg1, cb1, al1
				VertexNormal Cl\Surf[s], v1, nx1, ny1, nz1
				v2 = AddVertex(Cl\Surf[s], x2, y2, z2)
				VertexTexCoords Cl\Surf[s], v2, u2a, v2a, 1, 0
				VertexTexCoords Cl\Surf[s], v2, u2b, v2b, 1, 1
				VertexColor Cl\Surf[s], v2, cr2, cg2, cb2, al2
				VertexNormal Cl\Surf[s], v2, nx2, ny2, nz2
				nope = AddTriangle(Cl\Surf[s], v0, v1, v2)
			EndIf
		Next
	Next

	; Finalise chunks by removing blank surfaces and creating the scenery object
	For Cl.Cluster = Each Cluster
		Delete Each Remove_Surf
	    For i = 1 To CountSurfaces(Cl\Mesh)
			If CountVertices(GetSurface(Cl\Mesh, i)) <= 0
				RemS.Remove_Surf = New Remove_Surf
				RemS\ID = i
			EndIf
		Next
		Cl\Mesh = RemoveSurface(Cl\Mesh)
		;EntityFX Cl\Mesh, 1 + 2
		PositionEntity Cl\Mesh, XPos#, YPos#, ZPos#
		ScaleEntity Cl\Mesh, XScale#, YScale#, ZScale#
		EntityType Cl\Mesh, C_Triangle
		EntityPickMode Cl\Mesh, 2
		ResetEntity Cl\Mesh
		Sc.Scenery = New Scenery
		Sc\EN = Cl\Mesh
		Sc\ScaleX# = XScale#
		Sc\ScaleY# = YScale#
		Sc\ScaleZ# = ZScale#
		Sc\TextureID = 65535
		NameEntity Sc\EN, Handle(Sc)
	Next

	FreeEntity Mesh
	Delete Each Cluster
         
End Function

Function NearestPower(N#, Snapper#)

	Return Float#(Int(N# / Snapper#)) * Snapper#

End Function

Function RemoveSurface(Ent)

	; Rebuild the mesh
	newmesh = CreateMesh()
	ns = CountSurfaces(Ent)

	For i = 1 To ns
		nogo = False
		For RemS.Remove_Surf = Each Remove_Surf
			If i = RemS\ID Then nogo = True
		Next
		If nogo = False
			surf = GetSurface(Ent, i)	
			newsurf = CreateSurface(newmesh)
			brush = GetSurfaceBrush(surf)
			tc = CountTriangles(surf)

			For tri = 0 To tc - 1
				v_r1# = VertexRed(surf, TriangleVertex(Surf, tri, 0) )
				v_g1# = VertexGreen(surf, TriangleVertex(Surf, tri, 0)) 
				v_b1# = VertexBlue(surf, TriangleVertex(Surf, tri, 0) )
				v_r2# = VertexRed(surf, TriangleVertex(Surf, tri, 1) )
				v_g2# = VertexGreen(surf, TriangleVertex(Surf, tri, 1)) 
				v_b2# = VertexBlue(surf, TriangleVertex(Surf, tri, 1) )
				v_r3# = VertexRed(surf, TriangleVertex(Surf, tri, 2) )
				v_g3# = VertexGreen(surf, TriangleVertex(Surf, tri, 2)) 
				v_b3# = VertexBlue(surf, TriangleVertex(Surf, tri, 2) )

				v_x0# = VertexX#(surf, TriangleVertex(surf, tri, 0))
				v_x1# = VertexX#(surf, TriangleVertex(surf, tri, 1))
				v_x2# = VertexX#(surf, TriangleVertex(surf, tri, 2))

				v_y0# = VertexY#(surf, TriangleVertex(surf, tri, 0))
				v_y1# = VertexY#(surf, TriangleVertex(surf, tri, 1))
				v_y2# = VertexY#(surf, TriangleVertex(surf, tri, 2))

				v_z0# = VertexZ#(surf, TriangleVertex(surf, tri, 0))
				v_z1# = VertexZ#(surf, TriangleVertex(surf, tri, 1))
				v_z2# = VertexZ#(surf, TriangleVertex(surf, tri, 2))

				v_u0# = VertexU#(surf, TriangleVertex(surf, tri, 0))
				v_u1# = VertexU#(surf, TriangleVertex(surf, tri, 1))
				v_u2# = VertexU#(surf, TriangleVertex(surf, tri, 2))

				v_v0# = VertexV(surf, TriangleVertex(surf, tri, 0))
				v_v1# = VertexV(surf, TriangleVertex(surf, tri, 1))
				v_v2# = VertexV(surf, TriangleVertex(surf, tri, 2))
				
				v_u0b# = VertexU#(surf, TriangleVertex(surf, tri, 0),1)
				v_u1b# = VertexU#(surf, TriangleVertex(surf, tri, 1),1)
				v_u2b# = VertexU#(surf, TriangleVertex(surf, tri, 2),1)

				v_v0b# = VertexV(surf, TriangleVertex(surf, tri, 0),1)
				v_v1b# = VertexV(surf, TriangleVertex(surf, tri, 1),1)
				v_v2b# = VertexV(surf, TriangleVertex(surf, tri, 2),1)

				v_a0# = VertexAlpha#(surf, TriangleVertex(surf, tri, 0))
				v_a1# = VertexAlpha#(surf, TriangleVertex(surf, tri, 1))
				v_a2# = VertexAlpha#(surf, TriangleVertex(surf, tri, 2))

				v0 = AddVertex(newsurf, v_x0, v_y0, v_z0, v_u0, v_v0)
				v1 = AddVertex(newsurf, v_x1, v_y1, v_z1, v_u1, v_v1)
				v2 = AddVertex(newsurf, v_x2, v_y2, v_z2, v_u2, v_v2)
				VertexTexCoords newsurf, v0, v_u0b, v_v0b, 1, 1
				VertexTexCoords newsurf, v1, v_u1b, v_v1b, 1, 1
				VertexTexCoords newsurf, v2, v_u2b, v_v2b, 1, 1
				AddTriangle(newsurf, v0, v1, v2)

				VertexColor newsurf, v0, v_r1, v_g1, v_b1, v_a0
				VertexColor newsurf, v1, v_r2, v_g2, v_b2, v_a1
				VertexColor newsurf, v2, v_r3, v_g3, v_b3, v_a2
			Next

			PaintSurface newsurf, brush
			UpdateNormals newmesh
       EndIf 
	Next

	FreeEntity Ent
	Return newmesh

End Function