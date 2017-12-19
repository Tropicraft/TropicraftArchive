package tropicraft.client.entities;

import static org.lwjgl.opengl.GL11.*;

import tropicraft.client.entities.models.ModelEagleRay;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;

public class RenderEagleRay extends RenderLiving {
	public RenderEagleRay() {
		super(new ModelEagleRay(), 1f);
	}

	@Override
	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {
		glTranslatef(0f, 1f,0f);
	}
}