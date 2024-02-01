; Example of use FastExt library
; (c) 2006-2010 created by MixailV aka Monster^Sage [monster-sage@mail.ru]  http://www.fastlibs.com



;	������� ������ ������������ �������.
;	������ ����� ��������� �������� ��������� ������� ����� ��������� ��������,
;	� ��������� ��������� � ����������� - ������ ���� � ������������ ��������.

;	��������! ����� ����, ��� �� ������ ���������� ����� FE_PROJECT
;	����������� ������� ������ �������� ����� ��� �������, �������� ScaleTexture (texture, 1, 1),
;	����� �������� ������� ������� ������������� (��� ��� ���� �� ��������� ���������� �������������
;	�������� - ��� ���������, �� ��� �������� ��� ��������!)


; Simple example of use 2D projection blend for textures
; 2D project texture blend allows to do much different effects - reflections, refractions, water and etc.



Include "include\FastExt.bb"		; <<<<    Include FastExt.bb file

	
Graphics3D 800,600,0,2



InitExt						; <<<< 	����������� ������������ ����� Graphics3D
								;  Initialize library after Graphics3D function



CreateLight()   :   c=CreateCamera()   :   CameraClsColor c,0,0,64   :   PositionEntity c,0,0,-3



tex0 = LoadTexture ("..\media\Devil.png",16+32)
	TextureBlend tex0, FE_PROJECT			; <<<<	����� ����� ��� ��������� �������� ��� ��������
										;  New blend for 2D project
	ScaleTexture tex0,2,2						;		������� ����� 2, ����� �������� �������� �� ��� ������ ������ (��������)
										; Set scale 2 for fullscreen project
	PositionTexture tex0,0.5,0.5				;		������� ������� 0.5, ����� ����� �������� ��� � ������ ������ (��������)
										; Set position 0.5 for center texture of the screen


tex1 = LoadTexture ("..\media\Alien.png",16+32)
	TextureBlend tex1, FE_PROJECT			; <<<<	����� ����� ��� ��������� �������� ��� ��������
	ScaleTexture tex1,2,2						;		������� ����� 2, ����� �������� �������� �� ��� ������ ������ (��������)
	PositionTexture tex1,0.5,0.5				;		������� ������� 0.5, ����� ����� �������� ��� � ������ ������ (��������)
	RotateTexture tex1,180					;		�������� �������� ��� �������. ������������� ��������
										;		���� ����� �������� ������ ������� �������.
										;  You can use rotation for texture



piv=CreatePivot()
ent0=CreateCube(piv) : PositionEntity ent0,-1.5,0,0
EntityTexture ent0,tex0

ent1=CreateSphere(5,piv) : PositionEntity ent1,1.5,0,0 : ScaleEntity ent1,1.5,1.5,1.5
EntityTexture ent1,tex1



While Not KeyHit(1)
	TurnEntity piv, 0, 0, 0.6
	TurnEntity ent0,0.3,0.5,0
	RenderWorld
	Flip
Wend