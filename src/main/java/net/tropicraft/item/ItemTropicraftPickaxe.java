package net.tropicraft.item;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.tropicraft.block.BlockTropicraftOre;
import net.tropicraft.registry.BlockRegistry;

import com.google.common.collect.Sets;

public class ItemTropicraftPickaxe extends ItemPickaxe {

	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.activator_rail, Blocks.coal_ore, Blocks.cobblestone, Blocks.detector_rail, 
			Blocks.diamond_block, Blocks.diamond_ore, Blocks.double_stone_slab, Blocks.golden_rail, Blocks.gold_block, Blocks.gold_ore, Blocks.ice, Blocks.iron_block, 
			Blocks.iron_ore, Blocks.lapis_block, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.mossy_cobblestone, Blocks.netherrack, Blocks.packed_ice, Blocks.rail, 
			Blocks.redstone_ore, Blocks.sandstone, Blocks.red_sandstone, Blocks.stone, Blocks.stone_slab,
			BlockRegistry.oreBlock, BlockRegistry.oreAzurite, BlockRegistry.oreEudialyte, BlockRegistry.oreZircon});
	
	public ItemTropicraftPickaxe(ToolMaterial material) {
		super(material);
	}
	
    @Override
    public boolean canHarvestBlock(Block block) {
        if (block instanceof BlockTropicraftOre) {
            if (block == BlockRegistry.oreAzurite) {
                return this.toolMaterial.getHarvestLevel() == 3;
            } else {
                return this.toolMaterial.getHarvestLevel() > 1;
            }
        }

        return super.canHarvestBlock(block);
    }
    
    public float getStrVsBlock(ItemStack stack, Block block) {
        return EFFECTIVE_ON.contains(block) ? this.efficiencyOnProperMaterial : 1.0F;
    }

}
