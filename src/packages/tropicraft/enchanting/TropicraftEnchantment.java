package tropicraft.enchanting;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * Class that mocks vanilla enchantments, but with some TropiCustomizations (TM) (not really TM)
 */
public class TropicraftEnchantment extends Enchantment {

	public TropicraftEnchantment(int effectID, int weight) {
		super(effectID, weight, EnumEnchantmentType.all);
		
	}
	
	@Override
    public boolean canApply(ItemStack itemstack)
    {
        return EnchantmentManager.canApply(itemstack);
    }

    /**
     * This applies specifically to applying at the enchanting table. The other method {@link #func_92037_a(ItemStack)}
     * applies for <i>all possible</i> enchantments.
     * @param stack
     * @return
     */
	@Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    /**
     * Sets the enchantment name
     */
	@Override
    public Enchantment setName(String newName)
    {
		LanguageRegistry.instance().addStringLocalization("enchantment." + newName, newName);
        this.name = newName;
        return this;
    }	
}
