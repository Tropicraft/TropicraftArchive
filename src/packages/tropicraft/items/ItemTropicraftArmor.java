package tropicraft.items;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IArmorTextureProvider;
import net.minecraftforge.common.ISpecialArmor;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import weather.c_CoroWeatherUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTropicraftArmor extends ItemArmor implements ISpecialArmor {

	@SideOnly(Side.CLIENT)
	public static List[] fxLayers;
	
	public ItemTropicraftArmor(int par1, int par3, int par4, EnumArmorMaterial material){
		super(par1, material, par3, par4);
		setCreativeTab(TropiCreativeTabs.tabCombat);		
	}
	public ItemTropicraftArmor(int par1, int par3, int par4) {
		super(par1, EnumArmorMaterial.IRON, par3, par4);
        setCreativeTab(TropiCreativeTabs.tabCombat);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
    }

	@Override
	public ArmorProperties getProperties(EntityLiving player, ItemStack armor,
			DamageSource source, double damage, int slot) {
		if(source == DamageSource.inFire)
			return new ArmorProperties(10, 1.0, Integer.MAX_VALUE);
		else
			return new ArmorProperties(10, 0.3, Integer.MAX_VALUE);
	}

	/**
	 * Get the displayed effective armor.
	 *
	 * @param player The player wearing the armor.
	 * @param armor The ItemStack of the armor item itself.
	 * @param slot The armor slot the item is in.
	 * @return The number of armor points for display, 2 per shield.
	 */
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 3;
	}

	/**
	 * Applies damage to the ItemStack. The mod is responsible for reducing the
	 * item durability and stack size. If the stack is depleted it will be cleaned
	 * up automatically.
	 *
	 * @param entity The entity wearing the armor
	 * @param armor The ItemStack of the armor item itself.
	 * @param source The source of the damage, which can be used to alter armor
	 *     properties based on the type or source of damage.
	 * @param damage The amount of damage being applied to the armor
	 * @param slot The armor slot the item is in.
	 */
	@Override
	public void damageArmor(EntityLiving player, ItemStack stack,
			DamageSource source, int damage, int slot) {
		stack.damageItem(damage, player);
	}
}
