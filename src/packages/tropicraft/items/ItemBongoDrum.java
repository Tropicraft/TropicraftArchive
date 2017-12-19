package tropicraft.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBongoDrum extends ItemBlockWithMetadata {
	public ItemBongoDrum(int par1, Block par2Block) {
		super(par1, par2Block);
	}

	@Override
	public int getMetadata(int damage) {
		int meta = damage&3;

		if (meta == 3) {
			meta = 0;
		}
		
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String name = null;

		switch (stack.getItemDamage()) {
		case 2:
			name = "big";
			break;
		case 1:
			name = "medium";
			break;
		case 0:
		default:
			name = "small";
			break;
		}
		
		return super.getUnlocalizedName()+"."+name;
	}
}
