Strict
EnableGC

; Deliberate runtime integer divide-by-zero. Used by test.bat / test.sh to verify
; the Windows structured-exception translator (seTranslator in bbruntime_dll.cpp)
; labels the fault correctly as "Integer divide by zero" -- not the pre-fix
; mislabel "Stack overflow!" caused by missing `break`s in its switch.
;
; This program PANICS and exits non-zero by design, so it lives OUTSIDE tests/
; (it must not run in the `blitzcc -t` suite loop) and outside samples/tutorials/
; games/ (the corpus compile-sweep). The harness invokes it explicitly with -t and
; asserts the panic message.
;
; The divisor is a variable so the divide is not constant-folded at compile time.
Test divideByZeroFault()
	Local denom = 0
	Local result = 10 / denom
	Assert( result = 0 )
End Test
