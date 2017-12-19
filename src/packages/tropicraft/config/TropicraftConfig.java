package tropicraft.config;

import java.io.File;
import java.util.Arrays;

import modconfig.IConfigCategory;
import tropicraft.Tropicraft;

public class TropicraftConfig implements IConfigCategory {

	public static boolean genVolcanoes = true;
	public static boolean comeSailAway = false;
	public static String coconutBombWhitelist = "";
	
	public static boolean debugConsole = true;
	public static boolean eventMiniSpawns = true;
	
	public static boolean genTropicraftInOverworld = true;
	public static boolean genTropicraftFlowersInOverworld = false;
	public static boolean genTropicraftEIHInOverworld = false;
	
	public static boolean versionChecker = true;

	@Override
	public String getConfigFileName() {
		// TODO Auto-generated method stub
		return "Tropicraft" + File.separator + "Misc";
	}

	@Override
	public String getCategory() {
		// TODO Auto-generated method stub
		return "Tropicraft Misc";
	}

	@Override
	public void hookUpdatedValues() {
		
		Tropicraft.COCONUT_BOMB_WHITELIST = coconutBombWhitelist.contains(",") ? coconutBombWhitelist.replace(" ", "").split(",") : coconutBombWhitelist.split(" ");
		Tropicraft.coconutBombWhitelistedUsers = Arrays.asList(Tropicraft.COCONUT_BOMB_WHITELIST);
	}

}
