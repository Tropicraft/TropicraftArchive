package tropicraft.registry;

import net.minecraft.entity.Entity;
import tropicraft.ModInfo;
import tropicraft.Tropicraft;
import tropicraft.entities.EntityEagleRay;
import tropicraft.entities.blocks.EntityFallingPurifiedSand;
import tropicraft.entities.hostile.land.Creeper;
import tropicraft.entities.hostile.land.EIH;
import tropicraft.entities.hostile.land.Skeleton;
import tropicraft.entities.hostile.land.SpiderAdult;
import tropicraft.entities.hostile.land.SpiderChild;
import tropicraft.entities.hostile.land.SpiderEgg;
import tropicraft.entities.hostile.land.TreeFrogBlue;
import tropicraft.entities.hostile.land.TreeFrogGreen;
import tropicraft.entities.hostile.land.TreeFrogRed;
import tropicraft.entities.hostile.land.TreeFrogYellow;
import tropicraft.entities.hostile.land.tribes.ashen.AshenHunter;
import tropicraft.entities.hostile.land.tribes.ashen.LostMask;
import tropicraft.entities.hostile.land.tribes.koa.v3.EntityKoaFisher;
import tropicraft.entities.hostile.land.tribes.koa.v3.EntityKoaHunter;
import tropicraft.entities.hostile.land.tribes.koa.v3.EntityKoaShaman;
import tropicraft.entities.hostile.land.tribes.koa.v3.EntityKoaTrader;
import tropicraft.entities.items.EntityTCItemFrame;
import tropicraft.entities.items.EntityWallStarfish;
import tropicraft.entities.items.WallMask;
import tropicraft.entities.items.WallShell;
import tropicraft.entities.passive.flying.Failgull;
import tropicraft.entities.passive.land.Iguana;
import tropicraft.entities.passive.land.VMonkey;
import tropicraft.entities.passive.water.EntityManOWar;
import tropicraft.entities.passive.water.EntityMarlin;
import tropicraft.entities.passive.water.EntitySeaTurtle;
import tropicraft.entities.passive.water.EntitySeaUrchin;
import tropicraft.entities.passive.water.EntitySeaUrchinEgg;
import tropicraft.entities.passive.water.EntityStarfish;
import tropicraft.entities.passive.water.EntityStarfishEgg;
import tropicraft.entities.passive.water.EntityTropicalFish;
import tropicraft.entities.passive.water.EntityTurtleEgg;
import tropicraft.entities.placeable.EntityChair;
import tropicraft.entities.placeable.EntitySnareTrap;
import tropicraft.entities.placeable.EntityUmbrella;
import tropicraft.entities.projectiles.EntityCoconutGrenade;
import tropicraft.entities.projectiles.EntityDart;
import tropicraft.entities.projectiles.EntityFireBall;
import tropicraft.entities.projectiles.EntityIceBall;
import tropicraft.entities.projectiles.EntityTameBall;
import tropicraft.entities.projectiles.EntityTropicraftLeafballNew;
import tropicraft.fishing.EntityHook;
import CoroUtil.entity.EntityTropicalFishHook;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class TCEntityRegistry {
	/**
	 * Entity id unique to Tropicraft mod
	 */
	private static int entityId = 0;

	/**
	 * Init all mappings here
	 */
	public static void init() {
		//addMapping(EntityKoaManly.class, "Koa Man", 64, 3, true);
		addMapping(EntityTropicraftLeafballNew.class, "Leaf Ball", 120, 5, true);
		addMapping(EntityTropicalFishHook.class, "EntityTropicalFishHook", 64, 3, true);
		addMapping(Skeleton.class, "TropiSkeleton", 64, 3, true);
		addMapping(Creeper.class, "TropiCreeper", 64, 3, true);
		addMapping(EIH.class, "EIH", 64, 3, true);
		addMapping(Iguana.class, "Iguana", 64, 3, true);
		addMapping(TreeFrogGreen.class, "TreeFrogGreen", 64, 3, true);
		addMapping(TreeFrogRed.class, "TreeFrogRed", 64, 3, true);
		addMapping(TreeFrogBlue.class, "TreeFrogBlue", 64, 3, true);
		addMapping(TreeFrogYellow.class, "TreeFrogYellow", 64, 3, true);
		addMapping(VMonkey.class, "VMonkey", 64, 3, true);
		addMapping(AshenHunter.class, "AshenHunter", 64, 3, true);
		addMapping(EntityTropicalFish.class, "TropicalFish", 64, 3, true);
		addMapping(EntityDart.class, "Dart", 120, 3, true);
		addMapping(WallShell.class, "Wall Shell", 120, 3, false);
		addMapping(EntitySnareTrap.class, "Snare Trap", 64, 10, false, false);
		addMapping(Failgull.class, "Failgull", 64, 3, true);
		addMapping(EntitySeaUrchin.class, "SeaUrchin", 64, 3, true);
		addMapping(EntitySeaUrchinEgg.class, "SeaUrchinEgg", 64, 3, false);
		addMapping(EntityFallingPurifiedSand.class, "FallingPurifiedSand", 64, 3, true);
		addMapping(EntityChair.class, "Chair", 120, 10, false, false);
		addMapping(EntityUmbrella.class, "Umbrella", 120, 10, false, false);
		addMapping(EntityStarfish.class, "Starfish", 64, 3, false);
		addMapping(EntityStarfishEgg.class, "StarfishEgg", 64, 3, false);
		addMapping(EntityWallStarfish.class, "WallStarfish", 160, Integer.MAX_VALUE, false);
		addMapping(EntityEagleRay.class, "EagleRay", 64, 3, false);
		addMapping(EntityFireBall.class, "Fire Ball", 120, 5, true);
		addMapping(EntityIceBall.class, "Ice Ball", 120, 5, true);
		addMapping(EntityTameBall.class, "Tame Ball", 120, 5, true);
		addMapping(EntityMarlin.class, "Marlin", 64, 3, false);
		addMapping(EntityManOWar.class, "MOW", 64, 3, false);
		addMapping(EntityTCItemFrame.class, "TCItemFrame", 160, Integer.MAX_VALUE, false);
		addMapping(EntityKoaShaman.class, "Shaman", 64, 3, true);
		addMapping(EntitySeaTurtle.class, "Sea Turtle", 64, 3, true);
		addMapping(EntityTurtleEgg.class, "Turtle Egg", 64, 3, true);
		addMapping(WallMask.class, "Wall Mask", 120, 3, false);
		addMapping(EntityKoaHunter.class, "KoaHunter", 64, 3, true);
		addMapping(EntityKoaFisher.class, "KoaFisher", 64, 3, true);
		addMapping(EntityKoaTrader.class, "KoaTrader", 64, 3, true);
		addMapping(EntityHook.class, "FishingHook", 128, 1, true);
		addMapping(EntityCoconutGrenade.class, "CoconutBomb", 128, 1, true);
		addMapping(LostMask.class, "Lost Mask", 120, 1, true);
		addMapping(SpiderAdult.class, "SpiderAdult", 48, 3, true);
		addMapping(SpiderChild.class, "SpiderChild", 48, 3, true);
		addMapping(SpiderEgg.class, "SpiderEgg", 48, 3, true);
		
		initMobNames();
	}

	/**
	 * Add the names of all mobs
	 */
	private static void initMobNames() {
		addMobName("Iguana");
		addMobName("Chair", "Beach Chair");
		addMobName("Umbrella", "Beach Umbrella");
		addMobName("EIH", "Easter Island Head");
		addMobName("Sea Turtle");
		addMobName("Turtle Egg", "Sea Turtle Egg");
		addMobName("Wall Shell");
		addMobName("Beach Float");
		addMobName("Purified Sand");
		addMobName("TropiCreeper");
		addMobName("Marlin");
		addMobName("MOW", "Man 'O War");
		addMobName("Starfish");
		addMobName("VervetMonkey", "Vervet Monkey");
		addMobName("TropiSkeleton", "TropiSkelly");
		addMobName("TreeFrogGreen", "Green Tree Frog");
		addMobName("TreeFrogRed", "Red Tree Frog");
		addMobName("TreeFrogBlue", "Blue Tree Frog");
		addMobName("TreeFrogYellow", "Yellow Tree Frog");
		addMobName("Lost Mask");
		addMobName("Dart");
		addMobName("Ashen");
		addMobName("AshenHunter", "Ashen Hunter");
		addMobName("TropicalFish", "Tropical Fish");
		addMobName("Seaweed");
		addMobName("Leaf Ball");
		addMobName("Coconut Bomb");
		addMobName("Koa Man");
		addMobName("EntityTropicalFishHook", "Tropical Fish Hook");
		addMobName("WallStarfish");
		addMobName("EagleRay", "Spotted Eagle Ray");
		addMobName("Fire Ball");
		addMobName("Marlin");
		addMobName("MOW", "Man O' War Jellyfish");
		addMobName("TCItemFrame");
		addMobName("Shaman", "Koa Shaman");
		addMobName("Sea Turtle");
		addMobName("Turtle Egg", "Sea Turtle Egg");
		addMobName("Chair", "Beach Chair");
		addMobName("Umbrella", "Beach Umbrella");
		addMobName("Wall Mask");
		addMobName("FishingHook", "Fishing Hook");
	}
	

	public static void addMapping(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency) {
		entityId++;
		EntityRegistry.registerModEntity(entityClass, entityName, entityId, Tropicraft.instance, trackingRange, updateFrequency, false);
	}

	public static void addMapping(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		addMapping(entityClass, entityName, trackingRange, updateFrequency, sendsVelocityUpdates, false);
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
			EntityRegistry.registerModEntity(entityClass, entityName, EntityRegistry.findGlobalUniqueEntityId(), Tropicraft.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		} else {
			entityId++;
			EntityRegistry.registerModEntity(entityClass, entityName, entityId, Tropicraft.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		}
	}
	
	   /**
     * Convenience method for adding a name for when an entity dies <br><br>
     * LanguageRegistry.instance().addStringLocalization("entity.RedDragon.name", "en_US", "Red Dragon");<br>
     *
     * @param name: entity.name.name and name will both be the same
     */
    private static void addMobName(String name) {
        addMobName(name, name);
    }
    
    /**
     * Convenience method for adding a name for when an entity dies <br><br>
     * LanguageRegistry.instance().addStringLocalization("entity.RedDragon.name", "en_US", "Red Dragon");<br>
     *
     * @param longName ex: entity.RedDragon.name
     * @param shortName ex: Red Dragon
     */
    private static void addMobName(String longName, String shortName) {
        LanguageRegistry.instance().addStringLocalization(String.format("entity.%s." + longName + ".name", ModInfo.MODID), "en_US", shortName);
    }

}
