start = MilliSecs()

Graphics 800,600,32,2

SeedRnd(MilliSecs())



Heightmap = CreateImage(512,512)
Dim world(512,512)

Global buffer = ImageBuffer(Heightmap)

SetBuffer buffer
;WritePixel 100,100, $FFFF0000, buffer

mountainX = 256
mountainY = 256

For i=0 To 3
	a = i*360/3
	runChain(mountainX,mountainY,a+Rnd(-30,30), Rnd(0.5,1.0))
Next
;runChain(mountainX,mountainY,a+Rnd(-30,30), Rnd(0.5,1.5),128,0)

endheight = MilliSecs()-start

image = MilliSecs()
rebuildImage()
image = MilliSecs() - image

SetBuffer BackBuffer()

DrawImage Heightmap, 20,20

 Text 20,20, "Время генерации высот: "+endheight
Text 20,40,"Ребилд картинки: "+image
Flip
WaitKey
End


Function runChain(x,y,a#, speed# = 1, curColor = 255, turnChance# = 0.1)
	Repeat

		curColor = curColor - 1

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
		dist = 0
		Repeat
			placeHeight(x+curX+ Cos(a+90)*speed*dist,y+curY+ Sin(a+90)*speed*dist, curheight)
			placeHeight(x+curX+ Cos(a-90)*speed*dist,y+curY+ Sin(a-90)*speed*dist, curheight)
			curheight = curheight - coolness
			dist = dist + 1
		Until curheight < 0
	Until curColor = 0
End Function

Const coolness = 10

Function placeHeight(x,y,height)
		If x>0 And y>0 And x<511 And y<511
			If  height > world(x,y) Then
				world(x,y) = height
			End If
		End If
End Function

Function rebuildImage()
	For x = 1 To 511
		For y=1 To 511
			If world(x,y) = 0 Or 1 Then
				clr = (world(x+1,y)+world(x-1,y)+world(x,y-1)+world(x,y-1)+world(x+1,y-1)+world(x+1,y-1)+world(x-1,y-1)+world(x-1,y-1))/8
			Else
				clr = world(x,y)
			End If
			WritePixel x,y,(256 Shl 24) Or (clr Shl 16) Or (clr Shl 8) Or clr,buffer
		Next
	Next
End Function