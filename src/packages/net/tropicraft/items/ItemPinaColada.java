package net.tropicraft.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;

public class ItemPinaColada extends ItemTropicraftDrink {

    public int numDrank = 0;
    private int potionId;
    private int potionDuration;
    private int potionAmplifier;
    private float potionEffectProbability;
    
    private int drunkCounter;

    public ItemPinaColada(int i, int j) {
        super(i, j, false);
        drunkCounter = -1;
        maxStackSize = 1;
        this.setAlwaysEdible();
        this.setCreativeTab(TropicraftMod.tabFood);
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int par4, boolean par5) {
        if (this.drunkCounter >= 0) {
            drunkCounter--;
        }
    }

    @Override
    public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        itemstack.stackSize--;
        entityplayer.getFoodStats().addStats(this);

        entityplayer.setEntityHealth(entityplayer.getHealth() + 6);
        numDrank++;
        drunkCounter = 1800;

        world.playSoundAtEntity(entityplayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

        if (!world.isRemote && potionId > 0 && world.rand.nextFloat() < potionEffectProbability) {
            entityplayer.addPotionEffect(new PotionEffect(potionId, potionDuration * 20, potionAmplifier));
        }
        if (itemstack.stackSize <= 0) {
            return new ItemStack(TropicraftMod.bambooMugEmpty);
        } else {
            return itemstack;
        }
    }

    @Override
    public ItemFood setPotionEffect(int i, int j, int k, float f) {
        potionId = i;
        potionDuration = j;
        potionAmplifier = k;
        potionEffectProbability = f;
        return this;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.drink;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
        return itemstack;
    }
}