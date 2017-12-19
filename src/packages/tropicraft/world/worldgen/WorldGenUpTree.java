package tropicraft.world.worldgen;

import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class WorldGenUpTree extends TCGenBase {

	public WorldGenUpTree(World world, Random random) {
		super(world, random);
	}

	@Override
	public boolean generate(int i, int j, int k) {
		if(this.worldObj.getBlockId(i, j - 1, k) != Block.grass.blockID && this.worldObj.getBlockId(i, j - 1, k) != Block.dirt.blockID) {
			return false;
		}
		
		int height = rand.nextInt(4) + 6;
		
		for(int y = j; y < j + height; y++) {
			if(!this.isLeafId(this.worldObj.getBlockId(i, y, k)) && this.worldObj.getBlockId(i, y, k) != 0) {
				return false;
			}
		}
		
		for(int y = j; y < j + height; y++) {
			this.worldObj.setBlock(i, y, k, TropicraftBlocks.treeWood.blockID, 1, 3);
			if(rand.nextInt(5) == 0) {
				int x = rand.nextInt(3) - 1 + i;
				int z = k;
				if(x - i == 0) {
					z += rand.nextBoolean() ? 1 : -1;
				}
				this.worldObj.setBlock(x, y, z, TropicraftBlocks.tropicsLeaves.blockID, 3, 3);
			}
			
			if(y == j + height - 1) {
				this.worldObj.setBlock(i + 1, y, k, TropicraftBlocks.treeWood.blockID, 1, 3);
				this.worldObj.setBlock(i - 1, y, k, TropicraftBlocks.treeWood.blockID, 1, 3);
				this.worldObj.setBlock(i, y, k + 1, TropicraftBlocks.treeWood.blockID, 1, 3);
				this.worldObj.setBlock(i, y, k - 1, TropicraftBlocks.treeWood.blockID, 1, 3);
			}
		}
		
		int radius = rand.nextInt(2) + 3;
		
		this.genCircle(i, j + height, k, radius, 0, TropicraftBlocks.tropicsLeaves.blockID, 3, false);
		this.genCircle(i, j + height + 1, k, radius + 2, radius, TropicraftBlocks.tropicsLeaves.blockID, 3, false);
		this.genCircle(i, j + height + 2, k, radius + 3, radius + 2, TropicraftBlocks.tropicsLeaves.blockID, 3, false);
		
		return true;
	}

}
