package net.tropicraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import net.tropicraft.blocks.tileentities.TileEntityBambooChest;
import net.tropicraft.mods.TropicraftMod;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class TropicraftChestItemRenderer implements
		ISimpleBlockRenderingHandler {
	
	private static TileEntityBambooChest dummyBambooChest;
	private int id;

	public TropicraftChestItemRenderer() {
		dummyBambooChest = new TileEntityBambooChest();
		id = TropicraftMod.bambooChestModelId;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
        if (modelID == getRenderId()) {        	
            TileEntityRenderer.instance.renderTileEntityAt(dummyBambooChest, 0.0D, 0.0D, 0.0D, 0.0F);
            GL11.glEnable(org.lwjgl.opengl.GL12.GL_RESCALE_NORMAL);
            return;
        }
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return TropicraftMod.bambooChestModelId;
	}

}
