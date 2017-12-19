package tropicraft.blocks.renderhandlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import tropicraft.ModRenderIds;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class TikiTorchRenderHandler implements ISimpleBlockRenderingHandler {

	public TikiTorchRenderHandler() {
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		int metadata = world.getBlockMetadata(x, y, z);

		Tessellator tessellator = Tessellator.instance;
		Icon icon = block.getIcon(0, metadata);
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);

		//    int texX = (textureID.getOriginX() & 0x0f) << 4;
		//    int texY = textureID.getOriginY() & 0xf0;
		float pixX1 = icon.getMinU();//(float) texX / 256.0F;
		float pixX2 = icon.getMaxU();//((float) texX + 15.99F) / 256.0F;
		float pixY1 = icon.getMinV();//(float) texY / 256.0F;
		float pixY2 = icon.getMaxV();//((float) texY + 15.99F) / 256.0F;
		double topX1 = (double) pixX1 + 0.02734375D;
		double topY1 = (double) pixY1 + 0.0234375D;
		double topX2 = (double) pixX1 + 0.03515625D;
		double topY2 = (double) pixY1 + 0.03125D;
		double centerX = x + 0.5D;
		double centerZ = z + 0.5D;
		double width = 0.0625D;
		double height = metadata == 0 ? 0.625D : 1.0D;
		tessellator.addVertexWithUV(centerX - width, y + height, centerZ - width, topX1, topY1);
		tessellator.addVertexWithUV(centerX - width, y + height, centerZ + width, topX1, topY2);
		tessellator.addVertexWithUV(centerX + width, y + height, centerZ + width, topX2, topY2);
		tessellator.addVertexWithUV(centerX + width, y + height, centerZ - width, topX2, topY1);
		tessellator.addVertexWithUV(centerX - width, y + 1.0D, z, (double) pixX1, (double) pixY1);
		tessellator.addVertexWithUV(centerX - width, y, z, (double) pixX1, (double) pixY2);
		tessellator.addVertexWithUV(centerX - width, y, z + 1.0D, (double) pixX2, (double) pixY2);
		tessellator.addVertexWithUV(centerX - width, y + 1.0D, z + 1.0D, (double) pixX2, (double) pixY1);
		tessellator.addVertexWithUV(centerX + width, y + 1.0D, z + 1.0D, (double) pixX1, (double) pixY1);
		tessellator.addVertexWithUV(centerX + width, y, z + 1.0D, (double) pixX1, (double) pixY2);
		tessellator.addVertexWithUV(centerX + width, y, z, (double) pixX2, (double) pixY2);
		tessellator.addVertexWithUV(centerX + width, y + 1.0D, z, (double) pixX2, (double) pixY1);
		tessellator.addVertexWithUV(x, y + 1.0D, centerZ + width, (double) pixX1, (double) pixY1);
		tessellator.addVertexWithUV(x, y, centerZ + width, (double) pixX1, (double) pixY2);
		tessellator.addVertexWithUV(x + 1.0D, y, centerZ + width, (double) pixX2, (double) pixY2);
		tessellator.addVertexWithUV(x + 1.0D, y + 1.0D, centerZ + width, (double) pixX2, (double) pixY1);
		tessellator.addVertexWithUV(x + 1.0D, y + 1.0D, centerZ - width, (double) pixX1, (double) pixY1);
		tessellator.addVertexWithUV(x + 1.0D, y, centerZ - width, (double) pixX1, (double) pixY2);
		tessellator.addVertexWithUV(x, y, centerZ - width, (double) pixX2, (double) pixY2);
		tessellator.addVertexWithUV(x, y + 1.0D, centerZ - width, (double) pixX2, (double) pixY1);
		return true;

	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return ModRenderIds.tikiTorchRenderId;
	}

}