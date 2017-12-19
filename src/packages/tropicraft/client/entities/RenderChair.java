package tropicraft.client.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import tropicraft.ModInfo;
import tropicraft.client.entities.models.ModelChair;
import tropicraft.entities.placeable.EntityChair;

public class RenderChair extends Render {
    
    protected ModelBase modelChair;

    public RenderChair() {
        shadowSize = .5F;
        modelChair = new ModelChair();
    }

    public void renderChair(EntityChair entitychair, double d, double d1, double d2,
            float f, float f1) {
        String color = ModInfo.TEXTURE_ENTITY_LOC + "beachstuff/chairtext";
        //retry
        //color = "/mods/TropicraftMod/textures/entities/beachStuff/chairtext";
        StringBuilder sb = new StringBuilder(color);
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
//        float xOffset = (float)Math.cos(Math.toRadians(f + 90))*.25F;
//        float zOffset = (float)Math.sin(Math.toRadians(f + 90))*.25F;
       
        GL11.glTranslatef((float) d, (float) d1 + .3125F, (float) d2);
        //System.out.println(f);
        GL11.glRotatef(f + (180 - f)*2, 0.0F, 1.0F, 0.0F);
        //GL11.glTranslatef(0F	,0, -.35F);
       
        float f2 = (float) entitychair.getTimeSinceHit() - f1;
        float f3 = (float) entitychair.getCurrentDamage() - f1;
        if (f3 < 0.0F) {
            f3 = 0.0F;
        }
        if (f2 > 0.0F) {
            GL11.glRotatef(((MathHelper.sin(f2) * f2 * f3) / 10F) * (float) entitychair.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }
        //loadTexture("/tropicalmod/chairtext.png");
//        float f4 = 0.75F;
//        GL11.glScalef(f4, f4, f4);
//        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        //System.out.println("-" + sb.toString() + "-");
        loadTexture(sb.toString());
        GL11.glScalef(-1F, -1F, 1.0F);
        modelChair.render(entitychair, 0.0F, 1.0F, 0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderChair((EntityChair) entity, d, d1, d2, f, f1);
    }
}
