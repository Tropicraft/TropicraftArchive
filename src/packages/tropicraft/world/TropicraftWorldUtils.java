package tropicraft.world;

import tropicraft.world.teleporter.TeleporterTropics;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.registry.GameRegistry;

public class TropicraftWorldUtils {
	
	public static final int TROPICS_DIMENSION_ID = -127;
	
	public static void initializeDimension()
	{
		DimensionManager.registerProviderType(TROPICS_DIMENSION_ID, WorldProviderTropicraft.class, true);
		DimensionManager.registerDimension(TROPICS_DIMENSION_ID, TROPICS_DIMENSION_ID);	
	}
	
	public static void teleportPlayer(EntityPlayerMP player)
	{
		if (player.dimension == TROPICS_DIMENSION_ID) {
			TeleporterTropics tropicsTeleporter = new TeleporterTropics(MinecraftServer.getServer().worldServerForDimension(0));
			ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
			scm.transferPlayerToDimension(player, 0, tropicsTeleporter);
		} 
		else {
			TeleporterTropics tropicsTeleporter = new TeleporterTropics(MinecraftServer.getServer().worldServerForDimension(TROPICS_DIMENSION_ID));
			ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
			scm.transferPlayerToDimension(player, TROPICS_DIMENSION_ID, tropicsTeleporter);
		}
	}
	
}
