# Insert

# Parameters

  -------
  None.
  -------

# Description

  -----------------------------------------------------------------------
  I\'m not sure the practical usage of this command, but basically, you
  can control where you INSERT the current TYPE object into the TYPE
  collection. When you create a new Type object with the NEW command, it
  is automatically appended to the END of the collection. Using INSERT
  along with BEFORE and AFTER (and electively FIRST and LAST) to put the
  Type object exactly where you want it. Sounds confusing - and chances
  are likely you\'ll never need this ability. But its here if you need
  it. Check the example.\
  \
  See also: [Type](Type.htm){.small}, [New](New.htm){.small},
  [Before](Before.htm){.small}, [After](After.htm){.small},
  [First](First.htm){.small}, [Last](Last.htm){.small},
  [Each](Each.htm){.small}, [Delete](Delete.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Insert.bb)

  -----------------------------------------------------------------------
  ; INSERT example\
  \
  ; Define a CHAIR type with a created field to track what order it was
  created in.\
  Type CHAIR\
  Field created\
  End Type\
  \
  ; Create 10 chairs, setting created field to the order of creation\
  For t = 1 To 10\
  room.chair= New Chair\
  room\\created = t\
  Next\
  \
  ; Make a NEW chair (the 11th)\
  room.chair= New Chair\
  \
  ; Set its created value to 11\
  room\\created=11\
  \
  ; Now, let\'s insert this chair BEFORE the first one in the collection\
  Insert room Before First Chair\
  \
  ; Let\'s iterate through all the chairs, and show their creation order\
  For room.chair = Each chair\
  Print room\\created\
  Next

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Insert&ref=comments){target="_blank"}
to view the latest version of this page online
