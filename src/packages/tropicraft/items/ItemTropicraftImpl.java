package tropicraft.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;

public class ItemTropicraftImpl extends ItemTropicraft {

	/**
	 * Simple implementation of ItemTropicraft
	 * @param id Item id
	 * @param imageName Name of the image to use for this Item
	 */
	public ItemTropicraftImpl(int par1, String imageName) {
		super(par1, imageName);
		setCreativeTab(TropiCreativeTabs.tabMisc);
	}
	
	/**
	 * Simple implementation of ItemTropicraft
	 * @param id Item id
	 * @param imageName Name of the image to use for this Item
	 * @param tab TropiCreativeTabs
	 */
	public ItemTropicraftImpl(int par1, String imageName, CreativeTabs tab) {
		super(par1, imageName);
		setCreativeTab(tab);
	}

	/**
	 * Registers all icons used in this item
	 * @param iconRegistry IconRegister instance used to register all icons for this item
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.itemIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName());
	}

	/**
	 * @return Get the image name for this item
	 */
	@Override
	public String getImageName() {
		return this.imageName;
	}

}
