package net.tropicraft.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerFoliage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCoral extends ItemBlock
{
    public ItemCoral(int par1)
    {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1 | 6;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public int getIconFromDamage(int par1)
    {
        return Block.leaves.getBlockTextureFromSideAndMetadata(0, par1);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromDamage(int par1, int par2)
    {
        return (par1 & 1) == 1 ? ColorizerFoliage.getFoliageColorPine() : ((par1 & 2) == 2 ? ColorizerFoliage.getFoliageColorBirch() : ColorizerFoliage.getFoliageColorBasic());
    }

    public String getItemNameIS(ItemStack par1ItemStack)
    {
        int var2 = par1ItemStack.getItemDamage();

        if (var2 < 0 || var2 >= BlockLeaves.LEAF_TYPES.length)
        {
            var2 = 0;
        }

        return super.getItemName() + "." + BlockLeaves.LEAF_TYPES[var2];
    }
}