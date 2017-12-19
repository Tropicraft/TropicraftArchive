package net.tropicraft.darts;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.tropicraft.drinks.Drink;
import net.tropicraft.drinks.Ingredient;

public class CurareMix {

	private Curare result;
	
	private ItemStack[] ingredients;
	
	public CurareMix(Curare result, ItemStack...ingredients) {
		this.result = result;
		this.ingredients = ingredients;
		
	//	Arrays.sort(this.ingredients);
	}
	
	/**
	 * @return All ingredients used in this recipe
	 */
	public ItemStack[] getIngredients() {
		return ingredients;
	}
	
	
	public Curare getResult() {
		return result;
	}
	
	public int[] getSortedDamageVals() {
		int[] temp = new int[ingredients.length];
		
		int count = 0;
		
		for (ItemStack ing : ingredients) {
			temp[count] = ing.getItemDamage();
			count++;
		}
		
		Arrays.sort(temp);
		
		return temp;
	}
	
	@Override
	public String toString() {
		return getResult().toString();
	}

}
