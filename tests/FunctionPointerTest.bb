Strict
EnableGC

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

Function testFunction@(getPtr=False)
    if (getPtr)
        return FunctionPtr()
    end if

    counter = 1
    Return new BasicDTO(1)
End Function

Function intenseFunction@(arg.BasicDTO=Null)
    if (arg = Null)
        return FunctionPtr()
    end if

    delay 100
    counter = arg\var
    Return new BasicDTO(arg\var)
End Function

Function secondTestFunction@( dto.BasicDTO=Null )
    if (dto = Null)
        return FunctionPtr()
    end if

    counter = dto\var
    Return new BasicDTO(dto\var)
End Function

Function thirdTestFunction@( arg.BasicDTO=Null )
    if (arg = Null)
        return FunctionPtr()
    end if

    Local var = arg\var + 1
    return new BasicDTO(var)
End Function

Function fourthTestFunction@( threadPtr.BBThread=Null )
    if (threadPtr = Null)
        return FunctionPtr()
    end if

    Local result.BasicDTO = Await(threadPtr)

    Local var = result\var + 1
    return new BasicDTO(var)
End Function

Test testFunctionPointer()
    Local f_ptr = testFunction(true) ; To get a pointer of a function you have to call the function and return the pointer
    Assert(NOT f_ptr = 0)
    Local value.BasicDTO = testFunction()
    Assert(value\var = 1)
    Assert(counter = 1)

    ; Reset state
    resetGlobals()
End Test

Test testCallPointer()
    ; You can pass a null object signaling you only want the function pointer returned
    Local f_ptr.BBFunction = secondTestFunction(Null)
    Assert(NOT f_ptr = Null)

    ; Craft a DTO object to send to the pointer
    Local dto.BasicDTO = new BasicDTO(1)

    ; The function need to work with Ptrs so cast your DTO to a Ptr and then use Ptr to cast back the returned DTO
    Local result.BasicDTO = Call(f_ptr, Ptr dto)
    Assert(result\var = 1)
    Assert(counter = 1)

    ; Reset state
    resetGlobals()
End Test

Test testThread()
    Local f_ptr.BBFunction = testFunction(true)
    Assert(NOT f_ptr = Null)

    Local f_ptr_2.BBFunction = intenseFunction(Null)
    Assert(NOT f_ptr_2 = Null)

    ; You can asyncronously call function pointers as well
    Local thread.BBThread = Async (f_ptr_2, new BasicDTO(2))
    Assert(counter = 0)

    ; The above will return with a future variable so you can run other code on the main thread while waiting for it's value
    testFunction()
    Assert(counter = 1)

    ; You can also pause the current thread while you wait for the future variable to populate
    Local result.BasicDTO = Await (thread)
    Assert(counter = 2)
    Assert(result\var = 2)

    resetGlobals()
End Test

Test testThreadPoll()
    Local f_ptr.BBFunction = intenseFunction(Null)
    Assert(NOT f_ptr = Null)

    Local thread.BBThread = Async (f_ptr, new BasicDTO(2))
    Assert(counter = 0)

    Local checkCount = 0
    while(true)
        ; Poll allows for checking if a thread is ready to return a value so you don't have to block
        ; the current thread just to check if it is ready and then await will return the value immediately.
        if (Poll(thread))
            Local result.BasicDTO = Await(thread)
            Assert(counter = 2)
            Assert(result\var = 2)

            Exit
        end if
        checkCount = checkCount + 1
    wend

    Assert(checkCount > 0)

    resetGlobals()
End Test

Test testThen()
    Local f_ptr.BBFunction = thirdTestFunction()
    Local f_ptr_2.BBFunction = fourthTestFunction()

    Local t_ptr.BBThread = Async(f_ptr, new BasicDTO(1))
    Local t_ptr_2.BBThread = AsyncThen(t_ptr, f_ptr_2)

    Local result.BasicDTO = Await(t_ptr_2)
    Assert(result\var = 3)
End Test