package net.tropicraft.proxies;

import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

import cpw.mods.fml.client.FMLClientHandler;

public class RecordProxy extends RenderGlobalProxy {

	public RecordProxy() {
		
	}
	
	@Override
	public void playRecord(String text, String name, int i, int j, int k) {
	//	System.out.println("displayRecordGui");
		ModLoader.getMinecraftInstance().ingameGUI.setRecordPlayingMessage(text);
	//	ModLoader.getMinecraftInstance().sndManager.playStreaming(name, (float)i, (float)j, (float)k, 1.0F, 1.0F);
	}
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

}
