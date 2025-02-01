# Each type_variable

# Parameters

  ---------------------------------------------------
  type_variable = A previously declared TYPE object
  ---------------------------------------------------

# Description

  -----------------------------------------------------------------------
  If you haven\'t read up on the TYPE command, you might want to do so
  before continuing.\
  \
  The For .. Each loop allows you to walk through each object in the Type
  collection. This is perfect for updating a large group of objects (such
  as a group of alien invaders). Do look over the Type command.\
  \
  See also: [Type](Type.htm){.small}, [New](New.htm){.small},
  [Before](Before.htm){.small}, [After](After.htm){.small},
  [First](First.htm){.small}, [Last](Last.htm){.small},
  [Insert](Insert.htm){.small}, [Delete](Delete.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Each.bb)

  -----------------------------------------------------------------------
  ; Move them all over 1 (like the description example from TYPE
  command)\
  \
  For room.chair = Each chair\
  room\\x = room\\x + 1\
  Next

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Each&ref=comments){target="_blank"}
to view the latest version of this page online
