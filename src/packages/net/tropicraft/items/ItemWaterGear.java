package net.tropicraft.items;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWaterGear extends ItemArmor implements ITropicraftArmor {

	private boolean outOfWater = true;
	private boolean outOfWaterLast = true;
	private boolean hasFlippers = false;

	public ItemWaterGear(int i, int j, int k) {
		super(i, EnumArmorMaterial.CHAIN, j, k);
		setTextureFile("/tropicalmod/tropiitems.png");
		this.setCreativeTab(TropicraftMod.tabMisc);
		setMaxDamage(0);
		maxStackSize = 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value and the given render pass
	 */
	public int getIconFromDamageForRenderPass(int par1, int par2) {
		return this.iconIndex;
	}

	@Override
	public void onArmorUpdate(EntityPlayer player) {
		ItemStack item3 = player.inventory.armorInventory[3];
		ItemStack item0 = player.inventory.armorInventory[0];

		// snorkel stuff
		if (item3 != null && item3.itemID == TropicraftMod.snorkel.shiftedIndex) {
			outOfWater = !player.isInsideOfMaterial(Material.water) || !player.isInWater();

			if (outOfWaterLast && outOfWater) {
				player.setAir(300);
			}

			if (outOfWaterLast && !outOfWater) {
				player.setAir(1200);
				outOfWaterLast = false;
			}

			if (outOfWater) {
				outOfWaterLast = true;
			}
		} else
			if (player.getAir() > 300 && (item3 == null || item3.itemID != TropicraftMod.snorkel.shiftedIndex)) {
				player.setAir(300);
			} else {
				outOfWater = true;
				outOfWaterLast = true;
			}

		// flippers stuff
		if (item0 != null && item0.itemID == TropicraftMod.flippers.shiftedIndex) {
			if (player.isInsideOfMaterial(Material.water)) {
				player.capabilities.isFlying = true;
				player.landMovementFactor = player.capabilities.getWalkSpeed();
				if (item0.isItemEnchanted()) {
					player.moveFlying(1E-4F, 1E-4F, 0.00000000001f);
					player.motionX /= 1.06999999;
					player.motionZ /= 1.06999999;
					player.moveEntityWithHeading(-1E-4F, -1E-4F);
				} else {
					player.moveFlying(1E-4F, 1E-4F, 0.00000000001f);
					player.motionX /= 1.26999999;
					player.motionZ /= 1.26999999;
					player.moveEntityWithHeading(-1E-4F, -1E-4F);
				}
				//	item0.isItemEnchanted() ? player.moveEntityWithHeading(-1F, -1F) : player.moveEntityWithHeading(-1E-4F, -1E-4F);
			} else {
				player.landMovementFactor /= 1.33333;
				player.capabilities.isFlying = false;
			}

			hasFlippers = true;
		}

		if ((item0 == null || item0.itemID != TropicraftMod.flippers.shiftedIndex) && hasFlippers) {
			if (!player.capabilities.isCreativeMode && player.capabilities.isFlying) {
				System.out.println("herp : )");
				player.capabilities.isFlying = false;
			}
			
			System.out.println("herp2 : )");
			hasFlippers = false;
		}		
	}

	@SideOnly(Side.CLIENT)
	private void sparkle(World world, EntityPlayer player, float f1, float f2) {
		world.spawnParticle(
				"bubble",
				player.posX - f1,
				player.posY + .35F,
				player.posZ + f2,
				player.motionX,
				player.motionY
				- (double) ((new Random()).nextFloat() * 0.2F),
				player.motionZ);
	}
}