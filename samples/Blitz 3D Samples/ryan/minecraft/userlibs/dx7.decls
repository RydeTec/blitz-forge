.lib "dx7.dll"

DX7_SetSystemProperties%(d3d,dev7,draw7,hwnd,instance):"_SetSystemProperties@20"

DX7_SetRenderState%(renderstate,param):"_SetRenderState@8"
DX7_GetRenderState%(renderstate):"_GetRenderState@4"

DX7_SetMipmapLODBias%(bias#,index):"_SetMipmapLODBias@8"
DX7_GetMipmapLODBias#(index):"_GetMipmapLODBias@4"

DX7_GetTextureStageState%(stage,index):"_GetTextureStageState@8"
DX7_GetTextureStageStateF#(stage,index):"_GetTextureStageStateF@8"

DX7_SetTextureStageState%(stage,index,value):"_SetTextureStageState@12"
DX7_SetTextureStageStateF%(stage,index,value#):"_SetTextureStageStateF@12"

DX7_GetDevCaps%(D3DDeviceDesc7*):"_GetDevCaps@4"
DX7_GetTriCaps%(D3DPrimCaps*):"_GetTriCaps@4"
DX7_GetLineCaps%(D3DPrimCaps*):"_GetLineCaps@4"

DX7_GetClipPlane%(index,ClipPlane*):"_GetClipPlane@8"
DX7_SetClipPlane%(index,ClipPlane*):"_SetClipPlane@8"

DX7_DeviceClear%(BBRect*,flags, ColorRGBA, ZVal#, StencilVal%):"_DeviceClear@20"

DX7_SupportsAllStencilOPs%():"_SupportsAllStencilOPs@0"

DX7_EnumDD7Surfaces%(bank*):"_EnumDD7Surfaces@4"

DX7_CreateRenderTarget%(texBuffer,rt*):"_CreateRenderTarget@8"
DX7_FreeRenderTarget%(texBuffer,rt*):"_FreeRenderTarget@8"

DX7_GetRenderTarget%():"_GetRenderTarget@0"
DX7_SetRenderTarget%(buffer):"_SetRenderTarget@4"

DX7_EnumDXTC%():"_EnumDXTC@0"
DX7_ConvertToDXTC%(texBuffer,DXTCType):"_ConvertToDXTC@8"


DX7_CreateBumpTarget%(buffer):"_CreateBumpTarget@4"






; test crap
DX7_GetTransform%(tfState,D3DMATRIX*):"_GetTransform@8"
DX7_SetTransform%(tfState,D3DMATRIX*,camera):"_SetTransform@12"
DX7_SetTextureMatrix#(entity,cam,stage):"_SetTextureMatrix@12"
DX7_GetMipmap%(surf1,surf2):"_GetMipmap@8"
DX7_CopySurface%(src,dest,top,bottom,left,right):"_CopySurface@24"
DX7_GetSurfPixelFormat%(Surface7):"_GetSurfPixelFormat@4"

