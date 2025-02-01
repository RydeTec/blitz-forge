# Strict

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  Use this to enable strict typing for the current file. It must be
  placed as the first operation of the file. Strictness does not affect
  Include files, and strict declarations present in Include files do not
  affect the current file.\
  \
  When strict typing is enabled, the following rules apply:\
  \* String to Integer casting must be explicit. See
  [Int](../2d_commands/Int.htm) for more information.\
  \* Non-integer variables must be typed explicitly.

  -----------------------------------------------------------------------

# [Example](../2d_examples/If.bb)

  -----------------------------------------------------------------------
  ; Strict Example\
  \
  Strict\
  Local string_var = \"Hello\" ; This will cause a syntax error\
  Local string_var\$ = \"Hello\" ; This is correct\
  Local bank = CreateBank( 4 ) ; This will cause a syntax error\
  Local bank.BBBank = CreateBank( 4 ) ; This is correct\
  Local bank@ = Ptr CreateBank( 4 ) ; This is correct\
  Local bank = Ptr CreateBank( 4 ) ; This is correct

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
