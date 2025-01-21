Const chunknumberX = 24, chunknumberZ = 24, chunkW = 16, chunkD =16
Dim worldmesh(chunknumberX, chunknumberZ, 2)

Global wW = chunknumberX*chunkW, wH = 128, wD = chunknumberZ*chunkD
Dim world(wW,wH,wD, 8)

Const void = 0, grass = 1, rock = 2, dirt = 3, podzemka = 4, ironOre = 5, coalOre = 6, incognito = 15

Const rutChance# = 0.02, ironOreChance# = 0.3, coalOreChance# = 0.1
Const treeChance# = 0.03

Global lastrebuild

Global worldimage, grasscolor

Function generateWorld(seed=0)
	worldimage = CreateImage(wW,wD)
	grasscolor = LoadTexture("grasscolor.png");


	If seed = 0 Then seed = MilliSecs()
	SeedRnd (seed)

	sinShiftX# = Rnd(0,360)
	sinShiftZ# = Rnd(0,360)



	mountainX = wW/2
	mountainZ = wD/2
	
	For i=0 To 3
		a = i*360/3
		runChain(mountainX,mountainZ,a+Rnd(-30,30), Rnd(3,7))
	Next


smoothWorld()
smoothWorld()
smoothWorld()

     For x = 1 To wW
          For z = 1 To wD

			For y=wH To 0 Step -1
				If world(x,y,z,0)<>0 Or y<48 Then
					Exit
				End If
			Next

               grndHeight = y-3-Rand(0,2);y*0.8+Rnd(0,y*0.1)

               For j=y To grndHeight+1 Step -1
                    world(x,j,z,0)= dirt
               Next
               For j=grndHeight To  1 Step -1
                    world(x,j,z,0)= rock
               Next
          Next
     Next


	; Делаем залежи руд
	For x = 1 To wW
		For z = 1 To wD
			allowOre = 0
			For y = wH To 1 Step -1
				If world(x,y,z,0) <> 0 Then
					curIronOreChance# = ironOreChance*(-0.7/wH*y+0.1)
					If(Rnd(0,1)<curIronOreChance) Then
						generateCavity(x,y,z, ironOre, Rand(1,2),0,0,0,rock)
					End If

					curCoalOreChance# = coalOreChance*(-0.7/wH*y+0.15)
					If(Rnd(0,1)<curCoalOreChance) Then
						generateCavity(x,y,z, coalOre, Rand(1,2),0,0,0,rock)
					End If
				End If
			Next
		Next
	Next

	; Делаем рандомные рытвины на поверхности
	For x = 1 To wW
		For z = 1 To wD
			If (Rnd(0,1)<rutChance) Then
				For y = wH To 1 Step -1
					If world(x,y,z,0) <> 0 Then
						generateCavity(x,y+Rand(2,5),z)
						Exit
					End If
				Next
			End If
		Next
	Next

	growGrass()

	; Делаем рандомные деревья
	For x = 1 To wW
		For z = 1 To wD
			If (Rnd(0,1)<treeChance) Then
				For y = wH To 1 Step -1
					If world(x,y,z,0) = 1 Then
						tree = CopyEntity(meshes(0))
						PositionEntity tree, x,y+0.5,z
						Exit
					End If
				Next
			End If
		Next
	Next

	rebuildImage()


	; Строим все чанки

	If (localPlayer = Null) Then
		buildY = commanderY - 5
	Else
		buildY = wH
	End If

	For chunkX=0 To chunknumberX-1
		For chunkZ=0 To chunknumberZ-1
			rebuildChunk(chunkX, chunkZ, buildY)
		Next
	Next
End Function

