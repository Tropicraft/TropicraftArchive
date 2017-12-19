package tropicraft.blocks.renderhandlers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import tropicraft.ModRenderIds;
import tropicraft.blocks.BlockFlowerPot;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.blocks.tileentities.TileEntityFlowerPot;
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
        renderBlockFlowerpot(renderer, world, (BlockFlowerPot)block, x, y, z);
        return true;

    }

    @Override
    public boolean shouldRender3DInInventory() {
        return false;
    }

    @Override
    public int getRenderId() {
        return ModRenderIds.flowerPotRenderId;
    }
    
    /**
     * Renders flower pot
     */
    public boolean renderBlockFlowerpot(RenderBlocks rb, IBlockAccess blockAccess, BlockFlowerPot par1BlockFlowerPot, int par2, int par3, int par4) {
        rb.renderStandardBlock(par1BlockFlowerPot, par2, par3, par4);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(par1BlockFlowerPot.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
        float f = 1.0F;
        int l = par1BlockFlowerPot.colorMultiplier(blockAccess, par2, par3, par4);
        Icon icon = rb.getBlockIconFromSide(par1BlockFlowerPot, 0);
        float f1 = (float)(l >> 16 & 255) / 255.0F;
        float f2 = (float)(l >> 8 & 255) / 255.0F;
        float f3 = (float)(l & 255) / 255.0F;
        float f4;
        float f5;

        if (EntityRenderer.anaglyphEnable)
        {
            f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
            float f6 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
            f5 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
            f1 = f4;
            f2 = f6;
            f3 = f5;
        }

        tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        f4 = 0.1865F;
        rb.renderFaceXPos(par1BlockFlowerPot, (double)((float)par2 - 0.5F + f4), (double)par3, (double)par4, icon);
        rb.renderFaceXNeg(par1BlockFlowerPot, (double)((float)par2 + 0.5F - f4), (double)par3, (double)par4, icon);
        rb.renderFaceZPos(par1BlockFlowerPot, (double)par2, (double)par3, (double)((float)par4 - 0.5F + f4), icon);
        rb.renderFaceZNeg(par1BlockFlowerPot, (double)par2, (double)par3, (double)((float)par4 + 0.5F - f4), icon);
        rb.renderFaceYPos(par1BlockFlowerPot, (double)par2, (double)((float)par3 - 0.5F + f4 + 0.1875F), (double)par4, rb.getBlockIcon(Block.dirt));
       
        TileEntityFlowerPot te = (TileEntityFlowerPot)blockAccess.getBlockTileEntity(par2, par3, par4);

        int var19 = te.getDamage();

        if (var19 != 0)
        {
            f5 = 0.0F;
            float var15 = 4.0F;
            float var16 = 0.0F;
            Block var17 = null;

            tessellator.addTranslation(f5 / 16.0F, var15 / 16.0F, var16 / 16.0F);

            if (var19 > 0 && var19 < 17) {
                rb.drawCrossedSquares(TropicraftBlocks.tropicsFlowers, var19 - 1, (double)par2, (double)par3, (double)par4, 0.75F);
            } else
                if (var19 == 17) {
                    rb.drawCrossedSquares(TropicraftBlocks.tallFlower, 7, (double)par2, (double)par3, (double)par4, 0.75F);
                    rb.drawCrossedSquares(TropicraftBlocks.tallFlower, 9, (double)par2, (double)(par3 + 0.75), (double)par4, 0.75F);
                } else
                    if (var19 == 18) {
                        rb.drawCrossedSquares(TropicraftBlocks.tallFlower, 7, (double)par2, (double)par3, (double)par4, 0.75F);
                        rb.drawCrossedSquares(TropicraftBlocks.tallFlower, 15, (double)par2, (double)(par3 + 0.75), (double)par4, 0.75F);
                    } else
                        if (var19 > 18 && var19 < 24) {
                            rb.drawCrossedSquares(TropicraftBlocks.saplings, var19 - 19, (double)par2, (double)par3, (double)par4, 0.75F);
                        }

            tessellator.addTranslation(-f5 / 16.0F, -var15 / 16.0F, -var16 / 16.0F);
        }

        return true;
    }

}