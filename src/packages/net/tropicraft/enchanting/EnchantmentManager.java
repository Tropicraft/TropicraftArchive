package net.tropicraft.enchanting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.tropicraft.mods.TropicraftMod;

public final class EnchantmentManager {
	
	private static ArrayList<Integer> enchantableList = new ArrayList<Integer>();
	private static HashMap<Integer, List<TropicraftEnchantment>> enchantableMap = new HashMap<Integer, List<TropicraftEnchantment>>();
	
	private static ArrayList<Integer> ingredientIDList = new ArrayList<Integer>();
	
//	public static final TropicraftEnchantment fastSwim = new TropicraftEnchantmentFastSwim(TropicraftMod.fastSwimEnchantId, 10);
	
//	public static final TropicraftEnchantment test = new TropicraftEnchantmentTest(100, 10);
	
//	public static final TropicraftEnchantment fire = new TropicraftEnchantmentFire(101, 10);
	
//	public static final TropicraftEnchantment sinkhole = new TropicraftEnchantmentSinkhole(102, 10);
	
//	public static final TropicraftEnchantment fastSwimming = new TropicraftEnchantmentSwimming(103, 10);
	
	public EnchantmentManager() 
	{
		
	}
	
	public static Set<Integer> getEnchantableIDList()
	{
		return enchantableMap.keySet();
	}
	
	public static void initEnchantables()
	{
/*		addMapping(TropicraftMod.axeEudialyte, new TropicraftEnchantment[]{test});
		
		addMapping(TropicraftMod.axeZircon, new TropicraftEnchantment[]{test});	
		
		addMapping(TropicraftMod.pickaxeEudialyte, new TropicraftEnchantment[]{test, fire});
		
		addMapping(TropicraftMod.pickaxeZircon, new TropicraftEnchantment[]{test, fire});
		
		addMapping(TropicraftMod.swordEudialyte, new TropicraftEnchantment[]{test, fire});
		
		addMapping(TropicraftMod.swordZircon, new TropicraftEnchantment[]{test, fire});
		
		addIngredient(TropicraftMod.ashenMask);
		addIngredient(TropicraftMod.flowerCollection1);
		addIngredient(TropicraftMod.coconutItem);
		addIngredient(TropicraftMod.pearlItem);	*/
	}
	
	private static void addIngredient(Object i)
	{
		if(i instanceof Item)
			ingredientIDList.add(((Item)i).shiftedIndex);
		else
			if(i instanceof Block)
					ingredientIDList.add(((Block)i).blockID);
	}
	
	private static void addMapping(Integer itemID, TropicraftEnchantment[] enchants)
	{
		enchantableMap.put(itemID, getEnchantFor(enchants));
	}
	
	private static void addMapping(Item item, TropicraftEnchantment[] enchants)
	{
		enchantableMap.put(item.shiftedIndex, getEnchantFor(enchants));
	}
	
	private static void addMapping(Block block, TropicraftEnchantment[] enchants)
	{
		enchantableMap.put(block.blockID, getEnchantFor(enchants));
	}
	
	private static List<TropicraftEnchantment> getEnchantFor(TropicraftEnchantment[] teArray)
	{
		return Arrays.asList(teArray);
	}
}
