Graphics3D 1024,768,32,1
CreateMyCamera()
LoadMesh ("model.b3d")
Repeat
		MoveCamera()
RenderWorld 
Flip 1
Until KeyHit(1) 
End


Global camera_piv, camera, speed#
Global  mxs#, mys#,xang# ,yang# ,dest_xang# ,dest_yang#

 Function CreateMyCamera()
	camera_piv =CreatePivot ()
	camera=CreateCamera(camera_piv)
		MoveEntity camera_piv,0,15,-150
		speed# = 1
 End Function
 Function MoveCamera ()
	mxs# = MouseXSpeed()/7
   mys# = MouseYSpeed()/7
    	
		dest_xang# = dest_xang + mys
		dest_yang# = dest_yang - mxs
	
		 xang# = CurveValue (xang, dest_xang, 7) 
	    	yang# = CurveValue (yang, dest_yang, 7)

	   If xang>89 xang=89:dest_xang=89
		If xang<-89 xang=-89:dest_xang=-89
    RotateEntity camera,xang, 0, 0
    RotateEntity camera_piv,0, yang, 0
	   MoveMouse GraphicsWidth()/7,GraphicsHeight()/7



If KeyDown(17) MoveEntity camera_piv,0,0,      speed 		
If KeyDown(31)	MoveEntity camera_piv,0,0,    -speed 
If KeyDown(30) MoveEntity camera_piv,-speed ,0,     0 
If KeyDown(32)  MoveEntity camera_piv,speed   ,0,    0
If KeyDown(16) TranslateEntity camera_piv,0,-speed   ,     0 
If KeyDown(18) TranslateEntity camera_piv,0,speed   ,    0

End Function
Function CurveValue#(current#,destination#,curve)
	current#=current#+((destination#-current#)/curve)
	Return current#
End Function