Function generateCavity(x,y,z, ore = 0, radius# = 0, multX# = 0, multY# = 0, multZ# = 0, onlyType = 0)
	; Границы не должны выходить за пределы массива мира иначе пиздец

	If radius = 0 Then radius# = Rnd(3,10)

	If multX = 0 Then multX# = Rnd(0.5,1)
	If multY = 0 Then multY# = Rnd(0.5,0.7)
	If multZ = 0 Then multZ# = Rnd(0.5,1)

	For elX = -radius*multX To radius*multX
		For elZ = -radius*multZ To radius*multZ
			; Вычисляем абс. y эллипсоида по x и z
			; absElY = Sqr(radius*multX*radius*multX - elX^2 - elZ^2) ; - Бывш. сфера, все множители равны

			absElY = Sqr((1 - elX^2/(radius*multX)^2 - elZ^2/(radius*multZ)^2)*(radius*multY)^2)

			For elY = -radius*multY To radius*multY
				If elY>-absElY And elY<absElY Then
					If(elX+x>0 And elX+x < wW+1 And elY+y>0 And elY+y < wH+1 And elZ+z>0 And elZ+z < wD+1) Then
						If ((Not onlyType) Or onlyType = world(elX+x,elY+y,elZ+z,0)) Then
							world(elX+x,elY+y,elZ+z,0) = ore
						End If
					End If
				End If
			Next
		Next
	Next
End Function

Function growGrass() ; Вся земля, над которой нет никаких блоков, превращается в траву.
	For x = 1 To wW
		For z = 1 To wD
			For y = wH To 1 Step -1
				If world(x,y,z,0) = 3 Then
					 world(x,y,z,0) = 1
					Exit
				End If
			Next
		Next
	Next
End Function

Function rebuildChunk(chunkX, chunkZ, camheight  = -1)
	If camheight = -1 Then camheight = wH
	lastrebuild = MilliSecs()
	FreeEntity worldmesh(chunkX, chunkZ,0)

	worldmesh(chunkX, chunkZ,0) = CreateMesh()
	EntityFX worldmesh(chunkX, chunkZ,0), 2
	worldmesh(chunkX, chunkZ,1) = CreateSurface (worldmesh(chunkX, chunkZ,0))
	EntityTexture (worldmesh(chunkX, chunkZ,0), worldtex)
	EntityPickMode worldmesh(chunkX, chunkZ,0),2

	For x = chunkX*chunkW To (chunkX+1)*chunkW-1
		For z = chunkZ*chunkD To (chunkZ+1)*chunkD-1
			For y = 0 To wH
				If y > camheight Then Exit
				If(world(x,y,z,0)) Then
					; Выбор текстуры и цвета

					Select world(x,y,z,0)
						Case 1: ; Трава
	
							curgrasscolor =  ReadPixel(y,0,TextureBuffer(grasscolor))

							topClr = curgrasscolor
							botClr = $FFFFFFFF

							topTex = 0
							botTex = 2

							If (z-1 <> -1 And y-1 <> -1) Then
								If world(x, y-1, z-1,0) = grass Then
									frontTex = 0
									frontClr = curgrasscolor
								Else
									frontTex = 1
									frontClr = $FFFFFFFF
								End If
							Else
								frontTex = 1
								frontClr = $FFFFFFFF
							End If

							If (z+1 <> wD And y-1 <> -1) Then
								If world(x, y-1, z+1,0) = grass Then
									backTex = 0
									backClr = curgrasscolor
								Else
									backTex = 1
									backClr = $FFFFFFFF
								End If
							Else
								frontTex = 1
								backClr = $FFFFFFFF
							End If

							If (x-1 <> -1 And y-1 <> -1) Then
								If world(x-1, y-1, z,0) = grass Then
									leftTex = 0
									leftClr = curgrasscolor
								Else
									leftTex = 1
									leftClr = $FFFFFFFF
								End If
							Else
								leftTex = 1
								leftClr = $FFFFFFFF
							End If

							If (x+1 <> wW And y-1 <> -1) Then
								If world(x+1, y-1, z,0) = grass Then
									rightTex = 0
									rightClr = curgrasscolor
								Else
									rightTex = 1
									rightClr = $FFFFFFFF
								End If
							Else
								rightTex = 1
								rightClr = $FFFFFFFF
							End If
						Case 2: ; Скала
							topTex = 3
							botTex = 3

							frontTex = 3
							backTex = 3

							leftTex = 3
							rightTex = 3

							topClr = $FFFFFFFF
							botClr = $FFFFFFFF
							frontClr = $FFFFFFFF
							backClr = $FFFFFFFF
							leftClr = $FFFFFFFF
							rightClr = $FFFFFFFF
						Case 3: ; Грязь
							topTex = 2
							botTex = 2

							frontTex = 2
							backTex = 2

							leftTex = 2
							rightTex = 2

							topClr = $FFFFFFFF
							botClr = $FFFFFFFF
							frontClr = $FFFFFFFF
							backClr = $FFFFFFFF
							leftClr = $FFFFFFFF
							rightClr = $FFFFFFFF
						Case ironOre: ; Руда железа
							topTex = ironOre
							botTex = ironOre

							frontTex = ironOre
							backTex = ironOre

							leftTex = ironOre
							rightTex = ironOre

							topClr = $FFFFFFFF
							botClr = $FFFFFFFF
							frontClr = $FFFFFFFF
							backClr = $FFFFFFFF
							leftClr = $FFFFFFFF
							rightClr = $FFFFFFFF
						Case coalOre: ; Руда угля
							topTex = coalOre
							botTex = coalOre

							frontTex = coalOre
							backTex = coalOre

							leftTex = coalOre
							rightTex = coalOre
							topClr = $FFFFFFFF
							botClr = $FFFFFFFF
							frontClr = $FFFFFFFF
							backClr = $FFFFFFFF
							leftClr = $FFFFFFFF
							rightClr = $FFFFFFFF
					End Select

					; Верх
					If y+1 <> wH Then
						If world(x,y+1,z,0) = 0 Then
							buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 0, topTex, topClr)
						Else
							If y = camheight Then
								topTex = 15
								buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 0, topTex, topClr)
							End If
						End If
					Else
						buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 0, topTex, topClr)
					End If
					; Низ
					If y-1 <> 0 Then
						If world(x,y-1,z,0) = 0 Then
							buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 1, botTex, botClr)
						End If
					Else
						buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 1, botTex, botClr)
					End If

					; Перед
					If z-1 <> 0 Then
						If world(x,y,z-1,0) = 0 Then
							buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 2, frontTex, frontClr)
						End If
					Else
						buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 2, frontTex, frontClr)
					End If
					; Зад
					If z+1 <> wD+1 Then
						If world(x,y,z+1,0) = 0 Then
							buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 3, backTex, backClr)
						End If
					Else
						buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 3, backTex, backClr)
					End If

					; Лево
					If x-1 <> 0 Then
						If world(x-1,y,z,0) = 0 Then
							buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 4, leftTex, leftClr)
						End If
					Else
						buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 4, leftTex, leftClr)
					End If

					; Право
					If x+1 <> wD+1 Then
						If world(x+1,y,z,0) = 0 Then
							buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 5, rightTex, rightClr)
						End If
					Else
						buildSide(worldmesh(chunkX, chunkZ,1), x, y, z, 5, rightTex, rightClr)
					End If
				End If
			Next
		Next
	Next
	lastrebuild = MilliSecs() - lastrebuild
