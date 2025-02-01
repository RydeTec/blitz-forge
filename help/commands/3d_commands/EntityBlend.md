# EntityBlend Entity, Blend

# Parameters

  -----------------------------------------------------------------------
  Entity - Entity handle\
  \
  Blend - Blend mode of the entity.\
  1: Alpha (default)\
  2: Multiply\
  3: Add

  -----------------------------------------------------------------------

# Description

  -----------------------------------------------------------------------
  Sets the blending mode of an entity. This blending mode determines the
  way in which the new RGBA of the pixel being rendered is combined with
  the RGB of the background.\
  \
  To calculate the new RGBA of the pixel being rendered, the texture RGBA
  for the pixel (see
  [TextureBlend](../3d_commands/TextureBlend.htm){.small} for more
  information on how the texture RGBA is calculated) is taken, its alpha
  component multiplied by the entities/brushes (where applicable) alpha
  value and its color compentent multiplied by the entities/brushes
  colour. This is the RGBA which will then be blended into the background
  pixel, and how this is done depends on the EntityBlend value.\
  \
  Alpha:\
  This blends the pixels according to the Alpha value. This is rougly
  done to the formula:\
  \
  Rr = ( An \* Rn ) + ( ( 1.0 - An ) \* Ro )\
  Gr = ( An \* Gn ) + ( ( 1.0 - An ) \* Go )\
  Br = ( An \* Bn ) + ( ( 1.0 - An ) \* Bo )\
  \
  Where R = Red, G = Green, B = Blue, n = new pixel colour values, r =
  resultant colour values, o = old pixel colour values.\
  \
  Alpha blending is the default blending mode and is used with most world
  objects.\
  \
  Multiply:\
  This blend mode will darken the underlying pixels. If you think of each
  RGB value as being on a scale from 0% to 100%, where 0 = 0% and 255 =
  100%, the multiply blend mode will multiply the red, green and blue
  values individually together in order to get the new RGB value, roughly
  according to:\
  \
  Rr = ( ( Rn / 255.0 ) \* ( Ro / 255.0 ) ) \* 255.0\
  Gr = ( ( Gn / 255.0 ) \* ( Go / 255.0 ) ) \* 255.0\
  Br = ( ( Bn / 255.0 ) \* ( Bo / 255.0 ) ) \* 255.0\
  \
  The alpha value has no effect with multiplicative blending. Blending a
  RGB value of 255, 255, 255 will make no difference, while an RGB value
  of 128, 128, 128 will darken the pixels by a factor of 2 and an RGB
  value of 0, 0, 0 will completely blacken out the resultant pixels. An
  RGB value of 0, 255, 255 will remove the red component of the
  underlying pixel while leaving the other color values\
  untouched.\
  \
  Multiply blending is most often used for lightmaps, shadows or anything
  else that needs to \'darken\' the resultant pixels.\
  \
  Add:\
  Additive blending will add the new color values to the old, roughly
  according to:\
  \
  Rr = ( Rn \* An ) + Ro\
  Gr = ( Gn \* An ) + Go\
  Br = ( Bn \* An ) + Bo\
  \
  The resultant RGB values are clipped out at 255, meaning that multiple
  additive effects can quickly cause visible banding from smooth
  gradients.\
  \
  Additive blending is extremely useful for effects such as laser shots
  and fire.\
  \
  See also: [TextureBlend](TextureBlend.htm){.small},
  [EntityAlpha](EntityAlpha.htm){.small}.

  -----------------------------------------------------------------------

# [Example](../3d_examples/EntityBlend.bb)

  -----------------------------------------------------------------------
  Graphics3D 640,480\
  \
  SetBuffer BackBuffer()\
  \
  SeedRnd MilliSecs()\
  \
  \
  ; create camera\
  camera=CreateCamera()\
  CameraClsColor camera,160,160,160\
  PositionEntity camera,0,0,-30\
  middle=CreatePivot()\
  EntityParent camera,middle\
  \
  ; create add texture - white cirlce on a black background\
  For n=0 To 50\
  Color 5+(n\*5),5+(n\*5),5+(n\*5)\
  Oval 10+n,10+n,236-(n\*2),236-(n\*2),1\
  Next\
  \
  blob_tex=CreateTexture(256,256)\
  blob=CreateImage(256,256)\
  GrabImage blob,0,0\
  CopyRect 0,0,256,256,0,0,ImageBuffer(blob),TextureBuffer(blob_tex)\
  FreeImage blob\
  \
  max_blobs=100\
  \
  ; create blobs using add blend mode\
  Dim blobs(max_blobs) ; blob sprites\
  Dim xyblobs#(max_blobs,2) ; blob vector\
  \
  For n=0 To max_blobs\
  blobs(n)=CreateSprite()\
  EntityFX blobs(n),1\
  EntityBlend blobs(n),3 ;set blend mode to add\
  EntityTexture blobs(n),blob_tex\
  xyblobs(n,0)=Rnd(-.1,.1)\
  xyblobs(n,1)=Rnd(-.1,.1)\
  xyblobs(n,2)=Rnd(-.1,.1)\
  EntityColor blobs(n),Rand(0,255),Rand(0,255),Rand(0,255) ;give it a
  colour\
  Next\
  \
  ; create cube texture\
  Color 255,255,255\
  Rect 0,0,256,256,1\
  For n=0 To 7\
  If n=0 Then Color 0,0,0\
  If n=1 Then Color 0,0,255\
  If n=2 Then Color 0,255,0\
  If n=3 Then Color 0,255,255\
  If n=4 Then Color 255,0,0\
  If n=5 Then Color 255,0,255\
  If n=6 Then Color 255,255,0\
  If n=7 Then Color 255,255,255\
  Rect n\*32,n\*32,32,32,1\
  Next\
  Color 0,0,0\
  For n=0 To 255 Step 32\
  Line 0,n,255,n\
  Line n,0,n,255\
  Next\
  \
  cube_tex=CreateTexture(256,256)\
  cube=CreateImage(256,256)\
  GrabImage cube,0,0\
  CopyRect 0,0,256,256,0,0,ImageBuffer(cube),TextureBuffer(cube_tex)\
  FreeImage cube\
  \
  ; create cube\
  cube=CreateCube()\
  ScaleEntity cube,11,11,11\
  EntityTexture cube,cube_tex\
  EntityFX cube,17 ;set fullbright and 2 sided textures\
  EntityBlend cube,2 ;set multiply blend\
  \
  Repeat\
  \
  ; move the blobs around\
  For n=0 To max_blobs\
  MoveEntity blobs(n),xyblobs(n,0),xyblobs(n,1),xyblobs(n,2)\
  ;bounce off sides\
  If EntityX(blobs(n))\<-10 Or EntityX(blobs(n))\>10 Then
  xyblobs(n,0)=-xyblobs(n,0)\
  If EntityY(blobs(n))\<-10 Or EntityY(blobs(n))\>10 Then
  xyblobs(n,1)=-xyblobs(n,1)\
  If EntityZ(blobs(n))\<-10 Or EntityZ(blobs(n))\>10 Then
  xyblobs(n,2)=-xyblobs(n,2)\
  Next\
  \
  ; turn camera\
  TurnEntity middle,.1,.2,.3\
  \
  \
  UpdateWorld\
  RenderWorld\
  Flip\
  \
  \
  Until KeyHit(1)

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=EntityBlend&ref=comments){target="_blank"}
to view the latest version of this page online
