package net.tropicraft.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFruitJuice extends ItemTropicraftDrink {
	public ItemFruitJuice(int i, int j, float k) {
		super(i, j, k, false);
		setMaxStackSize(1);
		setContainerItem(TropicraftMod.bambooMugEmpty);
	}
	
    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.drink;
    }
    
    @Override
    public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
    	super.onFoodEaten(itemstack, world, entityplayer);
    	return new ItemStack(TropicraftMod.bambooMugEmpty);
    }
}
