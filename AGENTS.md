# AGENTS.md — BlitzForge

Orientation for Codex agents working in the BlitzForge compiler/runtime. User-facing docs live in [ReadMe.md](ReadMe.md); this file is the developer's-eye view.

## What this repo is

A modernized, open-source fork of the Blitz3D compiler + runtime, in C++17. Produces native executables from `.bb` Blitz source. Targets Windows x86_64 (stable) and macOS arm64 (alpha).

This repo is typically consumed as a **submodule** of a downstream project — most actively [rcce2](https://github.com/RydeTec/rcce2). When you're working in this repo via the submodule path, changes here require a corresponding "BlitzForge bump" PR in the parent repo to take effect.

## Skills available in this repo

Always check `.Codex/skills/` for relevant skills. The most load-bearing:

- **blitzforge-language** (in `.Codex/skills/`) — exact syntax of what BlitzForge adds vs base Blitz3D. Read it before writing or reviewing any `.bb` file, including the test suite under `tests/`. Your training data has Blitz3D from ~2003; it does not have the modern extensions (`Strict`, `EnableGC`, inheritance, methods, `BBList`, `Async`/`Await`, `TryCatch`).

## Build and test

The C++ toolchain is built with CMake + Ninja, but this repo ships per-platform wrapper scripts.

```powershell
# Windows (PowerShell or cmd)
.\compile.bat                  # builds blitzcc.exe + runtime DLLs via MSBuild
.\test.bat                     # runs every .bb under tests/ in test mode (-t)
.\publish.bat                  # stage release/ and produce blitzforge-windows-x64.zip
```

```bash
# macOS (Apple Silicon)
./scripts/bootstrap_macos.sh   # one-time: cmake/ninja/llvm via Homebrew
./compile.sh                   # builds bin/blitzcc + runtime.dylib + linker.dylib
./test.sh                      # compiles tests/*.bb against -target macos-arm64 (skips exec)
./publish.sh                   # produce release/blitzforge-macos-arm64.zip
```

**macOS runtime is alpha**: `test.sh` validates compile/translate/assemble but **skips in-process execution** with a clear "alpha stub runtime" notice. Don't trust a green macOS run to mean a feature works at runtime; verify on Windows.

The pre-commit hook ([.hooks/pre-commit](.hooks/pre-commit)) runs `test.bat` (Windows) / `test.sh` (Unix). Enable hooks per clone:

```bash
git config core.hooksPath .hooks
```

## Source layout

```
BlitzForge/
├── src/blitzrc/                  # main C++ source
│   ├── blitz/                    # blitzcc.exe entry point, libs, frontend glue
│   ├── compiler/                 # parser, AST, types, sema
│   │   ├── codegen_x86/          # x86 backend (32-bit legacy + 64-bit)
│   │   └── codegen_arm64/        # macOS / Apple Silicon backend
│   ├── bbruntime/                # standard library (graphics, audio, input, files, GC, lists, etc.)
│   ├── bbruntime_dll/            # runtime DLL/dylib export glue
│   ├── linker/                   # link step
│   ├── linker_dll/               # linker DLL/dylib glue
│   ├── gxruntime/                # Windows DirectX/audio/input backend
│   ├── debugger/                 # debug protocol
│   └── stdutil/                  # cross-platform utility layer
├── tests/                        # Blitz-language test suite — run on every commit
├── samples/ · games/             # example Blitz programs
├── help/language/                # HTML language reference (mostly legacy, but useful)
├── tutorials/                    # learning material
├── userlibs/                     # bundled .decls + DLL/dylib pairs
├── cfg/                          # IDE / editor configuration
├── scripts/                      # cross-platform build helpers (compile.sh, msbuild_*.bat)
├── docs/                         # contributor docs
├── .hooks/                       # opt-in git hooks (pre-commit runs tests)
└── bin/                          # compiled output (gitignored)
```

The four project files that anchor each component (visible in [.cursor/rules/blitzforge.mdc](.cursor/rules/blitzforge.mdc)):

| Component | Project file | Purpose |
|---|---|---|
| Compiler | [blitz.vcxproj](src/blitzrc/blitz/blitz.vcxproj) | `blitzcc.exe` entry point |
| Standard library | [bbruntime.vcxproj](src/blitzrc/bbruntime/bbruntime.vcxproj) | language API (graphics, audio, input, GC, lists) |
| Windows runtime | [gxruntime.vcxproj](src/blitzrc/gxruntime/gxruntime.vcxproj) | DirectX7 abstraction |
| Game engine | [blitz3d.vcxproj](src/blitzrc/blitz3d/blitz3d.vcxproj) | 3D engine |

## What's different from base Blitz3D source

The compiler/runtime fork is **drop-in source-compatible** with original Blitz3D `.bb` programs but adds significant language and tooling extensions. The full language additions are documented in the **blitzforge-language** skill. From the C++ side, you're most likely to touch:

- **Sema/parser changes** in `src/blitzrc/compiler/` to support `Strict`, type inheritance, `Method`, `Continue`, `Try/Throw/Catch`, function pointers (`@` sigil), `Async`/`Await`/`Poll`, `Recast`.
- **GC** runtime in `src/blitzrc/bbruntime/` (ref-counting `_bbRetain` / `_bbRelease` / `_bbDeleteVar`, per-BlitzType release dispatch for lists, `RefCount()` introspection).
- **Codegen** under `src/blitzrc/compiler/codegen_x86/` and `codegen_arm64/`. Both backends sit behind the `assem.h` abstraction. Don't leak ISA-specific assumptions into the front end.
- **Linker** under `src/blitzrc/linker/` (PE resource handling, ARM64 Mach-O support).
- **`gxruntime`** under `src/blitzrc/gxruntime/`. The Windows path uses DirectX 7-era APIs (DDraw, DDS, DirectInput); recent hardening has shored up bounds checks (DXT decoding, joystick deadzone, OGG/movie playback shutdown, SetDataFormat fallback).
- **macOS backend** at `src/blitzrc/gxruntime/macos_runtime_backend.mm` — Cocoa/Objective-C++ providing the Win32 surface that legacy modules expect.

## Conventions

- **C++17**, no exotic extensions. MSVC 2019+ on Windows, Xcode CLT on macOS.
- **Keep codegen backends behind `assem.h`.** ISA-specific code only lives in the per-backend directories. The front-end emits IR; backends translate.
- **Tests > comments.** Add or update a `.bb` test under `tests/` for any behavior change. The test framework is built into `blitzcc` itself: `blitzcc -t file.bb` runs every `Test foo() ... End Test` block.
- **One purpose per commit.** Smaller PRs land faster; mixed-purpose PRs invite review confusion.
- **License notices**: vendored third-party components retain bundled license texts. See [LICENSES.md](LICENSES.md) for the pointer map. Don't centralize them into a repo-root LICENSE without auditing each path.

## CI

- [`.github/workflows/ci.yml`](.github/workflows/ci.yml) — build `blitzcc` + run test suite. Triggers on push and PR.
- [`.github/workflows/static-analysis.yml`](.github/workflows/static-analysis.yml) — `yamllint` + `actionlint` on workflow files.

## Workflow (downstream submodule consumers)

If you're working in this repo because it's a submodule of [rcce2](https://github.com/RydeTec/rcce2):

1. Branch from `develop` in **this** repo (BlitzForge), commit + push, open PR targeting `develop`.
2. Once merged, bump the submodule pointer in the parent repo:
   ```bash
   cd /path/to/rcce2/compiler/BlitzForge && git fetch && git checkout <new-sha>
   cd /path/to/rcce2 && git add compiler/BlitzForge
   git commit -m "[DEPENDENCY] Update blitzforge dependencies"
   ```
3. Open a "BlitzForge bump" PR in the parent repo.

Releases of BlitzForge itself flow `develop → master` via PR.

## Gotchas

- **Submodule paths**: when this repo is checked out as a submodule, MSBuild project paths and IDE shortcuts still work but `git status` reflects the parent's state. `git submodule update --remote` will fast-forward you to upstream — use deliberately.
- **macOS runtime is incomplete**: `test.sh` deliberately skips in-process execution under `-target macos-arm64`. A green macOS run only proves compile+translate+assemble succeeded. Always cross-check on Windows for runtime behavior.
- **DirectX 7-era APIs**: `gxruntime` is built on DirectDraw/DirectInput from the early 2000s. When fixing bugs, be careful with `Release()` ordering (COM ref counts), `IDirectInputDevice::SetDataFormat` driver fallback, and `IDirectSoundBuffer8::UnqueueBuffers` semantics. Several merged PRs touch this surface — search merged history for `gx` keywords.
- **GC ref counts**: BlitzForge runtime is ref-counted, not tracing. Reference cycles leak. Be careful with self-referential structures; either break the cycle manually or use a non-owning reference pattern.
- **`_bbThrow` inline payload**: throwing primitive types (int/float/string) goes through an inline payload path; throwing BlitzType objects goes through the BlitzType release dispatch. See merged `BBB` track PRs for context.
- **Backup files in submodule directory**: same `.bb_bak1` / `.bb_bak2` legacy as rcce2 may show in editor saves. Gitignored; ignore in reviews.
- **Pre-commit hook**: opt-in (`git config core.hooksPath .hooks`). Don't assume it's running on a collaborator's clone.

## Memory + skills hygiene for you (the agent)

Save memories for non-obvious things you discover working in this repo. Examples:

- A subtle invariant in the parser ("`Recast` requires the variable's *declared* type to be the target; the runtime value's actual type determines field layout, hence the `testIncorrectCrossCasting` memory-aliasing example").
- A compatibility constraint with downstream consumers ("rcce2 relies on the BVM_CreateCommandSet alphabetical opcode ordering — don't change parser sort behavior without coordinating").

Don't save things derivable from reading the code.

## What's NOT in this repo

- Downstream game source (rcce2 lives separately).
- Pre-built binaries — released via GitHub Releases, not tracked.
- VS Code language extension — that's a separate repo at [vscode-blitz-forge](https://github.com/RydeTec/vscode-blitz-forge).
