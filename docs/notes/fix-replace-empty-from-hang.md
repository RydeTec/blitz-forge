# Working note — Fix `Replace()` hang on empty search string + first string test suite

Branch: `fix/replace-empty-from-hang`
Type: Bug / Reliability
Status: working note (delete-or-keep at reviewer's discretion; this is grounding intent, not shipped docs)

## Goal (restated)

`Replace(s$, from$, to$)` enters an infinite loop — the runtime hangs — whenever
`from$` is the empty string. Because `Replace` is one of the most-used string
commands in any Blitz program, a single accidental empty search string turns into
an unrecoverable hang with no error. Close that hang with a minimal, legacy-safe
guard, and pin the behavior (plus the surrounding string-command boundary contract)
with the suite's first dedicated string tests.

## Root cause

`src/blitzrc/bbruntime/bbstring.cpp:26-33`:

```cpp
BBStr *bbReplace( BBStr *s,BBStr *from,BBStr *to ){
    int n=0,from_sz=(int)from->size(),to_sz=(int)to->size();
    while( n<((int)s->size()) && (n=s->find( *from,n ))!=string::npos){
        s->replace( n,from_sz,*to );
        n+=to_sz;
    }
    delete from;delete to;return s;
}
```

When `from` is empty, `std::string::find("", n)` returns `n` for any `n <= size()`
(never `npos`), so the loop body always runs:

- empty `to` → `to_sz == 0`, `n` never advances → infinite loop.
- non-empty `to` → each iteration inserts `to` and grows `s` by `to_sz`, and
  advances `n` by `to_sz`; the gap `size() - n` stays constant → infinite loop.

Either way it hangs. Since the current behavior is a hang, **no working legacy
program can depend on it**, so any terminating behavior is strictly safer.

## Chosen semantic

`Replace(s, "", to)` returns `s` unchanged. This matches the least-surprise
contract used across Blitz dialects and other languages (an empty search string
has no occurrence to replace) and is the minimal change that removes the hang
while preserving every well-formed (non-empty `from`) result byte-for-byte.

## Approach (in order)

1. **Pin first** — add `tests/StringTest.bb` (`Strict` + `EnableGC`) asserting the
   intended behavior for the string commands, including:
   - `Replace` normal case, no-match case, and the empty-`from` case (must return
     the original string and, critically, must *terminate*).
   - `Left` / `Right` / `Mid` boundary clamping (offset/count past end, `Mid`
     count = -1 meaning "to end"), proving they return defined strings.
   - `Instr` found / not-found / `from` past end.
   - `Trim`, `Upper`, `Lower`, `LSet`, `RSet`, `Chr`, `Asc` (incl. `Asc("") = -1`),
     `Len`, `Hex`, `Bin` documented contracts.
2. **Fix** — in `bbReplace`, early-return `s` unchanged when `from` is empty,
   deleting `from`/`to` to preserve the function's ownership contract.
3. **Verify** — `test.bat` green (the new suite + all 16 existing suites).

## Files / interfaces touched

- `src/blitzrc/bbruntime/bbstring.cpp` — `bbReplace` only (one guard).
- `tests/StringTest.bb` — new.
- `docs/notes/fix-replace-empty-from-hang.md` — this note.

No public-API, ABI, or `.decls` change. No other string function is modified —
`bbMid`/`bbLeft`/`bbRight`/`bbLSet`/`bbRSet` were verified already clamped, so the
tests pin existing-safe behavior rather than changing it.

## Migration / rollback

No migration. Rollback = revert the commit; the only behavior change is that a
previously-hanging input now returns a value, so revert risk is nil.

## Sequencing

Test file and fix land together in the diff. The empty-`from` test would hang
against the unpatched runtime, so it is only meaningful alongside the fix — both
ship in the same PR.

## Trade-offs considered and rejected

- **Bundling math (`bbmath.cpp`) coverage** (recon pitch 2): rejected — no confirmed
  defect there; pure coverage would balloon a focused reliability fix into a broad
  test sweep. Deferred as its own future increment.
- **"Insert `to` between every char" semantic for empty `from`**: rejected — surprising,
  not legacy behavior, and still requires a termination guard anyway.
- **Documenting modern language features** (recon pitch 3): worthy, `M` effort,
  editorial; deferred.

## Deferred follow-ups

- `tests/MathTest`-style coverage for `bbmath.cpp` (Sqrt(-1), Log(0), trig domain).
- Broader string edge audit (multi-byte / locale behavior of `toupper`/`isgraph`).
- Modern-feature reference pages under `help/language/` (pitch 3).
