package tropicraft.client.entities;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import tropicraft.Tropicraft;
import tropicraft.TropicraftUtils;
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
    protected void preRenderCallback(EntityLivingBase entityliving, float f) {
        preRenderScale((EIH) entityliving, f);
    }

    @Override

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
    	String texture_path = "head" + (entity.getDataWatcher().getWatchableObjectByte(16) == 0 ? "" : "angry") + "text";
		return TropicraftUtils.bindTextureEntity(texture_path);
	}
}
