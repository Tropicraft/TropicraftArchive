package net.tropicraft.items;

import net.minecraft.item.ItemBucket;
import net.tropicraft.mods.TropicraftMod;

public class ItemTropicraftBucket extends ItemBucket {

    public ItemTropicraftBucket(int i, int j) {
        super(i, j);
        this.setTextureFile("/tropicalmod/tropiitems.png");
        this.setCreativeTab(TropicraftMod.tabMisc);
    }
}