package net.tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.tropicraft.blocks.BlockTropicraftFlower;

import java.util.Random;

public class WorldGenTropicraftFlowers extends WorldGenerator
{
	boolean isRainforestGen;

	private int plantBlockId;

	public WorldGenTropicraftFlowers(int i, boolean b)
	{
		plantBlockId = i;
		isRainforestGen = b;
	}

    @Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		if(!isRainforestGen)
		{
			for(int l = 0; l < 35; l++)
			{
				int j1 = (i + random.nextInt(8)) - random.nextInt(8);
				int l1 = (j + random.nextInt(4)) - random.nextInt(4);
				int j2 = (k + random.nextInt(8)) - random.nextInt(8);
				if(/*(plantBlockId == 188  || plantBlockId == 189) && */world.isAirBlock(j1, l1, j2) && ((BlockTropicraftFlower)Block.blocksList[plantBlockId]).canBlockStay(world, j1, l1, j2))
				{
					world.setBlockAndMetadata(j1, l1, j2, plantBlockId, random.nextInt(7));
				}
			}
		}
		else
		{
			for(int l = 0; l < 35; l++)
			{
				int j1 = (i + random.nextInt(8)) - random.nextInt(8);
				int l1 = (j + random.nextInt(4)) - random.nextInt(4);
				int j2 = (k + random.nextInt(8)) - random.nextInt(8);
				if(/*(plantBlockId == 188  || plantBlockId == 189) && */world.isAirBlock(j1, l1, j2) && ((BlockTropicraftFlower)Block.blocksList[plantBlockId]).canBlockStay(world, j1, l1, j2))
				{
					world.setBlockAndMetadata(j1, l1, j2, plantBlockId, random.nextInt(7) + 7);
				}
			}
		}

			/*     for(int i1 = 0; i1 < 9; i1++)
        {
            int k1 = (i + random.nextInt(8)) - random.nextInt(8);
            int i2 = (j + random.nextInt(4)) - random.nextInt(4);
            int k2 = (k + random.nextInt(8)) - random.nextInt(8);
            if(plantBlockId == 191 && world.isAirBlock(k1, i2, k2) && ((BlockTropicraftFlower)Block.blocksList[plantBlockId]).canBlockStay(world, k1, i2, k2))
            {
                world.setBlock(k1, i2, k2, plantBlockId);
            }
        }*/

			return true;
	}
}
