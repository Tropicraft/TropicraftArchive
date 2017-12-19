package tropicraft.items.koa;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.entities.projectiles.EntityTameBall;

/* extends to allow inner logic fixes to still happen */
public class ItemStaffOfTaming extends Item
{
    public ItemStaffOfTaming(int i)
    {
        super(i);
        maxStackSize = 1;
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	if (!entityplayer.capabilities.isCreativeMode)
        {
        	itemstack.damageItem(1, entityplayer);
        }
        world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote)
        {
            world.spawnEntityInWorld(new EntityTameBall(world, entityplayer));
        }
        return itemstack;
    }
    
    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
    		Entity entity) {
    	if (!player.worldObj.isRemote) {
    		if (player.isSneaking()) {
    			if (!(entity instanceof EntityPlayer)) {
	    			if (entity.ridingEntity != null) {
	    				entity.mountEntity(null);
	    			} else {
	    				entity.mountEntity(player);
	    			}
    			}
    		} else {
		    	if (player.ridingEntity != null) {
		    		player.mountEntity(null);
		    	} else {
		    		player.mountEntity(entity);
		    	}
    		}
    	}
    	return true;
    }
}
