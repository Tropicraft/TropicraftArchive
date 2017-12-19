package tropicraft.items;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.enchanting.EnchantmentManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWaterGear extends ItemArmor {

	private boolean outOfWater = true;
	private boolean outOfWaterLast = true;
	private boolean hasFlippers = false;
	private String imageName;

	public ItemWaterGear(int i, int j, int k, String imageName) {
		super(i, EnumArmorMaterial.CHAIN, j, k);
		this.imageName = imageName;
		setCreativeTab(TropiCreativeTabs.tabMisc);
		setMaxDamage(0);
		maxStackSize = 1;
	}
	
    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return this.itemIcon;
    }
	
    /**
     * Called to tick armor in the armor slot. Override to do something
     *
     * @param world
     * @param player
     * @param itemStack
     */
	@Override
    public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
    {
		ItemStack item3 = player.inventory.armorInventory[3];
		ItemStack item0 = player.inventory.armorInventory[0];

		// snorkel stuff
		if (item3 != null && item3.itemID == TropicraftItems.snorkel.itemID) {
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
			if (player.getAir() > 300 && (item3 == null || item3.itemID != TropicraftItems.snorkel.itemID)) {
				player.setAir(300);
			} else {
				outOfWater = true;
				outOfWaterLast = true;
			}

		// flippers stuff
		if (item0 != null && item0.itemID == TropicraftItems.flippers.itemID) {
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
				
				int l = EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.scubaSteve.effectId, item3);
				
				if (l > 0)
					player.moveEntityWithHeading(-1F, -1F);
				else
					player.moveEntityWithHeading(-1E-4F, -1E-4F);
			} else {
				player.landMovementFactor /= 1.33333;
				player.capabilities.isFlying = false;
			}

			hasFlippers = true;
		}

		if ((item0 == null || item0.itemID != TropicraftItems.flippers.itemID) && hasFlippers) {
			if (!player.capabilities.isCreativeMode && player.capabilities.isFlying) {
				player.capabilities.isFlying = false;
			}
			
			hasFlippers = false;
		}		
    }

	/**
	 * Create a bubble effect when using this water gear
	 * @param world
	 * @param player
	 * @param f1
	 * @param f2
	 */
	@SideOnly(Side.CLIENT)
	private void sparkle(World world, EntityPlayer player, float f1, float f2) {
		world.spawnParticle("bubble", player.posX - f1,	player.posY + .35F,	player.posZ + f2, player.motionX, player.motionY - (double) ((new Random()).nextFloat() * 0.2F), player.motionZ);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.itemIcon = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + imageName);
    }
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player,
			ScaledResolution resolution, float partialTicks, boolean hasScreen,
			int mouseX, int mouseY) {
		int i = resolution.getScaledWidth();
		int j = resolution.getScaledHeight();
		GL11.glDisable(2929 /*
							 * GL_DEPTH_TEST
							 */);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3008 /*
							 * GL_ALPHA_TEST
							 */);
		GL11.glBindTexture(3553 /*
								 * GL_TEXTURE_2D
								 */, Minecraft.getMinecraft().renderEngine
				.getTexture("%blur%/mods/TropicraftMod/gui/snorkelGrad.png"));
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(0.0D, j, -90D, 0.0D, 1.0D);
		tessellator.addVertexWithUV(i, j, -90D, 1.0D, 1.0D);
		tessellator.addVertexWithUV(i, 0.0D, -90D, 1.0D, 0.0D);
		tessellator.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
		tessellator.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(2929 /*
							 * GL_DEPTH_TEST
							 */);
		GL11.glEnable(3008 /*
							 * GL_ALPHA_TEST
							 */);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
		return slot == 2 ? "/mods/TropicraftMod/textures/armor/WaterGear_2.png" : slot == 0 || slot == 1 || slot == 3 ? "/mods/TropicraftMod/textures/armor/WaterGear_1.png" : null;
    }
}