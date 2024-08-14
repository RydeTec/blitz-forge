Strict

Type TestType
    Field testVar$

    Method create.TestType()
        self\testVar = "test"

        return self
    End Method
End Type

Type SecondType.TestType
    Field secondTestVar$
End Type

Test testTestVar()
    testType1.TestType = new TestType()
    Assert(testType1\testVar = "test")
End Test

Test testClassName()
    testType1.TestType = new TestType()
    Assert(testType1\className = "TestType")
End Test

Test testClassNameIntegrity()
    testType1.SecondType = new SecondType()
    Assert(testType1\className = "SecondType")
    testType2.TestType = testType1
    Assert(testType2\className = "SecondType")
End Test