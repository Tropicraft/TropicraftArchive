package tropicraft.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import tropicraft.ModInfo;
import tropicraft.blocks.TropicraftBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCoffeeBean extends ItemTropicraftImpl implements IPlantable {
	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	public ItemCoffeeBean(int i, String imgName, CreativeTabs tabs) {
		super(i, imgName, tabs);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		list.add(new ItemStack(this.itemID, 1, 0));
		list.add(new ItemStack(this.itemID, 1, 1));
		list.add(new ItemStack(this.itemID, 1, 2));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamage(int dmg) {
		dmg = dmg & 3; // last two bits
		return this.icons[dmg];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		this.icons = new Icon[] {
			iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName() + "raw"),
			iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName() + "roasted"),
			iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName() + "berry")
		};
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName()+"."+stack.getItemDamage();
	}

	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z) {
		return EnumPlantType.Crop;
	}

	@Override
	public int getPlantID(World world, int x, int y, int z) {
		return TropicraftBlocks.coffeePlant.blockID;
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
        
        world.setBlock(x, y + 1, z, TropicraftBlocks.coffeePlant.blockID, 0, 2);
        --stack.stackSize;
		return true;
	}
}
