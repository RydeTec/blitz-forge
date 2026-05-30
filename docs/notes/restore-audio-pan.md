# Working note — Restore SoundPan / ChannelPan on the OpenAL audio backend

Branch: `align/restore-audio-pan`
Type: Feature / Alignment (drop-in compat, INTENT #1) + Documentation-vs-reality fix

## Goal (restated)

`SoundPan` and `ChannelPan` are classic Blitz3D stereo-pan commands. BlitzForge
still ships docs/examples for them (`help/commands/.../SoundPan.htm`, `ChannelPan.htm`)
but the runtime neither registers nor implements them — casualties of the
DirectSound→OpenAL port (the impls/registrations are commented out in `bbaudio.cpp`
and the `setPan` virtual was dropped from `gxChannel`/`gxSound`). Legacy programs
calling them fail to compile ("SoundPan not found"); these are corpus-allowlist
Class B entries (`aristoids.bb`, `spider.bb`). Restore them.

## Approach (per file)

Only the Windows OpenAL backend implements these interfaces (verified: just
`SampleChannel`/`StreamChannel` and `gxSoundSample`/`gxSoundStream`; the macOS
`.mm` does not implement gxChannel/gxSound). So:

1. `gxchannel.h`: add `virtual void setPan(float)=0;` to `gxChannel`; declare it on
   `SampleChannel` and `StreamChannel`.
2. `gxchannel.cpp`: implement `setPan` on both — OpenAL constant-power pan on a
   listener-relative source: clamp pan∈[-1,1], `alSourcei(source,AL_SOURCE_RELATIVE,
   AL_TRUE); alSource3f(source,AL_POSITION,pan,0,-sqrtf(1-pan*pan));`.
3. `gxsound.h`: add `virtual void setPan(float)=0;` to `gxSound`; add a
   `float def_pan;` to its protected members; declare `setPan` on both sound
   classes.
4. `gxsound.cpp`: init `def_pan=0` in both ctors; store it in `setPan`; apply
   `retVal->setPan(def_pan)` in both `play()` paths (so `SoundPan` sets the default
   for subsequently-played channels, matching Blitz3D semantics).
5. `bbaudio.h`/`bbaudio.cpp`: uncomment `bbSoundPan` (guarded by `debugSound`) and
   `bbChannelPan` (guarded by `gx_audio->verifyChannel`, matching the other channel
   commands), and their two `rtSym` registrations.
6. `scripts/corpus_compile_allowlist.txt`: remove `aristoids.bb`/`spider.bb` once
   the sweep confirms they compile.
7. `tests/AudioPanTest.bb`: a headless-safe smoke test (symbols resolve, tolerate a
   Null sound/channel without crashing).

`set3d`/pan interaction: both touch `AL_SOURCE_RELATIVE`+`AL_POSITION`; last-call-
wins (store pan; the corpus samples use pan OR 3D, not both).

## Verification (acceptance is CI-deterministic; audible is manual)

- Build clean; `blitzcc` registers `SoundPan`/`ChannelPan`.
- `spider.bb` now compiles → removed from the allowlist (ratchet enforces it).
  `aristoids.bb`'s SoundPan blocker is resolved, but it ALSO uses `PlayMusic`
  (a separate, still-disabled Class B builtin) — so it stays allowlisted with its
  reason updated to PlayMusic. (Verified empirically; corpus sweep green.)
- `tests/AudioPanTest.bb` passes under `-t` (no crash; headless-safe).
- `test.bat` green.
- **Audible pan correctness is NOT headless-verifiable** (no audio device in CI).
  The OpenAL constant-power formula is standard and the source/relative-mode setup
  is confirmed; audible verification is a documented manual Windows step.

## Files touched

`gxchannel.{h,cpp}`, `gxsound.{h,cpp}`, `bbaudio.{h,cpp}`, `scripts/corpus_compile_allowlist.txt`,
`tests/AudioPanTest.bb`, this note. No compiler/codegen/front-end change.

## Trade-offs / rejected

- **Pure-virtual vs non-pure default:** chose pure-virtual on the base (only 4
  concrete implementers exist, all updated here; forces correctness). No macOS
  audio implementer exists, so no stub needed.
- **Rejected this iteration:** linker `loadImage` PE-header hardening (recon — real
  but weak verification, low practical payoff) and the `gxSoundSample::load`
  short-filename crash (recon — real one-line bug but borderline-too-small and
  uncertain headless testability). Both deferred.

## Deferred follow-ups

- `gxSoundSample::load` short-filename crash: `LoadSound("abc")` → `substr(size()-4)`
  underflow → `std::out_of_range` → process kill. One-line guard
  (`filename.size()<4 || ...`) at `gxsound.cpp:25`. Real, BASIC-reachable. STRONG
  cheap next pick (same file).
- Other Class B builtins (`PlayMusic`, `CreateListener`, `Load3DSound`, `StartNetGame`).
- Linker `loadImage` hardening; TCP-socket UAF; GC `reference_map` soundness;
  contextual-keyword Phase 2.
- Audible pan verification (manual Windows listen).
