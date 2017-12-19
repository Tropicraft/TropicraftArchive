package net.tropicraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.tropicraft.entities.EntitySnareTrap;

public class ItemSnareTrap extends ItemTropicraft {
	
	public ItemSnareTrap(int i) {
		super(i);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float offsetX, float offsetY, float offsetZ) {
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		if (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP) {
			return false;
		}
		if (world.isRemote) {
			return true;
		}
		
		x += dir.offsetX;
		z += dir.offsetZ;
		
		int height = getHeight(world, x, y, z);
		if (height < EntitySnareTrap.MIN_HEIGHT || height > EntitySnareTrap.MAX_HEIGHT) {
			return false;
		}
		
		EntitySnareTrap trap = new EntitySnareTrap(world, x, y-height+1, z, height, dir);
		world.spawnEntityInWorld(trap);
		if (!player.capabilities.isCreativeMode) {
			stack.stackSize--;
		}
		return true;
	}
	
	private int getHeight(World world, int x, int y, int z) {
		int height = 0;
		while (y >= 0 && world.getBlockId(x, y, z) == 0) {
			y--;
			height++;
		}
		return height;
	}
}
