# Working note — Contextual `Test` keyword (legacy-identifier compatibility, Phase 1)

Branch: `align/legacy-test-keyword-identifier`
Type: Feature / Alignment (drop-in source compatibility, INTENT #1)

## Goal (restated)

BlitzForge reserves its additive keywords globally in the tokenizer, so legacy
Blitz3D programs that use those words as ordinary identifiers fail to compile —
violating INTENT value #1 ("Drop-in source compatibility… Always"). This is the
corpus allowlist's Class A and has been deferred 4×; this iteration commits to it
with a de-risked **Phase 1: make `Test` contextual** (the dominant collision).

`Test` is the keyword ONLY as a statement-start block opener (`Test <ident>(` …
`End Test`). Used any other way — assignment target, call, parameter name, type
tag, or expression operand — it must be an ordinary identifier.

Phase 1 clears 4 of the 6 Class A allowlist entries (the `test=…` /
`test.Timer` files). Deferred: `Release` (expression-position keyword — needs
`parseUniExpr` lookahead), `object` (a *legacy* Blitz3D keyword, not a fork
addition — out of scope), `Method`/others.

## Approach (3 unified identifier-consumption sites in `parser.cpp`)

1. **Statement dispatch** (~line 153): before `switch`, compute
   `stmtTok = curr(); if (stmtTok==TEST && !(scope==STMTS_PROG && lookAhead(1)==IDENT)) stmtTok=IDENT;`
   and `switch(stmtTok)`. So `test=100` / `test\f=…` / `test(…)` route to the
   existing `case IDENT` statement logic (which reads `toker->text()` = "test"),
   while `Test foo() … End Test` still hits `case TEST`.
2. **`parseIdent()`** (line 65): accept `TEST` as an identifier
   (`curr()!=IDENT && curr()!=TEST`). Covers parameter names (`test.Timer`),
   `Local/Global/Field test`, type tags, labels — positions where `Test` can never
   be the block keyword (the dispatcher already consumed it if it were).
3. **Expression primary** (~line 1123): `case TEST:` falls through to `case IDENT:`
   so `test` is a valid operand (`x = test + 1`, `Assert(test = 42)`).

No tokenizer change. No new keyword removed from `makeKeywords()` (so `Test`/
`End Test` stay reserved tokens — they're just now also accepted as identifiers
where a block opener is impossible).

## Files touched

- `src/blitzrc/compiler/parser.cpp` — the 3 sites above.
- `tests/LegacyKeywordIdentifierTest.bb` — new: `test` as local/assignment/operand/param,
  alongside the `Test … End Test` block still working.
- `scripts/corpus_compile_allowlist.txt` — remove the 4 `Test`-collision Class A
  entries **only after** the sweep confirms each compiles cleanly.
- `docs/notes/legacy-test-keyword-identifier.md` — this note.

## Acceptance criteria (Artifact B)

- `printf 'test=100\nEnd\n' | blitzcc -c +q` compiles (currently "Expecting identifier").
- The 4 `Test`-collision samples compile under the corpus sweep; their allowlist
  lines are removed and `sweep_corpus.sh` stays green (0 new-fail).
- New `tests/LegacyKeywordIdentifierTest.bb` passes under `blitzcc -t`: `test` works
  as a local, assignment target, expression operand, and function parameter; a
  `Test … End Test` block in the same file still parses and runs.
- The ENTIRE existing `tests/*.bb` suite still passes (every file uses `Test`/
  `End Test` — maximal regression net). `test.bat` green; build 0 errors.

## Trade-offs / rejected

- **Phase 1 = `Test` only.** `Release` (spindisc.bb) is an expression-position
  keyword needing `parseUniExpr` disambiguation → Phase 2. `object` (CraftFlare.bb)
  is a legacy Blitz3D keyword, not a fork regression → out of scope. `Method` can
  silently terminate the method-parse loop if softened → deferred.
- **Known residual (documented):** a legacy *bare call* `test foo` (function call
  without parens, arg `foo`) at statement start would still be read as a block
  opener (`TEST` followed by IDENT). Not present in the corpus; statement-call of a
  function literally named `test` with a bare identifier arg is vanishingly rare.
- **Mechanism: parser-site acceptance, not a tokenizer rewrite** — keeps `Test`/
  `End Test` reserved and the block grammar intact; lowest blast radius.

## Deferred follow-ups (Phase 2+)

- `Release`/`Recast`/`Reference`/`Assert`/`Ptr` — expression-position contextual
  keywords (need `parseUniExpr` + the `Assert` token pre-scan handled).
- `Method`/`End Method` contextual (subtle: method-loop termination).
- `object` is legacy-keyword behavior, not in scope.
