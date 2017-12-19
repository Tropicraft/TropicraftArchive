package tropicraft.client.entities;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import tropicraft.ModInfo;
import tropicraft.entities.EntityEchinodermEgg;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class RenderEchinodermEgg extends Render {
	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
		EntityEchinodermEgg egg = (EntityEchinodermEgg) entity;
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		loadTexture(egg.getTexture());
		Tessellator tessellator = Tessellator.instance;

		//GL11.glRotatef(180f-this.renderManager.playerViewX, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(180f-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		
		GL11.glScalef(0.25f, 0.25f, 0.25f);

		float f = 0;
		float f1 = 1;
		float f2 = 0;
		float f3 = 1;
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		tessellator.addVertexWithUV((double)(0.0F - f5), (double)(0.0F - f6), 0.0D, (double)f, (double)f3);
		tessellator.addVertexWithUV((double)(f4 - f5), (double)(0.0F - f6), 0.0D, (double)f1, (double)f3);
		tessellator.addVertexWithUV((double)(f4 - f5), (double)(f4 - f6), 0.0D, (double)f1, (double)f2);
		tessellator.addVertexWithUV((double)(0.0F - f5), (double)(f4 - f6), 0.0D, (double)f, (double)f2);
		tessellator.draw();

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
