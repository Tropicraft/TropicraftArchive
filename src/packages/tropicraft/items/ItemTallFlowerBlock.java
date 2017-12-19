package tropicraft.items;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemTallFlowerBlock extends ItemBlock {

	private static final String[] flowerNames = new String[]{"Pineapple", "Iris"};
	
	public ItemTallFlowerBlock(int par1) {
		super(par1);
	}
	
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
	@Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return "tallFlowerItem" + "." + flowerNames[par1ItemStack.getItemDamage() == 0 ? 0 : 1];
    }
	
    /**
     * Returns the unlocalized name of this item.
     */
	@Override
    public String getUnlocalizedName()
    {
        return "tallFlowerItem";
    }
}
