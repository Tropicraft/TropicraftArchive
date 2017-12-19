package net.tropicraft.blocks.renderhandlers;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import net.tropicraft.blocks.tileentities.TileEntityEIHMixer;
import net.tropicraft.blocks.tileentities.TileEntityLemonSqueezer;
import net.tropicraft.mods.TropicraftMod;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class EIHMixerRenderHandler implements ISimpleBlockRenderingHandler {
	private TileEntityEIHMixer dummyTileEntity = new TileEntityEIHMixer();

	public EIHMixerRenderHandler() {
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		TileEntityRenderer.instance.renderTileEntityAt(dummyTileEntity, 0.0, 0.0, 0.0, 0f);
		GL11.glPopMatrix();
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
		return TropicraftMod.eihMixerRenderId;
	}

}
