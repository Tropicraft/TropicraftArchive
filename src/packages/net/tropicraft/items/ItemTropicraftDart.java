package net.tropicraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.tropicraft.drinks.Drink;
import net.tropicraft.mods.TropicraftMod;

public class ItemTropicraftDart extends ItemTropicraft {

	public static final String[] dartNames = new String[]{"paralyze", "poison", "moveSlowdown", "harm", "confusion", "hunger", "weakness"};    
    private static final int[] colors = new int[]{0xFFB5FF, 0x258EA3, 0xCFD7D9, 0xDFE23A, 0x5137FB, 0xE12424, 0x6CC7FF};
    private static final String[] tooltipText = new String[]{"\u00a7dDart of Paralysis", "\u00a73Poison Dart", "\u00a77Dart of Slow Movement", "\u00a76Dart of Harm", "\u00a71Dart of Confusion", 
    	"\u00a74Dart of Hunger", "\u00a79Dart of Weakness"};
	
	public ItemTropicraftDart(int i) {
		super(i);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
		this.setCreativeTab(TropicraftMod.tabCombat);
	}
	
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < dartNames.length; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getIconFromDamageForRenderPass(int par1, int par2) {
    	return par2 == 0 ? 57 : 58;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
    	return colors[par1ItemStack.getItemDamage()];
    }
    
    
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer ent, List list, boolean wat) {
		list.clear();
		list.add(tooltipText[itemstack.getItemDamage()]);
	}

}
