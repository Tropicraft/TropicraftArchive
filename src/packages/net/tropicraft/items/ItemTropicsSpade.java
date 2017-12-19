package net.tropicraft.items;

import net.minecraft.block.Block;
import net.tropicraft.EnumToolMaterialTropics;
import net.tropicraft.mods.TropicraftMod;

public class ItemTropicsSpade extends ItemToolTropics {

    /** an array of the blocks this spade is effective against */
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand, 
    	Block.mycelium, TropicraftMod.purifiedSand};


    public ItemTropicsSpade(int i, EnumToolMaterialTropics enumtoolmaterial) {
        super(i, 1, enumtoolmaterial, blocksEffectiveAgainst);
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        if (block == Block.snow) {
            return true;
        }
        return block == Block.blockSnow;
    }
}
