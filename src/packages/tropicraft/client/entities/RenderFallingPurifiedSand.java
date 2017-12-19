package tropicraft.client.entities;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;

import tropicraft.entities.blocks.EntityFallingPurifiedSand;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFallingPurifiedSand extends Render {

	private RenderBlocks renderBlocks = new RenderBlocks();

	public RenderFallingPurifiedSand() {
		shadowSize = 0.5F;
	}

	public void doRenderFallingSand(EntityFallingPurifiedSand entity, double x, double y, double z,
			float f, float f1) {
		Block block = Block.blocksList[entity.blockID];
		if(block != null) {
			renderBlocks.setRenderBoundsFromBlock(block);
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x, (float)y, (float)z);
			loadTexture("/mods/TropicraftMod/textures/blocks/purifiedsand.png");
			World world = entity.getWorld();
			GL11.glDisable(GL11.GL_LIGHTING);
			renderBlockFallingCube(block, entity.metadata, world, MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ));
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

	public void renderBlockFallingCube(Block par1Block, int par6, World par2World, int par3, int par4, int par5)
	{
		float f = 0.5F;
		float f1 = 1.0F;
		float f2 = 0.8F;
		float f3 = 0.6F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setBrightness(par1Block.getMixedBrightnessForBlock(par2World, par3, par4, par5));
		float f4 = 1.0F;
		float f5 = 1.0F;

		if (f5 < f4)
		{
			f5 = f4;
		}

		tessellator.setColorOpaque_F(f * f5, f * f5, f * f5);
		renderBlocks.renderFaceYNeg(par1Block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(par1Block, 0, par6));
		f5 = 1.0F;

		if (f5 < f4)
		{
			f5 = f4;
		}

		tessellator.setColorOpaque_F(f1 * f5, f1 * f5, f1 * f5);
		renderBlocks.renderFaceYPos(par1Block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(par1Block, 1, par6));
		f5 = 1.0F;

		if (f5 < f4)
		{
			f5 = f4;
		}

		tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
		renderBlocks.renderFaceZNeg(par1Block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(par1Block, 2, par6));
		f5 = 1.0F;

		if (f5 < f4)
		{
			f5 = f4;
		}

		tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
		renderBlocks.renderFaceZPos(par1Block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(par1Block, 3, par6));
		f5 = 1.0F;

		if (f5 < f4)
		{
			f5 = f4;
		}

		tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
		renderBlocks.renderFaceXNeg(par1Block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(par1Block, 4, par6));
		f5 = 1.0F;

		if (f5 < f4)
		{
			f5 = f4;
		}

		tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
		renderBlocks.renderFaceXPos(par1Block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(par1Block, 5, par6));
		tessellator.draw();
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		doRenderFallingSand((EntityFallingPurifiedSand) entity, d, d1, d2, f, f1);
	}
}