package net.tropicraft.blocks.renderhandlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.tropicraft.blocks.BlockFlowerPotTC;
import net.tropicraft.blocks.tileentities.TileEntityFlowerPot;
import net.tropicraft.mods.TropicraftMod;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class FlowerPotRenderHandler implements ISimpleBlockRenderingHandler {

	public FlowerPotRenderHandler() {
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		renderBlockFlowerpot(renderer, world, (BlockFlowerPotTC)block, x, y, z);
		return true;

	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return TropicraftMod.flowerPotRenderId;
	}

	/**
	 * Renders flower pot
	 */
	public boolean renderBlockFlowerpot(RenderBlocks rb, IBlockAccess blockAccess, BlockFlowerPotTC par1BlockFlowerPot, int par2, int par3, int par4)
	{
		rb.renderStandardBlock(par1BlockFlowerPot, par2, par3, par4);
		Tessellator var5 = Tessellator.instance;
		var5.setBrightness(par1BlockFlowerPot.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var6 = 1.0F;
		int var7 = par1BlockFlowerPot.colorMultiplier(blockAccess, par2, par3, par4);
		int var8 = par1BlockFlowerPot.getBlockTextureFromSide(0);
		float var9 = (float)(var7 >> 16 & 255) / 255.0F;
		float var10 = (float)(var7 >> 8 & 255) / 255.0F;
		float var11 = (float)(var7 & 255) / 255.0F;
		float var12;
		float var14;

		var5.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
		var12 = 0.1865F;
		rb.renderSouthFace(par1BlockFlowerPot, (double)((float)par2 - 0.5F + var12), (double)par3, (double)par4, var8);
		rb.renderNorthFace(par1BlockFlowerPot, (double)((float)par2 + 0.5F - var12), (double)par3, (double)par4, var8);
		rb.renderWestFace(par1BlockFlowerPot, (double)par2, (double)par3, (double)((float)par4 - 0.5F + var12), var8);
		rb.renderEastFace(par1BlockFlowerPot, (double)par2, (double)par3, (double)((float)par4 + 0.5F - var12), var8);
		rb.renderTopFace(par1BlockFlowerPot, (double)par2, (double)((float)par3 - 0.5F + var12 + 0.1875F), (double)par4, TropicraftMod.bambooBlock.blockIndexInTexture);

		TileEntityFlowerPot te = (TileEntityFlowerPot)blockAccess.getBlockTileEntity(par2, par3, par4);

		int var19 = te.getDamage();

		if (var19 != 0)
		{
			var14 = 0.0F;
			float var15 = 4.0F;
			float var16 = 0.0F;
			Block var17 = null;

			var5.addTranslation(var14 / 16.0F, var15 / 16.0F, var16 / 16.0F);

			if (var19 > 0 && var19 < 17) {
				rb.drawCrossedSquares(TropicraftMod.flowerCollection1, var19 - 1, (double)par2, (double)par3, (double)par4, 0.75F);
			} else 
				if (var19 == 17) {
					rb.drawCrossedSquares(TropicraftMod.pineappleFlower, 1, (double)par2, (double)par3, (double)par4, 0.75F);
					rb.drawCrossedSquares(TropicraftMod.pineappleFlower, 0, (double)par2, (double)(par3 + 0.75), (double)par4, 0.75F);
				} else
					if (var19 == 18) {
						rb.drawCrossedSquares(TropicraftMod.irisFlower, 1, (double)par2, (double)par3, (double)par4, 0.75F);
						rb.drawCrossedSquares(TropicraftMod.irisFlower, 0, (double)par2, (double)(par3 + 0.75), (double)par4, 0.75F);
					} else
						if (var19 > 18 && var19 < 24) {
							rb.drawCrossedSquares(TropicraftMod.saplings, var19 - 19, (double)par2, (double)par3, (double)par4, 0.75F);
						}

			var5.addTranslation(-var14 / 16.0F, -var15 / 16.0F, -var16 / 16.0F);
		}

		return true;
	}

}
