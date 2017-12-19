package tropicraft.items;

import java.util.Random;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tropicraft.EnumToolMaterialTropics;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.enchanting.EnchantmentManager;

public abstract class ItemToolTropics extends ItemTropicraft {

	public Block blocksEffectiveAgainst[];
	public float efficiencyOnProperMaterial;
	public int damageVsEntity;
	public EnumToolMaterialTropics toolMaterial;

	public ItemToolTropics(int i, int j, EnumToolMaterialTropics enumtoolmaterial, Block ablock[]) {
		super(i);
		efficiencyOnProperMaterial = 4F;
		toolMaterial = enumtoolmaterial;
		blocksEffectiveAgainst = ablock;
		setMaxStackSize(1);
		setMaxDamage(enumtoolmaterial.getMaxUses());
		efficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial();
		damageVsEntity = j + enumtoolmaterial.getDamageVsEntity();
        setCreativeTab(TropiCreativeTabs.tabTools);
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block block) {
		for (int i = 0; i < blocksEffectiveAgainst.length; i++) {        	
		/*	if (blocksEffectiveAgainst[i] == block || block == TropicraftMod.oreAzurite || block == TropicraftMod.oreZircon || block == TropicraftMod.oreEudialyte) {

				return efficiencyOnProperMaterial;
			}*/
		}

		return 1.0F;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityHit, EntityLivingBase player) {
		itemstack.damageItem(2, entityHit);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLiving)
	{
		if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D)
		{
			par1ItemStack.damageItem(1, par7EntityLiving);
		}
		
		int l = EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.midasTouch.effectId, par1ItemStack);
		
		if (l > 0 && !par2World.isRemote) {
			Random rand = new Random();
			
			if (rand.nextInt(20) < l) {
				EntityItem item = new EntityItem(par2World, par4 + rand.nextFloat(), par5 + rand.nextFloat(), par6 + rand.nextFloat(), new ItemStack(TropicraftItems.oreDrops, 1, 2));
				
				par2World.spawnEntityInWorld(item);
			}
		}

		return true;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
	
    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double)this.damageVsEntity, 0));
        return multimap;
    }

}
