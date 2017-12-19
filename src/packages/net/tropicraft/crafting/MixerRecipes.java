package net.tropicraft.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.tropicraft.drinks.Drink;
import net.tropicraft.drinks.Ingredient;
import net.tropicraft.drinks.MixerRecipe;
import net.tropicraft.drinks.MixerRecipeRegistry;

public class MixerRecipes {

	public MixerRecipes() {
		
	}
	
	public static void addMixerRecipes() {
		registerMixerRecipe(Drink.limeade, Ingredient.limeJuice, Ingredient.sugar);
		registerMixerRecipe(Drink.caipirinha, Ingredient.limeJuice, Ingredient.sugarcane);
		registerMixerRecipe(Drink.orangeade, Ingredient.orangeJuice, Ingredient.sugar);
		registerMixerRecipe(Drink.lemonade, Ingredient.lemonJuice, Ingredient.sugar);
		registerMixerRecipe(Drink.blackCoffee, Ingredient.roastedCoffeeBean, Ingredient.waterBucket);
	}

	/**
	 * Helper method for registering a mixer recipe
	 * @param result Result of the mixer recipe to be registered
	 * @param ingredients Ingredients of the mixer recipe to be registered
	 */
	private static void registerMixerRecipe(Drink result, Ingredient...ingredients) {
		MixerRecipeRegistry.getInstance().registerRecipe(new MixerRecipe(result, ingredients));
	}

}
