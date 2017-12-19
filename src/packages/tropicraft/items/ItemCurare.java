package tropicraft.items;

import java.util.List;

import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCurare extends ItemTropicraft {
	
	public static final String[] effectNames = new String[]{"paralyze", "poison", "moveSlowdown", "harm", "confusion", "hunger", "weakness"};
    private static final int[] colors = new int[]{0xFFB5FF, 0x258EA3, 0xCFD7D9, 0xDFE23A, 0x5137FB, 0xE12424, 0x6CC7FF};
	private static final String[] tooltipText = new String[]{"\u00a7dParalysis", "\u00a73Poison", "\u00a77Slow Movement", "\u00a76Harm", "\u00a71Confusion", "\u00a74Hunger", "\u00a79Weakness"};
	
	@SideOnly(Side.CLIENT)
	private Icon overlay;
	
	@SideOnly(Side.CLIENT)
	private Icon backImage;
	
	public ItemCurare(int i) {
		super(i);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        setCreativeTab(TropiCreativeTabs.tabCombat);
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
    public Icon getIconFromDamageForRenderPass(int par1, int par2) {
    	return par2 > 0 ? this.overlay : this.backImage/*getIconFromDamage(par1)*/;
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

	@Override
	public String getImageName() {
		return "curare";
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.overlay = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + "curare_overlay");
        this.backImage = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + "curare");
    }
}
