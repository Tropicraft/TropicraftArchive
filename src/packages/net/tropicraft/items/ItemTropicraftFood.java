package net.tropicraft.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.tropicraft.mods.TropicraftMod;

public class ItemTropicraftFood extends ItemFood {

    public ItemTropicraftFood(int i, int j, float f, boolean flag) {
        super(i, j, f, flag);
        setTextureFile("/tropicalmod/tropiitems.png");
        this.setCreativeTab(TropicraftMod.tabFood);
    }

    public ItemTropicraftFood(int i, int j, boolean flag) {
        super(i, j, flag);
        setTextureFile("/tropicalmod/tropiitems.png");
        this.setCreativeTab(CreativeTabs.tabFood);
    }
}