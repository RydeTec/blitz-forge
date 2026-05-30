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

; --- 'Release' as a legacy identifier ---
; 'Release' is BlitzForge's GC release operator (`Release.Type <obj>`), but legacy
; Blitz3D programs use `release` as an ordinary variable/parameter name (e.g. the
; spindisc.bb `If release = 1` case). It must work as an identifier everywhere it
; is not the release operator, while `Release.Type <obj>` still parses and works.

Type ReleaseHolder
	Field value
End Type

Type ReleaseTarget
	Field var
End Type

; 'release' as a function parameter name, used as an expression operand in the body.
Function withReleaseParam( release )
	Return release + 1
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

Test legacyReleaseAsLocalAndAssignment()
	; Local declaration named `release`, statement-start assignment, and use as an
	; expression operand -- all positions that previously tokenized as the RELEASE
	; keyword and failed.
	Local release = 41
	release = release + 1
	Assert( release = 42 )
End Test

Test legacyReleaseInIfCondition()
	; The spindisc.bb real-world case: `release` as an operand in an If condition.
	Local release = 1
	Assert( release = 1 )
	If release = 1
		release = release + 1
	EndIf
	Assert( release = 2 )
End Test

Test legacyReleaseAsParameter()
	Assert( withReleaseParam( 41 ) = 42 )
End Test

Test legacyReleaseFieldAccess()
	; `release` as a type-instance variable plus field access on it.
	Local release.ReleaseHolder = New ReleaseHolder()
	release\value = 7
	Assert( release\value = 7 )
End Test

Test releaseOperatorStillWorks()
	; Positive: the GC operator `Release.Type <obj>` must still parse and run.
	; Mirrors GarbageCollectionTest's testRef/testRel: New gives RefCount 1, and
	; after Release the object is gone so RefCount(First ReleaseTarget) = 0.
	Local obj.ReleaseTarget = New ReleaseTarget()
	Assert( RefCount(First ReleaseTarget) = 1 )
	Release.ReleaseTarget obj
	Assert( RefCount(First ReleaseTarget) = 0 )
End Test
