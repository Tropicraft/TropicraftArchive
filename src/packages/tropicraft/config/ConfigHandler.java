package tropicraft.config;

import tropicraft.ModIds;
import tropicraft.Tropicraft;
import modconfig.ConfigMod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

	public static void initConfig(FMLPreInitializationEvent event) {
		
		ConfigMod.addConfigFile(event, "tcconfigids", new ModIds(), false);
		ConfigMod.addConfigFile(event, "tcconfig", new TropicraftConfig());		
		
	}
}
