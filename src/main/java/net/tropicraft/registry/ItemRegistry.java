package net.tropicraft.registry;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.tropicraft.Tropicraft;
import net.tropicraft.item.ItemTropicraftFood;
import net.tropicraft.item.ItemTropicsOre;

public class ItemRegistry extends TropicraftRegistry {

	// Ore gems
	public static Item azurite, eudialyte, zircon;
	
	// Food
	public static Item grapefruit, lemon, lime, orange;

	/**
	 * Register items in preInit
	 */
	public static void init() {
		azurite = registerItem(new ItemTropicsOre(), "azurite");
		eudialyte = registerItem(new ItemTropicsOre(), "eudialyte");
		zircon = registerItem(new ItemTropicsOre(), "zircon");
		
		grapefruit = registerItem(new ItemTropicraftFood(2, 0.2F), "grapefruit");
		lemon = registerItem(new ItemTropicraftFood(2, 0.2F), "lemon");
		lime = registerItem(new ItemTropicraftFood(2, 0.2F), "lime");
		orange = registerItem(new ItemTropicraftFood(2, 0.2F), "orange");
	}
	
	private static Item registerItem(Item item, String name) {
		item.setUnlocalizedName(getNamePrefixed(name));
		
		GameRegistry.registerItem(item, name);
		Tropicraft.proxy.registerItemVariantModel(item, name, 0);
		
		return item;
	}
}
