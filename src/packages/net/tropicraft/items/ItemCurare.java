package net.tropicraft.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.tropicraft.mods.TropicraftMod;

public class ItemCurare extends ItemTropicraft {
	
	public static final String[] effectNames = new String[]{"paralyze", "poison", "moveSlowdown", "harm", "confusion", "hunger", "weakness"};
    private static final int[] colors = new int[]{0xFFB5FF, 0x258EA3, 0xCFD7D9, 0xDFE23A, 0x5137FB, 0xE12424, 0x6CC7FF};
	private static final String[] tooltipText = new String[]{"\u00a7dParalysis", "\u00a73Poison", "\u00a77Slow Movement", "\u00a76Harm", "\u00a71Confusion", "\u00a74Hunger", "\u00a79Weakness"};
	
	public ItemCurare(int i) {
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
        for (int var4 = 0; var4 < effectNames.length; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
    
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer ent, List list, boolean wat) {
		list.clear();
		list.add(tooltipText[itemstack.getItemDamage()] + " Curare");
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public int getIconFromDamageForRenderPass(int par1, int par2) {
    	return par2 == 0 ? 59 : 60;
    }
	
    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
    	return par2 == 0  ? 0x00ff00 : colors[par1ItemStack.getItemDamage()];
    }

}
