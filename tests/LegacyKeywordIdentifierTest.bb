Strict
EnableGC

; Drop-in compatibility: 'Test' is one of BlitzForge's added keywords, but legacy
; Blitz3D programs use `test` as an ordinary variable/parameter name. It must work
; as an identifier everywhere it is not the test-block opener (`Test <name>()`),
; while the test-block syntax (which this very file relies on) still parses.

Type LegacyHolder
	Field value
End Type

; 'test' as a function parameter name, used as an expression operand in the body.
Function withTestParam( test )
	Return test + 1
End Function

Test legacyTestAsLocalAndAssignment()
	; Local declaration named `test`, statement-start assignment, and use as an
	; expression operand -- all positions that previously tokenized as the TEST
	; keyword and failed with "Expecting identifier".
	Local test = 41
	test = test + 1
	Assert( test = 42 )
End Test

Test legacyTestAsParameter()
	Assert( withTestParam( 41 ) = 42 )
End Test

Test legacyTestFieldAccess()
	; `test` as a type-instance variable plus field access on it.
	Local test.LegacyHolder = New LegacyHolder()
	test\value = 7
	Assert( test\value = 7 )
End Test
