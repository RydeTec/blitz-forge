# Read variable

# Parameters

  -----------------------------------------------------------------------------------------
  variable = valid variable to match the data type you are reading (integer, string, etc)
  -----------------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This reads the next value in a set of Data statements. This allows you
  to store large blocks of constant information (the structure of tile
  blocks for a game level for example) into easy to maintain Data
  statements, then retrieve them for redrawing, etc.\
  \
  \
  Unlike most BASIC languages, Data statments do not have to be linear
  and sequential. Through the use of Labels (aka \'dot variable\') you
  can create \'banks\' of Data statments with the unique ability to
  \'Restore the Data pointer\' to any one of these labels. Each level
  could have its own label (.level1, .level2, etc). See the Data
  statement, Restore command, or .Label command for more information.\
  \
  \
  Note: You can read multiple values at one time; Read X,Y,Z for
  example.\
  \
  See also: [Data](Data.htm){.small}, [Restore](Restore.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Read.bb)

  -----------------------------------------------------------------------
  ; Sample of read/restore/data/label commands\
  \
  ; Let\'s put the data pointer to the second data set\
  Restore seconddata\
  \
  ; Let\'s print them all to the screen\
  For t = 1 To 10\
  Read num ; Get the next data value in the data stack\
  Print num\
  Next\
  \
  ; Now for the first set of data\
  Restore firstdata\
  \
  ; Let\'s print them all to the screen\
  For t = 1 To 10\
  Read num ; Get the next data value in the data stack\
  Print num\
  Next\
  \
  ; this is the first set of data\
  .firstdata\
  Data 1,2,3,4,5,6,7,8,9,10\
  \
  ; this is the second set of data\
  .seconddata\
  Data 11,12,13,14,15,16,17,18,19,20

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Read&ref=comments){target="_blank"}
to view the latest version of this page online
