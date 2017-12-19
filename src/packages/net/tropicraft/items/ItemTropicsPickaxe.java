package net.tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.tropicraft.EnumToolMaterialTropics;
import net.tropicraft.mods.TropicraftMod;

public class ItemTropicsPickaxe extends ItemToolTropics {
    
    /** an array of the blocks this pickaxe is effective against */
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, 
    	Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, 
    	Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, 
    	Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered,
    	TropicraftMod.oreAzurite, TropicraftMod.oreEudialyte, TropicraftMod.oreZircon, TropicraftMod.chunkBlock, TropicraftMod.chunkStairs,
    	TropicraftMod.tropicalDoubleSlab, TropicraftMod.tropicalSingleSlab};


    public ItemTropicsPickaxe(int i, EnumToolMaterialTropics enumtoolmaterial) {
        super(i, 2, enumtoolmaterial, blocksEffectiveAgainst);
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        if (block == Block.obsidian) {
            return toolMaterial.getHarvestLevel() == 3;
        }
        if (block == Block.blockDiamond || block == Block.oreDiamond) {
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if (block == Block.blockGold || block == Block.oreGold) {
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if (block == Block.blockSteel || block == Block.oreIron || block == TropicraftMod.oreAzurite || block == TropicraftMod.oreEudialyte || block == TropicraftMod.oreZircon) {
            return toolMaterial.getHarvestLevel() >= 1;
        }
        if (block == Block.blockLapis || block == Block.oreLapis) {
            return toolMaterial.getHarvestLevel() >= 1;
        }
        if (block == Block.oreRedstone || block == Block.oreRedstoneGlowing) {
            return toolMaterial.getHarvestLevel() >= 2;
        }
        if (block.blockMaterial == Material.rock) {
            return true;
        }
        return block.blockMaterial == Material.iron;
    }
    
    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block != null && (par2Block.blockMaterial == Material.iron || par2Block.blockMaterial == Material.anvil || par2Block.blockMaterial == Material.rock) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(par1ItemStack, par2Block);
    }
}
