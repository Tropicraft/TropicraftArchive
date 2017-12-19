package net.tropicraft.blocks.liquids;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class LAPIPacketHandler implements IPacketHandler {

	public LAPIPacketHandler() {
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {

	}

}
