package tropicraft.encyclopedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tropicraft.TickHandlerClient;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class NigelJournal extends TropicalBook {

    /*
     * A mapping of page names to each item that should be included in the
     * Encyclopedia
     */
    private HashMap<String, List<ItemStack>> itemEntries = new HashMap<String, List<ItemStack>>();
	
	public NigelJournal(String savedDataFile, String contentsFile,
			String outsideTex, String insideTex) {
		super(savedDataFile, contentsFile, outsideTex, insideTex);
	}
	
	public boolean getHasPage(int pageID) {
		return TickHandlerClient.pagesCollected.hasKey("p" + pageID);
	}
	
    /*
     * Adds an item name to ItemStack mapping for this encyclopedia. Multiple
     * items can be registered with the same name, so that having any of them
     * will make the given page visible
     * Note: the item name should match the page name given in the text file
     */
    public void includeItem(String itemname, ItemStack item) {
        if (!itemEntries.containsKey(itemname)) {
            itemEntries.put(itemname, new ArrayList<ItemStack>());
        }
        itemEntries.get(itemname).add(item);
    }

	@Override
	public void updatePagesFromInventory(InventoryPlayer inv) {
		for (ItemStack is : inv.mainInventory) {
            if (is != null) {
                ItemStack comparison = new ItemStack(is.getItem(), 1, is.getItemDamage());
                for (String entry : itemEntries.keySet()) {
                    if (!isPageVisible(entry)) {
                        List<ItemStack> itemsInBook = itemEntries.get(entry);
                        for (ItemStack itemInBook : itemsInBook) {
                            if (ItemStack.areItemStacksEqual(itemInBook, comparison)) {
                                markPageAsNewlyVisible(entry);
                            }
                        }
                    }
                }
            }
        }
	}
	
	@Override
    public boolean hasIndexIcons() {
        return false;
    }

    @Override
    public ItemStack getPageItemStack(int page) {
        if (page >= 0 && page < getPageCount()) {
            List<ItemStack> items = itemEntries.get(getPageName(page));
            if (items != null && !items.isEmpty()) {
                return items.get(0);
            }
        }
        return null;
    }
    
    public String getPageTitleNotVisible(int i) {
        return "";
    }

}
