# LoadFont (fontname\$\[,height\]\[,bold\]\[,italic\]\[,underlined\])

# Parameters

  -----------------------------------------------------------------------
  fontname\$ - name of font to be loaded, e.g. \"arial\"\
  height - height of font in points (default is 12)\
  bold - True to load bold version of font, False not to (default is
  False)\
  italic - True to load italic version of font, False not to (default is
  False)\
  underlined - True to load underlined version of font, False not to
  (default is False)

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Loads a font and returns a font handle.\
  \
  You can then use the font handle with commands such as SetFont and
  FreeFont.\
  \
  Note: Blitz doesn\'t work with SYMBOL fonts, like Webdings and
  WingDings.

  -----------------------------------------------------------------------

# [Example](../2d_examples/LoadFont.bb)

  -----------------------------------------------------------------------
  ; LoadFont/SetFont/FreeFont Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  ; Enable Graphics mode\
  Graphics 800,600\
  \
  ; Set global on font variables\
  Global fntArial,fntArialB,fntArialI,fntArialU\
  \
  ; Load fonts to a file handle variables\
  fntArial=LoadFont(\"Arial\",24)\
  fntArialB=LoadFont(\"Arial\",18,True)\
  fntArialI=LoadFont(\"Arial\",32,False,True)\
  fntArialU=LoadFont(\"Arial\",14,False,False,True)\
  \
  ; Set the font and print text\
  SetFont fntArial\
  Text 400,0,\"This is just plain Arial 24 point\",True\
  \
  SetFont fntArialB\
  Text 400,30,\"This is bold Arial 18 point\",True\
  \
  SetFont fntArialI\
  Text 400,60,\"This is italic Arial 32 point\",True\
  \
  SetFont fntArialU\
  Text 400,90,\"This is underlined Arial 14 point\",True\
  \
  ; Standard \'wait for ESC\' from user\
  While Not KeyHit(1)\
  Wend\
  \
  ; Clear all the fonts from memory!\
  FreeFont fntArial\
  FreeFont fntArialB\
  FreeFont fntArialI\
  FreeFont fntArialU

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=LoadFont&ref=comments){target="_blank"}
to view the latest version of this page online
