package net.tropicraft.drinks;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.tropicraft.items.ItemDrink;

public interface IMixerRecipe {
	/**
	 * @return List of ingredients for this recipe in ItemStack form
	 */
	Ingredient[] getIngredients();
	
	/**
	 * @return Result possible when all the ingredients are in the mixer
	 */
	Drink getCraftingResult();
	
}
