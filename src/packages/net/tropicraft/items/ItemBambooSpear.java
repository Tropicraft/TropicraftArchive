package net.tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

public class ItemBambooSpear extends ItemTropicraft {

	public int weaponDamage;

	public ItemBambooSpear(int i) {
		super(i);
		maxStackSize = 1;
		setMaxDamage(16);
		weaponDamage = 4;
		this.setCreativeTab(TropicraftMod.tabCombat);
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {

		itemstack.damageItem(1, entityliving1);
		return true;
	}

	@Override
	public int getDamageVsEntity(Entity entity) {
		int k = (this.getMaxDamage()) / 2;
		if (k <= 0) {
			return 1;
		}
		return k;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 72000;
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return block.blockID == Block.web.blockID;
	}

	@Override
	public int getItemEnchantability() {
		return 1;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {

		if (entityplayer.capabilities.isCreativeMode || entityplayer.inventory.hasItem(Item.arrow.shiftedIndex)) {

			int j = getMaxItemUseDuration(itemstack) - i;
			float f = (float) j / 20F;
			f = (f * f + f * 2.0F) / 3F;
			if ((double) f < 0.10000000000000001D) {
				return;
			}
			if (f > 1.0F) {
				f = 1.0F;
			}
/*
			EntitySpear entitydart = new EntitySpear(world, entityplayer, f * 2.0F);
			itemstack.damageItem(1, entityplayer);
			world.playSoundAtEntity(entityplayer, "dartblow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
			entityplayer.inventory.consumeInventoryItem(Item.arrow.shiftedIndex);
			if (!world.isRemote) {
				world.spawnEntityInWorld(entitydart);

			}
*/
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {

		if(!entityplayer.isInWater())
			return itemstack;
		else
		{
			if (entityplayer.capabilities.isCreativeMode)
			{
			//	entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
			}

			return itemstack;
		}
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}
}
