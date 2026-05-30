# Working note — Close the OOB hole in ReadBytes / WriteBytes

Branch: `fix/readbytes-writebytes-bounds`
Type: Bug / Reliability (memory-safety)

## Goal (restated)

`bbReadBytes`/`bbWriteBytes` (`src/blitzrc/bbruntime/bbbank.cpp:170-184`) pass a
caller-supplied `count` straight to `bbStream::read/write(char*, int)` after only a
single composite `debugBank(b, offset+count-1, ...)` check — no `count <= 0` guard
and no independent start-offset check. Two arbitrary out-of-bounds memory
primitives reachable from pure Blitz code:
- `ReadBytes(bank, s, 10, -5)` → composite index `4` is in range → passes →
  `s->read(data+10, -5)`; the negative `count` widens to a huge `streamsize` →
  massive OOB write into the bank.
- `ReadBytes(bank, s, -100, 200)` → composite index `99` is in range → passes →
  reads into `data-100` → OOB underflow.

`bbCopyBank` (`bbbank.cpp:98-112`) already fixed this exact class (Round-4 audit):
`if(count<=0) return;` then independent start AND end `debugBank` checks. ReadBytes/
WriteBytes are the unfinished tail — they still carry the old dead `//if(debug){`
scaffolding. INTENT: "no silent corruption … bounds checks always worth doing."

## Approach

Mirror `bbCopyBank` in both functions:
```cpp
int bbReadBytes( bbBank *b,bbStream *s,int offset,int count ){
    if( count <= 0 ) return 0;
    if (!debugBank( b,offset,"ReadBytes" )) return 0;            // start
    if (!debugBank( b,offset+count-1,"ReadBytes" )) return 0;    // end
    if (!debugStream( s,"ReadBytes" )) return 0;
    return s->read( b->data+offset,count );
}
```
…and the same for `bbWriteBytes`. Remove the dead `//if(debug){` / `//}` comments.
`debugBank` is `debug ? RTEX : errorLog.push_back; return false` — in debug a bad
index raises a clean RuntimeError; otherwise the call no-ops and returns 0.

A huge positive `count` makes `offset+count-1` overflow to negative, which the
end `debugBank` rejects (`offset<0`) — same behaviour as CopyBank; matching the
established idiom over a bespoke overflow guard keeps the two paths consistent.

## Files touched

- `src/blitzrc/bbruntime/bbbank.cpp` — `bbReadBytes`, `bbWriteBytes`.
- `tests/BankBoundsTest.bb` — add ReadBytes/WriteBytes cases.
- `docs/notes/readbytes-writebytes-bounds.md` — this note.

No public-API/ABI change; valid (positive, in-range) calls are byte-identical.

## Acceptance criteria (Artifact B)

- `WriteBytes`/`ReadBytes` with `count <= 0` return 0 without calling
  `s->write`/`s->read`: verified by a `WriteBytes(..., -1)` leaving the output file
  at `FileSize = 0`, and a `ReadBytes(..., -1)` leaving a sentinel-filled bank
  unchanged. Both blocks complete (no crash) — the regression guard, since pre-fix
  the negative count drove a huge OOB transfer.
- Happy-path round-trip (bank → file via `WriteBytes`, file → bank via `ReadBytes`)
  reproduces the bytes exactly.
- Existing `BankBoundsTest.bb` (CopyBank) and full `test.bat` stay green; corpus
  sweep unaffected.

## Trade-offs / rejected

- **Pile-on note:** last iteration (#73) was BBList bounds-checking; this is another
  bounds fix. Weighed the loop's anti-pile-on bias and chose impact: a reachable
  arbitrary OOB read/write (INTENT's non-negotiable "no silent corruption") outranks
  the tunable heuristic, and this is a different module/function family completing a
  committed audit. Deliberately steering to diagnostics/DevEx next iteration.
- **Defense-in-depth negative-size guard inside `bbStream::read/write`** — broader,
  out of scope; the fix at the bank boundary closes the Blitz-reachable hole.
- Not touching CopyBank/Peek/Poke/`debugBank` (already correct).

## Deferred follow-ups

- `seTranslator` missing `break`s (Bug, confirmed, verification path known).
- Human-readable compiler diagnostics (source line + caret, `blitzide`-gated).
- `qstreambuf` `new[]`/`delete` mismatch (`stdutil.cpp` ~419/447) — narrower UB,
  flagged by recon, harder to exercise from Blitz.
