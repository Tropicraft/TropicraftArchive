package net.tropicraft.items;

import net.minecraft.item.Item;
import net.tropicraft.mods.TropicraftMod;

public class ItemTropicraft extends Item {

    public ItemTropicraft(int i) {
        super(i);
        this.setTextureFile("/tropicalmod/tropiitems.png");
        this.setCreativeTab(TropicraftMod.tabMisc);
    }
}
