package net.tropicraft.registry;

import net.minecraft.util.ResourceLocation;
import net.tropicraft.Info;
import net.tropicraft.Tropicraft;

public class TropicraftRegistry {

	public TropicraftRegistry() {
	
	}
	
	
	public static ResourceLocation getResource(String name) {
    	return new ResourceLocation(Tropicraft.modID, name);
    }
    
	/**
	 * For getting proper resource names
	 */
    public static String getNamePrefixed(String name) {
    	return Info.MODID + ":" + name;
    }

}
