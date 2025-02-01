# GetEntityBrush(entity)

# Parameters

  ------------------------
  entity - entity handle
  ------------------------

# Description

  -----------------------------------------------------------------------
  Returns a brush with the same properties as is applied to the specified
  entity.\
  \
  If this command does not appear to be returning a valid brush, try
  using [GetSurfaceBrush](../3d_commands/GetSurfaceBrush.htm){.small}
  instead with the first surface available.\
  \
  Remember, GetEntityBrush actually creates a new brush so don\'t forget
  to free it afterwards using FreeBrush to prevent memory leaks.\
  \
  Once you have got the brush handle from an entity, you can use
  GetBrushTexture and TextureName to get the details of what texture(s)
  are applied to the brush.\
  \
  See also: [GetSurfaceBrush](GetSurfaceBrush.htm){.small},
  [FreeBrush](FreeBrush.htm){.small},
  [GetBrushTexture](GetBrushTexture.htm){.small},
  [TextureName](TextureName.htm){.small}.

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=GetEntityBrush&ref=comments){target="_blank"}
to view the latest version of this page online
