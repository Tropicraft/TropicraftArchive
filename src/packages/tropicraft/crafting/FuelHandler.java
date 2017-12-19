package tropicraft.crafting;

import net.minecraft.item.ItemStack;
import tropicraft.blocks.TropicraftBlocks;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {

    @Override
    public int getBurnTime(ItemStack fuel) {
        if (fuel.itemID == TropicraftBlocks.tropicsSingleSlab.blockID && fuel.getItemDamage() == 3) {
            return 150;
        }
        return 0;
    }

}