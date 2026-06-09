# Working note — exit-time teardown crash (the rcce2 ItemsTest / chain-test CI flake)

Branch: `fix/exit-teardown-crash`
Type: Bug / Reliability

## Goal (restated)

rcce2 CI has a long-standing intermittent crash: `ItemsTest.bb` dies with
"Stack overflow!" and `OnlinePlayerChainTest.bb` with "Memory access violation"
*after all tests pass*, sometimes with `Active objects: 0` already printed.
Two rcce2-side mitigations (PR #313 start-of-test pool sweeps, PR #322 trailing
`Delete Each` teardown sweeps) reduced the rate from ~30-40% to ~5-7% but the
root cause was deferred to "the BlitzCC runtime pool-walker fix" and never
diagnosed. The historical "Stack overflow!" label proves nothing — until the
seTranslator fall-through fix, *every* native fault carried that label; the
real fault class was never observed.

Goal: identify the mechanism with evidence (fault address, deterministic
repro), fix it minimally in the runtime, and pin it with regression tests.

## Suspected mechanisms (to confirm or kill, in order)

1. **Dual-refcount desync (GC layer vs classic pool)** — `_bbObjDeleteEach` /
   `_bbObjDelete` (`bbruntime/basic.cpp:288-323`) maintain `BBObj::ref_cnt`
   and the used/free pool lists, but never touch the GC `reference_map`.
   A `Delete Each T` sweep (exactly what the rcce2 band-aids added) frees /
   recycles objects whose pointers remain counted in `reference_map`. When a
   GC-tracked reference later releases (`_bbRelease(vPtr,"BBCustom")`,
   basic.cpp:769-815), count hits 0 and `_bbObjDelete` runs **on freed or
   recycled memory** — releasing garbage STR/OBJ/VEC "fields" → heap
   corruption → delayed AV at exit (e.g. `basic_destroy`'s `usedStrs` walk),
   counters already 0. Intermittency = whether the slot was recycled and what
   now occupies it.
2. **`_bbObjDeleteEach` captured-next invalidation** — the walk captures
   `next=obj->next` before `_bbObjDelete(obj)`; the delete's field-release
   cascade can drop `next`'s ref_cnt to 0 and move it to the free list
   (chain shape: each node holds the only ref to the next — exactly the
   chain tests). The walk then continues through free-list linkage.
3. Genuine recursion depth in a release cascade (considered least likely:
   `_bbObjDelete`'s field loop is iterative; only nested object graphs
   recurse one level per edge).

## Approach

1. **Instrument first**: extend `seTranslator` (bbruntime_dll.cpp) to append
   the exception code, faulting instruction address, and module-relative
   offset (module name + base via `VirtualQuery`/`GetModuleFileName`) to the
   panic string. Permanent diagnostic upgrade; the `-t` path prints it to
   stdout where CI logs capture it.
2. **Deterministic probes** (uncommitted scratch .bb files first): (a) chain
   `Delete Each` survivor/ordering probe, no-GC; (b) GC-mode probe: build
   chain, `Delete Each`, force recycling via new allocations, then let
   GC-tracked references release. If a probe crashes or misbehaves
   deterministically, the mechanism is pinned without statistics.
3. **Statistical baseline**: loop rcce2's `test.bat ItemsTest` and
   `test.bat OnlinePlayerChainTest` (>=100 runs each) against the
   instrumented runtime; record failure rate + fault offsets; map offsets to
   functions via the linker map / dumpbin.
4. **Fix minimally** at the confirmed site. Candidate shapes: erase the
   pointer from `reference_map` inside `_bbObjDelete` (closing the desync);
   restart-walk in `_bbObjDeleteEach` (mirrors rcce2's own
   "Restart-on-Delete" iterator doctrine). Only what evidence supports.
5. **Pin**: BlitzForge `tests/` regression (long-chain create/Delete Each/
   exit, both GC and no-GC shapes); rerun the statistical loop (>=200 clean).
6. **Land**: BlitzForge PR → merge → rcce2 submodule-bump PR → update rcce2
   CLAUDE.md flake note.

## Fallback floor (if root cause resists one iteration)

The instrumentation, measured baseline, probe results, and this note merge
anyway — converting an opaque flake into an addressable bug with evidence.

## Files touched (planned)

- `src/blitzrc/bbruntime_dll/bbruntime_dll.cpp` — seTranslator diagnostics.
- `src/blitzrc/bbruntime/basic.cpp` — the fix (site TBD by evidence).
- `tests/` — regression test(s).
- this note.

## Acceptance criteria

- Panic messages include exception code + module-relative fault address.
- Root cause identified from evidence (address/probe), not the symptom label.
- Baseline failure rate measured pre-fix; >=200 consecutive clean runs of both
  flaky rcce2 tests post-fix.
- BlitzForge suite green; full rcce2 compile + suite green on the bump.
- rcce2 CLAUDE.md "Known intermittent flake" updated.
