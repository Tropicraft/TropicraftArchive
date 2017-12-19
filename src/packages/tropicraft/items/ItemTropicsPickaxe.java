package tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import tropicraft.EnumToolMaterialTropics;
import tropicraft.blocks.TropicraftBlocks;

public class ItemTropicsPickaxe extends ItemToolTropics {
    
    /** an array of the blocks this pickaxe is effective against */
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, 
    	Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold, 
    	Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, 
    	Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered,
    	TropicraftBlocks.tropiOres, TropicraftBlocks.chunkOHead, TropicraftBlocks.chunkStairs, TropicraftBlocks.tropicsDoubleSlab, TropicraftBlocks.tropicsSingleSlab};


    public ItemTropicsPickaxe(int i, String imageName, EnumToolMaterialTropics enumtoolmaterial) {
        super(i, 2, enumtoolmaterial, blocksEffectiveAgainst);
        this.imageName = imageName;
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
        if (block == Block.blockIron || block == Block.oreIron || block == TropicraftBlocks.tropiOres) {
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

	@Override
	public String getImageName() {
		return "pickaxe_" + imageName;
	}
}
