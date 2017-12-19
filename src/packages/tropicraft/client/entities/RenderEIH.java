package tropicraft.client.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import tropicraft.client.entities.models.ModelEIH;
import tropicraft.entities.hostile.land.EIH;

public class RenderEIH extends RenderLiving {

    public RenderEIH(ModelEIH modeleih, float f) {
        super(modeleih, f);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        super.doRenderLiving((EIH) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        doRenderLiving((EIH) entity, d, d1, d2, f, f1);
    }

    protected void preRenderScale(EIH entityeih, float f) {
        GL11.glScalef(2.0F, 1.75F, 2.0F);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f) {
        preRenderScale((EIH) entityliving, f);
    }
}
