Strict
EnableGC

Global currentDir$ = CurrentDir$()

Function DeleteTestDirAndFiles()
    DeleteFile(currentDir$ + "testfile.txt")
    DeleteFile(currentDir$ + "testfile2.txt")
    DeleteFile(currentDir$ + "testdir/subfile.txt")
    DeleteFile(currentDir$ + "testdir2/subfile2.txt")
    DeleteFile(currentDir$ + "testdir/subdir/subfile.txt")
    DeleteFile(currentDir$ + "testdir2/subdir/subfile.txt")
    DeleteDir(currentDir$ + "testdir/subdir")
    DeleteDir(currentDir$ + "testdir2/subdir")
    DeleteDir(currentDir$ + "testdir")
    DeleteDir(currentDir$ + "testdir2")
End Function

Test testCreateDir()
    CreateDir(currentDir$ + "testdir")
    Assert(FileType(currentDir$ + "testdir") = 2)
    DeleteTestDirAndFiles()
End Test

Test testCreateFile()
    local file.BBStream = WriteFile(currentDir$ + "testfile.txt")
    CloseFile(file)
    Assert(FileType(currentDir$ + "testfile.txt") = 1)
    DeleteTestDirAndFiles()
End Test

Test testCopyDir()
    CreateDir(currentDir$ + "testdir")
    CopyDir(currentDir$ + "testdir", currentDir$ + "testdir2")
    Assert(FileType(currentDir$ + "testdir2") = 2)
    DeleteTestDirAndFiles()
End Test

Test testCopyFile()
    local file.BBStream = WriteFile(currentDir$ + "testfile.txt")
    CloseFile(file)
    CopyFile(currentDir$ + "testfile.txt", currentDir$ + "testfile2.txt")
    Assert(FileType(currentDir$ + "testfile2.txt") = 1)
    DeleteTestDirAndFiles()
End Test

Test testCopySubDir()
    CreateDir(currentDir$ + "testdir")
    CreateDir(currentDir$ + "testdir2")
    CreateDir(currentDir$ + "testdir/subdir")
    CreateDir(currentDir$ + "testdir2/subdir")
    CopyDir(currentDir$ + "testdir", currentDir$ + "testdir2")
    Assert(FileType(currentDir$ + "testdir2/subdir") = 2)
    DeleteTestDirAndFiles()
End Test

Test testCopySubFile()
    CreateDir(currentDir$ + "testdir")
    CreateDir(currentDir$ + "testdir2")
    local file.BBStream = WriteFile(currentDir$ + "testdir/subfile.txt")
    CloseFile(file)
    CopyFile(currentDir$ + "testdir/subfile.txt", currentDir$ + "testdir2/subfile2.txt")
    Assert(FileType(currentDir$ + "testdir2/subfile2.txt") = 1)
    DeleteTestDirAndFiles()
End Test

Test testCopySubDirWithFile()
    CreateDir(currentDir$ + "testdir")
    CreateDir(currentDir$ + "testdir2")
    CreateDir(currentDir$ + "testdir/subdir")
    CreateDir(currentDir$ + "testdir2/subdir")
    local file.BBStream = WriteFile(currentDir$ + "testdir/subdir/subfile.txt")
    CloseFile(file)
    CopyDir(currentDir$ + "testdir", currentDir$ + "testdir2")
    Assert(FileType(currentDir$ + "testdir2/subdir/subfile.txt") = 1)
    DeleteTestDirAndFiles()
End Test
