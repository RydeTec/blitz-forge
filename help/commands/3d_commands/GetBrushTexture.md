# GetBrushTexture(brush\[,index=0\])

# Parameters

  -----------------------------------------------------------------------
  brush - brush handle\
  index (optional) - index of texture applied to brush, from 0-7.
  Defaults to 0.

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Returns the texture that is applied to the specified brush.\
  \
  The optional index parameter allows you to specify which particular
  texture you\'d like returning, if there are more than one textures
  applied to a brush.\
  \
  You should release the texture returned by GetBrushTexture after use to
  prevent leaks! Use
  [FreeTexture](../3d_commands/FreeTexture.htm){.small} to do this.\
  \
  To find out the name of the texture, use
  [TextureName](../3d_commands/TextureName.htm){.small}\
  \
  See also: [TextureName](TextureName.htm){.small},
  [FreeTexture](FreeTexture.htm){.small},
  [GetEntityBrush](GetEntityBrush.htm){.small},
  [GetSurfaceBrush](GetSurfaceBrush.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/GetBrushTexture.bb)

  -----------------------------------------------------------------------
  ; GetBrushTexture Example\
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GetBrushTexture&ref=comments){target="_blank"}
to view the latest version of this page online
