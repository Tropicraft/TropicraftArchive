package net.tropicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.tropicraft.mods.TropicraftMod;

public class TropicraftBlock extends Block {

    public TropicraftBlock(int i, Material material) {
        super(i, material);
        this.setCreativeTab(TropicraftMod.tabBlock);
        this.setTextureFile("/tropicalmod/tropiterrain.png");
    }

    public TropicraftBlock(int i, int j, Material material) {
        super(i, j, material);
        this.setCreativeTab(TropicraftMod.tabBlock);
        this.setTextureFile("/tropicalmod/tropiterrain.png");
    }
}
