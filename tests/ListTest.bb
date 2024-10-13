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