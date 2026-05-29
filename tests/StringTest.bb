Strict
EnableGC

; Acceptance suite for the string standard library.
; Each block proves one documented contract with exact literal expectations.
; No block performs an operation documented to potentially hang.

; --- Replace: empty `from` must terminate and return s unchanged (criterion 1) ---

Test ReplaceEmptyFromTest()
    Assert( Replace("hello", "", "x") = "hello" )
    Assert( Replace("hello", "", "") = "hello" )
    ; Empty source string with empty from must also terminate and return unchanged.
    Assert( Replace("", "", "x") = "" )
End Test

; --- Replace: normal match / no match (criterion 2) ---

Test ReplaceMatchTest()
    ; Every occurrence replaced.
    Assert( Replace("aaa", "a", "b") = "bbb" )
    Assert( Replace("hello world", "o", "0") = "hell0 w0rld" )
    Assert( Replace("ababab", "ab", "X") = "XXX" )
End Test

Test ReplaceNoMatchTest()
    Assert( Replace("hello", "z", "Q") = "hello" )
    Assert( Replace("hello", "xyz", "Q") = "hello" )
End Test

; --- Left / Right / Mid boundaries (criterion 3) ---

Test LeftBoundaryTest()
    Assert( Left("abc", 2) = "ab" )
    Assert( Left("abc", 3) = "abc" )
    ; n past end returns whole string.
    Assert( Left("abc", 10) = "abc" )
    Assert( Left("abc", 0) = "" )
End Test

Test RightBoundaryTest()
    Assert( Right("abc", 2) = "bc" )
    Assert( Right("abc", 3) = "abc" )
    ; n past end returns whole string.
    Assert( Right("abc", 10) = "abc" )
    Assert( Right("abc", 0) = "" )
End Test

Test MidBoundaryTest()
    ; Default count (-1) means "to end of string".
    Assert( Mid("hello", 2) = "ello" )
    Assert( Mid("hello", 1) = "hello" )
    ; Explicit count.
    Assert( Mid("hello", 2, 3) = "ell" )
    ; start past the end returns "".
    Assert( Mid("hi", 10, 5) = "" )
    ; count past end clamps to remaining chars.
    Assert( Mid("hello", 3, 99) = "llo" )
End Test

; --- Instr: found / not-found / from past end (criterion 4) ---

Test InstrFoundTest()
    Assert( Instr("hello", "l") = 3 )
    Assert( Instr("hello", "hello") = 1 )
    Assert( Instr("hello", "o") = 5 )
End Test

Test InstrNotFoundTest()
    Assert( Instr("hello", "z") = 0 )
End Test

Test InstrFromTest()
    ; from skips earlier matches.
    Assert( Instr("hello", "l", 4) = 4 )
    ; from past end returns 0.
    Assert( Instr("hello", "o", 99) = 0 )
End Test

; --- Trim (criterion 5) ---

Test TrimTest()
    Assert( Trim("  hello  ") = "hello" )
    Assert( Trim("hello") = "hello" )
    ; Tabs and other non-graphical chars are whitespace.
    Assert( Trim( Chr(9) + "hello" + Chr(9) ) = "hello" )
    ; whitespace-only string trims to empty.
    Assert( Trim("    ") = "" )
End Test

; --- Upper / Lower (criterion 6) ---

Test UpperLowerTest()
    Assert( Upper("Hello World") = "HELLO WORLD" )
    Assert( Lower("Hello World") = "hello world" )
    ; Non-alpha chars are left untouched.
    Assert( Upper("abc123") = "ABC123" )
    Assert( Lower("ABC123") = "abc123" )
End Test

; --- LSet / RSet truncate + pad (criterion 6) ---

Test LSetTest()
    ; pad shorter with trailing spaces.
    Assert( LSet("ab", 5) = "ab   " )
    ; truncate longer.
    Assert( LSet("abcdef", 3) = "abc" )
    ; exact width unchanged.
    Assert( LSet("abc", 3) = "abc" )
End Test

Test RSetTest()
    ; pad shorter with leading spaces.
    Assert( RSet("ab", 5) = "   ab" )
    ; truncate longer, keeping rightmost.
    Assert( RSet("abcdef", 3) = "def" )
    ; exact width unchanged.
    Assert( RSet("abc", 3) = "abc" )
End Test

; --- Chr / Asc, including Asc("") = -1 (criterion 6) ---

Test ChrAscTest()
    Assert( Chr(65) = "A" )
    Assert( Asc("A") = 65 )
    ; Asc of first char only.
    Assert( Asc("ABC") = 65 )
    ; round-trip.
    Assert( Asc( Chr(97) ) = 97 )
    ; empty string returns -1.
    Assert( Asc("") = -1 )
End Test

; --- Len (criterion 6) ---

Test LenTest()
    Assert( Len("hello") = 5 )
    Assert( Len("") = 0 )
    Assert( Len(" ") = 1 )
End Test

; --- Hex (criterion 6) ---

Test HexTest()
    Assert( Hex(255) = "000000FF" )
    Assert( Hex(0) = "00000000" )
    Assert( Hex(16) = "00000010" )
End Test

; --- Bin (criterion 6) ---

Test BinTest()
    Assert( Len( Bin(5) ) = 32 )
    Assert( Right( Bin(5), 4 ) = "0101" )
    Assert( Bin(0) = "00000000000000000000000000000000" )
    Assert( Right( Bin(1), 1 ) = "1" )
End Test
