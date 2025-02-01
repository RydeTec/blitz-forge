# SeedRnd seed

# Parameters

  -----------------------------
  seed = valid integer number
  -----------------------------

# Description

  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Computer random number generators are not truly random. They generate numbers based on a seed value (an integer number). If you \'seed\' the random number generator with the same seed, it will always generate the same set of numbers. Use this command to ensure you get a good set of numbers. Usually you set the seed value to a timer or system clock value to ensure that each time the program is run, a new value is seeded. Look at the example for normal usage of this command.
  -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/SeedRnd.bb)

  -----------------------------------------------------------------------------------------
  SeedRnd Millisecs() ; Seed the randomizer with the current system time in milliseconds.
  -----------------------------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=SeedRnd&ref=comments){target="_blank"}
to view the latest version of this page online
