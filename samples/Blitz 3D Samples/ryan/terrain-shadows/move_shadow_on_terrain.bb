sp=2 ; movement speed

While Not KeyHit(1)
    If KeyDown (30)
        TranslateEntity testbal, -0.1*sp, 0, 0

        ; add xspeed divided by texscale divided by terrain x size
        tex_xpos = tex_xpos - (0.1*sp / tex_scale) / terrain_scale
        PositionTexture shadowtex, tex_xpos , tex_zpos
    EndIf

    If KeyDown (32)
        TranslateEntity testbal, 0.1*sp, 0, 0

        ; add xspeed divided by texscale divided by terrain z size
        tex_xpos = tex_xpos + (0.1*sp / tex_scale) / terrain_scale
        PositionTexture shadowtex, tex_xpos , tex_zpos
    EndIf

    If KeyDown (17)
        TranslateEntity testbal, 0, 0, 0.1*sp

        ; add xspeed divided by texscale divided by terrain x size
        tex_zpos = tex_zpos - (0.1*sp / tex_scale) / terrain_scale
        PositionTexture shadowtex, tex_xpos , tex_zpos
    EndIf

    If KeyDown (31)
        TranslateEntity testbal, 0, 0,-0.1*sp

        ; add xspeed divided by texscale divided by terrain z size
        tex_zpos = tex_zpos + (0.1*sp / tex_scale) / terrain_scale
        PositionTexture shadowtex, tex_xpos , tex_zpos
    EndIf

    cx# = EntityX(testbal)
    cy# = EntityY(testbal)
    cz# = EntityZ(testbal)

    ctx#=TerrainX(terrain,cx#,cy#,cz#)
    cty#=TerrainY(terrain,cx#,cy#,cz#)+1
    ctz#=TerrainZ(terrain,cx#,cy#,cz#)

    PositionEntity testbal,ctx,cty,ctz

    UpdateWorld 
    RenderWorld
    Flip
Wend
End