Strict
EnableGC

// Regression: dual-refcount desync between the classic object pool and the
// GC reference_map.
//
// Delete / Delete Each free pool-side objects directly (_bbObjDelete) and
// used to leave the pointer still counted in the GC reference_map. A later
// _bbRelease on that stale entry hit count==0 and re-ran _bbObjDelete
// against a freed -- possibly recycled -- slot, releasing garbage "fields"
// (heap corruption surfacing as the intermittent exit-time access
// violation in downstream rcce2 CI: ItemsTest / OnlinePlayerChainTest).
// _bbObjDelete now erases the pointer from reference_map, so RefCount on a
// pool-deleted object must read 0 and the eventual scope-end release must
// be a no-op.

Type RefMapNode
	Field nxt.RefMapNode
	Field tag$
End Type

Test gcRefMapClearedByDeleteEach()
	Local n.RefMapNode = New RefMapNode()
	n\tag = "tracked"
	Assert(RefCount(n) >= 1)
	Delete Each RefMapNode
	// Pre-fix this read the stale reference_map count (>= 1).
	Assert(RefCount(n) = 0)
End Test

Test gcRefMapClearedByExplicitDelete()
	Local n.RefMapNode = New RefMapNode()
	Assert(RefCount(n) >= 1)
	Delete n
	Assert(RefCount(n) = 0)
End Test

// Sweep + recycle + release: the historical crash shape. With the erase in
// place the stale releases are no-ops and the recycled occupants survive.
Test gcStaleReleaseAfterRecycleIsBenign()
	Local head.RefMapNode = New RefMapNode()
	Local cur.RefMapNode = head
	Local i%
	For i = 1 To 200
		cur\nxt = New RefMapNode()
		cur\nxt\tag = "n" + i
		cur = cur\nxt
	Next
	cur = Null
	Delete Each RefMapNode

	// Recycle the freed slots with new, differently-tagged occupants.
	Local keep.RefMapNode = New RefMapNode()
	keep\tag = "occupant"
	Local j%
	For j = 1 To 200
		Local filler.RefMapNode = New RefMapNode()
		filler\tag = "f" + j
		filler = Null
	Next

	// Stale release of the swept chain head: must not touch the occupants.
	head = Null
	Assert(keep\tag = "occupant")
End Test
