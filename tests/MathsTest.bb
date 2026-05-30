Strict
EnableGC

; --- Arithmetic operators (compiler codegen) ---

Test AdditionTest()
    Assert( 1 + 1 = 2 )
End Test

Test SubtractionTest()
    Assert( 2 - 1 = 1 )
End Test

Test MultiplicationTest()
    Assert( 2 * 2 = 4 )
End Test

Test DivisionTest()
    Assert( 4 / 2 = 2 )
End Test

; --- Math library (bbmath.cpp) ---
; Float helper: Assert takes a bool, and exact float equality is fragile, so
; compare within a small epsilon. Avoids Abs() to keep the type unambiguous.

Function FloatClose%( a#, b# )
    Local d# = a - b
    If d < 0.0 Then d = -d
    Return ( d < 0.0001 )
End Function

; Trig is DEGREE-based in Blitz (Sin(90)=1, not Sin(pi/2)). This is a deliberate,
; non-obvious contract; pin it so a future "fix" to radians is caught.
Test testDegreeTrig()
    Assert( FloatClose( Sin(0.0), 0.0 ) )
    Assert( FloatClose( Sin(90.0), 1.0 ) )
    Assert( FloatClose( Sin(30.0), 0.5 ) )
    Assert( FloatClose( Cos(0.0), 1.0 ) )
    Assert( FloatClose( Cos(90.0), 0.0 ) )
    Assert( FloatClose( Tan(45.0), 1.0 ) )
End Test

Test testInverseTrig()
    Assert( FloatClose( ASin(1.0), 90.0 ) )
    Assert( FloatClose( ACos(0.0), 90.0 ) )
    Assert( FloatClose( ATan(1.0), 45.0 ) )
    Assert( FloatClose( ATan2(1.0, 1.0), 45.0 ) )
End Test

Test testSqrFloorCeil()
    Assert( FloatClose( Sqr(9.0), 3.0 ) )
    Assert( FloatClose( Sqr(2.0), 1.41421 ) )
    Assert( Floor(2.7) = 2 )
    Assert( Floor(-2.1) = -3 )
    Assert( Ceil(2.1) = 3 )
    Assert( Ceil(-2.9) = -2 )
End Test

Test testExpLog()
    Assert( FloatClose( Exp(0.0), 1.0 ) )
    Assert( FloatClose( Log(1.0), 0.0 ) )
    Assert( FloatClose( Log10(1000.0), 3.0 ) )
End Test

; --- Deterministic PRNG (bbmath.cpp Lehmer LCG) ---

; Seeding makes the stream reproducible. rcce2 and seeded games depend on this.
Test testSeedRndReproducible()
    SeedRnd( 1234 )
    Local a1 = Rand( 1, 1000000 )
    Local a2 = Rand( 1, 1000000 )
    Local a3 = Rand( 1, 1000000 )
    SeedRnd( 1234 )
    Assert( Rand( 1, 1000000 ) = a1 )
    Assert( Rand( 1, 1000000 ) = a2 )
    Assert( Rand( 1, 1000000 ) = a3 )
End Test

; Frozen literals captured from the Windows build. The integer LCG is
; bit-reproducible across platforms, so these also serve as a cross-platform
; contract: any algorithm/seed drift changes them.
Test testRandKnownSequence()
    SeedRnd( 1234 )
    Assert( Rand( 1, 1000000 ) = 911355 )
    Assert( Rand( 1, 1000000 ) = 624215 )
    Assert( Rand( 1, 1000000 ) = 759072 )
End Test

; SeedRnd masks to 0x7fffffff and maps seed 0 to state 1, so SeedRnd(0) and
; SeedRnd(1) produce the same stream.
Test testSeedRndZeroMapsToOne()
    SeedRnd( 0 )
    Local a = Rand( 1, 1000000 )
    SeedRnd( 1 )
    Assert( Rand( 1, 1000000 ) = a )
End Test

; Rand is inclusive on both ends and swaps reversed arguments.
Test testRandRangeAndSwap()
    Assert( Rand( 5, 5 ) = 5 )
    SeedRnd( 42 )
    Local i, ok = True
    For i = 1 To 500
        Local r = Rand( 6, 1 )   ; reversed args -> normalized to [1,6]
        If r < 1 Or r > 6 Then ok = False
    Next
    Assert( ok )
End Test

; Rnd returns a float strictly within its range; Rnd(0,1) in (0,1).
Test testRndRange()
    SeedRnd( 99 )
    Local i, ok = True
    For i = 1 To 500
        Local r# = Rnd( 0.0, 1.0 )
        If r# <= 0.0 Or r# >= 1.0 Then ok = False
    Next
    Assert( ok )
End Test

; Pin Rnd's float path determinism directly (not just transitively via Rand):
; the same seed must reproduce the same float draws. Exact float equality is
; valid here because it's bit-identical operations from an identical state.
Test testRndReproducible()
    SeedRnd( 314 )
    Local b1# = Rnd( 0.0, 1.0 )
    Local b2# = Rnd( -5.0, 5.0 )
    SeedRnd( 314 )
    Assert( Rnd( 0.0, 1.0 ) = b1# )
    Assert( Rnd( -5.0, 5.0 ) = b2# )
End Test

; RndSeed reports the current generator state; after the same seed + draws it is
; reproducible.
Test testRndSeedState()
    SeedRnd( 7777 )
    Rand( 1, 100 ) : Rand( 1, 100 )
    Local s1 = RndSeed()
    SeedRnd( 7777 )
    Rand( 1, 100 ) : Rand( 1, 100 )
    Assert( RndSeed() = s1 )
End Test
