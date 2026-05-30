# Working note — Math library + numeric/string conversion test coverage

Branch: `test/math-conversion-coverage`
Type: Test coverage

## Goal (restated)

`tests/MathsTest.bb` is named for math but tests only `+ - * /` (compiler codegen).
Every function actually in `src/blitzrc/bbruntime/bbmath.cpp` — `Sin Cos Tan ASin
ACos ATan ATan2 Sqr Floor Ceil Exp Log Log10 Rnd Rand SeedRnd RndSeed` — has zero
coverage, and so do the `Str`/`Int`/`Float` conversion paths (`_bbIntToStr`/itoa,
`_bbStrToInt`/atoi, `_bbStrToFloat`/atof, `_bbFloatToStr`/ftoa). These pin two
regression-prone, non-obvious contracts that matter for downstream reproducibility:
- **Degree-based trig** (`bbmath.cpp:14-20`): `Sin(90)=1`, not radians. A future
  "fix" to radians would silently break every Blitz program.
- **Deterministic PRNG** (`bbmath.cpp:24-50`): fixed Lehmer LCG; `SeedRnd(s)` makes
  the stream reproducible; `SeedRnd(0)` maps to state 1 (so `≡ SeedRnd(1)`);
  `Rand(from,to)` swaps reversed args and is inclusive. rcce2 relies on seeded
  reproducibility.

Add executable `blitzcc -t` coverage that locks these contracts in.

## Approach (in order)

1. **Expand `tests/MathsTest.bb`** (keep the 4 arithmetic tests; add math-library
   `Test` blocks): degree trig with epsilon tolerance (`Sin(0)=0`, `Sin(90)≈1`,
   `Cos(0)=1`, `Cos(90)≈0`, `Tan(45)≈1`, inverse round-trips `ASin(1)≈90`,
   `ACos(0)≈90`, `ATan(1)≈45`, `ATan2(1,1)≈45`); exact-where-exact (`Sqr(9)=3`,
   `Floor(2.7)=2`, `Floor(-2.1)=-3`, `Ceil(2.1)=3`, `Ceil(-2.9)=-2`, `Exp(0)=1`,
   `Log(1)=0`, `Log10(1000)≈3`); PRNG determinism.
2. **PRNG strategy** — prefer *reproduction-based* asserts (seed, draw a sequence,
   re-seed the same value, assert the sequence repeats) so the test is robust and
   cross-platform; ADD a few *frozen* `Rand` literals captured from a real Windows
   run to pin the actual algorithm (the integer LCG is bit-reproducible across
   platforms, so frozen `Rand` values double as a cross-platform contract). Also:
   `SeedRnd(0)` stream == `SeedRnd(1)` stream; `Rand(5,5)=5`; `Rand(6,1)` ∈ [1,6]
   (swap path); `Rnd(0,1)` ∈ (0,1).
3. **New `tests/ConversionTest.bb`** — `Str(int)`, `Int(str)` (`Int("42")=42`,
   `Int("12abc")=12`, `Int("")=0`), `Float(str)` (`Float("3.14")≈3.14`), and a few
   `Str(float)` values **captured empirically** from ftoa (not guessed).
4. Float comparisons use an `Abs(a-b) < eps` helper (`Assert` takes a bool; exact
   float equality is fragile). Integer/`Sqr(9)`/`Floor`/`Ceil` asserted exactly.

## Files touched

- `tests/MathsTest.bb` (expand)
- `tests/ConversionTest.bb` (new)
- `docs/notes/math-conversion-coverage.md` (this note)

Test-only. No `src/` changes. If a test surfaces a real bug, file it separately
(do not fix here).

## Acceptance criteria (Artifact B = the tests, validated by running them)

- `MathsTest.bb` covers all 17 `bbmath.cpp` functions; `ConversionTest.bb` covers
  `Str`/`Int`/`Float`. Both run under `blitzcc -t` and pass on Windows (`test.bat`
  exit 0).
- A dedicated block asserts the **degree-trig** contract and another asserts
  **`SeedRnd` reproducibility** (+ the `SeedRnd(0)==SeedRnd(1)` remap).
- Every pinned literal/float expectation is captured from a real run, not guessed.
- The corpus sweep and all other suites remain green.

## Trade-offs / rejected

- **Rejected: rewrite MathsTest from scratch** — keep the existing arithmetic tests
  (no coverage lost), just add to them.
- **Rejected this iteration: the `seTranslator` missing-`break` bug** (recon agent 2).
  Real, confirmed, tiny — but asserting an SEH panic message from inside the `-t`
  harness is awkward (a panic exits the process). Deferred as the top Bug candidate;
  its verification needs a manual crash-repro rather than a Test block.
- Frozen-literal RNG values risk locking in a buggy stream — mitigated by also using
  reproduction-based asserts and reviewing values against the documented LCG.

## Deferred follow-ups

- `seTranslator` missing-`break` fix (Bug/Reliability), with a manual-repro
  verification approach.
- Banks / type-list / stream edge-case coverage.
