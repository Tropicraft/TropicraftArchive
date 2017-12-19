package tropicraft.items;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemTropicraftArmorScale extends ItemTropicraftArmor {

	public ItemTropicraftArmorScale(int par1, int par3, int par4) {
		super(par1, par3, par4);
	}
	
	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
		return slot == 2 ? "/mods/TropicraftMod/textures/armor/scale_2.png" : slot == 0 || slot == 1 || slot == 3 ? "/mods/TropicraftMod/textures/armor/scale_1.png" : null;
    }

}
