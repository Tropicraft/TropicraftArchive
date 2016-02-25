package net.tropicraft.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.tropicraft.Tropicraft;
import net.tropicraft.item.ItemTropicraftAxe;
import net.tropicraft.item.ItemTropicraftFood;
import net.tropicraft.item.ItemTropicraftPickaxe;
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
    public static Item pickaxeEudialyte;
    public static Item pickaxeZircon;
    public static Item shovelEudialyte;
    public static Item shovelZircon;
    public static Item axeEudialyte;
    public static Item axeZircon;
    public static Item swordEudialyte;
    public static Item swordZircon;

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
		
		hoeEudialyte = registerItem(new ItemHoe(materialEudialyteTools), "hoe_eudialyte");
		hoeZircon = registerItem(new ItemHoe(materialZirconTools), "hoe_zircon");
		pickaxeEudialyte = registerItem(new ItemTropicraftPickaxe(materialEudialyteTools), "pickaxe_eudialyte");
		pickaxeZircon = registerItem(new ItemTropicraftPickaxe(materialZirconTools), "pickaxe_zircon");
		shovelEudialyte = registerItem(new ItemSpade(materialEudialyteTools), "shovel_eudialyte");
		shovelZircon = registerItem(new ItemSpade(materialZirconTools), "shovel_zircon");
		axeEudialyte = registerItem(new ItemTropicraftAxe(materialEudialyteTools), "axe_eudialyte");
		axeZircon = registerItem(new ItemTropicraftAxe(materialZirconTools), "axe_zircon");
		swordEudialyte = registerItem(new ItemSword(materialEudialyteTools), "sword_eudialyte");
		swordZircon = registerItem(new ItemSword(materialZirconTools), "sword_zircon");
	}
	
	private static Item registerItem(Item item, String name) {
		item.setUnlocalizedName(getNamePrefixed(name));
		
		GameRegistry.registerItem(item, name);
		item.setCreativeTab(CreativeTabRegistry.tropicraftTab);
		Tropicraft.proxy.registerItemVariantModel(item, name, 0);
		
		return item;
	}
}
