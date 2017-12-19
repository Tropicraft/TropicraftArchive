package tropicraft.commands;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

/**
 * Register all Command classes here
 *
 */
public class CommandRegistry {
	/**
	 * Method to register all commands in. Called in Tropicraft.serverStarting
	 * @param event Event triggered upon server start
	 */
	public static void init(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandTeleportTropics());
		event.registerServerCommand(new CommandTC());
	}
}
