package tropicraft.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import tropicraft.ModInfo;
import tropicraft.creative.TropiCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTropicraftArmor extends ItemArmor implements ISpecialArmor {

	@SideOnly(Side.CLIENT)
	public static List[] fxLayers;
	
	/** Image name of file for icon */
	private String imageName;
	
	public ItemTropicraftArmor(int par1, int par3, int par4, EnumArmorMaterial material) {
		super(par1, material, par3, par4);
		setCreativeTab(TropiCreativeTabs.tabCombat);		
	}
	
	public ItemTropicraftArmor(int par1, String imageName, int par3, int par4) {
		super(par1, EnumArmorMaterial.IRON, par3, par4);
		this.imageName = imageName;
        setCreativeTab(TropiCreativeTabs.tabCombat);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        // If it is null, then we can assume this method is being overridden and set elsewhere
        if (this.imageName != null) {
        	this.itemIcon = par1IconRegister.registerIcon(ModInfo.ICONLOCATION + imageName);
        }
    }

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor,
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
	public void damageArmor(EntityLivingBase player, ItemStack stack,
			DamageSource source, int damage, int slot) {
		stack.damageItem(damage, player);
	}
}
