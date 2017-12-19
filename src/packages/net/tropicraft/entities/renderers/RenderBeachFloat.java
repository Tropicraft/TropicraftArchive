package net.tropicraft.entities.renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.tropicraft.entities.EntityBeachFloat;
import net.tropicraft.entities.models.ModelBeachFloat;

import org.lwjgl.opengl.GL11;

public class RenderBeachFloat extends Render {
    
    protected ModelBase modelBeachFloat;

    public RenderBeachFloat() {
        shadowSize = .5F;
        modelBeachFloat = new ModelBeachFloat();
    }

    public void func_157_a(EntityBeachFloat entitychair, double d, double d1, double d2,
            float f, float f1) {
        String color = "/tropicalmod/beachStuff/floattext";
        StringBuilder sb = new StringBuilder(color);
        //System.out.println(entitychair.color);
        switch (entitychair.getColor()) {
            case 0:
                sb.append("blue.png");
                break;
            case 1:
                sb.append("red.png");
                break;
            case 2:
                sb.append("yellow.png");
                break;
            case 3:
                sb.append("pink.png");
                break;
            case 4:
                sb.append("green.png");
                break;
            default:
                break;

        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1 + .90F, (float) d2);
        GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
        float f2 = (float) entitychair.boatTimeSinceHit - f1;
        float f3 = (float) entitychair.boatCurrentDamage - f1;
        if (f3 < 0.0F) {
            f3 = 0.0F;
        }
        if (f2 > 0.0F) {
            GL11.glRotatef(((MathHelper.sin(f2) * f2 * f3) / 10F) * (float) entitychair.boatRockDirection, 1.0F, 0.0F, 0.0F);
        }
        //loadTexture("/tropicalmod/chairtext.png");
        float f4 = 0.75F;
        GL11.glScalef(f4, f4, f4);
        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        loadTexture(sb.toString());
        GL11.glScalef(-1F, -1F, 1.0F);
        modelBeachFloat.render(entitychair, 0.0F, 1.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        func_157_a((EntityBeachFloat) entity, d, d1, d2, f, f1);
    }
}