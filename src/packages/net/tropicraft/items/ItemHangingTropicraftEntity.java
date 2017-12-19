package net.tropicraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.tropicraft.entities.EntityHangingTropicraft;
import net.tropicraft.mods.TropicraftMod;

public class ItemHangingTropicraftEntity extends ItemHangingEntity {

	private final Class hangingEntityClass;
	private boolean shouldDropContents;
	
	public ItemHangingTropicraftEntity(int par1, Class par2Class, boolean shouldDropContents) {
		super(par1, par2Class);
        this.hangingEntityClass = par2Class;
        this.setCreativeTab(TropicraftMod.tabDecorations);
        this.setTextureFile("/tropicalmod/tropiitems.png");
        this.shouldDropContents = shouldDropContents;		//koa use this
	}

	/**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 == 0)
        {
            return false;
        }
        else if (par7 == 1)
        {
            return false;
        }
        else
        {
            int var11 = Direction.vineGrowth[par7];
            EntityHangingTropicraft var12 = this.createHangingEntity(par3World, par4, par5, par6, var11);

            if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
            {
                return false;
            }
            else
            {
                if (var12 != null && var12.onValidSurface())
                {
                    if (!par3World.isRemote)
                    {
                        par3World.spawnEntityInWorld(var12);
                    }

                    --par1ItemStack.stackSize;
                }

                return true;
            }
        }
    }

    /**
     * Create the hanging entity associated to this item.
     */
    private EntityHangingTropicraft createHangingEntity(World par1World, int par2, int par3, int par4, int par5)
    {
        return new EntityHangingTropicraft(par1World, par2, par3, par4, par5, shouldDropContents);
    }
}
