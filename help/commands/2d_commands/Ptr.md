# Ptr

# Parameters

  -------------------
  Variable or value
  -------------------

# Description

  -----------------------------------------------------------------------
  Use this command to convert a variable or value to a pointer. This is
  useful with code that must perform low level interaction with the
  operating system and/or speed sensitive code. The result is returned as
  a BBPointer internal type which can be cast to an Integer or String
  which will be the address value of the pointed at data. The pointer can
  be cast back to the original type.\
  The pointer variable type can be denoted with .BBPointer or using the @
  symbol as shorthand.\
  \
  Pointers are not recommended for general use, as misuse of pointers can
  easily result in memory corruption causing all sorts of bugs and
  headaches!\
  Pointers cannot point to integer variables. Casting an integer to a
  pointer will point to the memory address the integer value represents.
  This is not the same as a pointer to an integer variable itself.\

  -----------------------------------------------------------------------

# [Example](../2d_examples/Ptr.bb)

  -----------------------------------------------------------------------
  ; PTR example\
  Global bank.BBBank = CreateBank( 4 )\
  Global bank_ptr_1.BBPointer = Ptr bank\
  Global bank_ptr_2@ = Ptr bank\
  Global bank_ptr_3@ = bank_ptr_1\
  \
  Function AreBanksEqual()\
  Return BankSize(bank) = BankSize(bank_ptr_1) And BankSize(bank) =
  BankSize(bank_ptr_2) And BankSize(bank) = BankSize(bank_ptr_3)\
  End Function\
  \
  Local all_banks_equal = AreBanksEqual()\
  \
  ResizeBank bank, 8\
  Local all_banks_equal_after_resize = AreBanksEqual()\
  \
  ResizeBank bank_ptr_1, 16\
  Local all_banks_equal_after_resize_1 = AreBanksEqual()\
  \
  ResizeBank bank_ptr_2, 32\
  Local all_banks_equal_after_resize_2 = AreBanksEqual()\
  \
  ResizeBank bank_ptr_3, 64\
  Local all_banks_equal_after_resize_3 = AreBanksEqual()\
  \
  Print \"All banks are equal before resize: \" + all_banks_equal\
  Print \"All banks are equal after resize: \" +
  all_banks_equal_after_resize\
  Print \"All banks are equal after resize 1: \" +
  all_banks_equal_after_resize_1\
  Print \"All banks are equal after resize 2: \" +
  all_banks_equal_after_resize_2\
  Print \"All banks are equal after resize 3: \" +
  all_banks_equal_after_resize_3\
  \
  WaitKey\
  End\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
