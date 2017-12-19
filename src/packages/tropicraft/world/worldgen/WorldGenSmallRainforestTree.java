package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;
import tropicraft.world.biomes.BiomeGenTropicraft;

public class WorldGenSmallRainforestTree extends TCGenBase {

	public WorldGenSmallRainforestTree(World world, Random random) {
		super(world, random);
	}

	@Override
	public boolean generate(int i, int j, int k) {

		if(!BiomeGenTropicraft.rainforestBiomes.contains(worldObj.getBiomeGenForCoords(i, k)))
		{
			return false;
		}
		
		j = getTerrainHeightAt(i, k);
		
		int height = rand.nextInt(9) + 6;
		
		int belowId = worldObj.getBlockId(i, j - 1, k);

		if(height + j + 3 > 127)
		{
			return false;
		}
		
		if(worldObj.getBlockId(i, j - 1, k) != Block.dirt.blockID && worldObj.getBlockId(i, j - 1, k) != Block.grass.blockID)
		{
			return false;
		}
		
		int tx = i;
		int tz = k;
		
		int yh = 0;
		
		int xChange = rand.nextInt(2);
		int zChange = rand.nextInt(2);
		
		int a = (int)((int) height * 0.5 - 1);
		
		int ty;
		int ta = a;
		int ttx = tx;
		int ttz = tz;

		for(ty = j; ty < j + height; ty++)
		{
			if(ty - j - yh == ta)
			{
				if(worldObj.getBlockId(ttx, ty, ttz) != 0 && worldObj.getBlockId(ttx, ty, ttz) != Block.tallGrass.blockID)
				{
					return false;
				}
				yh = ty - j - yh;
				ta--;
				ttx += xChange;
				ttz += zChange;
			}
			if(worldObj.getBlockId(ttx, ty, ttz) != 0 && worldObj.getBlockId(ttx, ty, ttz) != Block.tallGrass.blockID && !isLeafId(worldObj.getBlockId(ttx, ty, ttz)))
			{
				return false;
			}
		}
		
		int y;
		int lSize = 3;
		
		for(y = j; y < j + height; y++)
		{
			if(y - j - yh == a)
			{
				worldObj.setBlock(tx, y, tz, TropicraftBlocks.treeWood.blockID);
				yh = y - j - yh;
				a--;
				tx += xChange;
				worldObj.setBlock(tx, y, tz, TropicraftBlocks.treeWood.blockID);
				tz += zChange;
			}
			worldObj.setBlock(tx, y, tz, TropicraftBlocks.treeWood.blockID);
			if(y > height - 4 + j)
			{
				genCircle(tx, y, tz, lSize, 0, TropicraftBlocks.treeWood.blockID, 3, false);				
			}
			if(y > height - 3 + j)
			{
				genCircle(tx, y, tz, lSize, 0, TropicraftBlocks.treeWood.blockID, 3, false);
				lSize--;
			}
		}
		genCircle(tx, y, tz, 1, 0, TropicraftBlocks.treeWood.blockID, 3, false);
		worldObj.setBlock(tx, y + 1, tz, TropicraftBlocks.treeWood.blockID, 3, 3);
		
		
		return true;
	}

}
