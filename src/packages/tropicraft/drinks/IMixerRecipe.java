package tropicraft.drinks;

import java.util.List;

import net.minecraft.item.ItemStack;

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
