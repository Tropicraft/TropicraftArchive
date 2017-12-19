package tropicraft.config;

import java.io.File;
import java.util.Arrays;

import modconfig.ConfigComment;
import modconfig.IConfigCategory;
import tropicraft.Tropicraft;

public class TropicraftConfig implements IConfigCategory {
	
	@ConfigComment("Message that will be displayed when someone without permission tries to use a coconut bomb")
	public static String cocoBombWarning = "You do not have permission to use coconut bombs :) Check the coconut bomb whitelist in the Tropicraft config file!";

	/** Should volcanoes generate? True by default */
	@ConfigComment("Should volcanoes generate?")
	public static boolean genVolcanoes = true;

	/** Should chairs act like boats? False by default :( */
	@ConfigComment("Should chairs act like boats?")
	public static boolean comeSailAway = false;

	/** List of users who can use coconut bombs. Empty by default */
	@ConfigComment("List of users who can use coconut bombs.")
	public static String coconutBombWhitelist = "";

	/** Coding debug, we guess! True by default */
	@ConfigComment("Coding debug, we guess!")
	public static boolean debugConsole = true;

	/** Mini event of tropiskellys spawning and hunting you down. True by default */
	@ConfigComment("Mini event of tropiskellys spawning and hunting you down.")
	public static boolean eventMiniSpawns = true;
	
	@ConfigComment("Mini event of tropiskellys spawning and hunting you down.")
	public static int eventMiniSpawnGroupCount = 10;

	/** Should Tropicraft things generate in the overworld? More of a short-circuit than anything. True by default */
	@ConfigComment("Should Tropicraft things generate in the overworld?")
	public static boolean genTropicraftInOverworld = true;

	/** Should all the awesome tropiflowers generate in the overworld? False by default */
	@ConfigComment("Should all the awesome tropiflowers generate in the overworld?")
	public static boolean genTropicraftFlowersInOverworld = false;

	/** Should the EIH statues generate in the overworld? False by default */
	@ConfigComment("Should the EIH statues generate in the overworld?")
	public static boolean genTropicraftEIHInOverworld = false;

	/** Should palm trees only generate in beaches in the overworld, rather than also deserts? False by default because palms are awesome */
	@ConfigComment("Should palm trees only generate in beaches in the overworld, rather than also deserts?")
	public static boolean genOverworldPalmsInBeachOnly = false;

	/** Should pineapples generate in the overworld? True by default */
	@ConfigComment("Should pineapples generate in the overworld?")
	public static boolean genPineapplesInOverworld = true;

	/** Should bamboo generate in the overworld? True by default */
	@ConfigComment("Should bamboo generate in the overworld?")
	public static boolean genBambooInOverworld = true;

	/** Should palm trees generate in the overworld? True by default */
	@ConfigComment("Should palm trees generate in the overworld?")
	public static boolean genPalmsInOverworld = true;

	/** Make this value higher to make the chance of palms spawning in a chunk lower. -1 if you want them to always generate */
	@ConfigComment("Higher value means lower chance of palms spawning in a chunk. -1 means they will always generate.")
	public static int palmChanceOfGenInOverworld = -1;

	/** Determines how many palm trees should try to generate per chunk. 3 by default. Lower is less, higher is more. 0 means none. */
	@ConfigComment("How many palm trees should try to generate per chunk? Lower is less. 0 is none.")
	public static int palmPopulationFactorInOverworld = 3;

	/** Should the mod check to see if there is an update available on startup? True by default */
	@ConfigComment("Should the mod check to see if there is an update available on startup?")
	public static boolean versionChecker = true;
	
	@ConfigComment("Biome ID: Tropics Ocean")
	public static int biomeTropicsOceanID = 60;
	
	@ConfigComment("Biome ID: Tropics")
	public static int biomeTropicsID = 61;
	
	@ConfigComment("Biome ID: Rainforest Plains")
	public static int biomeRainforestPlainsID = 62;
	
	@ConfigComment("Biome ID: Rainforest Hills")
	public static int biomeRainforestHillsID = 63;
	
	@ConfigComment("Biome ID: Rainforest Mountains")
	public static int biomeRainforestMountainsID = 64;
	
	@ConfigComment("Biome ID: Tropics River")
	public static int biomeTropicsRiverID = 65;
	
	@ConfigComment("Biome ID: Tropics Beach")
	public static int biomeTropicsBeachID = 66;
	
	@ConfigComment("Biome ID: Lake")
	public static int biomeLakeID = 67;
	
	@ConfigComment("Biome ID: Island Mountain")
	public static int biomeIslandMountainID = 68;

	@Override
	public String getConfigFileName() {
		return "Tropicraft" + File.separator + "Misc";
	}

	@Override
	public String getCategory() {
		return "Tropicraft Misc";
	}

	@Override
	public void hookUpdatedValues() {		
		Tropicraft.COCONUT_BOMB_WHITELIST = coconutBombWhitelist.contains(",") ? coconutBombWhitelist.replace(" ", "").split(",") : coconutBombWhitelist.split(" ");
		Tropicraft.coconutBombWhitelistedUsers = Arrays.asList(Tropicraft.COCONUT_BOMB_WHITELIST);
	}

}
