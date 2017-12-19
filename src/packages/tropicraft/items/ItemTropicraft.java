package tropicraft.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tropicraft.ModInfo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

public abstract class ItemTropicraft extends Item {
	
	/**
	 * Name of the image to be loaded for this item
	 */
	public String imageName;
	
	/**
	 * Simple constructor for an item
	 * @param id itemID
	 */
	public ItemTropicraft(int id) {
		super(id);
	}
	
	public ItemTropicraft(int id, String imgName) {
		super(id);
		this.imageName = imgName;
	}
	
	/**
	 * Constructor that calls super with the item  id, then sets the creative tab this Item should be displayed on
	 * @param id itemID
	 * @param tab Which tab this item should be displayed on in Creative Mode
	 */
	public ItemTropicraft(int id, CreativeTabs tab) {
		super(id);
		this.setCreativeTab(tab);
	}

	/**
	 * Register all Icons used in this item
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.itemIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName());
	}
	
	/**
	 * Returns the file name of the image icon
	 * @return File name of image icon for this Item
	 */
	public abstract String getImageName();
}
