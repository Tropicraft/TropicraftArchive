package build.config;

import org.lwjgl.input.Keyboard;

import modconfig.IConfigCategory;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

public class BuildConfig implements IConfigCategory {
	
	public static boolean enableEditMode = false;
	public static String key_Build = "B";//Keyboard.KEY_B;
	public static String key_Copy = "C";//Keyboard.KEY_C;
	public static String key_Rotate = "V";
    
	@Override
	public String getCategory() {
		return "Build Mod Config";
	}

	@Override
	public String getConfigFileName() {
		return "BuildMod";
	}

	@Override
	public void hookUpdatedValues() {
		
	}

}
