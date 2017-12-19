package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public abstract class WorldGenRainforest extends WorldGenerator {

	public void place(World world, int i, int j, int k, int id)
	{
		world.setBlock(i, j, k, id);
	}

	public void place(World world, int i, int j, int k, Block b)
	{
		world.setBlock(i, j, k, b.blockID);
	}

	public void placeWithMetadata(World world, int i, int j, int k,
			int blockID, int l) {
		world.setBlock(i, j, k, blockID, l, 3);		
	}

	public void genCircle(World world, int i, int j, int k, double distance, double distance2, int id, int meta, boolean solid)
	{
		for(int x = (int)(-distance - 2) + i; x < (int)(distance + 2) + i; x++)
		{
			for(int z = (int)(-distance - 2) + k; z < (int)(distance + 2) + k; z++)
			{
				double d = (x - i) * (x - i) + (z - k) * (z - k);
				if(d <= distance * distance && d >= distance2 * distance2)
				{
					if(world.isAirBlock(x, j, z) || solid)
					{
						world.setBlock(x, j, z, id, meta, 3);
					}
				}
			}
		}
	}
	
	public boolean genVines(World worldObj, Random rand, int i, int j, int k)
	{
		int m = 2;

        do
        {
            if (m > 5)
            {
            	return false;
            }

            if (Block.vine.canPlaceBlockOnSide(worldObj, i, j, k, m) && worldObj.getBlockId(i, j, k) == 0)
            {
                worldObj.setBlock(i, j, k, Block.vine.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[m]], 3);
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
        		worldObj.setBlock(i, y, k, Block.vine.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[m]], 3);        	
        	}
        	else
        	{
        		return true;
        	}
        }
        
		return true;
	}
}