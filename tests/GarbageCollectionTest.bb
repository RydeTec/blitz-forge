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

; --- Reference-count contract (pins the behavior the language reference cites) ---
; Each concern uses its own Type so the per-block object lists stay isolated.
; All asserted values were captured from the live runtime, not assumed.

Type GcAlias
    Field var
End Type

; RefCount tracks the GC's reference/lifetime count, NOT a count of every alias:
; binding a second local to the same object does not increment it. (This is also
; why field-reference cycles below do not leak.)
Test testAliasDoesNotIncrementRefCount()
    Local a.GcAlias = new GcAlias()
    Assert(RefCount(a) = 1)
    Local b.GcAlias = a
    Assert(RefCount(a) = 1)
    Assert(NOT b = Null)
End Test

Type GcCycle
    Field other.GcCycle
End Type

Function makeGcCycle()
    Local a.GcCycle = new GcCycle()
    Local b.GcCycle = new GcCycle()
    a\other = b
    b\other = a
End Function

; A reference cycle (two objects pointing at each other through fields) is
; COLLECTED when the holding variables go out of scope -- it does not leak --
; because field assignments are not ref-counted, so the cycle adds no references.
Test testReferenceCycleIsCollected()
    makeGcCycle()
    Assert(First GcCycle = Null)
    Local count = 0
    For obj.GcCycle = Each GcCycle
        count = count + 1
    Next
    Assert(count = 0)
End Test

Type GcField
    Field other.GcField
    Field tag
End Type

; An object referenced only through another live object's field stays alive and
; readable as long as that owner is reachable.
Test testFieldReferencedObjectStaysAlive()
    Local container.GcField = new GcField()
    container\tag = 100
    container\other = new GcField()
    container\other\tag = 200

    Local count = 0
    For obj.GcField = Each GcField
        count = count + 1
    Next
    Assert(count = 2)
    Assert(container\other\tag = 200)
End Test
