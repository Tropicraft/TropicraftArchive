package tropicraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tropicraft.ModInfo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

/**
 * Use this class when you want to have an item with basic implementation details but multiple items in one id, using damage values
 */
public class ItemTropicraftMulti extends ItemTropicraft {

	@SideOnly(Side.CLIENT)
	private Icon[] images;
	
	private final String[] displayNames;
	private final String[] imageNames;
	
	public ItemTropicraftMulti(int id, String[] displayNames, String[] imageNames) {
		super(id);
		this.displayNames = displayNames;
		this.imageNames = imageNames;
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}
	
    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int damage)
    {
        int j = MathHelper.clamp_int(damage, 0, 15);
        return this.images[j];
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int id, CreativeTabs creativeTabs, List list)
    {
        for (int meta = 0; meta < imageNames.length; ++meta)
        {
            list.add(new ItemStack(id, 1, meta));
        }
    }
    
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
        return super.getUnlocalizedName() + "." + displayNames[i];
    }
	
	/**
	 * Registers all icons used in this item
	 * @param iconRegistry IconRegister instance used to register all icons for this item
	 */
    @SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		images = new Icon[displayNames.length];
		this.itemIcon = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName());
		for (int i = 0; i < displayNames.length; i++) {
			images[i] = iconRegistry.registerIcon(ModInfo.ICONLOCATION + imageNames[i]);
		}
	}

	@Override
	public String getImageName() {
		if (imageNames.length <= 0) {
			throw new IllegalArgumentException("Length of names array must be > 0 in ItemTropicraftMulti");
		}
		
		return imageNames[0];
	}

}
