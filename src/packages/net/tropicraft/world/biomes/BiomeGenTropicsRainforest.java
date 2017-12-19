package net.tropicraft.world.biomes;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntitySpider;

public class BiomeGenTropicsRainforest extends BiomeGenTropicraft {

	protected BiomeGenTropicsRainforest(int i) {
		super(i);
		topBlock = (byte)Block.grass.blockID;
		fillerBlock = (byte)Block.dirt.blockID;

		beachAlternateTopBlock = (byte)Block.gravel.blockID;
		beachAlternateFillerBlock = (byte)Block.gravel.blockID;
		beachAlternateLayerLowered = true;

		addMobWithNaturalSpawn(net.tropicraft.entities.EntityTreeFrog.class, 1, 3, 14);
		addMobWithNaturalSpawn(net.tropicraft.entities.EntityPoisonousTreeFrogRed.class, 1,3,12);
		addMobWithNaturalSpawn(net.tropicraft.entities.EntityPoisonousTreeFrogYellow.class, 1,3,12);
		addMobWithNaturalSpawn(net.tropicraft.entities.EntityPoisonousTreeFrogBlue.class, 1,3,12);
		addMobWithNaturalSpawn(net.tropicraft.entities.EntityAshenHunter.class, 1, 3, 4);
		addMonsterWithNaturalSpawn(EntitySpider.class, 2, 2, 10);
        addMonsterWithNaturalSpawn(net.tropicraft.entities.EntityTropiSkeleton.class, 2, 4, 10);
		addMobWithNaturalSpawn(net.tropicraft.entities.EntityTropiCreeper.class, 1, 2, 5);
	}

}
