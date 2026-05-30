# Working note — Bounds-check the BBList runtime API

Branch: `fix/bblist-bounds-checking`
Type: Bug / Reliability

## Goal (restated)

The BBList API (`_bbVector*` in `src/blitzrc/bbruntime/basic.cpp`, exposed as
`ListAt`/`ListInsert`/`ListRemove`/`ListReplace`/`ListFirst`/`ListLast`) does zero
index/emptiness validation on caller-supplied indices. From ordinary Blitz code:
- `ListInsert`/`ListRemove`/`ListReplace` compute `begin()+idx` with no range check
  → invalid iterator → **undefined behaviour / heap corruption**.
- `ListFirst`/`ListLast` call `front()`/`back()` on a possibly-empty vector → UB.
- `ListAt` calls `vec.at(idx)`, whose `std::out_of_range` escapes to the top-level
  `bbruntime_run` catch and **terminates the whole program** with an STL string,
  not a Blitz RuntimeError — and with no release-mode soft-recovery.

Every other stdlib module (bank, stream, file) funnels bad input through the
established `debug ? RTEX(msg) : errorLog.push_back(prefix+msg); return false`
idiom (see `bbbank.cpp` `debugBank`). BBList — a modern BlitzForge API that rcce2
leans on — is the lone exception. This contradicts INTENT #6 ("Trust the runtime")
and the "bounds checks … always worth doing" goal.

## Approach (in order)

1. Add two inline guards in `basic.cpp` mirroring `debugBank`:
   - `listIndexOk(vec, idx, who)` — rejects `idx < 0 || idx >= size()`.
   - `listNotEmpty(vec, who)` — rejects `empty()`.
   Each: `debug ? RTEX(...) : errorLog.push_back(who+": ...")`; returns bool.
2. Apply:
   - `_bbVectorAt`: `if(!listIndexOk(v,idx,"ListAt")) return 0;` then `(*v)[idx]`
     (drop the throwing `at()`).
   - `_bbVectorBack`/`_bbVectorFirst`: `if(!listNotEmpty(...)) return 0;`.
   - `_bbVectorRemove`/`_bbVectorReplace`: `if(!listIndexOk(...)) return;` at top
     (before the release/erase), so internal valid-index calls are unchanged.
   - `_bbVectorInsert`: allow the append position — reject only `idx<0 || idx>size()`
     (inline, since `listIndexOk` rejects `idx==size`).
3. Add bounds/empty `Test` blocks to `tests/ListTest.bb`.

## Why this is safe / behavior

- In `-t` mode `debug` is false (only `-d` sets it; `debug` == "debugger attached"),
  so bad-index ops take the `errorLog` path: **no-op + safe default, program
  continues**. That is what the new tests assert. In `-d` debug mode they raise a
  clean Blitz RuntimeError instead of UB.
- Happy-path behaviour is byte-identical (guards only add a leading check).
- The API is BlitzForge-specific → no legacy-Blitz3D compat risk.
- `errorLog` non-empty does not fail a test (pass/fail is Assert-driven), so the
  soft-fail tests pass.

## Files touched

- `src/blitzrc/bbruntime/basic.cpp` — two guards + 6 call sites.
- `tests/ListTest.bb` — bounds/empty coverage.
- `docs/notes/bblist-bounds-checking.md` — this note.

## Acceptance criteria (Artifact B)

- `ListAt`/`ListRemove`/`ListReplace` with `idx<0` or `idx>=ListSize`, and
  `ListFirst`/`ListLast` on an empty list, no longer crash: in `-t` they no-op /
  return 0, `ListSize` is unchanged, and a subsequent `Assert(True)` still runs
  (proving the process did not die — the pre-fix `ListAt` out-of-range would abort
  the whole run).
- `ListInsert` accepts `idx == ListSize` (append) and rejects `idx > ListSize`.
- All existing `ListTest.bb` happy-path blocks still pass unmodified.
- Full `test.bat` green; corpus sweep unaffected.

## Trade-offs / rejected

- **Negative-index "count from end"** semantics — that's a feature, not a fix. Out.
- **`_bbVectorClear` shadowed-`idx` / arg-count mismatch** (`basic.cpp:882`) — the
  rtSym declares one arg but the C fn takes two; the inner `for` shadows the unused
  param. Harmless, pre-existing; flagged, not fixed here.
- Rejected this iteration: the confirmed `seTranslator` missing-`break` fix
  (`bbruntime_dll.cpp`) and the compiler-diagnostic snippet UX — both good, lower
  impact than a reachable memory-safety defect; deferred (seTranslator verification
  path is now known: `-t` prints `Error: <msg>` to stdout).

## Deferred follow-ups

- `seTranslator` missing `break`s (Bug, trivial, verification path known).
- Human-readable compiler diagnostics (source line + caret, sanitize EOF byte).
