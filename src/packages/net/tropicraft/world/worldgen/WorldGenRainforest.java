package net.tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public abstract class WorldGenRainforest extends WorldGenerator {

	public void place(World world, int i, int j, int k, int id)
	{
		world.setBlockWithNotify(i, j, k, id);
	}

	public void place(World world, int i, int j, int k, Block b)
	{
		world.setBlockWithNotify(i, j, k, b.blockID);
	}

	public void placeWithMetadata(World world, int i, int j, int k,
			int blockID, int l) {
		world.setBlockAndMetadataWithNotify(i, j, k, blockID, l);		
	}

	public void genCircle(World world, int i, int j, int k, double distance, double distance2, int id, int meta, boolean solid)
	{
		for(int x = (int)(-distance - 2) + i; x < (int)(distance + 2) + i; x++)
		{
			for(int z = (int)(-distance - 2) + k; z < (int)(distance + 2) + k; z++)
			{
				double d = (Math.sqrt(Math.pow(x - i, 2) + Math.pow(z - k, 2)));
				if(d <= distance && d >= distance2)
				{
					if(world.isAirBlock(x, j, z) || solid)
					{
						world.setBlockAndMetadata(x, j, z, id, meta);
					}
				}
			}
		}
	}
	
	public boolean genVines(World worldObj, Random rand, int i, int j, int k)
	{
		int dir = rand.nextInt(4);
		int m = 2;

        do
        {
            if (m > 5)
            {
            	return false;
            }

            if (Block.vine.canPlaceBlockOnSide(worldObj, i, j, k, m) && worldObj.getBlockId(i, j, k) == 0)
            {
                worldObj.setBlockAndMetadata(i, j, k, Block.vine.blockID, 1 << Direction.vineGrowth[Facing.faceToSide[m]]);
                break;
            }

            m++;
        }
        while (true);
        
        int length = rand.nextInt(4) + 4;
        
        for(int y = j - 1; y > j - length; y--)
        {
        	if(worldObj.getBlockId(i, y, k) == 0)
        	{
        		worldObj.setBlockAndMetadata(i, y, k, Block.vine.blockID, 1 << Direction.vineGrowth[Facing.faceToSide[m]]);        	
        	}
        	else
        	{
        		return true;
        	}
        }
        
		return true;
	}
}