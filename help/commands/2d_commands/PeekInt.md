# PeekInt(bank,offset)

# Parameters

  -----------------------------------------------------------------------
  bank - bank handle\
  offset - offset in bytes, that the peek operation will be started at

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Reads an int from a memory bank and returns the value.\
  \
  An int takes up four bytes of a memory bank. Values can be in the range
  -2147483647 to 2147483647.\
  \
  See also: [PeekByte](PeekByte.htm){.small},
  [PeekShort](PeekShort.htm){.small}, [PeekFloat](PeekFloat.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/PeekInt.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=PeekInt&ref=comments){target="_blank"}
to view the latest version of this page online
