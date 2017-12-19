package net.tropicraft.entities.renderers;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.tropicraft.entities.EntityLavaBall;
import net.tropicraft.entities.models.ModelLavaBall;

import org.lwjgl.opengl.GL11;

public class RenderLavaBall extends Render {

    protected ModelLavaBall ball;

    public RenderLavaBall() {
        shadowSize = .5F;
        ball = new ModelLavaBall();
    }

    public void func_157_a(EntityLavaBall lavaBall, double d, double d1, double d2,
            float f, float f1) {

        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1 - .5F, (float) d2);
        GL11.glScalef(lavaBall.size, lavaBall.size, lavaBall.size);
        loadTexture("/tropicalmod/lavaball.png");
        GL11.glColor3f(1.0F, 1.0F, f1);
        ball.render(lavaBall, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        func_157_a((EntityLavaBall) entity, d, d1, d2, f, f1);
    }
}
