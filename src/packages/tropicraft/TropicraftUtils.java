package tropicraft;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class TropicraftUtils {
	
	/**
	 * Bind resource
	 * @param texLoc Texture location (eg blocks/chest.png)
	 */
	public static ResourceLocation bindTexture(String texLoc) {
        ResourceLocation res = new ResourceLocation(String.format("assets/%s.png", texLoc));
        Minecraft.getMinecraft().renderEngine.bindTexture(res);
        
        return res;
	}
	
	/**
	 * Bind resource
	 * @param texLoc Texture location (eg blocks/chest.png)
	 */
	public static ResourceLocation bindTextureMod(String texLoc) {
        ResourceLocation res = new ResourceLocation(ModInfo.MODID, String.format("textures/%s.png", texLoc));
        Minecraft.getMinecraft().renderEngine.bindTexture(res);
        
        return res;
	}
	
	/**
	 * Bind resource (specially crafted for blocks)
	 * @param texName Texture location (eg chest.png)
	 * @return 
	 */
	public static ResourceLocation bindTextureBlock(String texName) {
        return bindTextureMod(String.format("blocks/%s", texName));
	}
	
	/**
	 * Bind resource (specially crafted for items)
	 * @param texName Texture location (eg chest.png)
	 * @return 
	 */
	public static ResourceLocation bindTextureItem(String texName) {
        return bindTextureMod(String.format("items/%s", texName));
	}
	
	/**
	 * Bind resource (specially crafted for tile entities)
	 * @param texName Texture location (eg chest.png)
	 * @return 
	 */
	public static ResourceLocation bindTextureTE(String texName) {
        return bindTextureMod(String.format("blocks/tileentities/%s", texName));
	}
	
	/**
	 * Bind resource (specially crafted for entities)
	 * @param texName Texture location (eg chest.png)
	 * @return 
	 */
	public static ResourceLocation bindTextureEntity(String texName) {
        return bindTextureMod(String.format("entities/%s", texName));
	}

	/**
	 * Bind resource (specially crafted for gui overlays)
	 * @param texName Texture location (eg chest.png)
	 * @return 
	 */
	public static ResourceLocation bindTextureModGui(String texName) {
        ResourceLocation res = new ResourceLocation(ModInfo.MODID, String.format("gui/%s.png", texName));
        Minecraft.getMinecraft().renderEngine.bindTexture(res);
        
        return res;
	}
}
