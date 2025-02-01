# Data list_of_values

# Parameters

  -----------------------------------------------------------------------------------
  list_of_values = a list of comma delimited values (strings must be inside quotes)
  -----------------------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Data is used to create neat, comma delimited lists of values of a
  constant nature that you will be reading (and probably reusing) during
  the execution of your game. You may store level information (number of
  enemies, stars, etc) there or just about anything you can think of! You
  can easily mix datatypes (strings, integers, floats) in your Data
  statements, as long as they are read properly with the Read command
  later. You will need to use the Restore command to point to the .Label
  that begins your Data statements. See the related commands for more
  information and more examples.\
  \
  See also: [Read](Read.htm){.small}, [Restore](Restore.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Data.bb)

  -----------------------------------------------------------------------
  Print \"Here we go!\"\
  \
  ; Restore to the start of the Data statements\
  Restore startData\
  \
  ; Get the first number which is the total number of users\
  Read Users\
  \
  ; Print them all!\
  For T = 1 To Users\
  Read firstname\$\
  Read age\
  Read accuracy#\
  Read lastname\$\
  Print firstname\$ + \" \" + lastname\$ + \" is \" + age + \" years old
  with \" + accuracy# + \" accuracy!\"\
  Next\
  \
  While Not KeyHit(1)\
  Wend\
  End\
  \
  .startData\
  Data 3\
  Data \"Shane\", 31, 33.3333, \"Monroe\"\
  Data \"Bob\", 28, 12.25, \"Smith\"\
  Data \"Roger\", 54, 66.66, \"Rabbit\"

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Data&ref=comments){target="_blank"}
to view the latest version of this page online
