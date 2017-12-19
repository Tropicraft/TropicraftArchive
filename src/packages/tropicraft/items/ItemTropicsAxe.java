package tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import tropicraft.EnumToolMaterialTropics;
import tropicraft.blocks.TropicraftBlocks;

public class ItemTropicsAxe extends ItemToolTropics {

    /** an array of the blocks this axe is effective against */
    public static final Block[] blocksEffectiveAgainst = new Block[] {Block.planks, Block.bookShelf, Block.wood, Block.chest, 
    	Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.pumpkin, Block.pumpkinLantern, TropicraftBlocks.bambooChest, TropicraftBlocks.tropicsBuildingBlock, 
        TropicraftBlocks.treeWood};

    
    public ItemTropicsAxe(int i, String imageName, EnumToolMaterialTropics enumtoolmaterial) {
        super(i, 3, enumtoolmaterial, blocksEffectiveAgainst);
        this.imageName = imageName;
    }
    
    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block != null && (par2Block.blockMaterial == Material.wood || par2Block.blockMaterial == Material.plants || par2Block.blockMaterial == Material.vine) ? this.efficiencyOnProperMaterial : super.getStrVsBlock(par1ItemStack, par2Block);
    }

	@Override
	public String getImageName() {
		return "axe_" + imageName;
	}    
}
