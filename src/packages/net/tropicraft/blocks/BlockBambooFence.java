package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

public class BlockBambooFence extends BlockFence {

    public BlockBambooFence(int i) {
        super(i, 0);
        Block.setBurnProperties(i, 5, 20);
        this.setCreativeTab(TropicraftMod.tabBlock);
    }

    @Override
    public String getTextureFile() {
        return "/tropicalmod/tropiterrain.png";
    }

    @Override
    public boolean canConnectFenceTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {

        int var5 = par1IBlockAccess.getBlockId(par2, par3, par4);
        if (var5 != this.blockID && var5 != TropicraftMod.bambooFenceGate.blockID) {
            Block var6 = Block.blocksList[var5];
            return var6 != null && var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock() ? var6.blockMaterial != Material.pumpkin : false;
        } else {
            return true;
        }
    }
    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
    {
        return true;
    }
    
}
