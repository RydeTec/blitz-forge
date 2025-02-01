# BankSize (bankhandle)

# Parameters

  --------------------------------------------------------
  bankhandle - handle assigned to the bank when created.
  --------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Use this command to determine the size of an existing bank.\
  \
  See also: [CreateBank](CreateBank.htm){.small},
  [ResizeBank](ResizeBank.htm){.small}, [CopyBank](CopyBank.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/BankSize.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=BankSize&ref=comments){target="_blank"}
to view the latest version of this page online
