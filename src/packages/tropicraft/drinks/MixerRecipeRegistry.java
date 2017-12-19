package tropicraft.drinks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tropicraft.blocks.tileentities.TileEntityEIHMixer;
import tropicraft.items.ItemCocktail;
import tropicraft.items.TropicraftItems;
import tropicraft.Tropicraft;

public class MixerRecipeRegistry {

	/**
	 * Singleton instance of MixerRecipeManager
	 */
	private static MixerRecipeRegistry instance = null;

	/**
	 * List of recipes for use in the mixer
	 */
	private List<MixerRecipe> recipes = new LinkedList<MixerRecipe>();

	private MixerRecipeRegistry() {}

	/**
	 * Getter for the singleton MixerRecipeManager instance
	 * @return singleton MixerRecipeManager instance
	 */
	public static MixerRecipeRegistry getInstance() {
		if (instance == null) {
			instance = new MixerRecipeRegistry();
		}

		return instance;
	}

	/**
	 * Register a recipe with the mixer
	 * @param recipe MixerRecipe instance to register
	 */
	public void registerRecipe(MixerRecipe recipe) {
		recipes.add(recipe);
	}

	/**
	 * Returns true if the ItemStack's Item is used in any of the recipes, that way we don't get weird recipes
	 * @param item ItemStack to check for validity
	 * @return true if the ItemStack sent in is used in any of the registered recipes
	 */
	public boolean isRegisteredIngredient(ItemStack item) {
		if (item.itemID == TropicraftItems.cocktail.itemID) {
			return true; // assuming we didn't put garbage in
		}
		
		for (MixerRecipe recipe : recipes) {
			for (Ingredient i : recipe.getIngredients()) {
				if (ItemStack.areItemStacksEqual(i.getIngredient(), item)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isRegisteredIngredient(int itemId) {
		return isRegisteredIngredient(new ItemStack(itemId, 1, 0));
	}

	public ItemStack getResult(ItemStack[] ingredients) {
		for (MixerRecipe recipe : recipes) {
			if (ItemStack.areItemStacksEqual(recipe.getIngredients()[0].getIngredient(), ingredients[0]) && ItemStack.areItemStacksEqual(recipe.getIngredients()[1].getIngredient(), ingredients[1])) {
				return ItemCocktail.makeCocktail(recipe);
			}
			if (ItemStack.areItemStacksEqual(recipe.getIngredients()[0].getIngredient(), ingredients[1]) && ItemStack.areItemStacksEqual(recipe.getIngredients()[1].getIngredient(), ingredients[0])) {
				return ItemCocktail.makeCocktail(recipe);
			}
		}
		
		List<Ingredient> is = new ArrayList<Ingredient>();
		is.addAll(TileEntityEIHMixer.listIngredients(ingredients[0]));
		is.addAll(TileEntityEIHMixer.listIngredients(ingredients[1]));
		Collections.sort(is);
		
		return ItemCocktail.makeCocktail(is.toArray(new Ingredient[is.size()]));
	}
	
	public List<MixerRecipe> getRecipes() {
		return recipes;
	}

}
