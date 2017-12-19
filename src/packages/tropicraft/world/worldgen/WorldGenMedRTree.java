package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import tropicraft.world.biomes.BiomeGenTropicraft;

import java.util.Arrays;
import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;

public class WorldGenMedRTree extends TCGenBase {

	private boolean gen = false;
	private int woodBlockId = TropicraftBlocks.treeWood.blockID;
	private int leafBlockId = TropicraftBlocks.tropicsLeaves.blockID;

	public WorldGenMedRTree(World world, Random random, boolean flag) {
		super(world, random, true);
		gen = flag;
	}
	
	public WorldGenMedRTree(World world, Random random, boolean flag, boolean rainforestOnly) {
		super(world, random, rainforestOnly);
		gen = flag;
	}

	@Override
	public boolean generate(int i, int j, int k) 
	{	
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if(gen)
		{
			if(isRainforestOnly && !BiomeGenTropicraft.rainforestBiomes.contains(biome))
			{
				return false;
			}
		}
		if(gen)
		{
			j = getTerrainHeightAt(i, k);
		}
		
		int bid = worldObj.getBlockId(i, j - 1, k);
		if(bid != Block.dirt.blockID && bid != Block.grass.blockID)
		{
			return false;
		}
		bid = worldObj.getBlockId(i + 1, j - 1, k);
		if(bid != Block.dirt.blockID && bid != Block.grass.blockID)
		{
			return false;
		}
		bid = worldObj.getBlockId(i - 1, j - 1, k);
		if(bid != Block.dirt.blockID && bid != Block.grass.blockID)
		{
			return false;
		}
		bid = worldObj.getBlockId(i, j - 1, k + 1);
		if(bid != Block.dirt.blockID && bid != Block.grass.blockID)
		{
			return false;
		}
		bid = worldObj.getBlockId(i, j - 1, k - 1);
		if(bid != Block.dirt.blockID && bid != Block.grass.blockID)
		{
			return false;
		}
		
		int tMod = 1;
		
		int height = rand.nextInt(8) + 10;
		
		int bs1 = height - rand.nextInt(3) - 2;
		
		int xm = 0;
		int zm = 0;
		
		if(rand.nextBoolean())
		{
			xm = rand.nextBoolean() ? -1 : 1;
			if(rand.nextBoolean())
			{
				zm = rand.nextBoolean() ? -1 : 1;
			}
		}
		else
		{
			zm = rand.nextBoolean() ? -1 : 1;
			if(rand.nextBoolean())
			{
				xm = rand.nextBoolean() ? -1 : 1;
			}
		}
		
		int xb = xm;
		int zb = zm;
		
		if(xb == 0)
		{
			xb = 1;
		}
		if(zb == 0)
		{
			zb = 1;
		}

		int tbx = i;
		int tbz = k;
		
		for(int y = j; y <= j + height; y++)
		{
			if(y - j > tMod)
			{
				tbx += xm;
				tbz += zm;
				tMod *= 2;
			}
			int id = worldObj.getBlockId(tbz, y, tbz);
			if(id != 0 && id != Block.tallGrass.blockID && id != leafBlockId)
			{
				return false;
			}
		}
		
		int tx = i;
		int tz = k;
		
		for(int y = j; y <= j + height; y++)
		{
			if(y - j > tMod)
			{
				tx += xm;
				tz += zm;
				tMod *= 2;
			}
			worldObj.setBlock(tx, y, tz, woodBlockId, 1, 3);
			worldObj.setBlock(tx - 1, y, tz, woodBlockId, 1, 3);
			worldObj.setBlock(tx + 1, y, tz, woodBlockId, 1, 3);
			worldObj.setBlock(tx, y, tz - 1, woodBlockId, 1, 3);
			worldObj.setBlock(tx, y, tz + 1, woodBlockId, 1, 3);
			if(y - j == bs1)
			{
				int ox = tx + rand.nextInt(4) - 3;
				int oz = tz + rand.nextInt(4) - 3;
				int leafSize = rand.nextInt(4) + 4;
				genCircle(ox, y + 2, oz, leafSize, 0, leafBlockId, 3, false);
				genCircle(ox, y + 1, oz, leafSize + 1, leafSize - 1, leafBlockId, 3, false);			
				int branches = rand.nextInt(4) + 4;
				for(int b = 0; b < branches; b++)
				{
					double randAngle = randAngle();
					int branchLength = rand.nextInt(leafSize - 2) + 2;
					int ax = (int) Math.round((branchLength * Math.sin(randAngle)) + ox);
					int az = (int) Math.round((branchLength * Math.cos(randAngle)) + oz); 
					checkAndPlaceBlockLine(new int[] {tx, y, tz}, new int[] {ax, y + 1, az}, woodBlockId, 1, Arrays.asList(0, Block.leaves.blockID, Block.vine.blockID));
				}	
				for(int ttx = ox - leafSize; ttx < ox + leafSize; ttx++)
				{
					for(int ttz = oz - leafSize; ttz < oz + leafSize; ttz++)
					{
						for(int ty = y; ty <= y + 2; ty++)
						{
							if(rand.nextInt(3) == 0)
							{
								genVines(ttx, ty, ttz);
							}
						}
					}
				}
			}
			if(y - j == height)
			{
				int ox = tx + rand.nextInt(4) - 3;
				int oz = tz + rand.nextInt(4) - 3;
				int leafSize = rand.nextInt(4) + 4;
				genCircle(ox, y + 2, oz, leafSize, 0, leafBlockId, 3, false);
				genCircle(ox, y + 1, oz, leafSize + 1, leafSize - 1, leafBlockId, 3, false);	
				int branches = rand.nextInt(4) + 5;
				for(int b = 0; b < branches; b++)
				{
					double randAngle = randAngle();
					int branchLength = rand.nextInt(leafSize - 2) + 2;
					int ax = (int) Math.round((branchLength * Math.sin(randAngle)) + ox);
					int az = (int) Math.round((branchLength * Math.cos(randAngle)) + oz); 
					checkAndPlaceBlockLine(new int[] {tx, y, tz}, new int[] {ax, y + 1, az}, woodBlockId, 1, Arrays.asList(0, Block.leaves.blockID, Block.vine.blockID));
				}
				for(int ttx = ox - leafSize; ttx < ox + leafSize; ttx++)
				{
					for(int ttz = oz - leafSize; ttz < oz + leafSize; ttz++)
					{
						for(int ty = y; ty <= y + 2; ty++)
						{
							if(rand.nextInt(3) == 0)
							{
								genVines(ttx, ty, ttz);
							}
						}
					}
				}
			}
		}
		
		return true;
	}
	
	private boolean genVines(int i, int j, int k)
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
