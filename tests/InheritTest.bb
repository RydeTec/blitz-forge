Strict
EnableGC

Type Base
    Field baseVar$
End Type

Type SecondBase
    Field baseVar$
End Type

Type Inherit.Base
    Field inheritVar$
End Type

Type SecondInherit.Base
    Field secondInheritVar$
End Type

Type DoubleInherit.Inherit
    Field doubleInheritVar$
End Type

Type UnrelatedType
    Field unrelatedField$
End Type

Test testCastDown()
    ; Single cast down
    Local base1.Inherit = new Inherit()
    Local base2.Base = base1

    ; Double cast down
    Local base3.DoubleInherit = new DoubleInherit()
    Local base4.Inherit = base3
    Local base5.Base = base4

    ; Direct 2-tier cast down
    Local base6.DoubleInherit = new DoubleInherit()
    Local base7.Base = base6

    ; Incorrect cast to unrelated classes inheriting the same base
    ;base8.Inherit = new Inherit()
    ;base9.SecondInherit = base8 ; Should fail

    ; Incorrect cast to unrelated base class
    ;base10.Inherit = new Inherit()
    ;base11.SecondBase = base10 ; Should fail
End Test

Test testCastUp()
    ; All of these failures could be overidden by Recast but accessing the derived fields 
    ; would cause a stackoverflow because the instances were all created as the base class

    ; Single cast up
    ;base1.Base = new Base()
    ;base2.Inherit = base1 ; Fails as var was declared originally as Base

    ; Double cast up
    ;base3.Base = new Base()
    ;base4.Inherit = base3 ; Fails as var was declared originally as Base
    ;base5.DoubleInherit = base4 ; Fails as var was declared originally as Base

    ; Direct 2-tier cast up
    ;base6.Base = new Base()
    ;base7.DoubleInherit = base6 ; Fails as var was declared originally as Base

    ; Properly casting back up requires the inital instance creation be that derived class
    Local base1.Inherit = new Inherit()
    Local base2.Base = base1
    Local base3.Inherit = Recast.Inherit(base2)

    Local base4.SecondBase = new SecondBase()
    ;base5.Inherit = Recast.Inherit(base4) ; Fails due to type incompatibilty
End Test

Test testDoubleCastUp()
    ; Full path casting
    Local base1.DoubleInherit = new DoubleInherit()
    Local base2.Inherit = base1
    Local base3.Base = base2
    Local base4.Inherit = Recast.Inherit(base3)
    Local base5.DoubleInherit = Recast.DoubleInherit(base4)

    ; Full path down, direct up
    Local base6.DoubleInherit = new DoubleInherit()
    Local base7.Inherit = base1
    Local base8.Base = base2
    Local base9.DoubleInherit = Recast.DoubleInherit(base8)

    ; Direct casting
    Local base10.DoubleInherit = new DoubleInherit()
    Local base11.Base = base10
    Local base12.DoubleInherit = Recast.DoubleInherit(base11)
End Test

Test testCastDownIntegrity()
    Local base2.Inherit = new Inherit()
    base2\inheritVar = "that"

    ; Cast down
    Local base3.Base = base2

    ; Make sure casted down class didn't have value set for parent field
    Assert(base3\baseVar = "")

    ; Set parent field
    Local base4.Inherit = new Inherit()
    base4\baseVar = "foo"

    ; Make sure parent field set
    Assert(base4\baseVar = "foo")

    ; Cast down
    Local base5.Base = base4

    ; Check value is still accurate
    Assert(base5\baseVar = "foo")
End Test

Test testCastUpIntegrity()
    ; Set initial values
    Local base1.Inherit = new Inherit()
    base1\baseVar = "foo"
    base1\inheritVar = "bar"

    Assert(base1\baseVar = "foo")
    Assert(base1\inheritVar = "bar")

    ; Cast down to base and mutate
    Local base2.Base = base1
    base2\baseVar = "fizz"

    ; Make sure both variables were mutated
    Assert(base2\baseVar = "fizz")
    Assert(base1\baseVar = "fizz")

    ; Cast back up and check values
    Local base3.Inherit = Recast.Inherit(base2)
    
    Assert(base3\baseVar = "fizz")
    Assert(base3\inheritVar = "bar")
End Test

Test testDoubleCastDownIntegrity()
    Local base1.DoubleInherit = new DoubleInherit()
    base1\doubleInheritVar = "fizz"
    base1\inheritVar = "bar"
    base1\baseVar = "foo"

    Local base2.Inherit = base1
    Assert(base2\baseVar = "foo")
    Assert(base2\inheritVar = "bar")

    Local base3.Base = base2
    Assert(base3\baseVar = "foo")

    Local base4.DoubleInherit = new DoubleInherit()
    base4\doubleInheritVar = "fizz"
    base4\inheritVar = "bar"
    base4\baseVar = "foo"

    Local base5.Base = base4
    Assert(base5\baseVar = "foo")
End Test

Test testDoubleCastUpIntegrity()
    Local base2.Inherit = new Inherit()
    base2\baseVar = "foo"
    base2\inheritVar = "bar"

    ; Cast down
    Local base5.Base = base2

    ; Cast back up
    Local base9.Inherit = Recast.Inherit(base5)

    Assert(base9\baseVar = "foo")
    Assert(base9\inheritVar = "bar")


    Local base3.DoubleInherit = new DoubleInherit()
    base3\baseVar = "foo"
    base3\inheritVar = "bar"
    base3\doubleInheritVar = "fizz"

    ; Full path cast down
    Local base6.Inherit = base3
    Local base7.Base = base6

    ; Full path cast up
    Local base10.Inherit = Recast.Inherit(base7)
    Local base11.DoubleInherit = Recast.DoubleInherit(base10)

    Assert(base10\baseVar = "foo")
    Assert(base10\inheritVar = "bar")
    Assert(base11\baseVar = "foo")
    Assert(base11\inheritVar = "bar")
    Assert(base11\doubleInheritVar = "fizz")


    Local base4.DoubleInherit = new DoubleInherit()
    base4\baseVar = "foo"
    base4\inheritVar = "bar"
    base4\doubleInheritVar = "buzz"

    ; Direct cast down
    Local base8.Base = base4

    ; Direct Cast up
    Local base12.DoubleInherit = Recast.DoubleInherit(base8)
    
    Assert(base12\baseVar = "foo")
    Assert(base12\inheritVar = "bar")
    Assert(base12\doubleInheritVar = "buzz")
End Test

Test testIncorrectCrossCasting()
    ; Cast down and cast up to wrong type
    Local base2.Inherit = new Inherit()
    base2\baseVar = "foo"
    base2\inheritVar = "bar"

    ; Cast down
    Local base5.Base = base2

    ; Cast back up
    Local base9.SecondInherit = Recast.SecondInherit(base5)

    Assert(base9\baseVar = "foo")

    ; Because Recast is memory unsafe the secondInheritVar is in the second memory slot of the object
    ; where inheritVar was so now the secondInheritVar is showing that value because it is referencing
    ; the second memory slot.
    Assert(base9\secondInheritVar = "bar")
End Test