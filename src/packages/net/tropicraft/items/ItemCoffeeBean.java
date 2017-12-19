package net.tropicraft.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import net.tropicraft.mods.TropicraftMod;

public class ItemCoffeeBean extends ItemTropicraft implements IPlantable {
	public ItemCoffeeBean(int i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(TropicraftMod.tabFood);
	}
	
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(this.shiftedIndex, 1, 0));
		par3List.add(new ItemStack(this.shiftedIndex, 1, 1));
		par3List.add(new ItemStack(this.shiftedIndex, 1, 2));
	}

	@Override
	public int getIconFromDamage(int dmg) {
		dmg = dmg & 3; // last two bits
		return iconIndex + dmg;
	}

	@Override
	public String getItemNameIS(ItemStack stack) {
		return getItemName()+"."+stack.getItemDamage();
	}

	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z) {
		return EnumPlantType.Crop;
	}

	@Override
	public int getPlantID(World world, int x, int y, int z) {
		return TropicraftMod.coffeePlant.blockID;
	}

	@Override
	public int getPlantMetadata(World world, int x, int y, int z) {
		return 0;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float offsetX, float offsetY, float offsetZ) {
		if (stack.getItemDamage() != 0) { // 0 = raw, 1 = roasted, 2 = cherry
			return false;
		}
		
		if (side != 1) {
			return false;
		}
		
        if (!player.canPlayerEdit(x, y, z, side, stack) || !player.canPlayerEdit(x, y + 1, z, side, stack)) {
        	return false;
        }
        
        if (world.getBlockId(x, y, z) != Block.tilledField.blockID) {
        	return false;
        }
        
        if (!Block.tilledField.canSustainPlant(world, x, y, z, ForgeDirection.UP, this)) {
        	return false;
        }
        
        if (!world.isAirBlock(x, y+1, z)) {
        	return false;
        }
        
        world.setBlockWithNotify(x, y + 1, z, TropicraftMod.coffeePlant.blockID);
        --stack.stackSize;
		return true;
	}
}
