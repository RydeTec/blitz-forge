Strict
EnableGC

Type DTOTest
    Field val
End Type

Test testListCreation()
    Local testArray.BBList = CreateList()
    Assert(NOT testArray = Null)

    FreeList(testArray)
End Test

Test testListAdd()
    Local testArray.BBList = CreateList()
    ListAdd(testArray, new DTOTest())
    Assert(ListSize(testArray) = 1)
    Assert(NOT ListIsEmpty(testArray))

    FreeList(testArray)
End Test

Test testListClear()
    Local testArray.BBList = CreateList()
    ListAdd(testArray, new DTOTest())
    Assert(ListSize(testArray) = 1)
    Assert(NOT ListIsEmpty(testArray))
    ListClear(testArray)
    Assert(ListIsEmpty(testArray))

    FreeList(testArray)
End Test

Test testListFind()
    Local testArray.BBList = CreateList()

    Local dto.DTOTest = new DTOTest()
    dto\val = 321

    ListAdd(testArray, dto)

    Local dto2.DTOTest = new DTOTest()
    dto2\val = 123

    ListAdd(testArray, dto2)

    Local idx = ListFind(testArray, dto)
    Local idx2 = ListFind(testArray, dto2)

    Assert(idx = 0)
    Assert(idx2 = 1)

    FreeList(testArray)
End Test

Test testListFirstLast()
    Local testArray.BBList = CreateList()

    Local dto.DTOTest = new DTOTest()
    dto\val = 321

    ListAdd(testArray, dto)

    Local dto2.DTOTest = new DTOTest()
    dto2\val = 123

    ListAdd(testArray, dto2)

    Local value.DTOTest = ListFirst(testArray)
    Assert(value\val = 321)

    Local value2.DTOTest = ListLast(testArray)
    Assert(value2\val = 123)

    FreeList(testArray)
End Test

Test testListInsertRemoveReplace()
    Local testArray.BBList = CreateList()

    Local dto1.DTOTest = new DTOTest()
    dto1\val = 1

    Local dto2.DTOTest = new DTOTest()
    dto2\val = 2

    Local dto3.DTOTest = new DTOTest()
    dto3\val = 3

    Local dto4.DTOTest = new DTOTest()
    dto4\val = 4

    Assert(ListIsEmpty(testArray))

    ListInsert(testArray, 0, dto1)

    Assert(ListSize(testArray) = 1)

    ListRemove(testArray, 0)

    Assert(ListIsEmpty(testArray))

    ListAdd(testArray, dto1)
    ListAdd(testArray, dto2)

    Assert(ListSize(testArray) = 2)

    ListInsert(testArray, 1, dto3)

    Assert(ListSize(testArray) = 3)

    ListReplace(testArray, 2, dto4)

    Assert(ListSize(testArray) = 3)

    Local dtoAt1.DTOTest = ListAt(testArray, 1)
    Assert(dtoAt1\val = dto3\val)

    Local dtoAt2.DTOTest = ListAt(testArray, 2)
    Assert(dtoAt2\val = dto4\val)

    FreeList(testArray)
End Test

Test testEmbeddedLists()
    Local testArray.BBList = CreateList()
    Local testArray2.BBList = CreateList()
    ListAdd(testArray, testArray2)

    Local recoveredList.BBList = ListFirst(testArray)
    ListAdd(recoveredList, new DTOTest())

    FreeList(testArray2)
    FreeList(testArray)
End Test

; --- Bounds / emptiness guards ---
; Out-of-range indices and empty-list accessors must NOT crash or corrupt memory.
; Pre-fix, ListAt out of range threw std::out_of_range and killed the whole run;
; Insert/Remove/Replace with a bad index dereferenced begin()+idx (UB); First/Last
; on an empty list called front()/back() (UB). In test mode they now no-op / return
; Null and the program keeps running. The trailing asserts only execute if the bad
; calls did not abort the process -- that is the regression guard.

Test testListAtOutOfRange()
    Local list.BBList = CreateList()
    ListAdd(list, new DTOTest())

    ; Out-of-range and negative indices return Null instead of crashing.
    Local hi.DTOTest = ListAt(list, 99)
    Assert(hi = Null)
    Local neg.DTOTest = ListAt(list, -1)
    Assert(neg = Null)

    ; Size is unchanged and the list is still usable.
    Assert(ListSize(list) = 1)

    FreeList(list)
End Test

Test testListFirstLastEmpty()
    Local list.BBList = CreateList()
    Assert(ListIsEmpty(list))

    ; front()/back() on an empty vector is UB; guarded to return Null.
    Local f.DTOTest = ListFirst(list)
    Assert(f = Null)
    Local l.DTOTest = ListLast(list)
    Assert(l = Null)

    ; Reached here => the empty-accessor calls did not abort the run.
    Assert(ListSize(list) = 0)

    FreeList(list)
End Test

Test testListRemoveReplaceOutOfRange()
    Local list.BBList = CreateList()
    Local a.DTOTest = new DTOTest()
    a\val = 7
    ListAdd(list, a)

    ; Out-of-range remove/replace are no-ops: size and contents unchanged.
    ListRemove(list, 99)
    ListRemove(list, -1)
    Assert(ListSize(list) = 1)

    ListReplace(list, 99, new DTOTest())
    ListReplace(list, -1, new DTOTest())
    Assert(ListSize(list) = 1)

    Local still.DTOTest = ListAt(list, 0)
    Assert(still\val = 7)

    FreeList(list)
End Test

Test testListInsertBoundary()
    Local list.BBList = CreateList()
    Local a.DTOTest = new DTOTest()
    a\val = 1
    ListAdd(list, a)

    ; idx == size is the valid append position.
    Local b.DTOTest = new DTOTest()
    b\val = 2
    ListInsert(list, 1, b)
    Assert(ListSize(list) = 2)
    Local appended.DTOTest = ListAt(list, 1)
    Assert(appended\val = 2)

    ; idx > size and negative idx are rejected (no-op).
    ListInsert(list, 99, new DTOTest())
    ListInsert(list, -1, new DTOTest())
    Assert(ListSize(list) = 2)

    FreeList(list)
End Test