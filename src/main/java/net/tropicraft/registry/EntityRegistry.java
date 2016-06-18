package net.tropicraft.registry;

import net.minecraft.entity.Entity;
import net.tropicraft.Tropicraft;
import net.tropicraft.entity.hostile.EntityEIH;

public class EntityRegistry {

	private static int entityID = 0;

	public static void init() {
		registerEntity(EntityEIH.class, "Easter Island Head", 80, 3, true);
	}
	
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(entityClass, entityName, entityID++, Tropicraft.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
	
}
