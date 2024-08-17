Strict

Test testPointers()
    Local bank.BBBank = CreateBank( 4 ) 
    Local bank_ptr_1.BBPointer = Ptr bank 
    Local bank_ptr_2@ = Ptr bank 
    Local bank_ptr_3@ = bank_ptr_1
    Local bank_ptr_4 = Int bank_ptr_3
    Local bank_ptr_5@ = Ptr bank_ptr_4

    Assert(BankSize(bank) = BankSize(bank_ptr_1) And BankSize(bank) = BankSize(bank_ptr_2) And BankSize(bank) = BankSize(bank_ptr_3) And BankSize(bank) = BankSize(bank_ptr_4) And BankSize(bank) = BankSize(bank_ptr_5))

    ResizeBank bank, 8
    Assert(BankSize(bank) = BankSize(bank_ptr_1) And BankSize(bank) = BankSize(bank_ptr_2) And BankSize(bank) = BankSize(bank_ptr_3) And BankSize(bank) = BankSize(bank_ptr_4) And BankSize(bank) = BankSize(bank_ptr_5))

    ResizeBank bank_ptr_1, 16
    Assert(BankSize(bank) = BankSize(bank_ptr_1) And BankSize(bank) = BankSize(bank_ptr_2) And BankSize(bank) = BankSize(bank_ptr_3) And BankSize(bank) = BankSize(bank_ptr_4) And BankSize(bank) = BankSize(bank_ptr_5))

    ResizeBank bank_ptr_2, 32
    Assert(BankSize(bank) = BankSize(bank_ptr_1) And BankSize(bank) = BankSize(bank_ptr_2) And BankSize(bank) = BankSize(bank_ptr_3) And BankSize(bank) = BankSize(bank_ptr_4) And BankSize(bank) = BankSize(bank_ptr_5))

    ResizeBank bank_ptr_3, 64
    Assert(BankSize(bank) = BankSize(bank_ptr_1) And BankSize(bank) = BankSize(bank_ptr_2) And BankSize(bank) = BankSize(bank_ptr_3) And BankSize(bank) = BankSize(bank_ptr_4) And BankSize(bank) = BankSize(bank_ptr_5))

    ResizeBank bank_ptr_4, 128
    Assert(BankSize(bank) = BankSize(bank_ptr_1) And BankSize(bank) = BankSize(bank_ptr_2) And BankSize(bank) = BankSize(bank_ptr_3) And BankSize(bank) = BankSize(bank_ptr_4) And BankSize(bank) = BankSize(bank_ptr_5))

    ResizeBank bank_ptr_5, 256
    Assert(BankSize(bank) = BankSize(bank_ptr_1) And BankSize(bank) = BankSize(bank_ptr_2) And BankSize(bank) = BankSize(bank_ptr_3) And BankSize(bank) = BankSize(bank_ptr_4) And BankSize(bank) = BankSize(bank_ptr_5))
End Test