package net.tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.EnumToolMaterialTropics;

public class ItemTropicsDagger extends ItemTropicraft {

    private int weaponDamage;

    public ItemTropicsDagger(int i, EnumToolMaterialTropics enumtoolmaterial) {
        super(i);
        maxStackSize = 1;
        setMaxDamage(enumtoolmaterial.getMaxUses());
        weaponDamage = 4 + enumtoolmaterial.getDamageVsEntity();// * 2;
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block) {
        return block.blockID != Block.web.blockID ? 1.5F : 15F;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        itemstack.damageItem(1, entityliving1);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        return true;
    }

    @Override
    public int getDamageVsEntity(Entity entity) {
        return weaponDamage;
    }

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
        return 0x11940;
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
}
