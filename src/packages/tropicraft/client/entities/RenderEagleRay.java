package tropicraft.client.entities;

import static org.lwjgl.opengl.GL11.glTranslatef;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import tropicraft.TropicraftUtils;
import tropicraft.client.entities.models.ModelEagleRay;

public class RenderEagleRay extends RenderLiving {
	public RenderEagleRay() {
		super(new ModelEagleRay(), 1f);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
		glTranslatef(0f, 1f,0f);
	}

	@Override

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TropicraftUtils.bindTextureEntity("eagleray");
	}
}