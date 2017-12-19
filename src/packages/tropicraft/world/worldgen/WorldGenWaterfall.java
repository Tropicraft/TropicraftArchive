package tropicraft.world.worldgen;

import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class WorldGenWaterfall extends TCGenBase {

	public WorldGenWaterfall(World world, Random random) {
		super(world, random);
	}

	@Override
	public boolean generate(int i, int j, int k) {
		if(this.worldObj.getBlockId(i, j, k) == Block.stone.blockID)
		{
			int size = rand.nextInt(3) + 3;
			if(this.worldObj.getBlockId(i + 1, j, k) == 0) {
				for(int x = 0; x < size; x++) {
					this.worldObj.setBlock(i, j, k + x, TropicraftBlocks.tropicsWaterFlowing.blockID);
					if(this.worldObj.getBlockId(i + 1, j, k + x + 1) != 0 || this.worldObj.getBlockId(i, j, k + x + 1) == 0) {
						break;
					}
				}
			}
			
			if(this.worldObj.getBlockId(i - 1, j, k) == 0) {
				for(int x = 0; x < size; x++) {
					this.worldObj.setBlock(i, j, k + x, TropicraftBlocks.tropicsWaterFlowing.blockID);
					if(this.worldObj.getBlockId(i - 1, j, k + x + 1) != 0 || this.worldObj.getBlockId(i, j, k + x + 1) == 0) {
						break;
					}
				}
			}
			
			if(this.worldObj.getBlockId(i, j, k + 1) == 0) {
				for(int x = 0; x < size; x++) {
					this.worldObj.setBlock(i + x, j, k, TropicraftBlocks.tropicsWaterFlowing.blockID);
					if(this.worldObj.getBlockId(i + x + 1, j, k + 1) != 0 || this.worldObj.getBlockId(i + x + 1, j, k) == 0) {
						break;
					}
				}
			}
			
			if(this.worldObj.getBlockId(i, j, k - 1) == 0) {
				for(int x = 0; x < size; x++) {
					this.worldObj.setBlock(i + x, j, k, TropicraftBlocks.tropicsWaterFlowing.blockID);
					if(this.worldObj.getBlockId(i + x - 1, j, k + 1) != 0 || this.worldObj.getBlockId(i + x + 1, j, k) == 0) {
						break;
					}
				}
			}
		}
		
		return true;
	}

}
