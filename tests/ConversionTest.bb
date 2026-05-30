Strict
EnableGC

; Numeric <-> string conversion contracts.
; Str(int)  -> itoa, Int(str) -> atoi, Float(str) -> atof, Str(float) -> ftoa.
; The atoi/atof contracts (partial parse, empty -> 0) and ftoa's formatting
; (trailing ".0", 6 significant digits) govern every number a Blitz program
; reads or prints, and were previously untested.

Test testIntToStr()
    Assert( Str(3) = "3" )
    Assert( Str(0) = "0" )
    Assert( Str(-42) = "-42" )
    Assert( Str(2147483647) = "2147483647" )
End Test

Test testStrToInt()
    Assert( Int("42") = 42 )
    Assert( Int("-7") = -7 )
    Assert( Int("0") = 0 )
    ; atoi parses the leading numeric prefix and stops at the first non-digit.
    Assert( Int("12abc") = 12 )
    ; non-numeric / empty parse to 0.
    Assert( Int("abc") = 0 )
    Assert( Int("") = 0 )
End Test

Test testStrToFloat()
    ; Float(str) is atof; compare within an epsilon.
    Local d# = Float("3.14") - 3.14
    If d < 0.0 Then d = -d
    Assert( d < 0.0001 )
    Assert( Float("0") = 0.0 )
    Assert( Float("10") = 10.0 )
    Assert( Float("") = 0.0 )
End Test

; Str(float) formatting (ftoa). Values captured from the Windows build; note the
; trailing ".0" on whole-valued floats (Str(1.0) = "1.0", not "1"). This pins the
; Windows ftoa formatting contract specifically; if/when the macOS runtime executes
; tests, float-string formatting may need a separate annotation if it diverges.
Test testFloatToStr()
    Assert( Str(1.5) = "1.5" )
    Assert( Str(0.5) = "0.5" )
    Assert( Str(1.0) = "1.0" )
    Assert( Str(100.0) = "100.0" )
    Assert( Str(3.14159) = "3.14159" )
End Test
