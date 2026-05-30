Strict
EnableGC

; Smoke test for the restored SoundPan / ChannelPan commands. Headless CI has no
; audio device, so LoadSound returns Null and the pan commands no-op -- this
; proves the symbols are registered and tolerate a Null sound/channel without
; crashing. Audible pan correctness is verified manually on Windows (no audio
; device in CI). The corpus sweep (aristoids.bb / spider.bb) is the compile-time
; registration guard.

Test testSoundPanChannelPanResolveAndAreNullSafe()
	Local snd.BBSound = LoadSound( "scripts/fixtures/none.ogg" )
	SoundPan snd, 0.5
	SoundPan snd, -1.0
	SoundPan snd, 2.0   ; out-of-range value must be tolerated (clamped)

	Local chan.BBChannel = PlaySound( snd )
	ChannelPan chan, -0.5

	; Reached here => the commands are registered and did not crash on Null.
	Assert( True )
End Test
