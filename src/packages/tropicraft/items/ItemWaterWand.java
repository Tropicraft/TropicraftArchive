package tropicraft.items;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.creative.TropiCreativeTabs;

public class ItemWaterWand extends ItemTropicraftImpl {

	private static final int MAXCHARGE = 100;
	
	public ItemWaterWand(int par1, String imageName) {
		super(par1, imageName);
		this.setMaxStackSize(1);
		this.setMaxDamage(2000);
		setCreativeTab(TropiCreativeTabs.tabTools);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer){
		double inc = Math.PI/12;
		if(!world.isRemote){
			for(double lat = 0; lat < 2*Math.PI; lat += inc){
				for( double lng = 0; lng < 2*Math.PI; lng += inc){
					for(double len = 1; len < 3; len +=.5D){
						int x1 = (int)(Math.cos(lat)*len);
						int z1 = (int)(Math.sin(lat)*len);
						int y1 = (int)(Math.sin(lng)*len);
						if(!removeWater(world, itemstack, entityplayer, (int)entityplayer.posX + x1, (int)(entityplayer.posY) + y1, (int)(entityplayer.posZ) + z1)){
							break;
						}
					}
				}
				
			}
			entityplayer.setItemInUse(itemstack, 1);
		}
		
		return itemstack;
    }

    private boolean removeWater(World world, ItemStack itemstack, EntityPlayer player, int x, int y, int z){
    	if (!world.isRemote) {
    		   		
	    	if (world.getBlockMaterial(x, y, z) == Material.water) {
	    		itemstack.damageItem(1, player);
	    		world.setBlockToAir(x, y, z);
	    		return true;
	    	}
	    	
	    	if(world.isAirBlock(x, y, z)) {
	    		return true;
	    	}
    	}
    	
    	return false;
    }
}
