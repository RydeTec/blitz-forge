Strict

// Regression: _bbObjDeleteEach captured-next invalidation.
//
// `Delete Each T` used to capture `next = obj->next` before deleting obj.
// The field-release cascade inside _bbObjDelete can drop another node's
// ref count to zero and move it from the type's `used` list to its `free`
// list -- including that captured `next`. The classic trigger is a chain
// where a zombie (explicitly Deleted, fields gone) is kept alive only by
// its predecessor's field: deleting the predecessor releases the zombie
// to the free list, the walk steps onto it, follows free-list linkage to
// the free sentinel (type == 0), and terminates -- silently leaving the
// rest of the list undeleted. Before the fix the zombie-chain test below
// left 499 of 1000 nodes alive.
//
// This is the runtime-side root of the long-standing rcce2 CI flake
// (ItemsTest / chain-test exit crashes): early-terminated or free-list-
// wandering sweeps. Fixed by a restart-on-delete walk in _bbObjDeleteEach.

Type ChainNode
	Field nxt.ChainNode
End Type

Function liveChainNodes%()
	Local n% = 0
	Local s.ChainNode = Null
	For s = Each ChainNode
		n = n + 1
	Next
	Return n
End Function

Function buildChain.ChainNode(count%)
	Local head.ChainNode = New ChainNode()
	Local cur.ChainNode = head
	Local i%
	For i = 2 To count
		cur\nxt = New ChainNode()
		cur = cur\nxt
	Next
	Return head
End Function

// Plain forward chain: every node must be swept.
Test deleteEachFullChain()
	Local head.ChainNode = buildChain(1000)
	head = Null
	Delete Each ChainNode
	Assert(liveChainNodes() = 0)
End Test

// Zombie-interleaved chain: Delete every other node first (each zombie's
// memory survives only through its predecessor's field), then sweep. The
// pre-fix walk terminated at the first zombie released mid-sweep.
Test deleteEachZombieChain()
	Local head.ChainNode = buildChain(1000)

	Local z.ChainNode = head\nxt
	While z <> Null
		Local znext.ChainNode = Null
		If z\nxt <> Null Then znext = z\nxt\nxt Else znext = Null
		Delete z
		z = znext
	Wend

	head = Null
	Delete Each ChainNode
	Assert(liveChainNodes() = 0)
End Test

// Sweep twice: the second sweep must see an empty list and no stale state.
Test deleteEachIdempotent()
	Local head.ChainNode = buildChain(50)
	head = Null
	Delete Each ChainNode
	Delete Each ChainNode
	Assert(liveChainNodes() = 0)
End Test
