Strict
EnableGC

; Regression tests for the bbBank / bbCopyBank bounds fixes shipped in
; the EE/GG/HH wave-1 PR. The original code did signed offset checks
; against `b->size` and computed `src_p + count - 1` for CopyBank,
; which meant:
;
;   * negative offsets trivially passed `offset >= b->size` (they were
;     less than size, the check was the wrong direction) and then
;     dereferenced outside the bank
;   * a negative count produced a wrapped huge size_t inside memmove
;     and copied gigabytes of memory -- arbitrary read/write from
;     inside a Blitz program
;
; These tests exercise the count <= 0 branch of CopyBank, which is
; the path that's safely observable from Blitz (negative-offset Peek
; / Poke would RTEX in the new code and crash the test runner).

Test testCopyBankCountZeroIsNoOp()
	Local src.BBBank = CreateBank(8)
	Local dest.BBBank = CreateBank(8)

	; Seed src with 0..7
	Local i
	For i = 0 To 7
		PokeByte src, i, i
	Next
	; Seed dest with all 0xFF
	For i = 0 To 7
		PokeByte dest, i, 255
	Next

	CopyBank src, 0, dest, 0, 0

	; dest should still be all 0xFF
	For i = 0 To 7
		Assert(PeekByte(dest, i) = 255)
	Next
End Test

Test testCopyBankNegativeCountIsNoOp()
	Local src.BBBank = CreateBank(8)
	Local dest.BBBank = CreateBank(8)

	Local i
	For i = 0 To 7
		PokeByte src, i, i
		PokeByte dest, i, 255
	Next

	; Pre-fix: negative count cast to size_t inside memmove and copied
	; gigabytes (arbitrary read/write). Post-fix: returns silently.
	CopyBank src, 0, dest, 0, -1

	For i = 0 To 7
		Assert(PeekByte(dest, i) = 255)
	Next
End Test

Test testCopyBankValidCopyStillWorks()
	; Sanity check that the new count guard doesn't break the happy path.
	Local src.BBBank = CreateBank(4)
	Local dest.BBBank = CreateBank(4)

	PokeByte src, 0, 1
	PokeByte src, 1, 2
	PokeByte src, 2, 3
	PokeByte src, 3, 4

	CopyBank src, 0, dest, 0, 4

	Assert(PeekByte(dest, 0) = 1)
	Assert(PeekByte(dest, 1) = 2)
	Assert(PeekByte(dest, 2) = 3)
	Assert(PeekByte(dest, 3) = 4)
End Test

; --- ReadBytes / WriteBytes bounds (the unfinished tail of the bank audit) ---
; These passed a caller-supplied count straight to the stream layer after only a
; single composite offset+count-1 check: a negative count widened to a huge
; streamsize (arbitrary OOB read/write), and a negative offset with a compensating
; count passed the lone check. Now guarded like CopyBank (count<=0 + independent
; start/end checks). The blocks completing is the regression guard -- pre-fix the
; negative-count transfer drove an out-of-bounds memory access.

Global bytesDir$ = CurrentDir$()

Test testWriteReadBytesRoundTrip()
	Local src.BBBank = CreateBank(4)
	PokeByte src, 0, 11
	PokeByte src, 1, 22
	PokeByte src, 2, 33
	PokeByte src, 3, 44

	Local out.BBStream = WriteFile(bytesDir$ + "bytetest.dat")
	WriteBytes src, out, 0, 4
	CloseFile out

	Local dest.BBBank = CreateBank(4)
	Local inp.BBStream = ReadFile(bytesDir$ + "bytetest.dat")
	ReadBytes dest, inp, 0, 4
	CloseFile inp

	Assert(PeekByte(dest, 0) = 11)
	Assert(PeekByte(dest, 1) = 22)
	Assert(PeekByte(dest, 2) = 33)
	Assert(PeekByte(dest, 3) = 44)

	DeleteFile(bytesDir$ + "bytetest.dat")
End Test

Test testWriteBytesNegativeCountIsNoOp()
	Local src.BBBank = CreateBank(8)
	Local i
	For i = 0 To 7
		PokeByte src, i, i
	Next

	Local out.BBStream = WriteFile(bytesDir$ + "byteneg.dat")
	; Pre-fix: count=-1 widened to a huge size in the stream write -> OOB read of src.
	WriteBytes src, out, 0, -1
	CloseFile out

	; Nothing was written.
	Assert(FileSize(bytesDir$ + "byteneg.dat") = 0)

	DeleteFile(bytesDir$ + "byteneg.dat")
End Test

Test testReadBytesNegativeCountIsNoOp()
	; Seed a file with 8 known bytes.
	Local src.BBBank = CreateBank(8)
	Local i
	For i = 0 To 7
		PokeByte src, i, 7
	Next
	Local out.BBStream = WriteFile(bytesDir$ + "byteneg2.dat")
	WriteBytes src, out, 0, 8
	CloseFile out

	; Fill dest with a sentinel, then ReadBytes with a negative count.
	Local dest.BBBank = CreateBank(8)
	For i = 0 To 7
		PokeByte dest, i, 255
	Next
	Local inp.BBStream = ReadFile(bytesDir$ + "byteneg2.dat")
	; Pre-fix: count=-1 -> OOB write into dest / crash.
	ReadBytes dest, inp, 0, -1
	CloseFile inp

	; dest is unchanged.
	For i = 0 To 7
		Assert(PeekByte(dest, i) = 255)
	Next

	DeleteFile(bytesDir$ + "byteneg2.dat")
End Test
