package tropicraft.items;

import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemBucket;

public class ItemTropicraftBucket extends ItemBucket {

    public ItemTropicraftBucket(int i, int j) {
        super(i, j);
        setCreativeTab(TropiCreativeTabs.tabMisc);
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
		return "waterbucket";
	}
}