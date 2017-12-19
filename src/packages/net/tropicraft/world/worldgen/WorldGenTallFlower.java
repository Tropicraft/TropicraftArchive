package net.tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenTallFlower extends WorldGenerator
{

    public WorldGenTallFlower(int i)
    {
        plantBlockId = i;
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k)
    {
        for(int l = 0; l < 20; l++)
        {
            int i1 = (i + random.nextInt(8)) - random.nextInt(8);
            int j1 = (j + random.nextInt(4)) - random.nextInt(4);
            int k1 = (k + random.nextInt(8)) - random.nextInt(8);
            if (j1 < world.getHeight() - 1 &&
                    world.isAirBlock(i1, j1, k1) &&
                    world.isAirBlock(i1, j1 + 1, k1) &&
                    Block.blocksList[plantBlockId].canBlockStay(world, i1, j1, k1)) {
                world.setBlockAndMetadata(i1, j1, k1, plantBlockId, 1);
                world.setBlockAndMetadata(i1, j1 + 1, k1, plantBlockId, 0);
            }
            //}
        }

        return true;
    }

    private int plantBlockId;
}
