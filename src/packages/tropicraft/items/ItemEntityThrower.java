package tropicraft.items;

import tropicraft.entities.hostile.land.tribes.ashen.LostMask;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEntityThrower extends ItemTropicraft{

	public ItemEntityThrower(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getImageName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int facing, float offsetX, float offsetY, float offsetZ){
		float angle = player.rotationYaw;
		LostMask mask = new LostMask(world, 2,  player.posX, player.posY + 1, player.posZ, angle);
		
		if (!world.isRemote) {
			world.spawnEntityInWorld(mask);
		}
		return true;
	}

}
