Global screenWidth=800
Global screenHeight=600
Graphics3D screenWidth,screenHeight
SetBuffer BackBuffer()
HidePointer
AmbientLight 255,255,255

Global cam=CreateCamera()
CameraRange cam,0.1,1000
PositionEntity cam,0,3,-10
CameraFogMode cam,1
CameraFogRange cam,20,60

light=CreateLight()
TurnEntity light,45,45,0

Global ground=CreatePlane();Cube();Sprite()
EntityFX ground,1

tex=LoadTexture("texture_stone.bmp")
if tex=0 Then RuntimeError "Failed to load texture_stone.bmp"
ScaleTexture tex,5,5
EntityTexture ground,tex,0,1

normal=LoadTexture("normal3.bmp")
if normal=0 Then RuntimeError "Failed to load normal3.bmp"
TextureBlend normal,4
EntityTexture ground ,normal,0,0
ScaleTexture normal,5,5

Global wall=CreateCube()
ScaleEntity wall,1,5,20
PositionEntity wall,10,5,0

nor=LoadTexture("normal3.bmp")
if nor=0 Then RuntimeError "Failed to load normal3.bmp"
TextureBlend nor,4
ScaleTexture nor,0.1,0.4
EntityTexture wall,nor,0,0

tex1=LoadTexture("texture_stone.bmp")
if tex1=0 Then RuntimeError "Failed to load texture_stone.bmp"
ScaleTexture tex1,0.1,0.4
EntityTexture wall,tex1,0,1


Global r=250
Global g=250
Global b=250
EntityColor ground,r,g,b

Global r1=250
Global g1=128
Global b1=250


;;;;;;;;;;;;;;;;;;

Global sprite=CreateSprite(cam)
PositionEntity sprite,0,0,1
EntityBlend sprite,2
;EntityFX sprite,32+1+4+2
EntityOrder sprite,-7
;EntityColor sprite,0,0,0

Global torchm=LoadMesh("models\torch.b3d")
EntityParent torchm,cam
ScaleEntity torchm,0.5,0.5,0.5
RotateEntity torchm,0,180,180
PositionEntity torchm,0,1,0.1
EntityOrder torchm,-10
EntityColor torchm,80,80,80


Global torch=LoadTexture("textures\light.PNG",16+32)
TextureBlend torch,5
EntityTexture sprite,torch

Global pointerOldX
Global pointerOldY
Global cameraSmooth		   	 	= 5  ; increase this value, for better smooth turning. Too much and the camera will rotate slowly.


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

While Not KeyHit(1)



	If KeyHit(2) Then SaveBuffer (FrontBuffer(),"screenie1.bmp")
	If KeyDown(205) Then MoveEntity cam,0.05,0,0
	If KeyDown(203) Then MoveEntity cam,-0.05,0,0
	If KeyDown(200) Then MoveEntity cam,0,0,0.05:PositionEntity cam,EntityX(cam),3,EntityZ(cam)
	If KeyDown(208) Then MoveEntity cam,0,0,-0.05:PositionEntity cam,EntityX(cam),3,EntityZ(cam)
	If KeyDown(2) Then b=b+1:If b>255 Then b=255
	If KeyDown(3) Then b=b-1:If b<0 Then b=0
	
;	TurnEntity cam,0,0.3,0

	update_floor()
	update_wall()
	;EntityColor wall,r1,g1,b1
	If MilliSecs()<timer+1000 Then
								frame=frame+1
	Else
								fps=frame
								frame=0
								timer=MilliSecs()
	End If
	mouse_move()
	UpdateWorld
	RenderWorld
	Text 0,0,"r="+r+" g="+g+" b="+b
	Text 0,10,"pitch="+EntityPitch(cam)
	Text 0,20,"r1="+r1+" g1="+g1+" b1="+b1
	Text 0,30,fps
	Flip
Wend
End

Function update_floor()
	tempy=EntityYaw(cam)
	tempy=tempy-90
	r=128*(Cos(-tempy)+1)
	g=128*(Sin(-tempy)+1)
	
	tempx#=EntityPitch(cam)
	b=184+(tempx/2)
	
	EntityColor ground,r,g,b
End Function

Function update_wall()
	tempy=EntityYaw(cam)
	;tempy=tempy-90
	r1=128*(Cos(-tempy)+1)
	;g1=128*(Sin(-tempy)+1)
	
	;tempx#=EntityPitch(cam)
	g1=128+EntityPitch(cam)
	b1=200
	
	EntityColor wall,r1,g1,b1
End Function

Function mouse_move()
	moveX#=MouseX()-pointerOldX; record the difference between the last mousex pos and the current one
	moveY#=MouseY()-pointerOldY; same as above, but for the Y axis
	; ABOVE: This is so the camera can be rotated with the mouse.

	RotateEntity cam,EntityPitch(cam),EntityYaw#(cam)-(moveX/cameraSmooth),EntityRoll(cam); Rotate player along the Y axis
	; NOTE:
	;      Player is only rotated in the Y axis. Camera is rotated on the x axis,
	;      because if the player were to rotate on the x axis, it would look very wrong.
	
	If EntityPitch(cam) + (moveY/cameraSmooth) <70 And EntityPitch(cam) + (moveY/cameraSmooth)>-70 Then      ; make sure camera doesn't rotate too far on X axis
	
		RotateEntity cam,EntityPitch#(cam)+(moveY/cameraSmooth),EntityYaw(cam),EntityRoll(cam)
	
	Else		;;; IF CAMERA IS ABOVE LIMITS, RESET TO LIMIT
	
		If EntityPitch(cam) + (moveY/cameraSmooth) > 70 Then ; if camera pitch is above 70, then reset to 70
			RotateEntity cam ,70  ,EntityYaw(cam) ,EntityRoll(cam)
		ElseIf EntityPitch(cam) + (moveY/cameraSmooth) <-70 Then ; if camera pitch is below -70 then reset to -70
			RotateEntity cam ,-70 ,EntityYaw(cam) ,EntityRoll(cam)
		End If
	End If
	
	MoveMouse screenWidth/2,screenHeight/2; set mouse pointer to middle of the screen. This stops
										  ; the cursor getting stuck at the edges.
										
	pointerOldX=MouseX(); SET OLD POINTER X TO LAST MOUSE POSITION
	pointerOldY=MouseY(); SAME AS ABOVE, BUT FOR Y
End Function

Delay 5000