Include "ShadowSystem.bb"

; CreateCube Example with Shadows
; -------------------------------

Graphics3D 1024,768,0,2
SetBuffer BackBuffer() 

center  =   CreatePivot()
Global ShadowsEnable=1

camera=CreateCamera() 
CameraClsColor camera,100,150,250
PositionEntity camera,15,10,-5

;----shadow / start
ShadowTexSize=512;<-shadow mapp size (tip: for 1024x768 use 512)
SoftShadow=0;<-soft shadows?
SoftShadowQuality=2
SoftShadowRadius=2
ShadowScale#=10;3.0
ShadowRange#=6000;<-default shadow range
Global AR#=128,AG#=128,AB#=128;<-ambient light color
AmbientLight AR#,AG#,AB#
ShadowAR#=AR#:ShadowAG#=AG#:ShadowAB#=AB#;match shadow color to world ambient
InitShadowSystem();<--- Start Shadow System
;----shadow / end

;----lightning / start
Global lightPiv,light
lightPiv=   CreatePivot()
light   =   CreateLight(1,lightPiv)
            PositionEntity light,-30,30,-60
;Local vis_light = CreateSphere(12,light):ScaleMesh vis_light,.1,.1,.1  ;<--- show the light source.

SetLight(light,1);<--- Cast Light.

; Create cube 
cube=CreateCube() 
PositionEntity cube,0,0,10
ScaleEntity cube,2,2,2
plane=CreateCube() 
PositionEntity plane,0,-2,15
ScaleEntity plane,20,.1,20

SetReceiver(plane)
EntityTexture cube,LoadTexture("media/wcrate.jpg")
groundtex = LoadTexture("media/mossyground.bmp")
ScaleTexture groundtex,.2,.2
EntityTexture plane,groundtex

PointEntity camera,plane

While Not KeyDown( 1 ) 
;PointEntity light, center

;----Shadow Update / start  
PointEntity light,lightPiv;<--- direct the light to the center.
PositionEntity lightPiv,EntityX(camera),0,EntityZ(camera);clamp shadow pivot to cameraeras cordinates
;hide stuff before shadow update
If ShadowsEnable=1 Then UpdateShadowSystem(camera);<--- UpdateShadows.
RenderWorld 

Flip 
If KeyHit(2) Then SaveBuffer( BackBuffer(), "ss"+MilliSecs()+".bmp" )
Wend 

End  
