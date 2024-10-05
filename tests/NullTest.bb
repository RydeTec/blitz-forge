Strict
EnableGC

Type testType 
    Field var
End Type

Test testNullWhenDeleted()
    Local testType1.testType = new testType()
    Delete testType1
    Assert(testType1 = Null)
End Test