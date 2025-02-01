# Field variable

# Parameters

  -------------------------------
  variable = any legal variable
  -------------------------------

# Description

  -----------------------------------------------------------------------
  If you haven\'t read up on the TYPE command, you might want to do so
  before continuing.\
  \
  When you define a custom Type, you need to assign some variables to
  track within in. Using the Field command within the Type .. End Type
  commands, you set a variable that can be used for EACH object created
  with the NEW command.\
  \
  See also: [Type](Type.htm){.small}, [End Type](End){.small
  type.htm=""}.

  -----------------------------------------------------------------------

# [Example](../2d_examples/Field.bb)

  -----------------------------------------------------------------------
  ; Define the CHAIR Type\
  \
  Type CHAIR\
  Field X\
  Field Y\
  Field HEIGHT\
  End Type\
  \
  ; Create 100 new chairs using FOR \... NEXT using the collection name
  of ROOM\
  \
  For tempx = 1 to 10\
  For tempy = 1 to 10\
  room.chair = New Chair\
  room\\x = tempx\
  room\\y = tempy\
  room\\height = Rnd(0,10) ; set a random height 0 to 10\
  Next\
  Next\
  \
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
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=Field&ref=comments){target="_blank"}
to view the latest version of this page online
