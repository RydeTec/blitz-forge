# PeekByte(bank,offset)

# Parameters

  -----------------------------------------------------------------------
  bank - bank handle\
  offset - offset in bytes, that the peek operation will be started at

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Reads a byte from a memory bank and returns the value.\
  \
  A byte takes up one byte of a memory bank. Values can be in the range 0
  to 255.\
  \
  See also: [PeekShort](PeekShort.htm){.small},
  [PeekInt](PeekInt.htm){.small}, [PeekFloat](PeekFloat.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/PeekByte.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=PeekByte&ref=comments){target="_blank"}
to view the latest version of this page online
