# Restore label

# Parameters

  ---------------------------------------
  label = any valid and exisiting label
  ---------------------------------------

# Description

  -----------------------------------------------------------------------
  When using Data statements to store large blocks of constants for use
  with the Read command, it is necessary to denote the start of the Data
  with a .Label. The Restore command moves the \'pointer\' to the first
  Data statement\'s value following the designated label. You MUST use
  the Restore label command prior to using the Read command. This method
  allows you to store groups of Data statements non-sequentially. Its
  different (if you are used to other BASIC languages) but you will find
  it most flexible. See the example and other commands related to Data
  command.\
  \
  See also: [Read](Read.htm){.small}, [Data](Data.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Restore.bb)

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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Restore&ref=comments){target="_blank"}
to view the latest version of this page online