End Function

Const texMultX# = 0.25, texMultY# = 0.25

Function buildSide(surf, x#,y#,z#, side, tex = 0, clr=$ffffffff)
	; side:
	; 0 - Верх
	; 1 - Низ !
	; 2 - Перед
	; 3 - Зад !
	; 4 - Лево
	; 5 - Право

	Select tex
		Case 0:
			texShiftX = 0
			texShiftY = 0
		Case 1:
			texShiftX = 1
			texShiftY = 0
		Case 2:
			texShiftX = 2
			texShiftY = 0
		Case 3:
			texShiftX = 3
			texShiftY = 0
		Case ironOre:
			texShiftX = 0
			texShiftY = 1
		Case coalOre:
			texShiftX = 1
			texShiftY = 1
		Case incognito:
			texShiftX = 3
			texShiftY = 3
	End Select

	Select side
		Case 0:
			v1 = AddVertex (surf, x-0.5,y+0.5,z-0.5, texShiftX*texMultX,(texShiftY+1)*texMultY)
			v2 = AddVertex (surf, x-0.5,y+0.5,z+0.5, texShiftX*texMultX,texShiftY*texMultY)
			v3 = AddVertex (surf, x+0.5,y+0.5,z+0.5,(texShiftX+1)*texMultX,texShiftY*texMultY)
			v4 = AddVertex (surf, x+0.5,y+0.5,z-0.5,(texShiftX+1)*texMultX,(texShiftY+1)*texMultY)
		
			VertexNormal surf,v1,0,1,0
			VertexNormal surf,v2,0,1,0
			VertexNormal surf,v3,0,1,0
			VertexNormal surf,v4,0,1,0

			AddTriangle surf, v1,v2,v3
			AddTriangle surf, v1,v3,v4

		Case 1:
			v1 = AddVertex (surf, x-0.5,y-0.5,z-0.5, texShiftX*texMultX,(texShiftY+1)*texMultY)
			v2 = AddVertex (surf, x-0.5,y-0.5,z+0.5, texShiftX*texMultX,texShiftY*texMultY)
			v3 = AddVertex (surf, x+0.5,y-0.5,z+0.5,(texShiftX+1)*texMultX,texShiftY*texMultY)
			v4 = AddVertex (surf, x+0.5,y-0.5,z-0.5,(texShiftX+1)*texMultX,(texShiftY+1)*texMultY)
		
			VertexNormal surf,v1,0,1,0
			VertexNormal surf,v2,0,1,0
			VertexNormal surf,v3,0,1,0
			VertexNormal surf,v4,0,1,0

			AddTriangle surf, v1,v3,v2
			AddTriangle surf, v1,v4,v3

		Case 2
			v1 = AddVertex (surf, x-0.5,y-0.5,z-0.5, texShiftX*texMultX,(texShiftY+1)*texMultY)
			v2 = AddVertex (surf, x-0.5,y+0.5,z-0.5, texShiftX*texMultX,texShiftY*texMultY)
			v3 = AddVertex (surf, x+0.5,y+0.5,z-0.5, (texShiftX+1)*texMultX,texShiftY*texMultY)
			v4 = AddVertex (surf, x+0.5,y-0.5,z-0.5, (texShiftX+1)*texMultX,(texShiftY+1)*texMultY)
	
			VertexNormal surf,v1,0,0,-1
			VertexNormal surf,v2,0,0,-1
			VertexNormal surf,v3,0,0,-1
			VertexNormal surf,v4,0,0,-1
		
			AddTriangle surf, v1,v2,v3
			AddTriangle surf, v1,v3,v4

		Case 3
			v1 = AddVertex (surf, x-0.5,y-0.5,z+0.5, texShiftX*texMultX,(texShiftY+1)*texMultY)
			v2 = AddVertex (surf, x-0.5,y+0.5,z+0.5, texShiftX*texMultX,texShiftY*texMultY)
			v3 = AddVertex (surf, x+0.5,y+0.5,z+0.5, (texShiftX+1)*texMultX,texShiftY*texMultY)
			v4 = AddVertex (surf, x+0.5,y-0.5,z+0.5, (texShiftX+1)*texMultX,(texShiftY+1)*texMultY)
	
			VertexNormal surf,v1,0,0,-1
			VertexNormal surf,v2,0,0,-1
			VertexNormal surf,v3,0,0,-1
			VertexNormal surf,v4,0,0,-1
		
			AddTriangle surf, v1,v3,v2
			AddTriangle surf, v1,v4,v3

		Case 4:
			v1 = AddVertex (surf, x-0.5,y-0.5,z-0.5, texShiftX*texMultX,(texShiftY+1)*texMultY)
			v2 = AddVertex (surf, x-0.5,y+0.5,z-0.5, texShiftX*texMultX,texShiftY*texMultY)
			v3 = AddVertex (surf, x-0.5,y+0.5,z+0.5, (texShiftX+1)*texMultX,texShiftY*texMultY)
			v4 = AddVertex (surf, x-0.5,y-0.5,z+0.5, (texShiftX+1)*texMultX,(texShiftY+1)*texMultY)
		
			VertexNormal surf,v1,-1,0,0
			VertexNormal surf,v2,-1,0,0
			VertexNormal surf,v3,-1,0,0
			VertexNormal surf,v4,-1,0,0
		
			AddTriangle surf, v1,v3,v2
			AddTriangle surf, v1,v4,v3
		
		Case 5:
			v1 = AddVertex (surf, x+0.5,y-0.5,z-0.5, texShiftX*texMultX,(texShiftY+1)*texMultY)
			v2 = AddVertex (surf, x+0.5,y+0.5,z-0.5, texShiftX*texMultX,texShiftY*texMultY)
			v3 = AddVertex (surf, x+0.5,y+0.5,z+0.5, (texShiftX+1)*texMultX,texShiftY*texMultY)
			v4 = AddVertex (surf, x+0.5,y-0.5,z+0.5, (texShiftX+1)*texMultX,(texShiftY+1)*texMultY)
		
			VertexNormal surf,v1,1,0,0
			VertexNormal surf,v2,1,0,0
			VertexNormal surf,v3,1,0,0
			VertexNormal surf,v4,1,0,0
		
			AddTriangle surf, v1,v2,v3
			AddTriangle surf, v1,v3,v4
	End Select

	HEX2CHANNELS(clr)

	r# = HexR
	g# = HexG
	b# = HexB

	VertexColor surf,v1,r,g,b
	VertexColor surf,v2,r,g,b
	VertexColor surf,v3,r,g,b
	VertexColor surf,v4,r,g,b

End Function

Function updateBlock(x,y,z)
	; Узнаем где это находится
	ChunkX = Floor(x/chunkW)
	ChunkZ = Floor(z/chunkD)

	rebuildChunk(ChunkX, ChunkZ)

	If x Mod chunkW = 0 Then
		rebuildChunk(ChunkX-1, ChunkZ)
	End If

	If x Mod chunkW = chunkW-1 Then
		rebuildChunk(ChunkX+1, ChunkZ)
	End If

	If z Mod chunkD = 0 Then
		rebuildChunk(ChunkX, ChunkZ-1)
	End If

	If z Mod chunkD = chunkD-1 Then
		rebuildChunk(ChunkX, ChunkZ+1)
	End If

End Function

Function runChain(x,y,a#, speed# = 1, curColor# = 64, turnChance# = 0.4)
	Repeat

		curColor# = curColor# - 1.0

		If(Rnd(0,1)<turnChance) Then
			a = a + Rnd(-30,30)
			newDir# = Rnd(0,1)
			If(curColor > 32) Then
				If(newDir<0.5) Then newDir = -1 Else newDir = 1
				runChain(x+curX#,y+curY#,a+90*newDir+Rnd(-15,15), speed*0.4, curColor, turnChance*0.1)
			End If
		End If

		curX# = curX + Cos(a)*speed
		curY# = curY + Sin(a)*speed

		curheight = curColor
		dist# = 0

		dirX# =  Cos(a+90)*speed
		dirY# =  Sin(a+90)*speed

		revX# =  Cos(a-90)*speed
		revY# =  Sin(a-90)*speed


		Repeat
			placeHeight(x+curX+dirX*dist,y+curY+ dirY*dist, curheight)
			placeHeight(x+curX+ revX*dist,y+curY+ revY*dist, curheight)
			curheight = curheight - coolness
			dist = dist + 1.0/speed
		Until curheight < 0
	Until curColor <= 0
End Function

Function smoothWorld()
	For x = 1 To wW
		For z=1 To wD
			y =  getHeight(x,z)-48
			summ = y
			count = 1

			If summ = 0 Then count = 0

			If (x-1 > -1) Then
				summ = summ+ getHeight(x-1,z)-48
				count=count+1
			End If

			If (x+1 < wW) Then
				summ = summ+ getHeight(x+1,z)-48
				count=count+1
			End If

			If (z-1 > -1) Then
				summ = summ+ getHeight(x,z-1)-48
				count=count+1
			End If

			If (z+1 < wD) Then
				summ = summ+ getHeight(x,z+1)-48
				count=count+1
			End If

			If (x-1 > -1) And (z-1 > -1) Then
				summ = summ+ getHeight(x-1,z-1)-48
				count=count+1
			End If

			If (x+1 < wW) And  (z+1 < wD)  Then
				summ = summ+ getHeight(x+1,z+1)-48
				count=count+1
			End If

			If (x+1 < wW) And  (z-1 > -1)  Then
				summ = summ+ getHeight(x+1,z-1)-48
				count=count+1
			End If

			If (x-1 > -1) And  (z+1 < wD)  Then
				summ = summ+ getHeight(x-1,z+1)-48
				count=count+1
			End If

			av = summ/count
			placeHeight(x,z,av,1)
			
		Next

	Next
End Function

Function rebuildImage()

	buffer = ImageBuffer(worldimage)

	For x = 1 To wW
		For y=1 To wD

				clr =  getHeight(x,y)*2
			WritePixel x,y,(256 Shl 24) Or (clr Shl 16) Or (clr Shl 8) Or clr,buffer
		Next

	Next

SaveBuffer(buffer, "world.bmp")

End Function

Const coolness# = 1.7

Function placeHeight(x,y,height, force = 0)
		If height > wH Then Return
		If x>0 And y>0 And x<wW And y<wD
			height = height + 48
			For i=wH To 0 Step -1
				If world(x,i,y,0)<>0 Then
					If(force) Then
						world(x,i,y,0) = 0
					Else
						wheight = i
						Exit
					End If
				End If
			Next
			If  height > wheight Then
				world(x,height,y,0) = 1
			End If
		End If
End Function

Function getHeight(x,z)
	For y=wH To 0 Step -1
		If world(x,y,z,0)<>0 Then
			Return y
		End If
	Next
End Function

Global HexA#, HexR#, HexG, HexB

Function HEX2CHANNELS(clr)
	HexA = (clr Shr 24)/255.0
	HexR = (clr Shr 16) Mod 256
	HexG = (clr Shr 8) Mod 256
	HexB = clr And $000000FF
End Function
