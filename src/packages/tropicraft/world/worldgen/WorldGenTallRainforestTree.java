package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;
import tropicraft.world.biomes.BiomeGenTropicraft;

public class WorldGenTallRainforestTree extends TCGenBase{

	public int woodBlockId = TropicraftBlocks.treeWood.blockID;
	public int leafBlockID = TropicraftBlocks.tropicsLeaves.blockID;
	
	public WorldGenTallRainforestTree(World world, Random random) {
		super(world, random);
	}

	@Override
	public boolean generate(int i, int j, int k) {

		
		if(!BiomeGenTropicraft.rainforestBiomes.contains(worldObj.getBiomeGenForCoords(i, k)))
		{
			return false;
		}
		
		int height = rand.nextInt(15) + 25 + j;

		if(height + 5 > 127)
		{
			return false;
		}
		
		if(worldObj.getBlockId(i, j - 1, k) != Block.dirt.blockID && worldObj.getBlockId(i, j - 1, k) != Block.grass.blockID)
		{
			return false;
		}
		
		for(int x = i - 1; x <= i + 1; x++)
		{
			for(int z = k - 1; z <= k + 1; z++)
			{
				for(int y = j; y < j + height; y++)
				{
					int bId = worldObj.getBlockId(x, y, z);
					if(bId != 0 && !isLeafId(bId) && bId != Block.tallGrass.blockID && bId != Block.snow.blockID)
					{
						return false;
					}
				}
			}
		}

		genCircle(i, j - 1, k, 1, 0, Block.dirt.blockID, 0, true);
		
		placeBlockCircleLine(new int[] { i, j, k }, new int[] { i, height, k }, 1.3, 0, woodBlockId, 1);
		
		genSphere(i, height, k, 4, leafBlockID, 3);
		
		genSphere(i - 3, height, k , 4, leafBlockID, 3);
		
		genSphere(i + 3, height, k , 4, leafBlockID, 3);
		
		genSphere(i, height, k + 3 , 4, leafBlockID, 3);		
		
		genSphere(i, height, k - 3 , 4, leafBlockID, 3);
		
		return true;
	}

}
