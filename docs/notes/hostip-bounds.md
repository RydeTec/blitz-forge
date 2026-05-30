# Working note — Bounds-check HostIP() in release mode (close an OOB read)

Branch: `fix/hostip-bounds`
Type: Bug / Reliability (security-adjacent)

## Goal (restated)

`bbHostIP(int index)` (`src/blitzrc/bbruntime/bbsockets.cpp:317-324`) indexes a
`std::vector<int>` with a BASIC-supplied index but validates it **only in debug
mode**. In release builds (what every shipped game runs) the check is skipped and
`host_ips[index-1]` executes unchecked: `HostIP(0)` reads `host_ips[-1]`,
`HostIP(big)` reads far past the vector — an out-of-bounds heap read (UB) that can
crash or leak adjacent heap bytes into a BASIC integer. This is the exact
debug-only-guard defect class already closed for banks/streams/lists; `bbsockets.cpp`
is the unswept remainder. INTENT #6 ("trust the runtime").

## Approach

Add a release-mode guard mirroring the file's sibling helpers (`debugUDPStream`
etc.) and the bank/stream idiom: validate in BOTH modes — debug → `RTEX`, release →
`errorLog` + `return 0` — before the dereference. Braced branches (the RTEX macro
ends in `;`, so an unbraced `if(debug) RTEX(); else` would break — see
[[runtime-error-guard-idiom]]).

```cpp
int bbHostIP( int index ){
	if( index<1 || index>(int)host_ips.size() ){
		if( debug ){
			RTEX( "Host index out of range" );
		} else {
			errorLog.push_back( "HostIP: Host index out of range" );
		}
		return 0;
	}
	return host_ips[index-1];
}
```

## Files touched

- `src/blitzrc/bbruntime/bbsockets.cpp` — `bbHostIP` guard.
- `tests/SocketTest.bb` — new; pins the bounds contract (CI-safe/offline).
- `docs/notes/hostip-bounds.md` — this note.

Windows runtime only (sockets are WSA-gated); macOS path unaffected. No ABI change.

## Verification / testability

Unlike audio/graphics, the socket host-IP path needs no device init, so it IS
reachable in headless `-t` (where `debug` is false → the release/errorLog path).
`tests/SocketTest.bb` asserts `HostIP(0)`, `HostIP(-1)`, and `HostIP(huge)` return
0 (out-of-range, regardless of how many IPs resolved — `host_ips` may be empty in a
hermetic CI with no resolver, which only makes every index out-of-range). Pre-fix,
`HostIP(0)` in release/test mode is an OOB read of `host_ips[-1]`; post-fix it
returns 0 cleanly — the regression guard.

## Acceptance criteria

- `HostIP(0)`, `HostIP(-1)`, `HostIP(n>CountHostIPs())` return 0 in release/test
  mode (no OOB); raise a clean RuntimeError in debug mode.
- Valid `HostIP(1..CountHostIPs())` path unchanged.
- New `tests/SocketTest.bb` passes under `blitzcc -t`, offline/CI-safe.
- `test.bat` green; corpus sweep unaffected; build 0 errors.

## Trade-offs / rejected

- **Rejected this iteration: contextual `Release`/`Recast`/`Reference` (Phase 2).**
  These are PREFIX OPERATORS in `parseUniExpr` (`Release[.Type] <operand>`), unlike
  `Test` (no expression-keyword case). Making them contextual needs
  expression-position disambiguation (operand-follows = keyword vs operator/
  terminator-follows = identifier) inside the unary-expression parser — M-effort,
  real ambiguity risk, and `Release` is GC-surface. Not a clean high-confidence
  one-iteration change. Deferred with this finding.
- **Rejected: gxSoundSample::load short-filename crash** — real, but NOT reachable
  in headless `-t` (audio not initialized), so no CI regression test; manual-only
  verification. Deferred.

## Deferred follow-ups

- Phase 2 contextual keywords (`Release`/`Recast`/`Reference`) — needs a focused
  feasibility pass on the parseUniExpr expression-position disambiguation.
- `gxSoundSample::load` short-filename crash (manual-verify) + the sibling
  filename-parsing audit (gxSoundStream missing ext validation; ddutil substr).
- Other Class B builtins; GC reference_map soundness; manual audible-pan check.
