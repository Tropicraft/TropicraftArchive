package net.tropicraft.blocks.renderhandlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import net.tropicraft.blocks.tileentities.TileEntityLemonSqueezer;
import net.tropicraft.mods.TropicraftMod;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class LemonSqueezerRenderHandler implements ISimpleBlockRenderingHandler {
	private TileEntityLemonSqueezer dummyTileEntity = new TileEntityLemonSqueezer();

	public LemonSqueezerRenderHandler() {
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		TileEntityRenderer.instance.renderTileEntityAt(dummyTileEntity, 0.0, 0.0, 0.0, 0f);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		TileEntityLemonSqueezer squeezer = (TileEntityLemonSqueezer) world.getBlockTileEntity(x, y, z);
		TileEntityRenderer.instance.renderTileEntity(squeezer, 0.0f);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return TropicraftMod.lemonSqueezerRenderId;
	}

}
