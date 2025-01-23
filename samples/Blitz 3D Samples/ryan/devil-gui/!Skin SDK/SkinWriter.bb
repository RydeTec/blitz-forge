Include "..\DevilGUI.bb"

WriteAllSkins()
End

Function WriteAllSkins()
dir = ReadDir(CurrentDir())
Repeat
	f$ = NextFile(dir)
	If f$ = "" Then Exit
	If FileType(f$) = 2 And f$ <> "." And f$ <> ".." Then
		WriteSkin(f$)
	EndIf
Forever
CloseDir dir
End Function

Function WriteSkin(name$)
file = WriteFile("..\Skins\" + name$ + ".skin")
WriteString file, GUI_SkinVersion$
PackFiles(file, name$ + "\")
CloseFile file
End Function

Function PackFiles(file, path$)
dir = ReadDir(path$)
Repeat
	f$ = NextFile(dir)
	If f$ = "" Then Exit
	Select FileType(path$ + f$)
		Case 1
			file2 = ReadFile(path$ + f$)
			size = FileSize(path$ + f$)
			WriteString file, f$
			WriteInt file, size
			For i = 0 To size
				WriteByte file, ReadByte(file2)
			Next
			CloseFile file2
		Case 2
			If f$ <> "." And f$ <> ".." Then PackFiles(file, path$ + f$ + "\")
	End Select
Forever
CloseDir dir
End Function