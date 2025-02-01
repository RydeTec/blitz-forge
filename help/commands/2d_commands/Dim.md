# Dim array_name(index1\[,index2\]\[,index3\]\[,\...\])

# Parameters

  -----------------------------------------------------------------------
  array_name - array name\
  index1 - index number of last variable to be created within that
  particular index-range\
  index2 (optional) - index number of last variable to be created within
  that particular index-range\
  index3 (optional) - index number of last variable to be created within
  that particular index-range\
  \... (optional) - and so on

  -----------------------------------------------------------------------

# Description

  ------------------------------------------------------------------------------------------------------------------------
  Creates an array of the specified type. For example, Dim tiles(10) creates an integer array, Dim tiles#(10) creates a
  float array and Dim tile\$(10) creates a string array.\
  \
  \
  The contents of an array can be accessed using the index notation: 0 - indexn, giving indexn+1 number of elements for
  that particular index range. You should not attempt to access a non-existent element of the array. In debug mode this
  will cause an error stating \'index out of bounds\'. With debug off however, you may get \'illegal memory access\'
  errors, or worse no immediate errors at all.\
  \
  Arrays are global, and must be defined in the main program.\
  \
  Arrays can be re-dimmed by using the Dim statement again with the same array name, but the contents of the array will be
  lost.\
  \
  \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  \*NEW\* BLITZ ARRAYS \*NEW\*\
  \
  Since a recent Blitz update you can now do what are called \'blitz arrays\'. These are very different to a Dim\'d array,
  in the following ways:\
  \
  They use square brackets \[\] instead of the normal round ones ().\
  \
  You declare them like you do Local or Global variables, example: Local myArray\[10\]\
  \
  They cannot be multi-dimensional, and cannot be resized.\
  \
  They can be stored in Type objects.\
  \
  They can be passed to functions.\
  \
  \-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  See also: [Global](Global.htm){.small}, [Local](Local.htm){.small}.

  ------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/Dim.bb)

  -----------------------------------------------------------------------
  ; Dim Example\
  ; Create a collection of 100 random numbers\
  \
  ; Create array\
  Dim nums(99)\
  \
  ; Fill each element with a random number\
  For i = 0 to 99\
  nums(i) = Rand(1,1000)\
  Next

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Dim&ref=comments){target="_blank"}
to view the latest version of this page online
