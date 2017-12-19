package modconfig;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import modconfig.gui.GuiBetterTextField;
import net.minecraft.client.Minecraft;

public class ConfigEntryInfo {
	public int index;
	public String name;
	public Object value;
	
	public boolean markForUpdate = false;
	
	@SideOnly(Side.CLIENT)
	public GuiBetterTextField editBox;
	
	public ConfigEntryInfo(int parIndex, String parName, Object parVal) {
		index = parIndex;
		name = parName;
		value = parVal;
		
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) initButton();
	}
	
	@SideOnly(Side.CLIENT)
	public void initButton() {
		int buttonWidth = 130;
        int buttonHeight = 16;
		editBox = new GuiBetterTextField(Minecraft.getMinecraft().fontRenderer, 0, 0, buttonWidth, buttonHeight);
		editBox.setText(value.toString());
	}
}
