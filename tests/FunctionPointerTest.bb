Strict

Global counter = 0

; For calls using function pointers if you need to pass or recieve more than a single integer
; You need to use DTO objects
Type BasicDTO
    Field var%

    Method create.BasicDTO(var%)
        self\var = var

        return self
    End Method
End Type

Function resetGlobals()
    ; Reset state
    counter = 0
End Function

Function testFunction%(getPtr=False)
    if (getPtr)
        return FunctionPtr()
    end if

    counter = 1
    Return 1
End Function

Function intenseFunction%(var%=0)
    if (var = 0)
        return FunctionPtr()
    end if

    delay 100
    counter = var
    Return var
End Function

Function secondTestFunction%( dto.BasicDTO=Null )
    if (dto = Null)
        return FunctionPtr()
    end if

    counter = dto\var
    Return Ptr (new BasicDTO(dto\var))
End Function

Function thirdTestFunction%( var%=0 )
    if (var = 0)
        return FunctionPtr()
    end if

    var = var + 1
    return var
End Function

Function fourthTestFunction%( funcPtr%=0 )
    if (funcPtr = 0)
        return FunctionPtr()
    end if

    var = Await(funcPtr)

    var = var + 1
    return var
End Function

Test testFunctionPointer()
    f_ptr = testFunction(true) ; To get a pointer of a function you have to call the function and return the pointer
    Assert(NOT f_ptr = 0)
    value = testFunction()
    Assert(value = 1)
    Assert(counter = 1)

    ; Reset state
    resetGlobals()
End Test

Test testCallPointer()
    ; You can pass a null object signaling you only want the function pointer returned
    f_ptr = secondTestFunction(Null)
    Assert(NOT f_ptr = 0)

    ; Craft a DTO object to send to the pointer
    dto.BasicDTO = new BasicDTO(1)

    ; The function need to work with Ptrs so cast your DTO to a Ptr and then use Ptr to cast back the returned DTO
    result.BasicDTO = Ptr Call(f_ptr, Ptr dto)
    Assert(result\var = 1)
    Assert(counter = 1)

    ; Reset state
    resetGlobals()
End Test

Test testThread()
    f_ptr = testFunction(true)
    Assert(NOT f_ptr = 0)

    f_ptr_2 = intenseFunction(0)
    Assert(NOT f_ptr_2 = 0)

    ; You can asyncronously call function pointers as well
    thread = Async (f_ptr_2, 2)
    Assert(counter = 0)

    ; The above will return with a future variable so you can run other code on the main thread while waiting for it's value
    testFunction()
    Assert(counter = 1)

    ; You can also pause the current thread while you wait for the future variable to populate
    result = Await (thread)
    Assert(counter = 2)
    Assert(result = 2)

    resetGlobals()
End Test

Test testThreadPoll()
    f_ptr = intenseFunction(0)
    Assert(NOT f_ptr = 0)

    thread = Async (f_ptr, 2)
    Assert(counter = 0)

    checkCount = 0
    while(true)
        ; Poll allows for checking if a thread is ready to return a value so you don't have to block
        ; the current thread just to check if it is ready and then await will return the value immediately.
        if (Poll(thread))
            result = Await(thread)
            Assert(counter = 2)
            Assert(result = 2)

            Exit
        end if
        checkCount = checkCount + 1
    wend

    Assert(checkCount > 0)

    resetGlobals()
End Test

Test testThen()
    f_ptr = thirdTestFunction()
    f_ptr_2 = fourthTestFunction()

    t_ptr = Async(f_ptr, 1)
    t_ptr_2 = AsyncThen(t_ptr, f_ptr_2)

    var = Await(t_ptr_2)
    Assert(var = 3)
End Test