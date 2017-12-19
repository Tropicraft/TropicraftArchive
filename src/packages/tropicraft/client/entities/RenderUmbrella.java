package tropicraft.client.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import tropicraft.Tropicraft;
import tropicraft.TropicraftUtils;
import tropicraft.client.entities.models.ModelUmbrella;
import tropicraft.entities.placeable.EntityUmbrella;

public class RenderUmbrella extends Render {
    
    protected ModelBase modelUmbrella;

    public RenderUmbrella() {
        shadowSize = 2F;
        modelUmbrella = new ModelUmbrella();
    }

    public void func_157_a(EntityUmbrella entityumbrella, double d, double d1, double d2,
            float f, float f1) {
    	String color = "beachstuff/umbrellatext";
        StringBuilder sb = new StringBuilder(color);

        switch (entityumbrella.getColor()) {
            case 0:
                sb.append("blue");
                break;
            case 1:
                sb.append("red");
                break;
            case 2:
                sb.append("yellow");
                break;
            case 3:
                sb.append("pink");
                break;
            case 4:
                sb.append("green");
                break;
            default:
                break;
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
        float f2 = (float) entityumbrella.umbrellaTimeSinceHit - f1;
        float f3 = (float) entityumbrella.umbrellaCurrentDamage - f1;
        if (f3 < 0.0F) {
            f3 = 0.0F;
        }
        if (f2 > 0.0F) {
            GL11.glRotatef(((MathHelper.sin(f2) * f2 * f3) / 10F) * (float) entityumbrella.umbrellaRockDirection, 1.0F, 0.0F, 0.0F);
        }

        float f4 = 0.75F;
        GL11.glScalef(f4, f4, f4);
        GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);
        TropicraftUtils.bindTextureEntity(sb.toString());
        GL11.glScalef(-1F, -1F, 1.0F);
        modelUmbrella.render(entityumbrella, 0.0F, 1.0F, 0.1F, 0.0F, 0.0F, 0.25F);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        func_157_a((EntityUmbrella) entity, d, d1, d2, f, f1);
    }

	@Override

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
