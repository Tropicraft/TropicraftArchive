package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;
import tropicraft.world.biomes.BiomeGenTropicraft;

public class WorldGenTualang extends TCGenBase {

	private int woodBlockId = TropicraftBlocks.treeWood.blockID;
	private int leafBlockID = TropicraftBlocks.tropicsLeaves.blockID;
	private int leafMeta = 3;
	private int maxHeight;
	private int baseHeight;
	
	public WorldGenTualang(World world, Random random, int mH, int bH) {
		super(world, random, true);
		maxHeight = mH;
		baseHeight = bH;
	}
	
	public WorldGenTualang(World world, Random random, int mH, int bH, boolean rainforestOnly) {
		super(world, random, rainforestOnly);
		maxHeight = mH;
		baseHeight = bH;
	}

	@Override
	public boolean generate(int i, int j, int k) {
		
		if (isRainforestOnly && !BiomeGenTropicraft.rainforestBiomes.contains(worldObj.getBiomeGenForCoords(i, k)))
		{
			return false;
		}
		
		int height = rand.nextInt(maxHeight - baseHeight) + baseHeight + j;
		int branches = rand.nextInt(6) + 4;
		
		if(height + 6 > 127)
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
		
		for(int x = i - 9; x >= i + 9; x++)
		{
			for(int z = k - 9; z >= k + 9; z++)
			{
				for(int y = height; y < height + 6; y++)
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
		
		placeBlockCircleLine( new int[] { i, j, k }, new int[] { i, height, k }, 1, 0, woodBlockId, 1);
		
		for(int lVar = 0; lVar < branches; lVar++)
		{
			double randAngle = randAngle();
			int branchHeight = rand.nextInt(4) + 2 + height;
			int branchLength = rand.nextInt(5) + 4;
			int ax = (int) Math.round((branchLength * Math.sin(randAngle)) + i);
			int az = (int) Math.round((branchLength * Math.cos(randAngle)) + k);
			
			int tax = (int) Math.round((Math.sin(randAngle)) + i);
			int taz = (int) Math.round((Math.cos(randAngle)) + k);
			
			placeBlockLine( new int[] { tax, height, taz }, new int[] { ax, branchHeight, az }, woodBlockId, 1);
			
			genCircle(ax, branchHeight, az, 2, 1, leafBlockID, leafMeta, false);
			genCircle(ax, branchHeight + 1, az, 3, 2, leafBlockID, leafMeta, false);
		}
		
		return true;
	}

}