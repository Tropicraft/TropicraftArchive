package tropicraft.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringTranslate;
import net.minecraft.world.World;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.entities.passive.water.EntityTropicalFish;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFishBucket extends ItemTropicraft {

    public ItemFishBucket(int i) {
        super(i);
        maxStackSize = 1;
        hasSubtypes = true;
        setCreativeTab(TropiCreativeTabs.tabMisc);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (!world.isRemote) {
            EntityTropicalFish fish = new EntityTropicalFish(world); //1.6.2 edited to adapt to new constructor, + 2 lines below
            fish.setPosition(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
            fish.setColor(itemstack.getItemDamage());
            fish.disableDespawning();
            world.spawnEntityInWorld(fish);
        }
        return new ItemStack(TropicraftItems.bucketTropicsWater, 1);
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < EntityTropicalFish.names.length; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
    	return EntityTropicalFish.names[itemstack.getItemDamage()];
    }

	@Override
	public String getImageName() {
		return "fishbucket";
	}
}
