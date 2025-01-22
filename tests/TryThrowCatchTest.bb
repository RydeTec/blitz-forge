Strict
EnableGC

; For calls using function pointers if you need to pass or recieve more than a single integer
; You need to use DTO objects
Type BasicDTO
    Field var%

    Method create.BasicDTO(var%)
        self\var = var

        return self
    End Method
End Type

Function functionToTry@(dto.BasicDTO=Null)
    if (dto = Null)
        return FunctionPtr()
    end if

    Return new BasicDTO(1)
End Function

Function functionToThrow@(dto.BasicDTO=Null)
    if (dto = Null)
        return FunctionPtr()
    end if

    Throw new BasicDTO(10)

    Return new BasicDTO(1)
End Function

Function functionToCatch@(code.BasicDTO=Null)
    if (code = Null)
        return FunctionPtr()
    end if

    Assert(code\var = 10)

    return new BasicDTO(2)
End Function

Function nestedFunctionToThrow@(dto.BasicDTO=Null)
    if (dto = Null)
        return FunctionPtr()
    end if

    functionToThrow(dto)

    return new BasicDTO(3)
End Function

Test testTry()
    local dto.BasicDTO = new BasicDTO(0)
    local t_func.BBFunction = functionToTry()
    local c_func.BBFunction = functionToCatch()
    local result.BasicDTO = TryCatch(t_func, c_func, dto)
    Assert(result\var = 1)
End Test

Test testCatch()
    local dto.BasicDTO = new BasicDTO(0)
    local t_func.BBFunction = functionToThrow()
    local c_func.BBFunction = functionToCatch()
    local result.BasicDTO = TryCatch(t_func, c_func, dto)
    Assert(result\var = 2)
End Test

Test testNestedThrow()
    local dto.BasicDTO = new BasicDTO(0)
    local t_func.BBFunction = nestedFunctionToThrow()
    local c_func.BBFunction = functionToCatch()
    local result.BasicDTO = TryCatch(t_func, c_func, dto)
    Assert(result\var = 2)
End Test

