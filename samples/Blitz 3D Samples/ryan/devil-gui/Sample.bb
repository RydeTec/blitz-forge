Include "DevilGUI.bb"

Graphics 1024, 768, 32, 2
SetBuffer BackBuffer()
SeedRnd MilliSecs()
ClsColor 255, 205, 105
HidePointer

;Globs
Global fnt = LoadFont("Courier New", 20, True, False, False)
Global cmbSkin, OldSkin$ = "WinXP"
Global sld1, sld2, lblSld1, lblSld2
Global prg1, prg2, lblPrg1, lblPrg2
Global scr1, lblScr1, btnLst1, lst1
Global btnDlg1, btnDlg2, btnDlg3, btnDlg4
Global lblFPS, lblEvent

;GUI
Const FirstSkin$ = "WinXP"
GUI_InitGUI("Skins\" + FirstSkin$ + ".skin")
GUI_CreateWindow(150, 150, 400, 300, "Dummy window")
dummy2 = GUI_CreateWindow(500, 450, 400, 300, "Dummy window 2 (Locked)")
GUI_Message(dummy2, "SetLocked", True)
CreateSampleWindow(-1, -1)

While Not KeyHit(1)
	Cls
	GUI_UpdateGUI()
	UpdateSampleWindow()
	Flip
Wend
GUI_FreeGUI()
End

Global FPS, FPS_temp, FPS_time
Function FPS()
ctime = MilliSecs()
FPS_temp = FPS_temp + 1
If ctime - FPS_time > 500 Then
	FPS = FPS_temp * 2
	FPS_temp = 0
	FPS_time = ctime
EndIf
Return FPS
End Function

Function CreateSampleWindow(x = -1, y = -1)
;Window
winMain = GUI_CreateWindow(x, y, 420, 395, "DevilGUI sample window", "Gfx\WindowIcon.png", True, True, True, True)

;Menu
mnuFile = GUI_CreateMenu(winMain, "File")
          GUI_CreateMenu(mnuFile, "New")
          GUI_CreateMenu(mnuFile, "Open")
          GUI_CreateMenu(mnuFile, "Save")
          GUI_CreateMenu(mnuFile, "-")
          GUI_CreateMenu(mnuFile, "Exit")
mnuEdit = GUI_CreateMenu(winMain, "Edit")
          GUI_CreateMenu(mnuEdit, "Cut")
          GUI_CreateMenu(mnuEdit, "Copy")
          GUI_CreateMenu(mnuEdit, "Paste")
mnuHelp = GUI_CreateMenu(winMain, "?")
          GUI_CreateMenu(mnuHelp, "Help")
          GUI_CreateMenu(mnuHelp, "About")

;Tabpage1
tabMain = GUI_CreateTab(winMain, 10, 35, 400, 220)
tabp1 = GUI_CreateTabPage(tabMain, "Gadgets 1")

;Button
grp1 = GUI_CreateGroupBox(tabp1, 10, 10, 96, 145, "Button")
btn1 = GUI_CreateButton(grp1, 10, 20, 75, 25, "Button 1")
btn2 = GUI_CreateButton(grp1, 10, 50, 75, 25, "Button 2")
btn3 = GUI_CreateButton(grp1, 10, 80, 75, 25, "", "Gfx\Smiley.png")
btn4 = GUI_CreateButton(grp1, 10, 110, 75, 25, "Button 4", "", False)

;Checkbox/Radiobutton
grp2 = GUI_CreateGroupBox(tabp1, 105, 10, 193, 80, "Checkbox/Radiobutton")
chk1 = GUI_CreateCheckBox(grp2, 10, 13, "Checkbox 1")
chk2 = GUI_CreateCheckBox(grp2, 10, 28, "Checkbox 2", True, True)
chk3 = GUI_CreateCheckBox(grp2, 10, 43, "Checkbox 3", False)
chk4 = GUI_CreateCheckBox(grp2, 10, 58, "Checkbox 4", False, True)
rad1 = GUI_CreateRadio(grp2, 100, 13, "Radiobutton 1", 0)
rad2 = GUI_CreateRadio(grp2, 100, 28, "Radiobutton 2", 0, True, True)
rad3 = GUI_CreateRadio(grp2, 100, 43, "Radiobutton 3", 1, False)
rad4 = GUI_CreateRadio(grp2, 100, 58, "Radiobutton 4", 1, False, True)

;Slider
grp3 = GUI_CreateGroupBox(tabp1, 105, 90, 193, 80, "Slider")
sld1 = GUI_CreateSlider(grp3, 10, 13, 150)
sld2 = GUI_CreateSlider(grp3, 10, 33, 150, 0, -10, 10)
sld3 = GUI_CreateSlider(grp3, 10, 53, 150, 30, 0, 100, False)
lblSld1 = GUI_CreateLabel(grp3, 170, 13, "%")
lblSld2 = GUI_CreateLabel(grp3, 170, 33, "%")

;Scrollbar
grp4 = GUI_CreateGroupBox(tabp1, 300, 10, 94, 180, "Scrollbar")
scr1 = GUI_CreateScrollBar(grp4, 10, 13, 140, 30)
scr2 = GUI_CreateScrollBar(grp4, 35, 13, 70, 30)
scr3 = GUI_CreateScrollBar(grp4, 35, 93, 70, 30)
scr4 = GUI_CreateScrollBar(grp4, 60, 13, 140, 30)
lblScr1 = GUI_CreateLabel(grp4, 10, 157, "%")

;Tabpage2
tabp2 = GUI_CreateTabPage(tabMain, "Gadgets 2")

;Spinner
grp5 = GUI_CreateGroupBox(tabp2, 10, 10, 95, 80, "Spinner")
spn1 = GUI_CreateSpinner(grp5, 10, 13, 75, 5, 0, 10)
spn2 = GUI_CreateSpinner(grp5, 10, 40, 75, 0, -1, 1, .1)

;ComboBox
grp6 = GUI_CreateGroupBox(tabp2, 10, 90, 95, 85, "Combobox")
cmb1 = GUI_CreateComboBox(grp6, 10, 13, 75, False)
cmb2 = GUI_CreateComboBox(grp6, 10, 40, 75)
GUI_Message(cmb1, "AddItem", -1, "Item #" + 1)
For i = 1 To 3
	GUI_Message(cmb2, "AddItem", -1, "Item #" + i)
Next
For i = 4 To 6
	GUI_Message(cmb2, "AddItem", -1, "Item #" + i, "Gfx\Smiley.png")
Next

;Progressbar
grp7 = GUI_CreateGroupBox(tabp2, 105, 10, 170, 75, "Progressbar")
prg1 = GUI_CreateProgressBar(grp7, 10, 13, 120, 20)
prg2 = GUI_CreateProgressBar(grp7, 10, 43, 120, 20)
lblPrg1 = GUI_CreateLabel(grp7, 135, 15, "%")
lblPrg2 = GUI_CreateLabel(grp7, 135, 45, "%")

;Icon
grp8 = GUI_CreateGroupBox(tabp2, 105, 90, 170, 100, "Icon")
ico1 = GUI_CreateIcon(grp8, 10, 13, 35, 35, "Icon1", "", False)
ico2 = GUI_CreateIcon(grp8, 48, 13, 35, 35, "", "Gfx\Smiley.png", True, 0)
ico3 = GUI_CreateIcon(grp8, 86, 13, 35, 35, "", "Gfx\Smiley.png", True, 1)
ico4 = GUI_CreateIcon(grp8, 124, 13, 35, 35, "", "Gfx\Smiley.png", True, 2)
ico5 = GUI_CreateIcon(grp8, 10, 53, 35, 35, "Icon5", "")
ico6 = GUI_CreateIcon(grp8, 48, 53, 35, 35, "Icon6", "")
ico7 = GUI_CreateIcon(grp8, 86, 53, 35, 35, "Icon7", "", True, 3)
ico8 = GUI_CreateIcon(grp8, 124, 53, 35, 35, "Icon8", "", True, 3)

;Listbox
grp9 = GUI_CreateGroupBox(tabp2, 275, 10, 120, 180, "Listbox")
lst1 = GUI_CreateListBox(grp9, 10, 13, 100, 123)
For i = 1 To 4
	GUI_Message(lst1, "AddItem", -1, "Item#" + i)
Next
For i = 5 To 9
	GUI_Message(lst1, "AddItem", -1, "Item#" + i, "Gfx\Smiley.png")
Next
btnLst1 = GUI_CreateButton(grp9, 10, 143, 75, 25, "Add Item")

;Tabpage3
tabp3  = GUI_CreateTabPage(tabMain, "Gadgets 3")

;Edit
grp10 = GUI_CreateGroupBox(tabp3, 10, 10, 120, 80, "Edit")
edt1 = GUI_CreateEdit(grp10, 10, 13, 100, "Edit field...")

;Image
tabp4 = GUI_CreateTabPage(tabMain, "", "Gfx\Smiley.png")
img = GUI_CreateImage(tabp4, 10, 10, 380, 180, "Gfx\DGUI_Logo.png")

;Tabpage5
tabp5 = GUI_CreateTabPage(tabMain, "Disabled", "", False)

;Skin controller
GUI_CreateLabel(winMain, 10, 10, "Skin:")
cmbSkin = GUI_CreateComboBox(winMain, 40, 8, 100)
dir = ReadDir("Skins\")
Repeat
	f$ = NextFile(dir)
	If f$ = "" Then Exit
	If FileType("Skins\" + f$) = 1 And f$ <> "." And f$ <> ".." Then
		txt$ = Left(f$, Len(f$) - 5)
		GUI_Message(cmbSkin, "AddItem", -1, txt$, "Gfx\Logo_" + txt$ + ".png")
	EndIf
Forever
CloseDir dir

;Dialogs
grp11 = GUI_CreateGroupBox(winMain, 10, 263, 210, 77, "Dialogues")
btnDlg1 = GUI_CreateButton(grp11, 15, 15, 85, 21, "Message box 1")
btnDlg2 = GUI_CreateButton(grp11, 15, 40, 85, 21, "Message box 2")
btnDlg3 = GUI_CreateButton(grp11, 105, 15, 85, 21, "Message box 3")
btnDlg4 = GUI_CreateButton(grp11, 105, 40, 85, 21, "Color Picker")

;Debug
grp12 = GUI_CreateGroupBox(winMain, 223, 263, 187, 77, "Debug")
lblFPS = GUI_CreateLabel(grp12, 10, 13, "XXX FPS")
lblEvent = GUI_CreateLabel(grp12, 10, 23, "Last event: <none>")

;Reset
GUI_Message(cmbSkin, "SetText", FirstSkin$)
GUI_Message(tabMain, "SetIndex", 1)
End Function

Function UpdateSampleWindow()
;Gadget movements
GUI_Message(lblSld1, "SetText", Int(GUI_Message(sld1, "GetValue")))
GUI_Message(lblSld2, "SetText", Int(GUI_Message(sld2, "GetValue")))
v1 = Sin(MilliSecs() * .1) * 50 + 50
v2 = Cos(MilliSecs() * .1) * 50 + 50
GUI_Message(prg1, "SetStatus", v1)
GUI_Message(prg2, "SetStatus", v2)
GUI_Message(lblPrg1, "SetText", v1 + "%")
GUI_Message(lblPrg2, "SetText", v2 + "%")
GUI_Message(lblScr1, "SetText", Int(GUI_Message(scr1, "GetStatus")) + "%")
If GUI_AppEvent() = btnLst1 Then GUI_Message(lst1, "AddItem", -1, "Costum item")
;Skin combobox
ns$ = GUI_Message(cmbSkin, "GetText")
If OldSkin$ <> ns$ And ns$ <> "" Then GUI_LoadSkin("Skins\" + ns$ + ".skin")
OldSkin$ = ns$
;Dialogues
If GUI_AppEvent() = btnDlg1 Then GUI_MsgBox("Information", "The Devil GUI is the successor of the XGui." + Chr(10) + "Did you know!?")
If GUI_AppEvent() = btnDlg2 Then GUI_MsgBox("Warning", "You need a new GUI!" + Chr(10) + "Get one on www.devil-engines.net", 2)
If GUI_AppEvent() = btnDlg3 Then
	Select GUI_MsgBox("Confirmation", "Do you like the Devil GUI?", 3)
		Case 1
			GUI_MsgBox("", "Thank you :)")
		Case 2
			GUI_MsgBox("Offence", "Go to hell!")
	End Select
EndIf
If GUI_AppEvent() = btnDlg4 Then
	GUI_MsgBox("Information", "Colour picker returned: " + Chr(10) + GUI_ColorPicker())
EndIf
GUI_Message(lblFPS, "SetText", "FPS: " + FPS())
If GUI_AppEvent() <> -1 Then GUI_Message(lblEvent, "SetText", "Last event: " + GUI_AppEvent())
End Function