package net.tropicraft.blocks.liquids;

import static net.tropicraft.blocks.liquids.LAPI.nextID;

import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

import cpw.mods.fml.client.FMLClientHandler;

public class LAPIClientProxy extends LAPICommonProxy {

	public LAPIClientProxy() {
	}
	
	@Override
	public int getUniqueLiquidID() {
		if(nextID < 40)
		{
			nextID++;
			int a = LAPI.getUniqueStationaryID();
			int b = LAPI.getUniqueFlowingID();

			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(new LAPITextureFX(a, false));
			ModLoader.getMinecraftInstance().renderEngine.registerTextureFX(new LAPITextureFX(b, true));

			LAPI.terrainIDMap.put(a, nextID);

			return a;
		} else
			try {
				throw new Exception("No more unique LAPI block textures available!");
			} catch (Exception e) {
				e.printStackTrace();
			}

		return 0;	
	}
	
	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

}
