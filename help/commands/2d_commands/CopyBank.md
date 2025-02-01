# CopyBank src_bank,src_offset,dest_bank,dest_offset,count

# Parameters

  -----------------------------------------------------------------------
  src_bank = handle of source memory bank\
  src_offset = offset location to start copying from\
  dest_bank = handle of destination memory bank\
  dest_offset = offset location to start writing to\
  count = how many bytes to copy

  -----------------------------------------------------------------------

# Description

  ----------------------------------------------
  Copies data from one memory bank to another.
  ----------------------------------------------

# [Example](../2d_examples/CopyBank.bb)

  -----------------------------------------------------------------------
  ; BankSize, ResizeBank, CopyBank Example\
  \
  ; create a bank\
  bnkTest=CreateBank(5000)\
  \
  ; Fill it with rand Integers\
  For t = 0 To 4999\
  PokeByte bnkTest,t,Rand(9)\
  Next\
  \
  ; Resize the bank\
  ResizeBank bnkTest,10000\
  \
  ; Copy the first half of the bank to the second half\
  CopyBank bnkTest,0,bnkTest,5000,5000\
  \
  ; Print final banksize\
  Print BankSize(bnkTest)

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=CopyBank&ref=comments){target="_blank"}
to view the latest version of this page online
