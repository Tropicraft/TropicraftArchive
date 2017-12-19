package net.tropicraft.packets;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.tropicraft.mods.TropicraftMod;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class TropicraftConnectionHandler implements IConnectionHandler {

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler,
			INetworkManager manager) {
		ServerConfigurationManager scm = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager();
		
		// this is true on server start (e.g., in singleplayer upon loading a world)
		// playerLoggedIn fires after the player is added to the list
		// fixes a bug where if you join world 1, get caught in trap, close world;
		// join world 2 -> you are still upside down
		if (scm.playerEntityList.size() == 1) {
			TropicraftMod.proxy.getUpsideDownUsernames().clear();
		}
		
		NetServerHandler nsh = (NetServerHandler) netHandler;
		nsh.sendPacketToPlayer(TropicraftPacketHandler.getSnareTrapPacket(true, true, TropicraftMod.proxy.getUpsideDownUsernames()));
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager) {
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager) {
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager) {
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) {
	}

}
