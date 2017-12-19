package net.tropicraft.world.biomes;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.tropicraft.world.worldgen.WorldGenTropicraftFruitTrees;

import java.util.Random;

public class BiomeGenTropics extends BiomeGenTropicraft {

	protected BiomeGenTropics(int i) {
		super(i);
		topBlock = (byte)Block.grass.blockID;
		fillerBlock = (byte)Block.dirt.blockID;

		addMobWithNaturalSpawn(net.tropicraft.entities.EntityIguana.class, 4, 4, 20);
		addMobWithNaturalSpawn(net.tropicraft.entities.EntityEIH.class, 1,1, 10);
		addMobWithNaturalSpawn(net.tropicraft.entities.EntityVMonkey.class, 4,4, 20);
		addMobWithNaturalSpawn(EntityCow.class, 4, 4, 10);
		addMobWithNaturalSpawn(EntitySheep.class, 4, 4, 10);
		addMobWithNaturalSpawn(net.tropicraft.entities.EntityTropiCreeper.class, 1, 2, 5);
        addMonsterWithNaturalSpawn(EntitySpider.class, 2, 2, 10);
        addMonsterWithNaturalSpawn(net.tropicraft.entities.EntityTropiSkeleton.class, 2, 4, 10);
		addMobWithNaturalSpawn(EntityChicken.class, 2, 4, 5);

		addWaterMobWithNaturalSpawn(net.tropicraft.entities.EntityTropicalFish.class, 1, 1, 8);
	}

    @Override
	public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return new WorldGenTropicraftFruitTrees(false, (new Random()).nextInt(4));
	}

}
