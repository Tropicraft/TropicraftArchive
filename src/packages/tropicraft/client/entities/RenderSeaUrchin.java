package tropicraft.client.entities;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import tropicraft.client.entities.models.ModelSeaUrchin;
import tropicraft.entities.passive.water.EntitySeaUrchin;

public class RenderSeaUrchin extends RenderLiving {
	/**
	 * Amount freshly hatched sea urchins are scaled down while rendering.
	 */
	public static final float BABY_RENDER_SCALE = 0.5f;

	/**
	 * Amount mature sea urchins are scaled down while rendering.
	 */
	public static final float ADULT_RENDER_SCALE = 1f;

	public RenderSeaUrchin() {
		super(new ModelSeaUrchin(), 0.5f);
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2,
			double par4, double par6, float par8, float par9) {
		super.doRenderLiving(par1EntityLiving, par2, par4, par6, par8, par9);
	}

	@Override
	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {
		EntitySeaUrchin urchin = (EntitySeaUrchin) par1EntityLiving;
		float growthProgress = urchin.getGrowthProgress();
		float scale = BABY_RENDER_SCALE + growthProgress*(ADULT_RENDER_SCALE-BABY_RENDER_SCALE);
		float preScale = 0.5f;
		
		GL11.glScalef(preScale*scale, preScale*scale, preScale*scale);
	}
}
