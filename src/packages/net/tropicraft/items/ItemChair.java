package net.tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.tropicraft.entities.EntityChair;
import net.tropicraft.mods.TropicraftMod;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemChair extends ItemTropicraft {

    public ItemChair(int i) {
        super(i);
        setHasSubtypes(true);
        maxStackSize = 1;
    }
    
    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
    
    @Override
    public String getItemNameIS(ItemStack itemstack) {
        return getItemName() + "." + itemstack.getItemDamage();
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 5; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int getIconFromDamage(int i) {
        if (i == 0) {
            return 11;
        }

        int doop = 112;

        return i + doop - 1;
    }
 
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        int color = itemstack.getItemDamage();
        float f = 1.0F;
        float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double) f;
        double d1 = (entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double) f + 1.6200000000000001D) - (double) entityplayer.yOffset;
        double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double) f;
        Vec3 vec3d = Vec3.createVectorHelper(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;
        double d3 = 5D;
        
        
        Vec3 vec3d1 = vec3d.addVector((double) f7 * d3, (double) f8 * d3, (double) f9 * d3);
        MovingObjectPosition movingobjectposition = world.rayTraceBlocks_do(vec3d, vec3d1, true);
        if (movingobjectposition == null) {
            return itemstack;
        }
        if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE && movingobjectposition.sideHit == 1) {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
//            float f10 = (float)Math.toDegrees(Math.atan2((float)k + .5F - entityplayer.posZ, (float)i +.5F - entityplayer.posX));
//            		TropicraftMod.debugOut(getClass(), ((Float)f10).toString());
            if (!world.isRemote) {
                if (world.getBlockId(i, j, k) == Block.snow.blockID) {
                    j--;
                }
                
                world.spawnEntityInWorld(new EntityChair(world, (float) i + 0.5F, (float) j + 1.0F, (float) k + 0.5F, 0, color, entityplayer));

            }
            
            if(!entityplayer.capabilities.isCreativeMode)
            	itemstack.stackSize--;
        }
        return itemstack;
    }
}