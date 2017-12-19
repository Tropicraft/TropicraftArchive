package tropicraft.items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.entities.hostile.land.tribes.koa.EntityKoaMemberNew;
import tropicraft.entities.passive.water.EntityTropicalFish;
import CoroAI.componentAI.ICoroAI;
import CoroAI.entity.EnumJob;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMobEgg extends ItemTropicraft {

	private Icon[] images;
	private String[] imageNames;

	public static String[] names = {"Iguana Egg", "Starfish Egg", "Green Frog Spawn", "Red Frog Spawn", "Yellow Frog Spawn",
		"Blue Frog Spawn", "Eye of Head", "Marlin Spawn", "Tropical Fish Spawn", "Ashen Ash", "Turtle Egg", "Man o' War", "Monkey's Paw",
		"Koa Headband", "TropiCreeper Egg", "TropiSkelly Skirt", "Spotted Eagle Ray", "Failgull", "Sea Urchin"};

	public ItemMobEgg(int id, String[] imageNames) {
		super(id);
		this.imageNames = imageNames;
		setMaxStackSize(64);
		setHasSubtypes(true);
		this.setCreativeTab(TropiCreativeTabs.tabMisc);
		
		if (names.length != imageNames.length)
			throw new IllegalArgumentException("A Tropicraft developer failed to make the number of mob egg names in ItemMobEgg match up with " +
					"the number of mob egg names in TropicraftItems!");
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
	public Icon getIconFromDamage(int i) {
		return images[i];
	}
	
    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
    	return names[itemstack.getItemDamage()];
    }

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10) {
		if (!world.isRemote) {
			String s = "";
			switch (itemstack.getItemDamage()) {
			case 0:
				s = "Iguana";
				break;
			case 1:
				s = "Starfish";
				break;
			case 2:
				s = "TreeFrogGreen";
				break;
			case 3:
				s = "TreeFrogRed";
				break;
			case 4:
				s = "TreeFrogYellow";
				break;
			case 5:
				s = "TreeFrogBlue";
				break;
			case 6:
				s = "EIH";
				break;
			case 7:
				s = "Marlin";
				break;
			case 8:
				s = "Tropical Fish";
				if(!world.isRemote){
				EntityTropicalFish fish = new EntityTropicalFish(world);
				fish.setLocationAndAngles((double) i + 0.5D, j, (double) k + 5.5D, 0.0F, 0.0F);
				world.spawnEntityInWorld(fish);
				return true;
				}
				break;
			case 9:
				s = "AshenHunter";
				break;
			case 10:
				s = "Turtle Egg";
				break;
			case 11:
				s = "MOW";
				break;
			case 12:
				s = "VMonkey";
				break;
			case 13:
				s = "Koa Man";
				break;
			case 14:
				s = "TropiCreeper";
				break;
			case 15:
				s = "TropiSkeleton";
				break;
			case 16:
				s = "EagleRay";
				break;
			case 17:
				//TODO failgull egg texture is fail
				s = "Failgull";
				break;
			case 18:
				s = "SeaUrchin";
				break;
			default:
				return true;
			}
			
			if (s.equals("Koa Man")) {
				Random rand = new Random();
				int choice = rand.nextInt(2);
				s = "KoaHunter";
				if (choice == 1) s = "KoaFisher";
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
	
				}/* else if (entity instanceof ICoroAI) { //this is handled in the built in one, initCreature();
					((ICoroAI)entity).getAIAgent().spawnedOrNBTReloadedInit();
				}*/
				if(!world.isRemote) {
					if (entity instanceof EntityLiving) ((EntityLiving)entity).initCreature();
					world.spawnEntityInWorld(entity);
				}
	
			} else System.out.println("Error spawning: " + s);
		}
		return true;
	}

	/**
	 * Register all Icons used in this item
	 */
	@Override
	public void registerIcons(IconRegister iconRegistry) {
		images = new Icon[imageNames.length];
		
		for (int i = 0; i < imageNames.length; i++)
			this.images[i] = iconRegistry.registerIcon(ModInfo.ICONLOCATION + getImageName() + imageNames[i]);
	}

	@Override
	public String getImageName() {
		return "egg_";
	}
}
