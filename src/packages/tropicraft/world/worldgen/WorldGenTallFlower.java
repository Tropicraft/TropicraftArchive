package tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;

public class WorldGenTallFlower extends WorldGenerator
{

    private int plantBlockId = TropicraftBlocks.tallFlower.blockID;
    private int plantBottomMeta;
    private int plantTopMeta;
    
    public WorldGenTallFlower(int plantBottomMeta, int plantTopMeta)
    {
    	this.plantBottomMeta = plantBottomMeta;
    	this.plantTopMeta = plantTopMeta;
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
                world.setBlock(i1, j1, k1, plantBlockId, plantBottomMeta, 3);
                world.setBlock(i1, j1 + 1, k1, plantBlockId, plantTopMeta, 3);
            }
            //}
        }

        return true;
    }
}
