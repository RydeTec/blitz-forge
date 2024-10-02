Strict
EnableGC

Type TestType
    Field var
End Type

Test testRef()
    Local testType1.TestType = new TestType()
    Assert(RefCount(First TestType) = 1)
End Test

Test testRel()
    Assert(RefCount(First TestType) = 0)

    Local testType2.TestType = First TestType
    Assert(testType2 = Null)
End Test