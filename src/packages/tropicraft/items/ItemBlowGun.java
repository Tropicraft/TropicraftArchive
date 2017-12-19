package tropicraft.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.entities.projectiles.EntityDart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlowGun extends ItemTropicraft {

	public float FOV;
	public float derp;

	public static final String[] dartNames = new String[]{"paralyze", "poison", "moveSlowdown", "harm", "confusion", "hunger", "weakness"};    
	private static final String[] tooltipText = new String[]{"\u00a7dParalysis", "\u00a73Poison", "\u00a77Slow Movement", "\u00a76Harm", "\u00a71Confusion", "\u00a74Hunger", "\u00a79Weakness"};

	public ItemBlowGun(int i) {
		super(i);
		maxStackSize = 1;
		derp = 0;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		setCreativeTab(TropiCreativeTabs.tabCombat);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer ent, List list, boolean wat) {
		list.clear();
		list.add("Blow Gun of " + tooltipText[itemstack.getItemDamage()]);
	}

	@SideOnly(Side.CLIENT)

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < dartNames.length; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {

		if (entityplayer.capabilities.isCreativeMode || entityplayer.inventory.hasItemStack(new ItemStack(TropicraftItems.dart, 1, itemstack.getItemDamage()))) {

			//System.out.println(entityplayer.inventory.hasItemStack(new ItemStack(TropicraftItems.dart, itemstack.getItemDamage())));

			int j = getMaxItemUseDuration(itemstack) - i;
			float f = (float) j / 20F;
			f = (f * f + f * 2.0F) / 3F;

			if ((double) f < 0.10000000000000001D) {
				return;
			}

			if (f > 1.0F) {
				f = 1.0F;
			}

			EntityDart dart = new EntityDart(world, entityplayer, f * 2.0F, (short)itemstack.getItemDamage());

			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "dartblow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

			int slot = 0;

			for (ItemStack stack : entityplayer.inventory.mainInventory) {
				if (stack != null && stack.getItem().itemID == TropicraftItems.dart.itemID && stack.getItemDamage() == itemstack.getItemDamage()) {
					break;
				}
				slot++;
			}

			if (!world.isRemote) {
				entityplayer.inventory.mainInventory[slot].stackSize--;
				EntityDart entitydart = new EntityDart(world, entityplayer, f * 2.0F, (short)itemstack.getItemDamage());
				if (f == 1.0F)
				{
			//TODO		entitydart.setIsCritical(true);
				}
				world.spawnEntityInWorld(entitydart);
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 25000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!world.isRemote && entityplayer.capabilities.isCreativeMode || entityplayer.inventory.hasItemStack(new ItemStack(TropicraftItems.dart, 1, itemstack.getItemDamage()))) {
			entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
			derp = 0;
		}
		return itemstack;
	}

	@Override
	public String getImageName() {
		return "dartgun";
	}
}
