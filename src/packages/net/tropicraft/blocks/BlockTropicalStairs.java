package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.tropicraft.mods.TropicraftMod;

public class BlockTropicalStairs extends BlockStairs {

    public BlockTropicalStairs(int i, Block b, int meta) {
        super(i, b, meta);
        this.setLightOpacity(0);
        setRequiresSelfNotify();
        setCreativeTab(TropicraftMod.tabBlock);
    }

    @Override
    public String getTextureFile() {
        return "/tropicalmod/tropiterrain.png";
    }
}
