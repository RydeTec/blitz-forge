; Example of use FastExt library
; (c) 2006-2010 created by MixailV aka Monster^Sage [monster-sage@mail.ru]  http://www.fastlibs.com



; ������ �������� ��������:
;
; 1. ������� ����� ������ (�� ������� ������� ������) (+ ����������� �������� �������, ��� ������ � ����������� ������)
; 2. ������� � ���������� � ��
; 3. ������� ���-������ ��� �������������
;
; � ����� ������ ������������ CLS �������, ������� ����� �������� �����-������������ ��� ������� � �������� ���-�������


; Example or render to texture




Include "include\FastExt.bb"					; <<<<    Include FastExt.bb file


Graphics3D 800,600,0,2


InitExt									; <<<<    Initialize library after Graphics3D function


texRender = CreateTexture(400,400, 1 + 2 + 256 + FE_ExSIZE + FE_RENDER + FE_ZRENDER)				;<<<<  �����! (new flags)
				; FE_ExSIZE - ����� ������ � ������ �������� (�� ������� ������� ������)
								; any size for texture
				; FE_RENDER - � �������� ����� ���������
								; render to texture flag
				; FE_ZRENDER - �������� ����� ����� Z-������ (����� ��������� �� ������ 2�, �� � ���������� 3�)
								; create depth buffer (Z-buffer) for 3D rendering
				; ��������! ��� ������������ ������� ���� 256 ������ ����������� ���� �������!
				; Flag 256 obligatory!!!

CreateLight
cam=CreateCamera()
PositionEntity cam,0,0,-3
cub=CreateCube()
cub1=CreateCube()
ScaleEntity cub1,2.0,0.2,0.2

tex= LoadTexture ("..\media\Devil.png",1+2)
SetBuffer TextureBuffer(tex)
Rect 0,0,TextureWidth(tex),TextureHeight(tex),0
Rect 1,1,TextureWidth(tex)-2,TextureHeight(tex)-2,0


While Not KeyHit(1)
	TurnEntity cub,0.1,0.2,0.3
	TurnEntity cub1,-0.1,0,-0.3
	
	
	EntityTexture cub,tex
	SetBuffer TextureBuffer(texRender)			;<<<<	������ ������� SetBuffer ����� ���������� � �������� ��� �������
										; Set texture for render
										
	ClsColor 128,0,0,64						;<<<<	������ � ClsColor ����� ������ Alpha ������� (������������) � Z-������� (������ ��� ��� ��� �������� ��� ��� ����� :)
										; Set Cls color 128,0,0 AND alpha = 64
										
	Cls									;<<<<	������ Cls ������� � �������� ��� �������, ���������� ��������� ����� ClsColor
										;		(��� ������� ����������� �������� Viewport � ��������� ������ ��� �������!)
										
	CameraClsMode cam,0,0					; �������� Cls ������, ����� �� �����, �� ��� ��������� �������� ��� ��� ����!
										; disable Cls in camera
	RenderWorld											; render scene to texture
	Rect 0,0,TextureWidth(texRender),TextureHeight(texRender),0		; draw rectangle
	
	
	EntityTexture cub,texRender		; <<<<	������ ������� �� ����� ���� �������� ��� �������
								; place texture to entity
	SetBuffer BackBuffer()			; � �������� ��� ������ �� ���-�����
	CameraClsMode cam,1,1			; ������ ������ Cls, ���� ����� ��������� �������� � ������� ������ (restore Cls mode)
	RenderWorld					; render scene to back buffer
	
	
	Flip
Wend

FreeTexture texRender			;<<<<	������� �������� � Z-�������� (���� FE_ZRENDER) ����� ����� FreeTexture,
							;		����� �������� ������ ���������� �� Z-�������
							;		��������� ������ ��� ����� ����������� (����� �� ������ �����-������),
							;		� ������ ������� ����������
							;		��� ���������� ������ ���� ������ ��� ���� �������

							
							
							
							
							
							