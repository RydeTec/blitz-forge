# Working note — Correct the GC doc & pin the real ref-count contract

Branch: `test/gc-refcount-contract`
Type: Documentation (correctness) + Test coverage

## Goal (restated)

Fix a shipped-doc correctness defect I introduced in PR #67:
`help/language/lang_ref_modern.html` (GC section, ~lines 50-57) makes two claims
that do NOT match the actual runtime, and cites `tests/GarbageCollectionTest.bb`
as evidence for a contract that test never exercises:
1. "RefCount reports how many references currently keep an object alive" — implies
   it counts every alias. It does not.
2. "reference cycles … are not collected automatically — break the cycle yourself"
   — cycles of field references ARE collected at scope exit in this runtime.

Correct the doc to match empirically-verified behavior, and expand the cited test
so it actually demonstrates the contract.

## Empirically verified (current `bin/blitzcc.exe`, via scratch `.bb` probes)

- `Local a.T = New T()` → `RefCount(a) = 1`; at scope exit the object is collected
  (`First T = Null`, `RefCount(First T) = 0`).
- Local aliasing `Local b.T = a` does NOT change the count → `RefCount(a)` stays 1.
- Field assignment `a\other = b` does NOT increment the count either.
- A field-reference cycle (`a\other=b : b\other=a`, both function-locals) is
  COLLECTED at scope exit — `First T = Null`, `Each T` count 0. **No leak.**
- An object referenced only via another live object's field stays alive and
  readable while the owner is reachable (`Each T` count 2; `container\other\tag`
  readable).

Mechanism context (do NOT change): `_bbReference`/`_bbRelease`/`_bbReferenceCount`
operate on `reference_map` (`basic.cpp:744-823`); local-alias and field
assignments don't route through `_bbReference`, so `RefCount` reflects the
binding/lifetime count, not an alias count. (There's a separate older
`obj->ref_cnt` path; the `reference_map`-vs-`obj->ref_cnt` distinction is a deeper
question — see deferred follow-ups. This increment pins observed behavior, it does
not change the runtime.)

## Approach

1. **Expand `tests/GarbageCollectionTest.bb`** with blocks pinning the verified
   behavior, using a distinct type per concern to avoid cross-block contamination:
   - single ref = 1 (+ object exists);
   - scope-exit collects (First = Null / RefCount = 0) — keep existing;
   - aliasing does not increment RefCount (pins the narrow semantics);
   - a field-reference cycle is collected at scope exit (no leak);
   - a field-only-referenced object stays alive while its owner is reachable.
   All assertions are captured from real runs, not assumed.
2. **Correct `lang_ref_modern.html`** GC section: describe RefCount as the GC's
   tracked reference/lifetime count (freed when the owning variable leaves scope),
   not "how many references"; replace the false cycle-leak claim with the verified
   behavior (cycles of field refs are collected; keep a live variable reference to
   anything you need to retain); the citation now points to a test that actually
   demonstrates these cases.

## Files touched

- `tests/GarbageCollectionTest.bb` (expand)
- `help/language/lang_ref_modern.html` (GC section correction)
- `docs/notes/gc-refcount-contract.md` (this note)

No `src/` change. Counteracts the recent runtime/bug-fix theme (Test + Docs).

## Acceptance criteria

- Every new `Test` block passes under `blitzcc -t`, asserting empirically-captured
  values (single=1, alias stays 1, cycle collected, field-ref object survives).
- The doc no longer claims RefCount counts all aliases, no longer claims cycles
  leak; its `tests/GarbageCollectionTest.bb` citation is now accurate.
- `test.bat` green; corpus sweep unaffected; no `src/` change.

## Trade-offs / rejected

- **Rejected: a full idealized ref-count acceptance suite** asserting
  aliasing→RefCount=2 etc. — the runtime does NOT behave that way; asserting it
  would be wrong. Pin reality instead (the iteration-4 empirical-capture pattern).
- **Rejected this iteration: SoundPan/ChannelPan restoration** (recon) — strong
  INTENT-#1 corpus-closing pick, but touches the OpenAL audio backend + a macOS
  stub and the panning correctness isn't CI-verifiable; deferred.
- **Rejected this iteration: the TCP-socket UAF** (recon) — real bug, but socket
  lifetime is hard to verify headlessly; deferred with the cited trace.

## Deferred follow-ups

- **GC soundness / `reference_map` vs `obj->ref_cnt`:** clarify whether the two
  ref-count mechanisms should be unified, and whether field/alias references
  SHOULD be counted. Potential correctness question (premature free / leak in
  configurations not covered here). Needs a dedicated investigation — do NOT
  rush a runtime change.
- SoundPan/ChannelPan (corpus Class B + shipped-doc gap); TCP-socket UAF;
  contextual-keyword Phase 2.
