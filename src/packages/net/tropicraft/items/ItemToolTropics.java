package net.tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.EnumToolMaterialTropics;
import net.tropicraft.mods.TropicraftMod;

public class ItemToolTropics extends ItemTropicraft {
        
    public Block blocksEffectiveAgainst[];
    public float efficiencyOnProperMaterial;
    public int damageVsEntity;
    public EnumToolMaterialTropics toolMaterial;

    public ItemToolTropics(int i, int j, EnumToolMaterialTropics enumtoolmaterial, Block ablock[]) {
        super(i);
        efficiencyOnProperMaterial = 4F;
        toolMaterial = enumtoolmaterial;
        blocksEffectiveAgainst = ablock;
        maxStackSize = 1;
        setMaxDamage(enumtoolmaterial.getMaxUses());
        efficiencyOnProperMaterial = enumtoolmaterial.getEfficiencyOnProperMaterial();
        damageVsEntity = j + enumtoolmaterial.getDamageVsEntity();
        this.setCreativeTab(TropicraftMod.tabTools);
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block) {
        for (int i = 0; i < blocksEffectiveAgainst.length; i++) {        	
        	if (blocksEffectiveAgainst[i] == block || block == TropicraftMod.oreAzurite || block == TropicraftMod.oreZircon || block == TropicraftMod.oreEudialyte) {
        		
        		return efficiencyOnProperMaterial;
            }
        }
		
		return 1.0F;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLiving entityHit, EntityLiving player) {
    	itemstack.damageItem(2, entityHit);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D)
        {
            par1ItemStack.damageItem(1, par7EntityLiving);
        }

        return true;
    }

    @Override
    public int getDamageVsEntity(Entity entity) {
        return damageVsEntity;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}
