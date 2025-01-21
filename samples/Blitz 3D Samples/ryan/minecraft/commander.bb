Global commanderX#, commanderY#, commanderZ#

Global commanderStatus = 0;
Const cIdle = 0, cDig = 1,cBuild = 2

Function updateCommander()
	If(KD_D) Then commanderX = commanderX + .1*dt
	If(KD_A) Then commanderX = commanderX - .1*dt

	If(KD_W) Then commanderZ = commanderZ + .1*dt
	If(KD_S) Then commanderZ = commanderZ - .1*dt

	If(KH_Q) Then
		commanderY = commanderY - 1
		needrebuild = 1
	End If

	If(KH_E) Then
		commanderY = commanderY + 1
		needrebuild = 1
	End If

	If needrebuild Then
		For chunkX=0 To chunknumberX-1
			For chunkZ=0 To chunknumberZ-1
				rebuildChunk(chunkX, chunkZ, commanderY-5)
			Next
		Next
	End If

	CameraPick(cam, MouseX(), MouseY());

	selectedX = Int(PickedX()-PickedNX()*0.5)
	selectedY = Int(PickedY()-PickedNY()*0.5)
	selectedZ = Int(PickedZ()-PickedNZ()*0.5)

	PositionEntity selectionCube,selectedX,selectedY,selectedZ

	PositionEntity cam, commanderX#, commanderY#, commanderZ#
End Function