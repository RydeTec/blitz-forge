Const playerActiveDistance# = 3.5

Global LocalPlayer.Player

Global Mouse1Hit

Global KD_W, KD_A, KD_S, KD_D

Global KH_Q, KH_E, KH_F

Global KD_SPACE
Global KH_SPACE

Global KH_TAB

Global selectedX, selectedY, selectedZ

Function getKeys()
	Mouse1Hit = 0

	KD_W = 0
	KD_A = 0
	KD_S = 0
	KD_D = 0

	KH_E = 0
	KH_Q = 0

	KD_SPACE = 0
	KH_SPACE = 0

	KH_TAB = 0

	KH_F = 0

	If MouseHit(1) Then Mouse1Hit = 1

	If KeyDown(17) Then KD_W = 1
	If KeyDown(31) Then KD_S = 1
	
	If KeyDown(32) Then KD_D = 1
	If KeyDown(30) Then KD_A = 1

	If KeyHit(16) Then KH_Q = 1
	If KeyHit(18) Then KH_E = 1
	If KeyHit(33) Then KH_F = 1

	If KeyDown(57) Then KD_SPACE = 1
	If KeyHit(57) Then KH_SPACE = 1

	If KeyHit(15) Then KH_TAB = 15
End Function

Type Player
	Field x#,y#,z#
	Field vX#, vY#, vZ#
	Field isLocal
	Field isOnGround
End Type

Function mouselook(ent)

 mxspd#=MouseXSpeed()*0.25
 myspd#=MouseYSpeed()*0.25

 MoveMouse GraphicsWidth()/2,GraphicsHeight()/2 
 
 campitch#=EntityPitch(ent)+myspd#
 
 If campitch#<-85 Then campitch#=-85
 If campitch#>85 Then campitch#=85

 RotateEntity ent,campitch#,EntityYaw(ent)-mxspd#,EntityRoll(ent)
End Function

Function CreatePlayer.player(x#,y#,z#,isLocal)
	p.Player = New Player
	p\x = x
	p\y = y
	p\z = z

	p\isLocal = isLocal
	Return p
End Function

Function updatePlayer(p.Player)

	If Int(p\x)>1 And Int(p\x)<wW And Int(p\y)>1 And Int(p\y)<wH And  Int(p\z)>1 And Int(p\z)<wD Then
		If world(p\x,p\y-1,p\z,0) = 0 Then ; игрок падает
			p\isOnGround = False
		Else
			p\isOnGround = True
		End If
	Else
		p\vY = 0
	End If

	If (p\isOnGround) Then
		If p\vY <= 0 Then
			p\vY = 0
			If(p\Y < Int(p\y)) Then p\y = Int(p\y)
		End If
	Else
		p\vY = p\vY - 0.0015*dt
	End If

	If(p\isLocal) Then
		If (KD_W) Then
			p\vX = p\vX - 0.001*Sin(EntityYaw(cam))*dt
			p\vZ = p\vZ + 0.001*Cos(EntityYaw(cam))*dt
		End If

		If (KD_S) Then
			p\vX = p\vX + 0.001*Sin(EntityYaw(cam))*dt
			p\vZ = p\vZ - 0.001*Cos(EntityYaw(cam))*dt
		End If

		If (KD_D) Then
			p\vX = p\vX + 0.001*Cos(EntityYaw(cam))*dt
			p\vZ = p\vZ + 0.001*Sin(EntityYaw(cam))*dt
		End If

		If (KD_A) Then
			p\vX = p\vX - 0.001*Cos(EntityYaw(cam))*dt
			p\vZ = p\vZ - 0.001*Sin(EntityYaw(cam))*dt
		End If

		If (KH_SPACE And p\isOnGround) Then
			p\vY = p\vY + 0.025*dt
		End If
	End If

	If Int(p\x)<wW Then
		If world(p\x+0.25,p\y,p\z,0) <> 0 Or world(p\x+0.25,p\y+1,p\z,0) <> 0 Then
			If(p\vX > 0) Then p\vX = 0
		End If
	End If

	If Int(p\x)>-1 Then
		If world(p\x-0.25,p\y,p\z,0) <> 0 Or world(p\x-0.25,p\y+1,p\z,0) <> 0 Then
			If(p\vX < 0) Then p\vX = 0
		End If
	End If

	If Int(p\y)<wH Then
		If world(p\x,p\y+0.25,p\z,0) <> 0 Or world(p\x,p\y+0.75,p\z,0) <> 0 Then
			If(p\vY > 0) Then p\vY = 0
		End If
	End If

	

	If Int(p\z)<wD Then
		If world(p\x,p\y,p\z+0.25,0) <> 0 Or world(p\x,p\y+1,p\z+0.25,0) <> 0 Then
			If(p\vZ > 0) Then p\vZ = 0
		End If
	End If

	If Int(p\z)>0 Then
		If world(p\x,p\y,p\z-0.25,0) <> 0 Or world(p\x,p\y+1,p\z-0.25,0) <> 0 Then
			If(p\vZ < 0) Then p\vZ = 0
		End If
	End If

	If (p\isLocal)

		PositionEntity cam, p\x,p\y+0.65,p\z
		mouselook(cam)

		CameraPick(cam, GraphicsWidth()/2, GraphicsHeight()/2)

		If ( TeoremaPifagora(p\x,p\y,p\z,PickedX(),PickedY(),PickedZ()) > playerActiveDistance)
			selectedX = -1
			selectedY = -1
			selectedZ = -1
		Else
			selectedX = Int(PickedX()-PickedNX()*0.5)
			selectedY = Int(PickedY()-PickedNY()*0.5)
			selectedZ = Int(PickedZ()-PickedNZ()*0.5)
		End If

		PositionEntity selectionCube,selectedX,selectedY,selectedZ

		If (Mouse1Hit) Then
			cameraDig(cam, p)
		End If
	End If

	p\vX = p\vX*(1 - 0.01*dt)
	If Abs(p\vX) < 0.001 Then p\vX = 0

	;p\vY = p\vY*(1 - 0.001*dt)
	;If Abs(p\vY) < 0.001 Then p\vY = 0

	p\vZ = p\vZ*(1 - 0.01*dt)
	If Abs(p\vZ) < 0.001 Then p\vZ = 0

	p\x = p\x + p\vX
	p\y = p\y + p\vY
	p\z = p\z + p\vZ

End Function

Function cameraDig(cam, p.Player)
	If selectedX <> -1 Then
		world(selectedX, selectedY, selectedZ,0) = 0
		updateBlock(selectedX, selectedY, selectedZ)
	End If
End Function

Function PleeraZapili()
	LocalPlayer = createPlayer(64,120,64,True)
End Function

Function PleeraVipili()
	Delete LocalPlayer
End Function