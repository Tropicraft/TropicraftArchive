package net.tropicraft.enchanting;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class TropicraftEnchantment extends Enchantment 
{	
	public static boolean hasInit = false;
	
	public TropicraftEnchantment(int par1, int par2) 
	{
		super(par1, par2, EnumEnchantmentType.all);
		
		if(!hasInit)
		{
			hasInit = !hasInit;
			EnchantmentManager.initEnchantables();
		}
	}
    /**
    * Called to determine if this enchantment can be applied to a ItemStack
    * @param item The ItemStack that the enchantment might be put on
    * @return True if the item is valid, false otherwise
    */
	@Override
    public boolean func_92037_a(ItemStack item)
    {
        return EnchantmentManager.getEnchantableIDList().contains(new Integer(item.itemID));
    }
	
    /**
     * Sets the enchantment name
     */
	@Override
    public Enchantment setName(String par1Str)
    {
		LanguageRegistry.instance().addStringLocalization("enchantment." + par1Str, par1Str);
        this.name = par1Str;
        return this;
    }
	
//	public TropicraftEnchantment getPossibleEnchantment
}
