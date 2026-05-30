# Working note — Fix seTranslator switch fall-through (mislabeled crash diagnostics)

Branch: `fix/setranslator-exception-labels`
Type: Bug / Reliability

## Goal (restated)

`seTranslator` (`src/blitzrc/bbruntime_dll/bbruntime_dll.cpp:55-70`) is the Windows
structured-exception translator: it maps a CPU fault code to a human-readable
panic message. Its `switch` has **no `break` statements**, so every case falls
through and `panicStr` always ends as `"Stack overflow!"`. Result: every native
crash — divide-by-zero, the very common null/dangling-object access violation,
illegal instruction — is reported to the user as "Stack overflow!", sending them
down the wrong debugging path. Directly contradicts INTENT #6 ("Trust the
runtime… instrumented enough to diagnose"). Confirmed by recon ~5×; the top
deferred Bug candidate.

## Approach

Add `break;` after each `case` in the switch so each exception code maps to its
correct message. Minimal correctness fix — no new codes, no pipeline redesign.

```cpp
switch( u ){
case EXCEPTION_INT_DIVIDE_BY_ZERO: panicStr = "Integer divide by zero"; break;
case EXCEPTION_ACCESS_VIOLATION:   panicStr = "Memory access violation"; break;
case EXCEPTION_ILLEGAL_INSTRUCTION:panicStr = "Illegal instruction"; break;
case EXCEPTION_STACK_OVERFLOW:     panicStr = "Stack overflow!"; break;
}
```

Message flow: `seTranslator → bbruntime_panic → RTEX → bbEx → debugError →
Debugger::debugMsg`. In test mode (`-t`) the DummyDebugger prints `"Error: " + msg`
to stdout and `exit(1)`; in normal runs it shows the message box.

## Verification

`-t` mode routes the panic message to stdout (DummyDebugger), so it is capturable:
a `.bb` that triggers a runtime integer divide-by-zero (divisor in a variable to
avoid constant folding), run with `blitzcc -t`, should print
`Error: Integer divide by zero` (post-fix) vs `Error: Stack overflow!` (pre-fix).
Plan: confirm this empirically against the rebuilt binary; if capturable, add a
CLI-contract check to `test.bat`/`test.sh` using a crash fixture generated OUTSIDE
`tests/` (a panic calls `exit(1)`, so it must NOT be a normal `-t` suite file —
that would abort the whole run). If the harness can't cleanly capture it, fall
back to documented manual repro + the obvious correctness of the four `break`s.

## Files touched

- `src/blitzrc/bbruntime_dll/bbruntime_dll.cpp` — `seTranslator` (4 `break`s).
- `test.bat` / `test.sh` — CLI crash-diagnostic contract (if capturable).
- `docs/notes/setranslator-exception-labels.md` — this note.

Windows-only by nature (`_set_se_translator`); macOS has its own signal path
(out of scope). No ABI/codegen/parser change.

## Acceptance criteria

- A runtime integer divide-by-zero reports "Integer divide by zero", not
  "Stack overflow!" (verified empirically; ideally pinned by a CLI check).
- Unknown codes still report "Unknown runtime exception".
- `test.bat` green; build 0 errors; no regression.

## Trade-offs / rejected

- **Skipped: adding more exception codes** (FLT_DIVIDE_BY_ZERO, PRIV_INSTRUCTION) —
  scope creep beyond the missing-break bug; keep one-purpose.
- A normal `-t` Test block can't assert this (the panic `exit(1)`s mid-run) — hence
  the standalone-fixture CLI-contract approach.

## Deferred follow-ups (high priority, surfaced this iteration)

- **GC doc false citation (my own #67 error):** `help/language/lang_ref_modern.html`
  cites `tests/GarbageCollectionTest.bb` as proof of the "reference cycles leak —
  break them yourself" contract, but that test has NO cycle case. Either add the
  cycle/aliasing test cases (GC acceptance suite — a recon pitch this iteration) or
  correct the citation. Worth doing soon (shipped-doc correctness).
- Contextual-keyword Phase 2 (`Release` + expression-position keywords).
