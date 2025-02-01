# LoadSound (filename\$, is3D)

# Parameters

  -----------------------------------------------------------------------
  filename\$ - name of sound file. Formats supported: ogg\
  is3D - 0 for 2D sound, 1 for 3D sound. Default: 0

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  This command loads a sound file into memory. It returns a BBSound type
  if successful, or Null if there was a problem loading the sound. You
  must assign the value this returns to a variable (preferably a Global
  variable) for subsequent playback using (PlaySound). Look at the
  example.\
  \
  For better performance, use
  [StreamSound](../2d_commands/StreamSound.htm) instead of LoadSound for
  large sound files.

  -----------------------------------------------------------------------

# [Example](../2d_examples/LoadSound.bb)

  -----------------------------------------------------------------------
  ; Assign a global variable for the sound\
  Global sndPlayerDie\
  \
  ; Load the sound file into memory\
  \
  sndPlayerDie=LoadSound(\"sounds/die.ogg\")\
  \
  ; Play the sound\
  \
  PlaySound sndPlayerDie\

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
