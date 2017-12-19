package net.tropicraft;

import net.minecraft.item.ItemStack;
import net.tropicraft.mods.TropicraftMod;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.itemID == TropicraftMod.tropicalSingleSlab.blockID && fuel.getItemDamage() == 3) {
			return 150;
		}
		return 0;
	}

}