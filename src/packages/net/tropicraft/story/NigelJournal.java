package net.tropicraft.story;

import net.minecraft.entity.player.InventoryPlayer;
import net.tropicraft.encyclopedia.TropicalBook;

public class NigelJournal extends TropicalBook {

	public NigelJournal(String savedDataFile, String contentsFile,
			String outsideTex, String insideTex, boolean savePerWorld) {
		super(savedDataFile, contentsFile, outsideTex, insideTex, savePerWorld);
	}

	@Override
	public void updatePagesFromInventory(InventoryPlayer inv) {
		// TODO Auto-generated method stub

	}

}
