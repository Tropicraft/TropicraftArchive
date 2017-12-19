package net.tropicraft;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.tropicraft.entities.EntityAshenHunter;
import net.tropicraft.entities.EntityBeachFloat;
import net.tropicraft.entities.EntityChair;
import net.tropicraft.entities.EntityCoconutGrenade;
import net.tropicraft.entities.EntityDart;
import net.tropicraft.entities.EntityEIH;
import net.tropicraft.entities.EntityFallingPurifiedSand;
import net.tropicraft.entities.EntityHangingTropicraft;
import net.tropicraft.entities.EntityIguana;
import net.tropicraft.entities.EntityKoaManly;
import net.tropicraft.entities.EntityKoaMemberNewest;
import net.tropicraft.entities.EntityLavaBall;
import net.tropicraft.entities.EntityLostMask;
import net.tropicraft.entities.EntityManOWar;
import net.tropicraft.entities.EntityMarlin;
import net.tropicraft.entities.EntityPoisonousTreeFrogBlue;
import net.tropicraft.entities.EntityPoisonousTreeFrogRed;
import net.tropicraft.entities.EntityPoisonousTreeFrogYellow;
import net.tropicraft.entities.EntitySeaTurtle;
import net.tropicraft.entities.EntitySmoke;
import net.tropicraft.entities.EntitySnareTrap;
import net.tropicraft.entities.EntityStarfish;
import net.tropicraft.entities.EntityTreeFrog;
import net.tropicraft.entities.EntityTropiCreeper;
import net.tropicraft.entities.EntityTropiSkeleton;
import net.tropicraft.entities.EntityTropicalFish;
import net.tropicraft.entities.EntityTropicraftLeafball;
import net.tropicraft.entities.EntityTurtleEgg;
import net.tropicraft.entities.EntityUmbrella;
import net.tropicraft.entities.EntityVMonkey;
import net.tropicraft.entities.EntityWallShell;
import net.tropicraft.mods.TropicraftMod;

import CoroAI.entity.EntityTropicalFishHook;
import cpw.mods.fml.common.registry.EntityRegistry;

public class TCEntityRegistry 
{
	public static TropicraftMod mod;

	private static int entityId = 1337;

	/**
	 * Register all entities here
	 */
	public static void addMappings(){
		addMapping(EntityChair.class, "Beach Chair", 64, 2);
		addMapping(EntityUmbrella.class, "Beach Umbrella", 64, 2);
		addMapping(EntityEIH.class, "EIH", 64, 5);
		addMapping(EntityIguana.class, "Iguana", 64, 3); //smooth movement issue, use update rate of 1
		addMapping(EntitySeaTurtle.class, "Sea Turtle", 64, 3);
		addMapping(EntityTurtleEgg.class, "Turtle Egg", 64, 3);
		addMapping(EntityWallShell.class, "Wall Shell", 64, 60);
		addMapping(EntityBeachFloat.class, "Beach Float", 64, 2);
		addMapping(EntityFallingPurifiedSand.class, "Purified Sand", 120, 20, true, false);
		addMapping(EntityTropiCreeper.class, "TropiCreeper", 64, 10);
		addMapping(EntityMarlin.class, "Marlin", 120, 5, true, false);
		addMapping(EntityManOWar.class, "MOW", 120, 3);
		addMapping(EntityStarfish.class, "Starfish", 64, 10);
		addMapping(EntityVMonkey.class, "VervetMonkey", 120, 3); //smooth movement issue, use update rate of 1
		addMapping(EntityTropiSkeleton.class, "TropiSkelly", 120, 3);
		addMapping(EntityTreeFrog.class, "Tree Frog", 120, 3); //smooth movement issue, use update rate of 1
		addMapping(EntityPoisonousTreeFrogRed.class, "Red Tree Frog", 120, 3); //smooth movement issue, use update rate of 1
		addMapping(EntityPoisonousTreeFrogBlue.class, "Blue Tree Frog", 120, 3); //smooth movement issue, use update rate of 1
		addMapping(EntityPoisonousTreeFrogYellow.class, "Yellow Tree Frog", 120, 3); //smooth movement issue, use update rate of 1
		addMapping(EntityLostMask.class, "Lost Mask", 120, 10);
		addMapping(EntityDart.class, "Dart", 120, 1, true, false);
		//addMapping(EntityAshen.class, "Ashen", 120, 3);
		addMapping(EntityAshenHunter.class, "Ashen Hunter", 120, 3);
		addMapping(EntityTropicalFish.class, "Tropical Fish", 64, 3);		
	//	addMapping(EntitySeaweed.class, "Seaweed", 120, 10);
	//	addMapping(SeaweedPart.class, "SeaweedPart", 120, 10);
		addMapping(EntityTropicraftLeafball.class, "Leaf Ball", 120, 5, true, false);
		addMapping(EntityCoconutGrenade.class, "Coconut Bomb", 120, 5, true, false);
		addMapping(EntityKoaManly.class, "Koa Man", 64, 3, true, false);
		addMapping(EntityTropicalFishHook.class, "EntityTropicalFishHook", 64, 3, true, false);
		addMapping(EntitySmoke.class, "Smoke", 120, 10, false, false);
		addMapping(EntityLavaBall.class, "Lava Ball", 120, 4, true, false);
		addMapping(EntitySnareTrap.class, "Snare Trap", 64, 10, false, false);
		addMapping(EntityKoaMemberNewest.class, "Koa Man2", 64, 3, true, false);
		addMapping(EntityHangingTropicraft.class, "TropiItemFrame", 120, 60, true, false);
	}

	public static void addMapping(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency) {
		entityId++;
		EntityRegistry.registerModEntity(entityClass, entityName, entityId, mod, trackingRange, updateFrequency, false);
//		EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityId);
	}

	/**
	 * Register the mod entity type with FML

	 * @param entityClass The entity class
	 * @param entityName A unique name for the entity
	 * @param trackingRange The range at which MC will send tracking updates
	 * @param updateFrequency The frequency of tracking updates
	 * @param sendsVelocityUpdates Whether to send velocity information packets as well
	 */
	public static void addMapping(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, boolean spawnerOrEgg) {
		if(spawnerOrEgg) {
			EntityRegistry.registerModEntity(entityClass, entityName, EntityRegistry.findGlobalUniqueEntityId(), mod, trackingRange, updateFrequency, sendsVelocityUpdates);
		} else {
			entityId++;
			EntityRegistry.registerModEntity(entityClass, entityName, entityId, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
		//	EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityId);
		}
	}


	/**
	 * Called via the TropicraftMod init method
	 * @param mod TropicraftMod instance
	 */
	public static void init(TropicraftMod mod) {
		TCEntityRegistry.mod = mod;
		addMappings();
	}

}
