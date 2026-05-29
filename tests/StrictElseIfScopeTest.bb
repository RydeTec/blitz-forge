Strict
EnableGC

Type StrictElseIfScopeSubject
    Field cat$
    Field latch%

    Method demo()
        Local localLatch% = 0

        If self\cat = "a"
            localLatch = 1
        Else If self\cat = "b"
            localLatch = 2
            If self\cat = "b"
                localLatch = localLatch + 10
            End If
        Else
            localLatch = 3
        End If

        self\latch = localLatch
    End Method
End Type

Test testStrictLocalReassignmentInElseIf()
    Local latch% = 0

    If False
        latch = 1
    Else If True
        latch = 2
    Else
        latch = 3
    End If

    Assert(latch = 2)
End Test

Test testStrictLocalReassignmentInNestedElseIfBlock()
    Local latch% = 0

    If False
        latch = 1
    Else If True
        If True
            latch = 4
        End If
    Else
        latch = 3
    End If

    Assert(latch = 4)
End Test

Test testStrictMethodLocalReassignmentInElseIf()
    Local subject.StrictElseIfScopeSubject = new StrictElseIfScopeSubject()
    subject\cat = "b"

    subject\demo()

    Assert(subject\latch = 12)
End Test

Test testStrictLocalReassignmentInIfAndElseStillWorks()
    Local firstLatch% = 0
    Local elseLatch% = 0

    If True
        firstLatch = 1
    Else If False
        firstLatch = 2
    Else
        firstLatch = 3
    End If

    If False
        elseLatch = 1
    Else If False
        elseLatch = 2
    Else
        elseLatch = 3
    End If

    Assert(firstLatch = 1)
    Assert(elseLatch = 3)
End Test
