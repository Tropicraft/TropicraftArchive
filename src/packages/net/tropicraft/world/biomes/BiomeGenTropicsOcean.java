package net.tropicraft.world.biomes;

import net.minecraft.entity.passive.EntitySquid;

public class BiomeGenTropicsOcean extends BiomeGenTropicraft {

	protected BiomeGenTropicsOcean(int i) {
		super(i);
		
        addWaterMobWithNaturalSpawn(EntitySquid.class, 1, 2, 5);
		addWaterMobWithNaturalSpawn(net.tropicraft.entities.EntityStarfish.class, 1, 4, 18);
		addWaterMobWithNaturalSpawn(net.tropicraft.entities.EntityMarlin.class, 1, 3, 20);
		addWaterMobWithNaturalSpawn(net.tropicraft.entities.EntityTropicalFish.class, 1, 1, 9);
		addWaterMobWithNaturalSpawn(net.tropicraft.entities.EntitySeaTurtle.class, 3, 7, 15);
		addWaterMobWithNaturalSpawn(net.tropicraft.entities.EntityManOWar.class, 1, 1, 14);
	}

}
