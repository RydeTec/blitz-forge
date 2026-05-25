# INTENT.md — BlitzForge

The north star. What this product is striving to become at its best. Use this to evaluate proposed changes — does this PR move us closer to the vision below, or sideways, or away?

## Core thesis

Blitz3D and BlitzBasic shaped a generation of indie game development. They were the first languages many of us used to ship something real. The original toolchain was tied to 32-bit Windows, closed-source, and frozen.

**BlitzForge keeps the language's strengths — accessibility, fast iteration, fun to write in — while bringing the toolchain into the modern era.** Open source. Cross-platform. Modern build system. CI on every change. Active maintenance. A respectful continuation of Mark Sibly and the original Blitz Research team's work.

The goal isn't to be a new language. It's to be the language so many people already love, but on hardware they actually own and with a process they can trust.

## What "good" looks like

A Blitz user, returning to the language after years away, should:

- **Compile their old game on day one.** Drop the existing `.bb` files in, run `blitzcc`, get a working executable. No source changes. No "well, almost compatible."
- **Use existing `.decls` userlibs unchanged.** The userlib ABI is preserved.
- **Find their content runs on modern hardware.** 64-bit Windows. Apple Silicon. (Linux when we get there.)
- **Discover the language has grown up.** Optional Strict typing. Optional garbage collection. Type inheritance and methods. `Async`/`Await`. `Try`/`Catch`. `BBList`. All additive — old code keeps working, new code can be cleaner.
- **See active maintenance.** Bug reports get triaged. PRs land. Releases ship. The repo has a pulse.
- **Trust the runtime.** No more "it crashed and the log says nothing." Bounds-checked, hardened, instrumented enough to diagnose.

## Values (in priority order)

1. **Drop-in source compatibility.** Existing `.bb` programs and `.decls` userlibs compile and run. Always. Additions are additive; nothing in the legacy surface gets removed or breaks without an exceptionally good reason and a long deprecation window.
2. **Open development.** Public source. Public CI. License notices in every vendored component. PRs welcome from anyone. Decisions explained.
3. **Cross-platform from the ground up.** Windows x86_64 is stable today. macOS Apple Silicon is alpha; the goal is parity. Linux is on the horizon. ARM64 codegen is first-class, not an afterthought.
4. **Modern language additions are first-class but opt-in.** `Strict` mode at the file top, `EnableGC` on the next line — that's the only ceremony needed to unlock everything modern. No new keywords clash with legacy programs.
5. **Tested over commented.** Every behavior change comes with a `.bb` test. The test suite runs on every commit and every PR. Failures block.
6. **Codegen behind `assem.h`.** x86 and ARM64 backends share a common abstraction. ISA-specific assumptions don't leak into the front end. New backends can be added without rewriting the language.
7. **Quality of life for downstream consumers.** rcce2 and the games built on top depend on a stable BlitzForge. Releases get tagged. Submodule bumps are reviewable. CI artifacts are cacheable.
8. **Respectful continuation.** Of Mark Sibly's work. Of every community port and patch that came before. The license notices stay where they are; the heritage gets acknowledged in the README.

## What we're walking away from

- Closed-source compiler. The whole reason this fork exists.
- Windows-32-bit-only. The platform left this behind a decade ago; the toolchain shouldn't anchor games to it.
- No tests, no CI. "It compiles, ship it" is not a quality bar.
- VS6-era build files as the only build path. CMake + Ninja are the modern standard.
- Silent corruption on edge cases. Bounds checks, audit ranges, hardened runtime calls — the work to close the next class of UAF / overflow / refcount-cycle bug is always worth doing.

## Long-horizon items

These are the bigger improvements still worth doing:

- **macOS runtime parity** with Windows. Today the compiler/translator/assembler work on `-target macos-arm64` but the runtime is alpha and `test.sh` skips in-process execution. Closing that gap unlocks the entire macOS path.
- **Linux runtime**. Community interest tracked in Discussions. Codegen is already mostly there; runtime backend is the lift.
- **Cycle-aware GC** or a documented "break your cycles" pattern. Refcount cycles leak silently; either detect them or document the discipline.
- **`_bbAsyncCallFunctionPointer` va_list lifetime audit** — deferred from a prior round. The current implementation copies args but ownership semantics aren't fully clear.
- **Updated language reference**. The HTML in `help/language/` is mostly legacy Blitz3D docs. The modern additions (Strict, GC, methods, inheritance, BBList, Async, TryCatch) deserve first-class reference pages.
- **Userlib ergonomics**. The `.decls` format is functional but C-API-only; modern interop with Rust / Swift / etc. would broaden the contributor base.
- **Debugger improvements**. The debug protocol exists; tooling on top of it (better breakpoints, watch expressions, REPL eval) lags behind.
- **Standalone language-server**. Today the VS Code extension does the heavy lifting; extracting a true LSP would let other editors join.

These are sketches, not commitments. Each becomes its own design conversation when it surfaces.

## How to use this file

When considering a change, ask: does it advance one of the eight values above without regressing another? Does it preserve drop-in compatibility for legacy programs? Does it move us toward something on the long-horizon list, or at minimum not away from it?

When in doubt, ship the smaller change with the clearer reasoning, write a test that pins the behavior, and write the *why* down.
