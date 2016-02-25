package net.tropicraft.item;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Sets;

public class ItemTropicraftAxe extends ItemAxe {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, 
    		Blocks.lit_pumpkin, Blocks.melon_block, Blocks.ladder});

    public ItemTropicraftAxe(ToolMaterial material)
    {
        super(material);
    }

    public float getStrVsBlock(ItemStack stack, Block block)
    {
        return block.getMaterial() != Material.wood && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine ? getTropiStrVsBlock(stack, block) : this.efficiencyOnProperMaterial;
    }
    
    public float getTropiStrVsBlock(ItemStack stack, Block block) {
        return EFFECTIVE_ON.contains(block) ? this.efficiencyOnProperMaterial : 1.0F;
    }

}
