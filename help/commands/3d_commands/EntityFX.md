# EntityFX entity,fx

# Parameters

  -----------------------------------------------------------------------
  entity - entity handle\
  \
  fx -\
  0: nothing (default)\
  1: full-bright\
  2: use vertex colors instead of brush color\
  4: flatshaded\
  8: disable fog\
  16: disable backface culling\
  32: force alpha-blending

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets miscellaneous effects for an entity.\
  \
  Flags can be added to combine two or more effects. For example,
  specifying a flag of 3 (1+2) will result in a full-bright and
  vertex-coloured brush.\
  \
  Flag 32, to force alpha-blending, must be used in order to enable
  vertex alpha (see VertexColor).\
  \
  See also: [VertexColor](VertexColor.htm){.small}.

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=EntityFX&ref=comments){target="_blank"}
to view the latest version of this page online
