The Swift Shadow System is a Blitz library designed for casting realtime shadows from animated and static meshes onto arbitrary geometry.

NOTE, MD2 meshes are not at this time supported.

B3D bone animated meshes and 3DS segmented animated meshes are both supported.

In plain english, my shadow system can cast multiple shadows from your animated characters onto your level. It can also cast shadows from static objects placed in your level to add detail, such as a desk.

How it works:
-------------
The system provides three diffrent types of shadows for maximum efficiency. The first two types are dynamic shadows. dynamic shadows change every frame, warping to fit the local level geometry as your object moves around. Of these two, there are rendered shadows, and textured shadows.

Rendered shadows are the most processing-intensive types of shadows. A rendered shadow is created by making a texture that approximates the shape of the object that is casting the shadow, and then applying it to the level surface.

Textured shadows on the other hand are cheats. Textured shadows take a premade texture you specify and project it onto the level. You can use textured shadows to cast round shadows from your characters that deform around geometry. Textured shadows are therefor a lot faster than rendered shadows.

The last shadow type is static. And thus they are called static shadows. Static shadows once rendered are never updated, though can be deleted and recreated on an as-needed basis. Static shadow use rendered shadow maps just like rendered shadows, and so are realistic looking. However, once rendered a static shadow never changes. It is always rendered with the same shadow map, and always to the same polygons, unless you delete it. This makes static shadows very fast, and appropriate to use throughout your world to make level decorations like desks, trees, and buildings cast shadows. The cost of a static shadow is merely the cost of an additional entity, a few extra polygons, and a texture.

In addition to supporting diffrent types of shadows to speed rendering, my shadow system also has several important optimizations. The most basic of which is the light radius.

When you want to create a shadow with my shadow system you specify every obejct that you want to cast shadows, every object that you wan tto recieve shadows, and every object that you want to be a shadow casting light.

For each of these light sources, you can specify a max radius of effect. Any object outside this radius will not cast a shadow for that specific light. This allows you to fill you level with lights and automatically cast shadows from only those nearest the player.

The second more advanced optimization the system does is that is re-renders shadow maps for dynamic rendered shadows only when the angle between the caster, receiver, and light source has chnaged enough that the shadow map has become too inaccurate a representation of the ideal real shadow. This cuts the amount of times the shadow map needs to be re-rendered greatly, and if neither the caster, light source, or receiver are moving at a particular moment then the shadows will render much more quickly than if any of the three are moving at a high rate of speed.

Below you will find a demo of the system, with a number of options you can play with. Take a look at it, and note that the demo has a high number of casters and receivers... More than you are likely to have in one room at one time in many types of game. So the demo is something of a stress test.


Known issues:
-------------
Note that if you double the number of recivers, the number of casters, or the number of lights, then you double the amount of work which needs to be done on a particular scene. That is why you will notice a drop in framerate when you have two lights flying around. Not only are the lights moving quickly, requirng the shadow maps to be re-rendered almost every frame, but you double the work when you add the second one! In many games, you're much more likely to have several NON-MOVING light sources in a room, and characters which move relatively slowly. So this demo is not neccessarily an accurate picture of what the speed will be like in your particular game.

Also note that the shadow system will work better if your level is broken up into several sections made with individual entities. This is how you should construct your levels anyhow for optimal rendering with modern 3D cards. Doing this allows the system to cull whole groups of polygons with simple boundign sphere tests.

Another thing to be aware of is that this system can only cast shadows ont static meshes. The meshes can rotate and move around, and you can even move the vertices around menaully. But you cannot cast a shadow onto a vertex/bone animated MD2 or B3D. You also cannot cast shadows onto terrains because they too animate their vertices internally to the Blitz 3D engine in such a way that you cannot access their changing locations.

You CAN of course cast shadows from B3D and 3DS animated meshes.

One last issue to be aware of is that with a frame tweening based setup, and a light source which is close to the objects being shadowed, the motion of the shadows over the surfaces may not be completely smooth. If you move the light source farther away, or use a delta time based system, then this will not be an issue.

So check out the demo, and the source code that accompanies it. The source code demonstrates how simple the calls are to activate the shadow system, and gives you a good idea of how well commented and formatted the shadow system source itself is.
