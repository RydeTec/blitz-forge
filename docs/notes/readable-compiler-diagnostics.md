# Working note — Human-readable compiler diagnostics (terminal), IDE format preserved

Branch: `devex/readable-compiler-diagnostics`
Type: DevEx / UX (with a Bug fix: the EOF-byte leak)

## Goal (restated)

When `blitzcc` rejects a `.bb` from the terminal, the only output is a single
machine line: `"file":row:col:row:col:msg` (`main.cpp:342-348`). A human gets no
source context — no offending line, no caret. Worse, unterminated-block/EOF errors
leak a raw `0xFF` byte into the message (`parser.cpp:62` concatenates
`toker->text()`, which at EOF is `line.substr(0,1)` where `line[0]=(char)EOF`).

Render a clang-style diagnostic (source line + `^` caret) for terminal users,
gated behind `getenv("blitzide")` so the IDE machine format stays byte-identical;
and fix the EOF-byte leak at its source. This is the DevEx increment the prior
iteration explicitly pre-planned, and it deliberately steers away from the recent
runtime-bounds theme.

## Why it's safe to change terminal output

- The IDE/VS Code extension and rcce2 tooling consume the machine format; it is
  preserved byte-for-byte behind `getenv("blitzide")` (the same env var already
  used at `main.cpp:292`).
- CI/`test.bat` assert only exit codes and the substrings `Usage:`,
  `Compiler version:`, `Unsupported -target` — never the compile-error line. So
  changing terminal error output cannot break the existing contract.

## Approach (in order)

1. **`toker.cpp` `Toker::textAt`** — return the literal `"<end of file>"` when the
   token is the EOF token (`tokes[toke].n == EOF`), so the `0xFF` byte never
   reaches any message (`Got:` text or `identifier near:`). Mode-independent fix.
2. **`main.cpp` `catch(Ex&)`** — branch on `getenv("blitzide")`:
   - IDE present → emit the exact current `"file":row:col:row:col:msg` line, unchanged.
   - Terminal → `file:row:col: error: msg`, then re-read the offending source line
     from `x.file` and print it with a `^` caret aligned to `col` (tabs copied
     verbatim into the caret line so alignment holds under tab expansion). If
     `x.pos < 0` (assembler/fileless errors) or the line can't be read, print the
     message header only — never crash, never a bogus caret.
3. **`test.bat` + `test.sh`** — add CLI-contract checks: generate a broken fixture
   in a temp dir (NOT under `tests/`, so the `-t` loop and corpus sweep don't pick
   it up), then assert terminal mode shows `error:` and `end of file`, and IDE mode
   (`blitzide` set) still emits the machine format and omits the `error:` prefix.

## Files touched

- `src/blitzrc/compiler/toker.cpp` — `textAt` EOF-token text.
- `src/blitzrc/blitz/main.cpp` — the compile-error catch block (+ `<cstdlib>` if
  `getenv` isn't already in scope).
- `test.bat`, `test.sh` — CLI diagnostic contract checks.
- `docs/notes/readable-compiler-diagnostics.md` — this note.

No `Ex` struct / pos-encoding change; no parser/sema/codegen change beyond the EOF
token text; no new CLI flags; the `blitzide` env var is the sole gate.

## Acceptance criteria (Artifact B)

- With `blitzide` set, a broken `.bb` produces output byte-identical to current
  `develop` (machine format), and does NOT contain the human `error:` prefix.
- Without `blitzide`, the same file prints `file:row:col: error: msg`, the offending
  source line, and a caret; exit code unchanged (non-zero).
- An unterminated block (`For` with no `Next`) reports `... Got: <end of file>` —
  no raw control byte — in both modes.
- `x.pos < 0` errors print message-only without crashing.
- `test.bat` (and `test.sh`) stay green with the new checks; corpus sweep unaffected.

## Trade-offs / rejected

- **Rejected this iteration: the legacy-identifier contextual-keyword fix**
  (recon, INTENT #1, breaks shipped samples). Highest *alignment*, but front-end
  grammar surgery with real ambiguity/regression risk — it keeps failing the
  "high-confidence COMPLETE one-iteration" bar (deferred 3x). It needs a DEDICATED
  iteration with the phased statement-only scope; flagging it so it doesn't become
  a perennial defer.
- **Rejected: seTranslator missing-`break`** — trivial/confirmed but lowest impact
  (cosmetic message on an already-fatal crash). Cheap future pick.
- No ANSI color (portability + log-capture noise); plain-text caret only.

## Deferred follow-ups

- Legacy-identifier compatibility (DEDICATED iteration, statement-only phase first).
- `seTranslator` missing `break`s.
- `qstreambuf` `new[]`/`delete` mismatch (`stdutil.cpp` ~419/447).
