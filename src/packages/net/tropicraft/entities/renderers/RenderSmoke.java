package net.tropicraft.entities.renderers;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.tropicraft.entities.EntitySmoke;

import org.lwjgl.opengl.GL11;

public class RenderSmoke extends RenderEntity {

    public RenderSmoke() {
    }

    public void doRenderSmoke(EntitySmoke entitySmoke, double d, double d1, double d2,
            float f, float f1) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glScalef(entitySmoke.smokeSize, entitySmoke.smokeSize, entitySmoke.smokeSize);
        loadTexture("/tropicalmod/smoke.png");
        Tessellator tessellator = Tessellator.instance;
        GL11.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glDisable(32826 /*
                 * GL_RESCALE_NORMAL_EXT
                 */);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, entitySmoke.smokeAlpha);
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f8, 0.0F - f9, 0.0D, 0.0F, 0.0F);
        tessellator.addVertexWithUV(f7 - f8, 0.0F - f9, 0.0D, 0.0F, 1.0F);
        tessellator.addVertexWithUV(f7 - f8, 1.0F - f9, 0.0D, 1.0F, 1.0F);
        tessellator.addVertexWithUV(0.0F - f8, 1.0F - f9, 0.0D, 1.0F, 0.0F);
        tessellator.draw();
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        doRenderSmoke((EntitySmoke) entity, d, d1, d2, f, f1);
    }
}
