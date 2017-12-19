package tropicraft.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tropicraft.ModInfo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ItemTropicraftArmorScale extends ItemTropicraftArmor {
	
	public ItemTropicraftArmorScale(int par1, String tex_name, int par3, int par4) {
		super(par1, tex_name, par3, par4);
	}
	
	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
		return slot == 2 ? ModInfo.TEXTURE_ARMOR_LOC + "scale_2.png" : slot == 0 || slot == 1 || slot == 3 ? ModInfo.TEXTURE_ARMOR_LOC + "scale_1.png" : null;
    }

}
