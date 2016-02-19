package net.tropicraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.tropicraft.registry.BlockRegistry;
import net.tropicraft.registry.ItemRegistry;

public class BlockTropicraftOre extends BlockTropicraft {

	public BlockTropicraftOre() {
		super(Material.rock);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setStepSound(Block.soundTypeStone);
		this.setHarvestLevel("pickaxe", 2);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if (this == BlockRegistry.oreAzurite) {
			return ItemRegistry.azurite;
		} else
			if (this == BlockRegistry.oreEudialyte) {
				return ItemRegistry.eudialyte;
			} else {
				return ItemRegistry.zircon;
			}
	}
	
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random) {
    	if (this == BlockRegistry.oreEudialyte) {
    		return 1 + random.nextInt(4);
    	} else {
    		return 1 + random.nextInt(1);
    	}
    }
	
	@Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return Math.max(0, random.nextInt(fortune + 2) - 1) + 1;
    }
	
	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}
	
    @Override
    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        return MathHelper.getRandomIntegerInRange(rand, 2, 7);
    }
}
