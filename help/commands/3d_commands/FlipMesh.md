# FlipMesh mesh

# Parameters

  --------------------
  mesh - mesh handle
  --------------------

# Description

  -----------------------------------------------------------------------
  Flips all the triangles in a mesh.\
  \
  This is useful for a couple of reasons. Firstly though, it is important
  to understand a little bit of the theory behind 3D graphics. A 3D
  triangle is represented by three points; only when these points are
  presented to the viewer in a clockwise-fashion is the triangle visible.
  So really, triangles only have one side.\
  \
  Normally, for example in the case of a sphere, a model\'s triangles
  face the inside of the model, so it doesn\'t matter that you can\'t see
  them. However, what about if you wanted to use the sphere as a huge sky
  for your world, i.e. so you only needed to see the inside? In this case
  you would just use FlipMesh.\
  \
  Another use for FlipMesh is to make objects two-sided, so you can see
  them from the inside and outside if you can\'t already. In this case,
  you can copy the original mesh using CopyEntity, specifying the
  original mesh as the parent, and flip it using FlipMesh. You will now
  have two meshes occupying the same space - this will make it
  double-sided, but beware, it will also double the polygon count!\
  \
  The above technique is worth trying when an external modelling program
  has exported a model in such a way that some of the triangles appear to
  be missing.

  -----------------------------------------------------------------------

# [Example](../3d_examples/FlipMesh.bb)

  -----------------------------------------------------------------------
  ; FlipMesh Example\
  ; \-\-\-\-\-\-\-\-\-\-\-\-\-\-\--\
  \
  Graphics3D 640,480\
  SetBuffer BackBuffer()\
  \
  camera=CreateCamera()\
  light=CreateLight()\
  \
  ; Create sphere\
  sphere=CreateSphere()\
  \
  ; Scale sphere\
  ScaleEntity sphere,100,100,100\
  \
  ; Texture sphere with sky texture\
  sky_tex=LoadTexture(\"media/sky.bmp\")\
  EntityTexture sphere,sky_tex\
  \
  ; Flip mesh so we can see the inside of it\
  FlipMesh sphere\
  \
  Color 0,0,0\
  \
  While Not KeyDown( 1 )\
  RenderWorld\
  Text 0,0,\"You are viewing a flipped sphere mesh - makes a great
  sky!\"\
  Flip\
  Wend\
  \
  End

  -----------------------------------------------------------------------

\
[Index](../index.htm){target="_top"}\
\
Click
[here](http://www.blitzbasic.co.nz/b3ddocs/command.php?name=FlipMesh&ref=comments){target="_blank"}
to view the latest version of this page online
