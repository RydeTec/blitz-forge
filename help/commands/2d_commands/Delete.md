# Delete custom_type_name

# Parameters

  ----------------------------------------------------------
  custom_type_name = the custom name of an existing object
  ----------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  If you haven\'t read up on the TYPE command, you might want to do so
  before continuing.\
  \
  Use the Delete command to remove an object from the Type collection.
  Use the commands FIRST, LAST, BEFORE, and NEXT to move the object
  pointer to the object you want to delete, then issue the Delete command
  with the custom Type name. If you wish to delete all objects of a
  certain type, you can use DELETE EACH .\
  \
  This is often used in a FOR \... EACH loop when a collision happens and
  you wish to remove the object (an alien ship object for example) from
  the collection.\
  \
  See also: [Type](Type.htm){.small}, [New](New.htm){.small},
  [Before](Before.htm){.small}, [After](After.htm){.small},
  [First](First.htm){.small}, [Last](Last.htm){.small},
  [Each](Each.htm){.small}, [Insert](Insert.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Delete.bb)

  -----------------------------------------------------------------------
  ; Move them all over 1 (like the description example from TYPE
  command)\
  ; If the chair isn\'t on the screen anymore, delete that chair object
  from the\
  ; collection.\
  \
  For room.chair = Each chair\
  room\\x = room\\x + 1\
  if room\\x \> 640 then\
  Delete room\
  Next

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Delete&ref=comments){target="_blank"}
to view the latest version of this page online
