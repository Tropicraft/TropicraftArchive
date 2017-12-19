package net.tropicraft.entities.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.tropicraft.entities.EntityFallingPurifiedSand;

import org.lwjgl.opengl.GL11;

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
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x, (float)y, (float)z);
			ForgeHooksClient.bindTexture(block.getTextureFile(), 0);
			World world = entity.getWorld();
			GL11.glDisable(GL11.GL_LIGHTING);
			renderBlockFallingCube(block, entity.metadata, world, MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ));
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

	public void renderBlockFallingCube(Block block, int metadata, World world, int i, int j, int k)
	{
		float var6 = 0.5F;
		float var7 = 1.0F;
		float var8 = 0.8F;
		float var9 = 0.6F;
		Tessellator var10 = Tessellator.instance;
		var10.startDrawingQuads();
		var10.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
		float var11 = 1.0F;
		float var12 = 1.0F;

		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var6 * var12, var6 * var12, var6 * var12);
		renderBlocks.renderBottomFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(0, metadata));
		var12 = 1.0F;

		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var7 * var12, var7 * var12, var7 * var12);
		renderBlocks.renderTopFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(1, metadata));
		var12 = 1.0F;

		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var8 * var12, var8 * var12, var8 * var12);
		renderBlocks.renderEastFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(2, metadata));
		var12 = 1.0F;

		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var8 * var12, var8 * var12, var8 * var12);
		renderBlocks.renderWestFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(3, metadata));
		var12 = 1.0F;

		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var9 * var12, var9 * var12, var9 * var12);
		renderBlocks.renderNorthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(4, metadata));
		var12 = 1.0F;

		if(var12 < var11) {
			var12 = var11;
		}

		var10.setColorOpaque_F(var9 * var12, var9 * var12, var9 * var12);
		renderBlocks.renderSouthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(5, metadata));
		var10.draw();
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		doRenderFallingSand((EntityFallingPurifiedSand) entity, d, d1, d2, f, f1);
	}
}
