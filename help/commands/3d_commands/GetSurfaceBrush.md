# GetSurfaceBrush(surface)

# Parameters

  --------------------------
  surface - surface handle
  --------------------------

# Description

  -----------------------------------------------------------------------
  Returns a brush with the same properties as is applied to the specified
  mesh surface.\
  \
  If this command does not appear to be returning a valid brush, try
  using [GetEntityBrush](../3d_commands/GetEntityBrush.htm){.small}
  instead.\
  \
  Remember, GetSurfaceBrush actually creates a new brush so don\'t forget
  to free it afterwards using
  [FreeBrush](../3d_commands/FreeBrush.htm){.small} to prevent memory
  leaks.\
  \
  Once you have got the brush handle from a surface, you can use
  [GetBrushTexture](../3d_commands/GetBrushTexture.htm){.small} and
  [TextureName](../3d_commands/TextureName.htm){.small} to get the
  details of what texture(s) are applied to the brush.\
  \
  See also: [GetEntityBrush](GetEntityBrush.htm){.small},
  [FreeBrush](FreeBrush.htm){.small},
  [GetSurface](GetSurface.htm){.small},
  [GetBrushTexture](GetBrushTexture.htm){.small},
  [TextureName](TextureName.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/GetSurfaceBrush.bb)

  -----------------------------------------------------------------------
  ; GetSurfaceBrush Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  \
  light=CreateLight()\
  RotateEntity light,90,0,0\
  \
  ; Load mesh\
  crate=LoadMesh(\"media/wood-crate/wcrate1.3ds\")\
  PositionEntity crate,0,0,100\
  \
  ; Get mesh surface\
  surf=GetSurface(crate,1)\
  \
  ; Get surface brush\
  crate_brush=GetSurfaceBrush(surf)\
  \
  ; Get brush texture\
  crate_tex=GetBrushTexture(crate_brush,0)\
  \
  While Not KeyDown( 1 )\
  \
  RenderWorld\
  \
  ; Display full texture name\
  Text 0,0,\"Texture name, as returned by TextureName\$():\"\
  Text 0,20,TextureName\$(crate_tex)\
  \
  ; Display trimmed texture name\
  Text 0,40,\"Texture name with path stripped:\"\
  Text 0,60,StripPath\$(TextureName\$(crate_tex))\
  \
  Flip\
  \
  Wend\
  \
  End\
  \
  Function StripPath\$(file\$)\
  \
  If Len(file\$)\>0\
  \
  For i=Len(file\$) To 1 Step -1\
  \
  mi\$=Mid\$(file\$,i,1)\
  If mi\$=\"\\\" Or mi\$=\"/\" Then Return name\$ Else
  name\$=mi\$+name\$\
  \
  Next\
  \
  EndIf\
  \
  Return name\$\
  \
  End Function

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GetSurfaceBrush&ref=comments){target="_blank"}
to view the latest version of this page online
