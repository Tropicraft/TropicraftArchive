package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

public class BlockTropicalWood extends TropicraftBlock {

    public BlockTropicalWood(int i) {
        super(i, Material.wood);
        Block.setBurnProperties(i, 5, 5);
        this.setCreativeTab(TropicraftMod.tabBlock);
    }
    
    /**
     * Gets the hardness of block at the given coordinates in the given world, relative to the ability of the given
     * EntityPlayer
     */
    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer entityplayer, World par2World, int par3, int par4, int par5)
    {
        return entityplayer.getCurrentPlayerStrVsBlock(Block.wood, 0) / blockHardness / 30F;
    }

}
