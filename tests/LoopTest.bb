Strict
EnableGC

Type TestType
    Field i = 0
End Type

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

Test testContinue()
    Local i = 0
    Local sum = 0
    For i = 0 To 10
        If i = 5 Then Continue
        sum = sum + i
    Next
    Assert(sum = 50)

    sum = 0
    i = 0
    While i < 10
        If i = 5
            i = i + 1
            Continue
        End If
        sum = sum + i
        i = i + 1
    Wend
    Assert(sum = 40)

    i = 0
    For i = 0 To 10
        Local t.TestType = new TestType()
    Next

    i = 0
    For t.TestType = Each TestType
        i = i + 1
        if i = 5 Then Continue
        t\i = 1
    Next

    i = 0
    For t.TestType = Each TestType
        i = i + 1
        if i = 5
            Assert(t\i = 0)
        Else
            Assert(t\i = 1)
        End If
    Next
End Test

Test testBreak()
    Local i = 0
    Local sum = 0
    For i = 0 To 10
        If i = 5 Then Exit
        sum = sum + i
    Next
    Assert(sum = 10)
End Test

Test testContinueAndBreak()
    Local i = 0
    Local sum = 0
    For i = 0 To 10
        If i = 5 Then Continue
        If i = 8 Then Exit
        sum = sum + i
    Next
    Assert(sum = 23)
End Test

