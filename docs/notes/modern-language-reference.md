# Working note — First-class language reference for BlitzForge's modern additions

Branch: `docs/modern-language-reference`
Type: Documentation (with a correctness fix to the Keywords page)

## Goal (restated)

The bundled HTML language reference under `help/language/` is 100% legacy Blitz3D
circa 2003. It documents none of the extensions that are the whole reason this
fork exists — the features INTENT.md's "discover the language has grown up"
promise is built on. Worse, `lang_ref_keywords.html` presents the legacy keyword
set as the *complete* list of reserved words ("may not be used as identifiers"),
which is now factually wrong: `toker.cpp` `makeKeywords()` reserves a dozen more.

Deliver first-class reference coverage of the modern surface, grounded in two
sources of truth: the tokenizer's keyword table (`toker.cpp`) for what's reserved,
and the passing test suite (`tests/*.bb`) for compilable example syntax.

## Scope (what ships)

1. **Fix `lang_ref_keywords.html`** — add a clearly-marked "BlitzForge additions"
   subsection listing the modern reserved words actually present in
   `toker.cpp` `alphaTokes`: `Continue, Method, End Method, Test, End Test, Assert,
   Recast, Release, Reference, Strict, NoTrace, DisableGC, EnableGC, Ptr`. Reword
   the lead so it no longer implies the legacy list is exhaustive.
2. **New page `lang_ref_modern.html`** — sections, each with a compilable example
   lifted from / consistent with the test that exercises it:
   - Strict mode (`StrictElseIfScopeTest.bb`, every test header)
   - Garbage collection: `EnableGC`/`DisableGC`/`NoTrace`, `RefCount` (`GarbageCollectionTest.bb`)
   - Type inheritance: `Type Child.Parent`, field inheritance, implicit down-cast (`InheritTest.bb`)
   - Methods: `Method`/`End Method`, `self\`, static `Type::m(inst,..)` + dynamic `inst\m(..)`, override, constructor `Method create.Type(..) .. Return self` (`MethodTest.bb`)
   - Recast: `Recast.Type(expr)` up-cast with runtime IS-A check returning `Null` (`InheritTest.bb`)
   - Continue (`LoopTest.bb`)
   - Function pointers: `@` sigil, `BBFunction`, `FunctionPtr()`, `Call(f, Ptr dto)`, DTO pattern (`FunctionPointerTest.bb`)
   - Async: `Async`/`Await`/`Poll`/`AsyncThen`, `BBThread` (`FunctionPointerTest.bb`)
   - Exceptions: `Throw`, `TryCatch(tryFn, catchFn, dto)`, DTO payload (`TryThrowCatchTest.bb`)
   - Pointers: `Ptr`, `BBPointer`, `@` typed locals (`PointerTest.bb`)
3. **Wire `lang_ref_modern.html` into `navbar.html`.**
4. **ReadMe.md** — only touch the `help/` reference description if it overstates
   coverage; keep minimal.

## Files touched

- `help/language/lang_ref_keywords.html` (edit)
- `help/language/lang_ref_modern.html` (new)
- `help/language/navbar.html` (edit)
- `ReadMe.md` (conditional, minimal)
- `docs/notes/modern-language-reference.md` (this note)

No change to anything under `src/`. Docs-only; cannot affect the compiler, runtime,
tests, or CI behavior.

## Acceptance criteria (Artifact B = verification, since docs have no unit tests)

- Every modern reserved word in `toker.cpp` `alphaTokes` (beyond the legacy set)
  appears on the Keywords page. Verified by diffing the page list against
  `makeKeywords()`.
- Each documented code example, extracted to a standalone `.bb`, compiles under
  the current `blitzcc` (verified by compiling scratch files in a temp dir — not
  committed). Snippets that are intentionally partial are clearly marked as
  fragments and a complete companion example is provided.
- `navbar.html` links the new page; the new page uses the existing `lang_ref.css`
  template (`<span class="Command">` header, `<p class="header">` sections,
  `<blockquote><i>` code blocks) so the frameset renders consistently.
- `test.bat` remains green (docs-only change; sanity check only).

## Trade-offs / rejected

- **Rejected: implement the legacy-identifier compatibility fix (recon pitch 3).**
  That is the highest-*alignment* problem (new keywords like `Test`/`Method`/
  `Release` are globally reserved and break legacy `.bb` programs + shipped
  samples, violating INTENT value #1). But the correct fix is contextual ("soft")
  keywords with per-site parser lookahead — `Release`/`Recast`/`Reference` appear
  in BOTH statement and expression positions (parser.cpp:483-506 and 1008-1026) —
  an M-effort, front-end-deep change with a 319-sample + modern-feature regression
  surface. Not deliverable at high confidence as a complete, safe increment in one
  iteration. Deferred with full evidence (see deferred follow-ups + memory).
- **Rejected: int-overflow guards (recon pitch 2).** `_bbAbs(INT_MIN)` is UB but
  wraps in practice; mostly theoretical, fuzzy acceptance semantics. Deferred.
- **Not converting the legacy frameset to a modern static site** — cosmetic churn,
  out of scope.
- **Not documenting the per-command runtime API** (graphics/audio/input) — separate,
  much larger increment.

## Deferred follow-ups

- **Legacy-identifier compatibility (HIGH priority, INTENT #1):** make BlitzForge's
  additive keywords contextual so legacy code using `test`/`release`/`method`/etc.
  as identifiers compiles. Repro: `printf 'test=100\nEnd\n' | blitzcc -c +q` →
  "Expecting identifier". Breaks `samples/Blitz 2D Samples/global.bb:20`,
  `fileio.bb:4`, `spindisc.bb:82`, `samples/Blitz 3D Samples/birdie/Mirror/mirror.bb:23`,
  `.../Death Island/deathisland.bb:29`. Root: `toker.cpp` reserves them globally.
- Wire `samples/` + `games/` corpus into a compile-only CI sweep (DevEx).
- Per-command runtime API reference pages.
