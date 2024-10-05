Strict
EnableGC

Test maxIntTest()
    Local test32BitInt = 2147483647
    Assert(test32BitInt = 2147483647)
    DebugLog(test32BitInt)

    test32BitInt = -2147483648
    Assert(test32BitInt = -2147483648)
    DebugLog(test32BitInt)
End Test