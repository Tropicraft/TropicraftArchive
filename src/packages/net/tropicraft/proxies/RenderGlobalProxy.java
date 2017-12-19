package net.tropicraft.proxies;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

public class RenderGlobalProxy implements IGuiHandler {

	public RenderGlobalProxy() {
	}
	
	public void playRecord(String text, String name, int i, int j, int k) {
		
	}
	
	public World getClientWorld() {
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

}
