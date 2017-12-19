package tropicraft.blocks.renderhandlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import tropicraft.blocks.tileentities.TileEntityBambooChest;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BambooChestItemRenderer implements
		ISimpleBlockRenderingHandler {
	
	private static TileEntity dummyTileEnt;
	private int id;

	public BambooChestItemRenderer(int parID, TileEntity parTEnt) {
		dummyTileEnt = parTEnt;
		id = parID;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
        if (modelID == getRenderId()) {        	
            TileEntityRenderer.instance.renderTileEntityAt(dummyTileEnt, 0.0D, 0.0D, 0.0D, 0.0F);
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
		return id;
	}

}
