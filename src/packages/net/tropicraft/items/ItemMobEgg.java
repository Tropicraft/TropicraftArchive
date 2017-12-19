package net.tropicraft.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.tropicraft.entities.EntityKoaMemberNew;
import net.tropicraft.entities.EntityTropicalFish;

import java.util.List;

import CoroAI.entity.EnumJob;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMobEgg extends ItemTropicraft {

	public static String[] names = {"Iguana Egg", "Starfish Egg", "Green Frog Spawn", "Red Frog Spawn", "Yellow Frog Spawn",
		"Blue Frog Spawn", "Eye of Head", "Marlin Spawn", "Tropical Fish Spawn", "Ashen Ash", "Turtle Egg", "Man o' War", "Monkey's Paw",
	"Koa Headband", "TropiCreeper Egg", "TropiSkelly Skirt"};

	public ItemMobEgg(int i) {
		super(i);
		setMaxStackSize(64);
		setHasSubtypes(true);
	}
	
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < names.length; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
    
    @SideOnly(Side.CLIENT)
	@Override
	public int getIconFromDamage(int i) {
		return iconIndex + i;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return getItemName() + "." + itemstack.getItemDamage();
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
		String s = "";
		switch (itemstack.getItemDamage()) {
		case 0:
			s = "Iguana";
			break;
		case 1:
			s = "Starfish";
			break;
		case 2:
			s = "Tree Frog";
			break;
		case 3:
			s = "Red Tree Frog";
			break;
		case 4:
			s = "Yellow Tree Frog";
			break;
		case 5:
			s = "Blue Tree Frog";
			break;
		case 6:
			s = "EIH";
			break;
		case 7:
			s = "Marlin";
			break;
		case 8:
			s = "Tropical Fish";
			break;
		case 9:
			s = "Ashen Hunter";
			break;
		case 10:
			s = "Turtle Egg";
			break;
		case 11:
			s = "MOW";
			break;
		case 12:
			s = "VervetMonkey";
			break;
		case 13:
			s = "Koa Man";
			break;
		case 14:
			s = "TropiCreeper";
			break;
		case 15:
			s = "TropiSkelly";
			break;
		default:
			return true;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("TropicraftMod.");
		sb.append(s);
		s = sb.toString();
		
		i += Facing.offsetsXForSide[l];
		j += Facing.offsetsYForSide[l];
		k += Facing.offsetsZForSide[l];
		//System.out.println(s + itemstack.getItemDamage());
		Entity entity = EntityList.createEntityByName(s, world);
		
		//System.out.println("Trying to spawn a null entity: " + (entity == null));
		
		
		
		if (entity != null) {
			if (!entityplayer.capabilities.isCreativeMode) {
				itemstack.stackSize--;
			}
			entity.setLocationAndAngles((double) i + 0.5D, j, (double) k + 0.5D, 0.0F, 0.0F);

			if (entity instanceof EntityTropicalFish) {
				((EntityTropicalFish) entity).disableDespawning();
			} else if (entity instanceof EntityKoaMemberNew) {
            	//((EntityKoaMemberNew)entity).initJobAndStates(((EntityKoaMemberNew)entity).getOccupation(((EntityKoaMemberNew)entity).gender));
            	((EntityKoaMemberNew)entity).initJobAndStates(EnumJob.TRADING);
            	((EntityKoaMemberNew)entity).homeX = i;
            	((EntityKoaMemberNew)entity).homeY = j;
            	((EntityKoaMemberNew)entity).homeZ = k;
            	
            } 
			if(!world.isRemote)
				world.spawnEntityInWorld(entity);

		} else System.out.println("Error spawning: " + s);
		return true;
	}
}
