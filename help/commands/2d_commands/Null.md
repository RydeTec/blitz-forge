# Null

# Parameters

  ------
  None
  ------

# Description

  -----------------------------------------------------------------------
  Designates a Null Type object. Useful for making a Type variable point
  to nothing or checking if a Type object exists. Also, useful for
  checking that there are still objects left on the end of a Type list
  using the After command.\
  \
  \
  Can used for testing and setting.\
  \
  See also: [Type](Type.htm){.small}, [New](New.htm){.small},
  [Delete](Delete.htm){.small}, [After](After.htm){.small},
  [Before](Before.htm){.small}, [First](First.htm){.small},
  [Last](Last.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Null.bb)

  -----------------------------------------------------------------------
  ; Null example\
  \
  Type Alien\
  Field x,y\
  End Type\
  \
  a.Alien = New Alien\
  If a \<\> Null Then Print \"Alien exists!\"\
  Delete a\
  if a = Null Then Print \"Alien gone!\"

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Null&ref=comments){target="_blank"}
to view the latest version of this page online
