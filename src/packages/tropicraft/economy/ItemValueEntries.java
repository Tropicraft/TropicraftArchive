package tropicraft.economy;

import tropicraft.items.TropicraftItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemValueEntries {
	
	public static ItemStack currency;

	public static void initEconomy() {
		addBuyableEntries();
		addValuedEntries();
		currency = new ItemStack(TropicraftItems.shells, 1, 1);
	}
	
	public static void addBuyableEntries() {
		//Buyables
		ItemValues.addEntryBuyable(new ItemStack(Item.fishRaw), 1);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.freshMarlin), 3);
		//missing z gem
		//missing e gem
		//missing a gem
		//missing spear
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.scaleChestplate), 15);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.scaleLeggings), 10);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.scaleHelm), 5);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.scaleBoots), 5);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.coconutBomb, 3), 15);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.leafBall, 15), 20);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.blowGun), 20);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.dart, 10), 30, true);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.fishingNet), 15);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.cocktail), 5);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.snorkel), 15);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.flippers), 15);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.recordEasternIsles), 1);
		ItemValues.addEntryBuyable(new ItemStack(TropicraftItems.recordTradeWinds), 1);
		
		/*addValuedItem(Item.fishRaw, 1);
		addValuedItem(TropicraftMod.zirconItem, 5);
		addValuedItem(TropicraftMod.eudialyteItem, 10);
		addValuedItem(TropicraftMod.azuriteItem, 15);
		
		addValuedItem(TropicraftMod.bambooSpear, 10);
		addValuedItem(TropicraftMod.scaleChestplate, 15);
		addValuedItem(TropicraftMod.scaleLeggings, 10);
		addValuedItem(TropicraftMod.scaleHelm, 5);
		addValuedItem(TropicraftMod.scaleBoots, 5);
		
		addValuedItem(TropicraftMod.coconutBomb, 15, 3);
		addValuedItem(TropicraftMod.leafBall, 20, 15);
		addValuedItem(TropicraftMod.blowGun, 20);
		addValuedItem(TropicraftMod.paraDart, 30, 10);
		addValuedItem(TropicraftMod.fishingNet, 15);
		addValuedItem(TropicraftMod.bambooSpear, 10);
		
		addValuedItem(TropicraftMod.bambooMugFull, 5);
		
		addValuedItem(TropicraftMod.snorkel, 15);
		addValuedItem(TropicraftMod.flippers, 15);
		
		addValuedItem(TropicraftMod.froxEasternIsles, 50);
		addValuedItem(TropicraftMod.froxTradeWinds, 50);*/
	}
	
	public static void addValuedEntries() {

		//Non-Buyables
		
		for (int i = 0; i < TropicraftItems.getShellDisplayNames().length; i++) ItemValues.addEntry(new ItemStack(TropicraftItems.shells, 1, i), 1, true);
		
		/*addValuedItem(TropicraftMod.shellCommon1, 1, false);
		addValuedItem(TropicraftMod.shellCommon2, 1, false);
		addValuedItem(TropicraftMod.shellCommon3, 1, false);
		addValuedItem(TropicraftMod.turtleShell, 1, false);
		addValuedItem(TropicraftMod.shellRare1, 3, false);*/
		
	}
	
}
