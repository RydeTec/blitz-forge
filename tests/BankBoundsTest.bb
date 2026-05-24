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
