package net.tropicraft.cojodungeon;

import net.minecraft.item.ItemStack;

public class Piece {

	public static Piece emptyPiece = new Piece(0);
	
	/**
	 * Damage value used in the ItemStack of this piece
	 */
	private int itemDamage;
	
	public Piece(int itemDamage) {
		this.itemDamage = itemDamage;
	}
	
	public ItemStack getItemStack() {
	//TODO	return new ItemStack(TropicraftMod.itemPiece, 1, itemDamage);
		return null;
	}

}
