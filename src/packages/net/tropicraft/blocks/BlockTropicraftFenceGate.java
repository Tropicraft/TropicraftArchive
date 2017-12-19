package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.tropicraft.mods.TropicraftMod;

public class BlockTropicraftFenceGate extends BlockFenceGate {

    public BlockTropicraftFenceGate(int i) {
        super(i, -1);
        Block.setBurnProperties(i, 5, 20);
        this.setCreativeTab(TropicraftMod.tabBlock);
    }

    @Override
    public String getTextureFile() {
        return "/tropicalmod/tropiterrain.png";
    }
}
