# Working note — Scientific-notation float literals (`1.5e10`, `1e30`)

Branch: `feat/scientific-notation-float-literals`
Type: Feature / Alignment (drop-in compatibility, INTENT #1)

## Goal (restated)

BlitzForge's lexer has no exponent-notation float literal. `1.5e10` lexes as
`FLOATCONST(1.5)` followed by `IDENT(e10)`, which the parser then treats as a
function call → `Function 'e10' not found`. The error points at a phantom function
and gives no hint the real problem is unsupported literal syntax. A real downstream
consumer (rcce2) was blocked by `Local huge# = 1.0e30` and had to rewrite every
constant. Add exponent notation to the tokenizer so `1.5e10`, `2.0e-3`, `1.0e+2`,
and `1e6` parse as the mathematically correct float.

## Root cause (verified)

`src/blitzrc/compiler/toker.cpp:217-231` — three numeric paths:
- `.`-leading float (`.5`) — line 217-220
- digit-leading, with optional dot (`1`, `1.5`) — line 222-230: emits `INTCONST`
  if no dot, `FLOATCONST` if a dot follows.

None of the three scans for an `[eE][+-]?digits` exponent suffix. The value pipeline
is already exponent-correct: `FloatConstNode` converts via `atof()`
(`parser.cpp:1106`, `exprnode.cpp:385`), and `atof("1.5e10")`/`atof("1e30")` are
correct. So the fix is purely tokenization: make the token *span* the exponent and
ensure the token type is `FLOATCONST` (so it routes to `atof`, not `atoi`).

## Approach (specific, in order)

1. Add a small local helper inside the tokenize loop in `toker.cpp`:
   `tryExponent(k)` — given `k` pointing just past the mantissa digits, if
   `line[k]` is `e`/`E` AND (the next char is a digit, OR is `+`/`-` followed by a
   digit), advance `k` past the whole `[eE][+-]?digits` run and return the new `k`;
   otherwise return `k` unchanged (conservative: consume the exponent ONLY on a full
   valid match).
2. `.`-leading float path (217-220): after the fractional-digit scan, call
   `tryExponent` before emitting `FLOATCONST`. (`.5e3`)
3. digit-leading path (222-230):
   - with-dot branch (224-227): after fractional digits, call `tryExponent`, emit
     `FLOATCONST`. (`1.5e3`)
   - no-dot branch (229): before emitting `INTCONST`, probe `tryExponent`. If it
     consumed an exponent, emit `FLOATCONST` instead (PROMOTE `1e6` → float);
     otherwise emit `INTCONST` unchanged. (`1e6`)
4. No change to parser, AST, codegen, or runtime — the token reaches codegen as an
   already-`atof`'d float constant.

## Files touched

- `src/blitzrc/compiler/toker.cpp` — the tokenize loop (numeric paths).
- `tests/MathsTest.bb` — new `Test` blocks (positive exponent cases + conservative
  guard cases), using the existing `FloatClose` epsilon helper.
- `help/language/lang_ref_basicdatatypes.html` — document exponent notation.
- `docs/notes/sci-notation-floats.md` — this note.

## Design decisions

- **Promote the no-dot form `1e30` to float.** It's the more common form users type;
  leaving it out creates a second sharp edge. It is safe: an identifier cannot start
  with a digit, so `1e30` can ONLY currently lex as `INTCONST(1)` + `IDENT(e30)`,
  which is *always* a parse error today (int immediately followed by identifier, no
  operator). Nothing valid depends on the current behavior.
- **Conservative exponent match.** Consume the `e`/`E` ONLY when a full
  `[eE][+-]?digit` follows. `1.0e`, `1.0eq`, `1.0e+` (no digit after sign) keep the
  current behavior: `FLOATCONST(1.0)` + identifier. This preserves the existing
  number-adjacent-identifier semantics and is the key regression guard.

## Migration / rollback

No migration. Purely additive — every input that newly compiles was previously a
hard compile error, so no behavior changes for any program that compiles today.
Rollback = revert the toker.cpp hunk.

## Trade-offs considered and rejected

- **Dot-form only, defer `1e30`.** Rejected: same change site, same risk profile,
  and the no-dot form is the common case — splitting it leaves a documented second
  sharp edge for no benefit.
- **Hex/binary exponents, float suffixes (`f`).** Out of scope — not part of the
  reported gap, not classic-Blitz syntax.

## Acceptance criteria

- `1.5e3`, `2.0e-3`, `1.0e+2`, `1e6`, `.5e2` compile and evaluate to the correct
  float (pinned by `Assert(FloatClose(...))` in `MathsTest.bb`).
- Conservative guard: `1.0e` / `1.0eq` still tokenize as `1.0` + identifier (proven
  by a Test that uses a variable/function whose name starts with `e` adjacent to a
  float, or a CLI negative case).
- `blitzcc -t tests/MathsTest.bb` passes; full `test.bat` green.
- `lang_ref_basicdatatypes.html` documents exponent notation with an example.
- Corpus sweep (`scripts/sweep_corpus.sh`) shows no new failures.

## Deferred follow-ups

- `Release`/`Recast`/`Reference` contextual-keyword Phase 2 (parser disambiguation) —
  still the top legacy-compat backlog item.
- `gxSoundSample::load` short-filename crash (not CI-testable).
