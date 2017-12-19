package net.tropicraft.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tropicraft.entities.EntityTropicraftLeafball;
import net.tropicraft.mods.TropicraftMod;

public class ItemTropicraftLeafball extends ItemTropicraft {

    public ItemTropicraftLeafball(int i) {
        super(i);
        maxStackSize = 16;
        this.setCreativeTab(TropicraftMod.tabCombat);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (!entityplayer.capabilities.isCreativeMode) {
            itemstack.stackSize--;
        }
        world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
        	Entity ent = new EntityTropicraftLeafball(world, entityplayer);
        	//System.out.println(entityplayer.getEyeHeight());
        	if (entityplayer.username.contains("fakePlayer")) ent.posY -= 1.5F;
        	ent.setPosition(ent.posX, ent.posY, ent.posZ);
            world.spawnEntityInWorld(ent);
        }
        return itemstack;
    }
}
