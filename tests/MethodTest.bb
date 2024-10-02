Strict
EnableGC

Type TestStruct
    Field testVar$
    Method testMethod(testArg$)
        self\testVar = testArg$
    End Method
End Type

Test testMutation()
    Local testType1.TestStruct = new TestStruct()

    testType1\testVar = "initial set"

    TestStruct::testMethod(testType1,"reset")

    Assert(testType1\testVar = "reset")
End Test

;Function m::func() ; This fails so that we don't overwrite exisiting type functions
;    Local test::test = "this"
;End Function

Type ConstructorTest
    Field value1$
    Field value2$
    Field value3$
    Method create.ConstructorTest(value1$, value2$, value3$)
        self\value1 = value1
        self\value2 = value2
        self\value3 = value3
        return self
    End Method
End Type

Test testConstructor()
    Local instance.ConstructorTest = new ConstructorTest("value1", "value2", "value3")
    Assert(instance\value1 = "value1") : Assert(instance\value2 = "value2") : Assert(instance\value3 = "value3")
End Test

Test testNullTypeAccess()
    Local instance.TestStruct = Null
    ;instance\testVar = "" ; Fail
End Test