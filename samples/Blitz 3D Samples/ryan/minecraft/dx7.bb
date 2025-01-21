; GetTextureStageState(D3D_TEXTURE_STAGE_STATE_TYPE,index%)
; SetTextureStageState(D3D_TEXTURE_STAGE_STATE_TYPE,index%, value%)

; GetMipmapLODBias(index%) ; B3D Default = 0.0
; SetMipmapLODBias(bias#, index%)

; D3D_TEXTURE_STAGE_STATE_TYPE
Const D3D_MAGFILTER = 16 ; D3DTEXTUREMAGFILTER filter To use For magnification
Const D3D_MINFILTER = 17 ; D3DTEXTUREMINFILTER filter To use For minification
Const D3D_MIPFILTER = 18 ; D3DTEXTUREMIPFILTER filter To use between mipmaps during minification
Const D3DTSS_MAXANISOTROPY = 21
Const D3DTSS_TEXCOORDINDEX = 11

; D3DTEXTUREMAGFILTER
Const MAG_POINT = 1
Const MAG_LINEAR = 2 ; B3D default
Const MAG_FLATCUBIC = 3
Const MAG_GAUSSIANCUBIC = 4
Const MAG_ANISOTROPIC = 5

; D3DTEXTUREMINFILTER
Const MIN_POINT = 1
Const MIN_LINEAR = 2 ; B3D default
Const MIN_ANISOTROPIC = 3

; D3DTEXTUREMIPFILTER
Const MIP_NONE = 1
Const MIP_POINT = 2
Const MIP_LINEAR = 3 ; B3D default


Global d3d,dev7,draw7,hwnd,instance
Global anistropicallowed=1
Global texturesinited=0

Function Textureinit()
d3d=SystemProperty$("Direct3D7")
dev7=SystemProperty$("Direct3DDevice7")
draw7=SystemProperty$("DirectDraw7")
hwnd=SystemProperty$("AppHWND")
instance=SystemProperty$("AppHINSTANCE")
anistropicallowed=1
If Not DX7_SetSystemProperties(d3d,dev7,draw7,hwnd,instance) Then TextureFilter "",9:anistropicallowed=0:RuntimeError "Something wrong with DX7_sys_prop"
texturesinited=1
Return anistropicallowed
End Function



Function EnableLinear(bias#=-0.2) 
If texturesinited=0 Then Textureinit()
If anistropicallowed=1 Then
DX7_SetTextureStageState(D3DTSS_MAXANISOTROPY,8,3)

DX7_SetTextureStageState(D3D_MAGFILTER,8,MAG_POINT)
DX7_SetTextureStageState(D3D_MINFILTER,8,MIN_POINT)
DX7_SetTextureStageState(D3D_MIPFILTER,8,MIP_ANISOTROPIC)
DX7_SetMipmapLODBias(bias, 8)
EndIf
End Function