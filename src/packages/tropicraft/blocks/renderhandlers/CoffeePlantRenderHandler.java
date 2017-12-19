package tropicraft.blocks.renderhandlers;

import tropicraft.ModRenderIds;
import tropicraft.blocks.BlockCoffeePlant;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class CoffeePlantRenderHandler implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		renderer.overrideBlockTexture = ((BlockCoffeePlant)block).stemIcon;
		renderer.renderCrossedSquares(block, x, y, z);
		renderer.overrideBlockTexture = null;
		renderer.renderStandardBlock(block, x, y, z);
		return true;
	}
	

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return ModRenderIds.coffeePlantRenderId;
	}
}
