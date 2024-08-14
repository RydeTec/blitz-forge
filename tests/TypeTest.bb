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

Type ConstructType
    Field setField$

    Method create.ConstructType(fieldToSet$)
        self\setField = fieldToSet

        return self
    End Method
End Type

Test testTestVar()
    testType1.TestType = new TestType()
    Assert(testType1\testVar = "test")
End Test

Test testClassName()
    testType1.TestType = new TestType()
    Assert(testType1\className = "TestType")
    Assert(testType1\className = TestType::className())

    ; If you call new TestType without () it will not be constructed and you will have to set className manually
End Test

Test testClassNameIntegrity()
    testType1.SecondType = new SecondType()
    Assert(testType1\className = "SecondType")
    testType2.TestType = testType1
    Assert(testType2\className = SecondType::className())
End Test

Test testRecastChecking()
    testType1.SecondType = new SecondType()
    testType2.TestType = testType1
    If (NOT testType2\className = SecondType::className())
        RuntimeError("Cannot cast to " + SecondType::className())
    End If
    testType3.SecondType = Recast.SecondType(testType2)
    Assert(testType3\className = SecondType::className())
End Test

Test testConstructorArgs()
    testType1.ConstructType = new ConstructType("testValue")
    Assert(testType1\setField = "testValue")

    ; You can still call new ConstructType without () to skip construction and set the Type up the old way
End Test