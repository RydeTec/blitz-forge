Strict
EnableGC

; Regression test for the HostIP() out-of-bounds read. bbHostIP indexes a vector
; with a caller-supplied index; the bounds check used to be debug-only, so a
; release/test-mode HostIP(0) read host_ips[-1] (OOB). Now out-of-range indices
; return 0 cleanly in both modes.
;
; Offline / CI-safe: it does not depend on name resolution succeeding. CountHostIPs
; may resolve 0 addresses in a hermetic CI (no resolver), which only makes every
; index out-of-range -- exactly what we assert. The socket host-IP path needs no
; audio/graphics device, so unlike most gxruntime code it is reachable under -t.

Test testHostIPOutOfRangeIsSafe()
	; Populate the host-IP cache (may be empty if no resolver is available).
	CountHostIPs( "localhost" )

	; Out-of-range indices must return 0, not read out of bounds.
	Assert( HostIP( 0 ) = 0 )
	Assert( HostIP( -1 ) = 0 )
	Assert( HostIP( 999999 ) = 0 )

	; Reaching here proves the calls did not crash on an OOB dereference.
	Assert( True )
End Test
