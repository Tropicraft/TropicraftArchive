package net.tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.world.biomes.BiomeGenTropicraft;

import java.util.Random;

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
				worldObj.setBlock(tx, y, tz, TropicraftMod.tropicalWood.blockID);
				yh = y - j - yh;
				a--;
				tx += xChange;
				worldObj.setBlock(tx, y, tz, TropicraftMod.tropicalWood.blockID);
				tz += zChange;
			}
			worldObj.setBlock(tx, y, tz, TropicraftMod.tropicalWood.blockID);
			if(y > height - 4 + j)
			{
				genCircle(tx, y, tz, lSize, 0, TropicraftMod.tropicLeaves.blockID, 3, false);				
			}
			if(y > height - 3 + j)
			{
				genCircle(tx, y, tz, lSize, 0, TropicraftMod.tropicLeaves.blockID, 3, false);
				lSize--;
			}
		}
		genCircle(tx, y, tz, 1, 0, TropicraftMod.tropicLeaves.blockID, 3, false);
		worldObj.setBlockAndMetadata(tx, y + 1, tz, TropicraftMod.tropicLeaves.blockID, 3);
		
		
		return true;
	}

}
