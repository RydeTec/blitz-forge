# Rnd (start#,end#)

# Parameters

  -----------------------------------------------------------------------
  start# = Lowest value to generate\
  end# = Highest value to generate

  -----------------------------------------------------------------------

# Description

  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  This returns either a floating point or integer number of a random value falling between the start and end number. It returns an integer if assiged to an integer variable, and it returns a floating point value if assiged to a floating number variable. The start and end values are inclusive. Be sure to use the SeedRnd command to avoid generating the same random numbers every time the program is run.
  -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

# [Example](../2d_examples/Rnd.bb)

  -----------------------------------------------------------------------
  y=Rnd(0,10) ; Set y to a random integer between 0 and 10\
  y#=Rnd(0,5) ; Set y floating value between 0.000000 and 5.000000

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Rnd&ref=comments){target="_blank"}
to view the latest version of this page online
