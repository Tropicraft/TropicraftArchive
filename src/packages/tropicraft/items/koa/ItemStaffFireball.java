package tropicraft.items.koa;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.entities.projectiles.EntityFireBall;

/* extends to allow inner logic fixes to still happen */
public class ItemStaffFireball extends Item
{
	public Icon particles[] = new Icon[8];
	
    public ItemStaffFireball(int i)
    {
        super(i);
        maxStackSize = 1;
        setMaxDamage(100);
    }
    
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
    	// TODO Auto-generated method stub
    	super.registerIcons(par1IconRegister);
    	particles[0] = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + "particle_firetrail");
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
            world.spawnEntityInWorld(new EntityFireBall(world, entityplayer));
        }
        return itemstack;
    }
}
