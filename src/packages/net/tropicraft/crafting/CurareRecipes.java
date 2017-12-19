package net.tropicraft.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.tropicraft.darts.Curare;
import net.tropicraft.darts.CurareMix;
import net.tropicraft.darts.CurareMixRegistry;
import net.tropicraft.items.ItemBlowGun;
import net.tropicraft.items.ItemCurare;
import net.tropicraft.mods.TropicraftMod;

public class CurareRecipes {

	public CurareRecipes() {
		
	}
	
	public static void addCurareMixerRecipes() {
		CurareMixRegistry.getInstance().registerRecipe(new CurareMix(Curare.confusion, 
				flower(7), flower(8), flower(9)
		));
		
		CurareMixRegistry.getInstance().registerRecipe(new CurareMix(Curare.harm, 
				flower(7), flower(7), flower(7), flower(7), flower(7), flower(7), flower(8), flower(9), flower(2)
		));
		
		CurareMixRegistry.getInstance().registerRecipe(new CurareMix(Curare.hunger, 
				flower(0), flower(1), flower(2)
		));
		
		CurareMixRegistry.getInstance().registerRecipe(new CurareMix(Curare.hunger, 
				flower(0), flower(1), flower(3)
		));
		
		CurareMixRegistry.getInstance().registerRecipe(new CurareMix(Curare.hunger, 
				flower(0), flower(1), flower(4)
		));
		
		CurareMixRegistry.getInstance().registerRecipe(new CurareMix(Curare.moveSlowdown, 
				flower(7), flower(1), flower(2)
		));
		
		CurareMixRegistry.getInstance().registerRecipe(new CurareMix(Curare.poison, 
				flower(7), flower(8), flower(4), flower(7)
		));
		
		CurareMixRegistry.getInstance().registerRecipe(new CurareMix(Curare.poison, 
				flower(7), flower(9), flower(4), flower(7)
		));
		
		CurareMixRegistry.getInstance().registerRecipe(new CurareMix(Curare.weakness, 
				flower(1), flower(3), flower(4)
		));
	}
	
	private static ItemStack flower(int damage) {
		return new ItemStack(TropicraftMod.flowerCollection1, 1, damage);
	}
	
	
	public static void addRecipes(boolean isServer) {		
		
		//register all blow types
		for (int damage = 0; damage < ItemBlowGun.dartNames.length; damage++) {
			createRecipe(isServer, true, new ItemStack(TropicraftMod.blowGun, 1, damage), new Object[]{
				"X  ", " I ", "  X",
				'X', TropicraftMod.bambooItem,
				'I', new ItemStack(TropicraftMod.curare, 1, damage)
			});
		}
		
		//keep classic paralysis dart recipe, use for poison frog skin for now :/
		createRecipe(isServer, true, new ItemStack(TropicraftMod.paraDart, 4), new Object[]{
			"XI", " C",
			'X', Item.ingotIron,
			'I', TropicraftMod.poisonSkin,
			'C', Item.feather
		});

		createRecipe(isServer, true, new ItemStack(TropicraftMod.paraDart, 4), new Object[]{
			"X ", "IC",
			'X', Item.ingotIron,
			'I', TropicraftMod.poisonSkin,
			'C', Item.feather
		});
		
		//register all types of curare, including paralysis
		for (int damage = 0; damage < ItemCurare.effectNames.length; damage++) {
			createRecipe(isServer, true, new ItemStack(TropicraftMod.paraDart, 4, damage), new Object[]{
				"XI", " C",
				'X', Item.ingotIron,
				'I', new ItemStack(TropicraftMod.curare, 1, damage),
				'C', Item.feather
			});

			createRecipe(isServer, true, new ItemStack(TropicraftMod.paraDart, 4, damage), new Object[]{
				"X ", "IC",
				'X', Item.ingotIron,
				'I', new ItemStack(TropicraftMod.curare, 1, damage),
				'C', Item.feather
			});
		}
	}
	
	private static void createRecipe(boolean isServer, boolean addToEncyclopedia, ItemStack result, Object[] recipe) {
		CraftingTropicraft.createRecipe(isServer, addToEncyclopedia, result, recipe);
	}	
}
