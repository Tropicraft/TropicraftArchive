package tropicraft.items;

import java.util.List;

import tropicraft.creative.TropiCreativeTabs;
import tropicraft.entities.passive.water.EntityTropicalFish;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemJournalPage extends ItemTropicraft {

	public ItemJournalPage(int id, String imgName) {
		super(id, imgName);
		hasSubtypes = true;
		this.setCreativeTab(TropiCreativeTabs.tabMisc);
	}
	
    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
    	return "Journal Page";
    }
	
	@Override
	public String getImageName() {
		return this.imageName;
	}
}
