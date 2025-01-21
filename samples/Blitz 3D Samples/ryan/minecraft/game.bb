Global textinfo

Function DrawInfo()
	Select textinfo
		Case 0
			Text 20,20, "Мусохранцы альфа 1"
			Text 20,40, "Кадров в секунду: "+fps
			Text 20,60, "Мир сгенерирован за: "+worldgen+" миилисекунд"
			Text 20,80, "Перестройка чанка: "+lastrebuild+" миилисекунд"
			If localPlayer<>Null Then
				Text 20,100, "Координаты локального игрока: "+localPlayer\x+" "+localPlayer\y+" "+localPlayer\z
			End If
		Case 1
			Select commanderStatus
				Case cIdle
					commStLine$ = "НЕТ ДЕЙСТВИЯ"
				Case cDig
					commStLine$ = "КОПАТЬ"
				Case cBuild
					commStLine$ = "СТРОИТЬ"
			End Select
			Text 20,20, "Действие: "+commStLine$
	End Select

	If(KH_TAB) Then
		textinfo = textinfo +1
		If textinfo > 1 Then textinfo = 0
	End If
End Function