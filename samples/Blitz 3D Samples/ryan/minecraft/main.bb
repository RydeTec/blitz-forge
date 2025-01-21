; Исходный код TBS "Мусохранцы"
; 8 апреля 2010

Global gx=0,gz=0,gbpp=0,gmode = -1 ; Настройки графики

Dim meshes(100)

sett = ReadFile("settings.txt")
If sett Then
	Repeat
		l$ = ReadLine(sett)
		If Mid(l, 1, 12) = "[resolution]" Then
			j=13
			For i=13 To Len(l)
				If(Mid(l, i,1)="x" Or i=Len(l)) Then
					If gx = 0 Then
						gx = Mid(l,j,i-j)
					Else
						If gy = 0 Then
							gy = Mid(l,j,i-j)
						Else
							If gbpp = 0 Then
								gbpp = Mid(l,j,i-j)
							Else
								If gmode = -1 Then
									gmode = Mid(l,j,1)
								End If
							End If
						End If
					End If
					j=i+1
				End If
			Next
		End If
	Until Eof(sett)
End If

If gx = 0 Then gx = 1024
If gy = 0 Then gy = 768
If gbpp = 0 Then gbpp = 32
If mode = -1 Then gmode = 2


Global cycletime, realdt=0, dt#=0, fps#=0

Include "mathematics.bb"
Include "world.bb"
Include "player.bb"
Include "commander.bb"
Include "dx7.bb"
Include "game.bb"

;Graphics3D 1024,600,32,2
Graphics3D gx,gy,gbpp,gmode
HidePointer
SetBuffer BackBuffer()
EnableLinear()

Global selectionCube = CreateCube()
ScaleEntity selectionCube, 0.55,0.55,0.55
EntityColor selectionCube, 0,0,255
EntityAlpha selectionCube, 0.5

Global treemesh = CreateMesh()

LocalPlayer = createPlayer(64,120,64,True)
commanderY = 128

;commanderX = 40
;commanderY = 53
;commanderZ = 40

Global worldtex = LoadTexture("worldtex.dds")

SetFont LoadFont("FixedSys")

Global cam = CreateCamera()


CameraFogRange cam, 30,60
CameraFogMode cam, 1

CameraRange cam, 0.01, 60

;CameraFogColor cam, 150,230,200
;CameraClsColor cam, 150,230,200

CameraFogColor cam, 200,100,255
CameraClsColor cam, 200,100,255

RotateEntity cam, 45,0,0
Global light = CreateLight()
RotateEntity light, 60,0,0

; Загрузка мешей

meshes(0) = LoadMesh("pine.b3d")
ScaleEntity meshes(0), 0.05,0.05,0.05
HideEntity meshes(0)

worldgen = MilliSecs()
generateWorld(0)
worldgen = MilliSecs() - worldgen
Repeat
cycletime = MilliSecs()

getKeys()

If localPlayer<>Null Then
	updatePlayer(localPlayer)
	If KH_F Then PleeraVipili() ; Костыль наспех, глючит
Else
	updateCommander()
	If KH_F Then PleeraZapili()
End If

	UpdateWorld()
	RenderWorld()
	DrawInfo()


	Text (MouseX()-StringWidth("+")/2), (MouseY()-StringHeight("+")/2), "+"

	;DrawImage grasscolor, 100,200

	Flip
realdt = MilliSecs()-cycletime
If(realdt<>0) Then
	fps = 1000/realdt
Else
	fps = 100500
End If
dt = realdt*0.4

Until KeyHit(1)
End