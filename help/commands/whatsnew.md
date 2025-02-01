# What\'s New?

1.90a\
\
\* 5 breaking changes introduced:\
\
[LoadSound](2d_commands/LoadSound.htm) - Load3DSound has been removed,
use LoadSound with the 3D flag instead\
[GetListener](3d_commands/GetListener.htm) - CreateListener has been
removed, use GetListener instead\
PlayMusic - Removed, use LoadSound, LoopSound and PlaySound instead\
SoundPan - Removed\
ChannelPan - Removed\
\
\* 17 new commands introduced:\
\
[Ptr](2d_commands/Ptr.htm) - new command to cast types to pointers\
[Strict](2d_commands/Strict.htm) - new command to enforce strict type
checking\
[StreamSound](2d_commands/StreamSound.htm) - new command to stream sound
from files instead of loading them entirely in memory\
[ErrorLog](2d_commands/ErrorLog.htm) - new command to get the last
internal error as a string\
[GetLineTrace](2d_commands/GetLineTrace.htm) - new command to get the
full stack trace as a string\
[TextureBumpEnvMat](3d_commands/TextureBumpEnvMat.htm) - direct d3d
abstraction of D3DTSS_BUMPENVMATXX\
[TextureBumpEnvScale](3d_commands/TextureBumpEnvScale.htm) - direct d3d
abstraction of D3DTSS_BUMPENVLSCALE\
[TextureBumpEnvOffset](3d_commands/TextureBumpEnvOffset.htm) - direct
d3d abstraction of D3DTSS_BUMPENVLOFFSET\
[TextureLodBias](3d_commands/TextureLodBias.htm) - direct d3d
abstraction of D3DSAMP_MIPMAPLODBIAS\
[SoundRange](3d_commands/SoundRange.htm) - set the near and far bounds a
sound is played\
[ChannelRange](3d_commands/ChannelRange.htm) - set the near and far
bounds a specific channel is played\
[ChannelPos](3d_commands/ChannelPos.htm) - set the 3d position and
velocity of a specific channel\
[ChannelSeek](2d_commands/ChannelSeek.htm) - new command to seek a
channel to a specific position\
[NoTrace](2d_commands/NoTrace.htm) - new command to disable stack
tracing\
[CopyDir](2d_commands/CopyDir.htm) - new command to copy an entire
directory recursively\
[Test](2d_commands/Test.htm) - new command to dictate a Test function\
[Assert](2d_commands/Assert.htm) - new command to assert a statement is
true\
\
\* 4 new capabilities:\
Memory Access Violations - Replaced with descriptive errors. If in debug
mode true Exceptions are thrown, if running in release mode errors are
handled gracefully and accessible via
[ErrorLog](2d_commands/ErrorLog.htm) without disrupting program
execution.\
3D Sound - 3D sounds can be given a position and velocity directly and
don\'t *have* to be attached to an entity. See
[PlaySound](2d_commands/PlaySound.htm) and
[ChannelPos](3d_commands/ChannelPos.htm)\
Sound Looping - can be toggled on and off. See
[LoopSound](2d_commands/LoopSound.htm)\
[ExecFile](2d_commands/ExecFile.htm) - improved to specify params and a
working directory as well has return process handle.\
\
\
\
\-\-\-\-\-\-\-\-\--\
\
1.88\
\
\* 1 command alteration documented:\
\
[SystemProperty](2d_commands/SystemProperty.htm) - new property strings
added\
\
\-\-\-\-\-\-\-\-\--\
\
1.87\
\
\* 1 new command documented:\
\
[HWTexUnits](3d_commands/HWTexUnits.htm)\
\
\* 1 command alteration documented:\
\
[TextureBlend](3d_commands/TextureBlend.htm) - \'multiply 2\' flag 5
added\
\
\* 2 previously undocumented commands now documented:\
\
[SetCubeMode](3d_commands/SetCubeMode.htm)\
[EntityClass](3d_commands/EntityClass.htm)\
\
\-\-\-\-\-\-\-\-\--\
\
1.85\
\
\* 6 new commands documented:\
\
[SetCubeFace](3d_commands/SetCubeFace.htm)\
[GfxDriverCaps3D](3d_commands/GfxDriverCaps3D.htm)\
[GetEntityBrush](3d_commands/GetEntityBrush.htm)\
[GetSurfaceBrush](3d_commands/GetSurfaceBrush.htm)\
[GetBrushTexture](3d_commands/GetBrushTexture.htm)\
[TextureName](3d_commands/TextureName.htm)\
\
\* 1 command alteration documented:\
\
[CreateTexture](3d_commands/CreateTexture.htm) / @
[LoadAnimTexture](3d_commands/LoadAnimTexture.htm) - cubic environment
map flag 128 added\
\
\* 6 previously undocumented commands now documented:\
\
[VertexAlpha](3d_commands/VertexAlpha.htm)\
[DeltaYaw](3d_commands/DeltaYaw.htm)\
[DeltaPitch](3d_commands/DeltaPitch.htm)\
[GetMatElement](3d_commands/GetMatElement.htm)\
[VectorYaw](3d_commands/VectorYaw.htm)\
[VectorPitch](3d_commands/VectorPitch.htm)\
\
\-\-\-\-\-\-\-\-\--\
\
1.83\
\
\* 3 command alterations documented:\
\
[EntityRadius](3d_commands/EntityRadius.htm) - optional y_value#
parameter added\
[EntityFX](3d_commands/EntityFX.htm) - flag 32 added\
[VertexColor](3d_commands/VertexColor.htm) - optional alpha# parameter
added\
\
\-\-\-\-\-\-\-\-\--\
\
1.80\
\
\* 8 new commands documented:\
\
[RndSeed](2d_commands/RndSeed.htm)\
[JoyHat](2d_commands/JoyHat.htm)\
[SetGamma](2d_commands/SetGamma.htm)\
[UpdateGamma](2d_commands/UpdateGamma.htm)\
[GammaRed](2d_commands/GammaRed.htm)\
[GammaGreen](2d_commands/GammaGreen.htm)\
[GammaBlue](2d_commands/GammaBlue.htm)\
[SetAnimTime](3d_commands/SetAnimTime.htm)
