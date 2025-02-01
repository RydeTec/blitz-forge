[  FindGfxMode (width,height,depth)   ]{.Command}

[Definition:]{.header}\
\

  ------------------------------------------------------------------
  Returns the mode number of a graphic mode meeting your criteria.
  ------------------------------------------------------------------

[\
Parameter Description:]{.header}\
\

  -----------------------------------------------------------------------
  width = width, in pixels (i.e. 640)\
  height = height, in pixels (i.e. 480)\
  depth = color depth (i.e. 16, 24, 32)\

  -----------------------------------------------------------------------

Command Description:\
\

  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Use this command to determine which of the user\'s graphic card mode supports the parameters you supply. You can get a full detailed list of the video card modes - see [CountGFXModes()](CountGFXModes().htm).
  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Example:\
\

  -----------------------------------------------------------------------
  ; FindGFXMode example\
  \
  ; change values to see different mode numbers\
  mode=FindGfxMode(800,600,16)\
  \
  ; If there is a mode, tell user\
  If mode \> 0 Then\
  Print \"The mode you requested is: \" + mode\
  Else\
  Print \"That mode doesn\'t exist for this video card.\"\
  End If\
  \
  ; Wait for ESC press from user\
  While Not KeyHit(1)\
  Wend\

  -----------------------------------------------------------------------

**[Index](../index.htm){target="_top"}**
