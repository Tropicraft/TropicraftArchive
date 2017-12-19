package tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import tropicraft.EnumToolMaterialTropics;
import tropicraft.creative.TropiCreativeTabs;
import tropicraft.enchanting.EnchantmentManager;
import tropicraft.entities.EnumTropiMobType;
import tropicraft.entities.ISpecialTropiMob;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTropicsSword extends ItemTropicraft {

	private int weaponDamage;
	
	public ItemTropicsSword(int id, String imgName, EnumToolMaterialTropics enumtoolmaterial) {
		super(id, imgName);
        this.setMaxStackSize(1);
        setMaxDamage(enumtoolmaterial.getMaxUses());
        weaponDamage = 4 + enumtoolmaterial.getDamageVsEntity() * 2;
        setCreativeTab(TropiCreativeTabs.tabCombat);
	}
	
	/**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    @Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        if (par2Block.blockID == Block.web.blockID)
        {
            return 15.0F;
        }
        else
        {
            Material var3 = par2Block.blockMaterial;
            return var3 != Material.plants && var3 != Material.vine && var3 != Material.leaves && var3 != Material.pumpkin ? 1.0F : 1.5F;
        }
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
    	int l = EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.baneOfReptiles.effectId, itemstack);
    	
    	if (l > 0 && entityliving instanceof ISpecialTropiMob) {
    		if (((ISpecialTropiMob)entityliving).getMobType() == EnumTropiMobType.REPTILE) {
    			entityliving.attackEntityFrom(DamageSource.magic, 4);
    		}
    	}
    	
        itemstack.damageItem(1, entityliving1);
        return true;
    }
    
    @Override
    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D)
        {
            par1ItemStack.damageItem(2, par7EntityLiving);
        }

        return true;
    }
    
    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
    	super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
    	
    	int l = EnchantmentHelper.getEnchantmentLevel(EnchantmentManager.baneOfReptiles.effectId, par1ItemStack);
    }
    
    @Override
    public int getDamageVsEntity(Entity entity) {
        return weaponDamage;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.block;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
        return itemstack;
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        return block.blockID == Block.web.blockID;
    }    

	@Override
	public String getImageName() {
		return "sword_" + imageName;
	}
}
