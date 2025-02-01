# CreateBank (\[size\])

# Parameters

  ----------------------------------------------------------
  size - size of memory bank in bytes (default is 0 bytes)
  ----------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  The bank commands allow you to perform high-speed data operations on a
  block of memory. This is useful for writing your own
  compression/decompression routines, passing and receiving data to and
  from a DLL and more. Banks start at 0 and finish at size-1.\
  \
  The data types available for use with a bank are:\
  \
  Byte - takes up one byte. Values can be in the range 0 to 255.\
  Short - takes up two bytes. Values can be in the range 0 to 65535.\
  Int - takes up four bytes. Values can be in the range -2147483647 to
  2147483647.\
  Float - takes up four bytes.\
  \
  See also: [FreeBank](FreeBank.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/CreateBank.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CreateBank&ref=comments){target="_blank"}
to view the latest version of this page online
