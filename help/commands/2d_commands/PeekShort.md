# PeekShort(bank,offset)

# Parameters

  -----------------------------------------------------------------------
  bank - bank handle\
  offset - offset in bytes, that the peek operation will be started at

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Reads a short from a memory bank and returns the value.\
  \
  A short takes up two bytes of a memory bank. Values can be in the range
  0 to 65535.\
  \
  See also: [PeekByte](PeekByte.htm){.small},
  [PeekInt](PeekInt.htm){.small}, [PeekFloat](PeekFloat.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/PeekShort.bb)

  -----------------------------------------------------------------------
  ; Bank Commands Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  bnkTest=CreateBank(12)\
  \
  PokeByte bnkTest,0,Rand(255)\
  PokeShort bnkTest,1,Rand(65535)\
  PokeInt bnkTest,3,Rand(-2147483648,2147483647)\
  PokeFloat bnkTest,7,0.5\
  \
  Print PeekByte(bnkTest,0)\
  Print PeekShort(bnkTest,1)\
  Print PeekInt(bnkTest,3)\
  Print PeekFloat(bnkTest,7)\
  \
  FreeBank bnkTest

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=PeekShort&ref=comments){target="_blank"}
to view the latest version of this page online
