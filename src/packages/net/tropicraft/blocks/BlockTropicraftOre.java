package net.tropicraft.blocks;

import net.minecraft.block.material.Material;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

public class BlockTropicraftOre extends TropicraftBlock {

    public BlockTropicraftOre(int i) {
        super(i, Material.rock);
        this.setCreativeTab(TropicraftMod.tabBlock);
    }

    @Override
    public int idDropped(int i, Random random, int j) {
        if (blockID == TropicraftMod.oreEudialyte.blockID) {
            return TropicraftMod.eudialyteItem.shiftedIndex;
        }
        if (blockID == TropicraftMod.oreZircon.blockID) {
            return TropicraftMod.zirconItem.shiftedIndex;
        }
        if (blockID == TropicraftMod.oreAzurite.blockID) {
            return TropicraftMod.azuriteItem.shiftedIndex;
        } else {
            return blockID;
        }
    }	

    @Override
    public int quantityDropped(Random random) {
        if (blockID == TropicraftMod.oreEudialyte.blockID) {
            return 4 + random.nextInt(3);
        }
        if (blockID == TropicraftMod.oreZircon.blockID) {
            return 2 + random.nextInt(2);
        }
        if (blockID == TropicraftMod.oreAzurite.blockID) {
            return 5 + random.nextInt(7);
        } else {
            return 1;
        }
    } 

    @Override
    public int quantityDroppedWithBonus(int i, Random random) {
        if (i > 0 && blockID != idDropped(0, random, i)) {
            int j = random.nextInt(i + 2) - 1;
            if (j < 0) {
                j = 0;
            }
            return quantityDropped(random) * (j + 1);
        } else {
            return quantityDropped(random);
        }
    }
}
