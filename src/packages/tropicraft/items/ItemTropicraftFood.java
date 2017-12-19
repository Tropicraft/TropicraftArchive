package tropicraft.items;

import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;

public class ItemTropicraftFood extends ItemFood {

	private String imageName;
	
	public ItemTropicraftFood(int par1, int par2, float par3, boolean par4, String imageName) {
		super(par1, par2, par3, par4);
		this.imageName = imageName;
		setCreativeTab(TropiCreativeTabs.tabFood);
	}

	public ItemTropicraftFood(int par1, int par2, boolean par3, String imageName) {
		super(par1, par2, par3);
		this.imageName = imageName;
		setCreativeTab(TropiCreativeTabs.tabFood);
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
	public String getImageName() {
		return this.imageName;
	}
}
