Strict
EnableGC

Test testForLoop()
    Local sum = 0
    For i = 0 To 10
        sum = sum + i
    Next
    Assert(sum = 55)
End Test

Test testWhileLoop()
    Local sum = 0
    Local i = 0
    While i < 10
        sum = sum + i
        i = i + 1
    Wend
    Assert(sum = 45)
End Test

Test testRepeatLoop()
    Local sum = 0
    Local i = 0
    Repeat
        sum = sum + i
        i = i + 1
    Until i >= 10
    Assert(sum = 45)
End Test

Test testSelectCase()
    Local sum = 0
    Local i = 0
    Select i
        Case 0
            sum = 1
        Case 1
            sum = 2
    End Select
    Assert(sum = 1)
End Test
