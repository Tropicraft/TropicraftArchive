package net.tropicraft.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.tropicraft.Tropicraft;
import net.tropicraft.item.ItemTropicraftFood;
import net.tropicraft.item.ItemTropicraftHoe;
import net.tropicraft.item.ItemTropicsOre;

public class ItemRegistry extends TropicraftRegistry {

	// Ore gems
	public static Item azurite, eudialyte, zircon;
	
	// Food
	public static Item grapefruit, lemon, lime, orange;
	
    public static ToolMaterial materialZirconTools = EnumHelper.addToolMaterial("zircon", 2, 200, 6.5F, 2.5F, 14);
    public static ToolMaterial materialEudialyteTools = EnumHelper.addToolMaterial("eudialyte", 2, 750, 5.5F, 1.5F, 14);
    public static ToolMaterial materialZirconiumTools = EnumHelper.addToolMaterial("zirconium", 3, 1800, 8.5F, 3.5F, 10);
	
	// Tools
    public static Item hoeEudialyte;
    public static Item hoeZircon;

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
		
		hoeEudialyte = registerItem(new ItemTropicraftHoe(materialEudialyteTools), "hoe_eudialyte");
		hoeZircon = registerItem(new ItemTropicraftHoe(materialZirconTools), "hoe_zircon");
	}
	
	private static Item registerItem(Item item, String name) {
		item.setUnlocalizedName(getNamePrefixed(name));
		
		GameRegistry.registerItem(item, name);
		Tropicraft.proxy.registerItemVariantModel(item, name, 0);
		
		return item;
	}
}
