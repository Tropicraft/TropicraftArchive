package net.tropicraft.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.tropicraft.mods.TropicraftMod;

public class ItemZircon extends ItemTropicraft {

	public ItemZircon(int i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(TropicraftMod.tabMaterials);
	}	
	
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(this.shiftedIndex, 1, 0));
		par3List.add(new ItemStack(this.shiftedIndex, 1, 1));
	}
	
	@Override
	public int getIconFromDamage(int dmg) {
		return dmg == 0 ? iconIndex : 87 /*TropicraftMod.zirconium.iconIndex + 1 - can't use b/c client only :(*/;
	}
	
	@Override
	public String getItemNameIS(ItemStack stack) {
		return getItemName()+"."+stack.getItemDamage();
	}

}
