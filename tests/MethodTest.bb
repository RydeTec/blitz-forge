Strict
EnableGC

Type TestStruct
    Field testVar$
    Method testMethod(testArg$)
        self\testVar = testArg$
    End Method
End Type

Type SecondStruct.TestStruct

End Type

Type ThirdStruct.TestStruct
    Method testMethod(testArg$)
        self\testVar = "pre" + testArg$
    End Method
End Type

Type FourthStruct.ThirdStruct

End Type


Test testMutation()
    Local testType1.TestStruct = new TestStruct()

    testType1\testVar = "foo"

    // You can either call the method as static
    TestStruct::testMethod(testType1,"bar")

    Assert(testType1\testVar = "bar")

    testType1\testVar = "fizz"

    // Or dynamic
    testType1\testMethod("buzz")

    Assert(testType1\testVar = "buzz")
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

Test testMethodInherit()
    local instance.SecondStruct = new SecondStruct()
    instance\testVar = "foo"
    instance\testMethod("bar")

    Assert(instance\testVar = "bar")
End Test

Test testMethodOverride()
    local instance.ThirdStruct = new ThirdStruct()
    instance\testVar = "foo"
    instance\testMethod("bar")

    Assert(instance\testVar = "prebar")
End Test

Test testMethodOverrideInherit()
    local instance.FourthStruct = new FourthStruct()
    instance\testVar = "foo"
    instance\testMethod("bar")

    Assert(instance\testVar = "prebar")
End Test