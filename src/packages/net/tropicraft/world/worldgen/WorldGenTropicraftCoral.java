package net.tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.tropicraft.blocks.BlockTropicraftCoral;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

public class WorldGenTropicraftCoral extends WorldGenerator
{

    public WorldGenTropicraftCoral()
    {

    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k)
    {
        for (int i1 = 0; i1 < 12; i1++)
        {
            int x = (i + random.nextInt(8)) - random.nextInt(8);
            int z = (k + random.nextInt(8)) - random.nextInt(8);
            int y;
            for (y = j; (world.isAirBlock(x, y, z) || world.getBlockMaterial(x, y - 1, z) == Material.water) && y > 0; y--) {}
            
            if (((BlockTropicraftCoral)Block.blocksList[TropicraftMod.coralCollection1.blockID]).canBlockStay(world, x, y, z))
            {
                world.setBlockAndMetadata(x, y, z, TropicraftMod.coralCollection1.blockID, random.nextInt(6));
            }
        }

        return true;
    }
}
