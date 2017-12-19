package net.tropicraft.koa;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.tropicraft.mods.TropicraftMod;

import java.util.ArrayList;
import java.util.HashMap;

public class ValuedItems {

	//Static managers
	public static boolean hasInit = false;
	public static ArrayList<ValuedItems> items = new ArrayList();
	public static ArrayList<ValuedItems> buyables = new ArrayList();
	public static HashMap<Item, Integer> lookupPrice = new HashMap();
	public static HashMap<Item, Integer> lookupCount = new HashMap();
	public static int priceBase = 50;
	
	//Contents
	public Item itemID;
	public int cost;
	public int count;
	public boolean buyable;
	
	public ValuedItems(Item parItem, int parCost, int parCount, boolean parBuyable) {
		this.itemID = parItem;
		this.cost = parCost;
		this.count = parCount;
		this.buyable = parBuyable;
	}
	
	public static int count() {
		return items.size();
	}
	
	public static void addValuedItem(Item parItem, int parCost) {
		addValuedItem(parItem, parCost, 1, true);
	}
	
	public static void addValuedItem(Item parItem, int parCost, int parCount) {
		addValuedItem(parItem, parCost, parCount, true);
	}
	
	public static void addValuedItem(Item parItem, int parCost, boolean parBuyable) {
		addValuedItem(parItem, parCost, 1, parBuyable);
	}
	
	public static void addValuedItem(Item parItem, int parCost, int parCount, boolean parBuyable) {
		ValuedItems v = new ValuedItems(parItem, parCost, parCount, parBuyable);
		items.add(v);
		lookupPrice.put(parItem, parCost);
		lookupCount.put(parItem, parCount);
		if (parBuyable) {
			buyables.add(v);
		}
	}
	
	public static Item getItem(int index) {
		if (!hasInit) initItems();
		return items.get(index).itemID;
	}
	
	public static Item getItemBuyable(int index) {
		if (!hasInit) initItems();
		return buyables.get(index).itemID;
	}
	
	public static int getItemCostByIndex(int index) {
		if (!hasInit) initItems();
		return items.get(index).cost;
	}
	
	public static int getBuyableItemCostByIndex(int index) {
		if (!hasInit) initItems();
		return buyables.get(index).cost;
	}
	
	public static int getItemCost(Item item) {
		if (!hasInit) initItems();
		return lookupPrice.get(item) != null ? lookupPrice.get(item) : 0;
	}
	
	public static int getBuyItemCount(Item item) {
		if (!hasInit) initItems();
		return lookupCount.get(item);
	}
	
	public static void initItems() {
		if (!hasInit) {
			hasInit = true;
			
			//Non-Buyables
			addValuedItem(TropicraftMod.shellCommon1, 1, false);
			addValuedItem(TropicraftMod.shellCommon2, 1, false);
			addValuedItem(TropicraftMod.shellCommon3, 1, false);
			addValuedItem(TropicraftMod.turtleShell, 1, false);
			addValuedItem(TropicraftMod.shellRare1, 3, false);
			
			//Buyables
			addValuedItem(Item.fishRaw, 1);
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
			addValuedItem(TropicraftMod.froxTradeWinds, 50);
			
			//addBuyItem(new ItemStack(ZCItems.itemM1911), (int)(priceBase*1.0));
			
		}
	}
}
