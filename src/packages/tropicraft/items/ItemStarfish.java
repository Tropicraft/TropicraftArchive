package tropicraft.items;

import java.util.ArrayList;
import java.util.List;

import tropicraft.entities.items.EntityWallStarfish;
import tropicraft.entities.passive.water.EntityStarfish;
import tropicraft.entities.passive.water.StarfishType;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemStarfish extends ItemTropicraft {
	public ItemStarfish(int id) {
		super(id);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public String getImageName() {
		return null;
	}

	@Override
	public void registerIcons(IconRegister iconRegistry) {
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int facing, float offsetX, float offsetY, float offsetZ) {
		if (facing == 0 || facing == 1) {
			return false;
		}

		int xPosition = x;
		int yPosition = y;
		int zPosition = z;
		
		int dmg = stack.getItemDamage();
		
		if (dmg > StarfishType.values().length) {
			dmg = 0;
		}
		
		StarfishType starfishType = StarfishType.values()[dmg];
		
		int direction = Direction.facingToDirection[facing];
		
		if (!player.canPlayerEdit(x, y, z, facing, stack)) {
			return false;
		}

		EntityWallStarfish starfish = new EntityWallStarfish(world, xPosition, yPosition, zPosition, direction, starfishType);
		
		if (!starfish.onValidSurface()) {
			return false;
		}
		
		if (!world.isRemote) {
			world.spawnEntityInWorld(starfish);
		}
		
		stack.stackSize--;
		
		return true;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int dmg = stack.getItemDamage();
		
		if (dmg >= StarfishType.values().length) {
			dmg = 0;
		}
		
		return StarfishType.values()[dmg].getUnlocalizedName();
	}



	@Override
	public void getSubItems(int par1, CreativeTabs tab, List list) {
		for (int i = 0; i < StarfishType.values().length; i++) {
			list.add(new ItemStack(itemID, 1, i));
		}
	}
	
	
}
