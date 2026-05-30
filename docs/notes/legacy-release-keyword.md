# Working note — `Release` as a contextual keyword (legacy-identifier Phase 2)

Branch: `align/legacy-release-keyword-identifier`
Type: Feature / Alignment (drop-in source compatibility, INTENT #1)

## Goal (restated)

BlitzForge globally reserves `Release` in the tokenizer, so a classic Blitz3D
program using `release` as an ordinary variable fails to compile. `Release` is a
*fork addition* (stock Blitz3D had no such keyword — release was `x = Null` /
`Delete`), so `release`-as-identifier is a genuine fork-introduced regression
against INTENT #1. PR #77 made `Test` contextual the same way and explicitly named
`Release` as the deferred Phase 2; this delivers it.

Scope is `Release` ONLY. `Object` (the other deferred token) is a *base Blitz3D*
keyword (`Object.Type(handle)`), so `object`-as-identifier would not have compiled
in stock Blitz3D either — a different, riskier problem class, deferred for separate
investigation.

## Root cause / current behavior (verified)

- `toker.cpp:81` reserves `Release` → `RELEASE` (unconditional).
- `parser.cpp:518` statement `case RELEASE` passes through to `parseExpr` (so the
  keyword statement form `Release.Type x` is handled by the expression parser).
- `parser.cpp:1032` expression primary `case RELEASE` handles `Release[.]Type
  operand` → `ReleaseNode` (semant casts operand to BBPointer, calls `__bbRelease`).
- `parser.cpp:70` `parseIdent` rejects everything but `IDENT`/`TEST`, so `release`
  can't be a declared name.
- Repro: `samples/Blitz 2D Samples/spindisc.bb:82` `release=1` →
  "Expecting identifier near: = Got: =". (Confirmed `release` is its only blocker.)

The keyword form ALWAYS has an identifier immediately after `Release` (a type name,
with an optional `.`): `Release.Type operand` or `Release Type operand`. So:
**`Release` is the keyword iff the next token is `.` or IDENT; otherwise it is an
ordinary identifier** (`release=`, `release\f`, `release(`, `release[`, `If
release=1`, ...). This is the exact disambiguator, mirroring #77's `Test` lookahead.

## Approach (specific, in order) — mirrors PR #77

1. `parser.cpp:70` (`parseIdent`): accept `RELEASE` as an identifier (add
   `&& toker->curr()!=RELEASE`), so `release` works as a parameter / Local / Global /
   Field name and type-instance var. Update the comment.
2. `parser.cpp:162-163` (statement dispatch): after the `TEST` disambiguator add
   `if( stmtTok==RELEASE && !(toker->lookAhead(1)=='.' || toker->lookAhead(1)==IDENT) ) stmtTok=IDENT;`
   — routing `release = ...` / `release(...)` / `release\f` statements through the
   IDENT statement path (which reads `toker->text()` directly, so it handles the
   `release` spelling without further change). `Release.Type x` keeps `stmtTok==RELEASE`.
3. `parser.cpp:1032` expression primary: make the keyword handling conditional and
   fall through to the IDENT primary when it's an identifier use. Relocate the
   `case RELEASE:` body to sit immediately above `case TEST: case IDENT:` (line 1135)
   so the identifier path is a clean fall-through:
   ```cpp
   case RELEASE:
       if( toker->lookAhead(1)=='.' || toker->lookAhead(1)==IDENT ){
           if( toker->next()=='.' ) toker->next();
           t=parseIdent();
           result=parseUniExpr( false );
           result=d_new ReleaseNode( result,t );
           break;
       }
       // else fall through: 'release' is an ordinary identifier
   case TEST:
   case IDENT:
       ...
   ```
4. Remove the old standalone `case RELEASE:` at 1032.

No tokenizer change; `Release.Type` grammar and `ReleaseNode` semantics untouched.

## Files touched

- `src/blitzrc/compiler/parser.cpp` — 3 sites (parseIdent, statement dispatch,
  expression primary).
- `tests/LegacyKeywordIdentifierTest.bb` — `release` as identifier in every position
  + a POSITIVE `Release.Type` keyword test proving the operator still works.
- `scripts/corpus_compile_allowlist.txt` — remove the `spindisc.bb` line.
- `docs/notes/legacy-release-keyword.md` — this note.

## Migration / rollback

Additive compatibility — every newly-accepted `release`-as-identifier form was a
hard compile error before, so no program can depend on the old behavior. Rollback =
revert the parser hunks + restore the allowlist line.

## Trade-offs considered and rejected

- **Include `Object` too** (the recon majority): rejected this iteration — `Object`
  is a base-Blitz3D keyword, not a fork addition, so it's a different problem class
  needing its own investigation. Keeping scope to the clear-cut fork regression.
- **Tokenizer-level soft keyword / general framework**: rejected — over-engineering;
  the per-site `.`/IDENT lookahead is the proven #77 pattern.

## Acceptance criteria

- `printf 'release=1\nIf release=1 Then release=0\nEnd\n' | blitzcc -c +q` exits 0.
- `release` works as Local, assignment target, expression operand, field-typed var,
  and function parameter (pinned by `tests/LegacyKeywordIdentifierTest.bb`).
- The `Release.Type obj` GC operator still parses AND works (positive test:
  RefCount drops after release) — keyword not broken.
- `spindisc.bb` compiles under `blitzcc -c +q`; its allowlist line is removed and
  `scripts/sweep_corpus.sh` stays green (one fewer known-fail, 0 new-fail).
- Full `blitzcc -t tests/*.bb` green.

## Deferred follow-ups

- `Object` contextual-identifier investigation (base-keyword problem class).
- `Recast`/`Reference` are also expression-position keywords with the same grammar
  shape — no corpus collision, but the same pattern now applies if ever needed.
- Modern-features docs as a CI-gated executable contract + BBList docs (strong
  next-iteration candidate; best anti-pile-on pick with compounding value).
